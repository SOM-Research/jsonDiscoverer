/*******************************************************************************
 * Copyright (c) 2008, 2013
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Javier Canovas (javier.canovas@inria.fr) 
 *******************************************************************************/

package fr.inria.atlanmod.discoverer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import coverage.util.CoverageCreator;

/**
 * Performs a composition between metamodels obtained from JSON files
 * This implementation does not depend on Xtext
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonComposer {
	/**
	 * Set of sources to compose
	 */
	private HashMap<String, JsonSource> sources;
	
	private HashMap<String, EClass> registry;
	private List<CoverageCreator> coverageCreators;
	
	HashMap<EAttribute, List<Object>> cacheValues;
	
	private final static Logger LOGGER = Logger.getLogger(JsonComposer.class.getName());

	public JsonComposer(List<JsonSource> sources) {
		if(sources == null) 
			throw new IllegalArgumentException("Sources cannot be null");
		else if(sources.size() == 0) 
			throw new IllegalArgumentException("At least 1 source is required to compose");

		// All the sources must include the metamodel
		this.sources = new HashMap<String, JsonSource>();
		for(JsonSource jsonSource : sources) {
			if(jsonSource.getMetamodel() == null) {
				// For those not including the metamodel, it is discovered
				JsonDiscoverer discoverer = new JsonDiscoverer();
				discoverer.discoverMetamodel(jsonSource);
			}
			this.sources.put(jsonSource.getName(), jsonSource); // TODO deep-clone?
		}
		this.cacheValues = new HashMap<EAttribute, List<Object>>();
	}
		
	/**
	 * Saves the coverage information
	 * 
	 * @param resultPath The path where the converage information will be saved
	 */
	public void saveCoverage(List<File> resultPaths) {
		if(resultPaths.size() == coverageCreators.size())
			for(int i = 0; i < coverageCreators.size(); i++) {
				CoverageCreator coverageCreator = coverageCreators.get(i); 
				coverageCreator.save(resultPaths.get(i));
			}
		else {
			throw new IllegalArgumentException("The size of the paths must match the size of coverage files");
		}
	}
	
	/**
	 * Compose the JSON files
	 * 
	 * @param resultingName The name for the resulting package metamodel
	 * @param resultPath The path where the resulting metamodel will be stored
	 * @return The resulting metamodel as EPackage
	 * @throws FileNotFoundException
	 */
	public EPackage compose(String resultingName, File resultPath) throws FileNotFoundException {
		EPackage finalPackage = compose(resultingName);
		saveEPackage(finalPackage, resultPath);
		return finalPackage;
	}
	
	/**
	 * Compose the JSON sources received when building the object
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public EPackage compose(String resultingName) throws FileNotFoundException  {
		// Creating the resulting metamodel
		EPackage finalPackage = EcoreFactory.eINSTANCE.createEPackage();
		finalPackage.setName(resultingName);
		finalPackage.setNsPrefix("composed" + resultingName.charAt(0));
		finalPackage.setNsURI("http://fr.inria.atlanmod/discovered/" + resultingName);

		// Initializing variables
		registry = new HashMap<String, EClass>();
		List<EReference> referencesToCheck = new ArrayList<EReference>();
		coverageCreators = new ArrayList<CoverageCreator>();
		for(JsonSource jsonSource : getSources()) {
			CoverageCreator coverageCreator = new CoverageCreator(jsonSource.getName(), jsonSource.getMetamodel(), finalPackage);

			for(EClassifier classifier : jsonSource.getMetamodel().getEClassifiers()) {
				if (classifier instanceof EClass) {
					EClass eClass = (EClass) classifier;
					EClass registryElement = registry.get(eClass.getName());
					if(registryElement == null) {
						EClass duplicatedEClass = duplicateEClass(eClass, coverageCreator);
						registry.put(eClass.getName(), duplicatedEClass);
						coverageCreator.createConceptMapping(eClass, duplicatedEClass);
						eClass = duplicatedEClass;
					} else {
						coverageCreator.createConceptMapping(eClass, registryElement);
						composeAttributes(registryElement, eClass, coverageCreator);
						composeReferences(registryElement, eClass, coverageCreator);
						eClass = registryElement;
					}
					for(EStructuralFeature otherFeature : eClass.getEStructuralFeatures()) 
						if (otherFeature instanceof EReference) 
							referencesToCheck.add((EReference) otherFeature);

				}
			}
			coverageCreators.add(coverageCreator);
		}

		for(EClass eClass : registry.values()) {
			finalPackage.getEClassifiers().add(eClass);
		}

		EClass unknown = EcoreFactory.eINSTANCE.createEClass();
		unknown.setName("Unknown");
		boolean unknownUsed = false;

		for(EReference reference : referencesToCheck) {
			EClassifier referredEClassifier = reference.getEType();
			if (referredEClassifier instanceof EClass) {
				EClass referredEClass = (EClass) referredEClassifier;
				EClass registryElement = registry.get(referredEClass.getName());
				if(registryElement == null) {
					reference.setEType(unknown);
					LOGGER.finer("Reference " + reference.getName() + " with unknown type");
					unknownUsed = true;
				} else {
					reference.setEType(registryElement);
					LOGGER.finer("Reference " + reference.getName() + " re-assigned");
				}
			}
		}

		if(unknownUsed) {
			finalPackage.getEClassifiers().add(unknown);
		}

		return finalPackage;
	}

	private void composeAttributes(EClass existingClass, EClass otherClass, CoverageCreator coverageCreator) throws FileNotFoundException  {
		for(EStructuralFeature otherFeature : otherClass.getEStructuralFeatures()) {
			if (otherFeature instanceof EAttribute) {
				EAttribute otherAttribute = (EAttribute) otherFeature;
				EStructuralFeature existingFeature = existingClass.getEStructuralFeature(otherAttribute.getName());
				if(existingFeature == null) {
					EAttribute similarAttribute = lookForSimilarAttribute(existingClass, otherAttribute, coverageCreator);
					if(similarAttribute == null) {
						EAttribute newAttribute = duplicateAttribute(otherAttribute);
						existingClass.getEStructuralFeatures().add(newAttribute);
						LOGGER.finer("Attribute " + newAttribute.getName() + " added");
						existingFeature = newAttribute;
					} else {
						LOGGER.finer("Attribute similar found: " + similarAttribute.getName());
						existingFeature = similarAttribute;
					}
				} else {
					existingFeature.setEType(EcorePackage.Literals.ESTRING);
					LOGGER.finer("Attribute " + existingFeature.getName() + " refined to String");
				}				
				coverageCreator.createAttMapping(otherAttribute, (EAttribute) existingFeature);
			}
		}

	}

	private void composeReferences(EClass existingClass, EClass otherClass, CoverageCreator coverageCreator) {
		for(EStructuralFeature otherFeature : otherClass.getEStructuralFeatures()) {
			if (otherFeature instanceof EReference) {
				EReference otherReference = (EReference) otherFeature;
				EStructuralFeature existingFeature = existingClass.getEStructuralFeature(otherReference.getName());
				if(existingFeature == null) {
					EReference newReference = duplicateReference(otherReference);
					existingClass.getEStructuralFeatures().add(newReference);
					LOGGER.finer("Reference " + newReference.getName() + " added");
					existingFeature = newReference;
					coverageCreator.createRefMapping(otherReference, (EReference) existingFeature);
				} 			
			}
		}

	}

	private EAttribute duplicateAttribute(EAttribute otherAttribute) {
		EAttribute newAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		newAttribute.setName(otherAttribute.getName());
		newAttribute.setEType(otherAttribute.getEType());
		newAttribute.setUpperBound(otherAttribute.getUpperBound());
		newAttribute.setLowerBound(otherAttribute.getLowerBound());
		return newAttribute;
	}

	private EReference duplicateReference(EReference otherReference) {
		EReference newReference = EcoreFactory.eINSTANCE.createEReference();
		newReference.setName(otherReference.getName());
		newReference.setEType(otherReference.getEType());
		newReference.setUpperBound(otherReference.getUpperBound());
		newReference.setLowerBound(otherReference.getLowerBound());
		return newReference;		
	}

	private EClass duplicateEClass(EClass otherClass, CoverageCreator coverageCreator) throws FileNotFoundException  {
		EClass newClass = EcoreFactory.eINSTANCE.createEClass();
		newClass.setName(otherClass.getName());
		newClass.setAbstract(otherClass.isAbstract());

		for(EStructuralFeature otherFeature : otherClass.getEStructuralFeatures()) {
			if (otherFeature instanceof EReference) {
				EReference duplicatedReference = duplicateReference((EReference) otherFeature);
				newClass.getEStructuralFeatures().add(duplicatedReference);
				coverageCreator.createRefMapping((EReference) otherFeature, duplicatedReference);	
			} else if (otherFeature instanceof EAttribute) {
				EAttribute duplicatedAttribute = duplicateAttribute((EAttribute) otherFeature);
				newClass.getEStructuralFeatures().add(duplicatedAttribute);
				coverageCreator.createAttMapping((EAttribute) otherFeature, duplicatedAttribute);
				cacheValues.put(duplicatedAttribute, getJSONValues(duplicatedAttribute.getName(), coverageCreator.getName()));
			}
		}
		return newClass;
	}


	private EAttribute lookForSimilarAttribute(EClass existingClass, EAttribute otherAttribute, CoverageCreator coverageCreator) throws FileNotFoundException  {
		List<Object> jsonValues = getJSONValues(otherAttribute.getName(), coverageCreator.getName());

		Iterator<EAttribute> it = cacheValues.keySet().iterator();
		while(it.hasNext()) {
			EAttribute eAttribute = it.next();
			for(Object o : cacheValues.get(eAttribute)) {
				if (o instanceof String) {
					String stringValue = (String) o;
					for(Object o2 : jsonValues) {
						if (o2 instanceof String) {
							String stringValue2 = (String) o2;
							if(stringValue.equals(stringValue2)) 
								return eAttribute;
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Gets the values for a particular key
	 * 
	 * @param name
	 * @param sourceName
	 * @return
	 * @throws FileNotFoundException
	 */
	private List<Object> getJSONValues(String name, String sourceName) throws FileNotFoundException {
		if(name == null || name.equals("")) 
			throw new IllegalArgumentException("Name cannot be null or empty");
		if(sourceName == null || sourceName.equals("")) 
			throw new IllegalArgumentException("sourceName cannot be null or empty");
		
		List<Object> result = new ArrayList<Object>();
		List<JsonElement> jsonDefs = sources.get(sourceName).getJsonDefs();
		if(jsonDefs.size() == 0) return result;
		JsonElement rootElement = jsonDefs.get(0); // TODO Consider all of them!
		
		List<JsonObject> elements = new ArrayList<JsonObject>();
		if (rootElement.isJsonArray()) {
			LOGGER.finer("Several objects found");
			for(int i = 0; i < rootElement.getAsJsonArray().size(); i++)
				if(rootElement.getAsJsonArray().get(i).isJsonObject())
					elements.add(rootElement.getAsJsonArray().get(i).getAsJsonObject());
		} else if(rootElement.isJsonObject()) {
			LOGGER.finer("Only one object found");
			elements.add(rootElement.getAsJsonObject());
		} else {
			LOGGER.finest("The root element was " + rootElement.getClass().getName());
			LOGGER.finest("It is: " + rootElement.getAsString());
		}
		
		for(JsonObject jsonObject : elements) {
			Iterator<Map.Entry<String, JsonElement>> pairs = jsonObject.entrySet().iterator();
			while(pairs.hasNext()) {
				Map.Entry<String, JsonElement> pair = pairs.next();
				String key = pair.getKey();
				
				if(key.equals(name) && pair.getValue().isJsonPrimitive() && pair.getValue().getAsJsonPrimitive().isString()) {
					result.add(pair.getValue().getAsJsonPrimitive().toString());
				} else if (pair.getValue().isJsonObject()) {
					JsonObject jsonObject2 = pair.getValue().getAsJsonObject();
					Iterator<Map.Entry<String, JsonElement>> pairs2 = jsonObject2.entrySet().iterator();
					while(pairs2.hasNext()) {
						Map.Entry<String, JsonElement> pair2 = pairs2.next();
						String key2 = pair2.getKey();
						if(key2.equals(name) && pair2.getValue().isJsonPrimitive() && pair2.getValue().getAsJsonPrimitive().isString()) {
							result.add(pair2.getValue().getAsJsonPrimitive().toString());
						} // TODO make this recursive!						
					}
				}
			}
		}
		
		return result;
	}

	private void saveEPackage(EPackage ePackage, File resultPath) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.createResource(URI.createFileURI(resultPath.getAbsolutePath()));

		try {
			res.getContents().add(ePackage);
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<JsonSource> getSources() {
		List<JsonSource> result = new ArrayList<JsonSource>();
		result.addAll(this.sources.values());
		return result;
	}
	
	
}
