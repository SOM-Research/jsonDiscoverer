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
import java.io.FileReader;
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
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import coverage.util.CoverageCreator;

/**
 * Performs a composition between metamodels obtained from JSON files
 * This implementation does not depend on Xtext
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonComposer {
	private List<EPackage> ePackages;
	private HashMap<String, EClass> registry;
	private List<CoverageCreator> coverageCreators;
	HashMap<File, List<File>> jsonFiles;
	HashMap<EAttribute, List<Object>> cacheValues;
	
	private final static Logger LOGGER = Logger.getLogger(JsonComposer.class.getName());

	/**
	 * Creates a JSON Composer. 
	 * 
	 * @param elements List of File elements pointing at ecore files or list of EPackages
	 */
	public JsonComposer(List elements) {
		this.ePackages = new ArrayList<EPackage>();
		
		// Limitation of Java. I would need two constructors: one for each type of element lists
		if(elements != null && elements.size() > 0) {
			Object object = elements.get(0);
			if (object instanceof File) {
				List<File> files = (List<File>) elements;
				for(File file : files)
					this.ePackages.add(loadEPackage(file));				
			} else if (object instanceof EPackage) {
				List<EPackage> ePackages = (List<EPackage>) elements;
				this.ePackages = ePackages;
			} else {
				throw new UnsupportedOperationException("The input type does not match with required");
			}
		}
		this.cacheValues = new HashMap<EAttribute, List<Object>>();
	}
	
	/**
	 * Creates a JSON Composer from a set of metamodels. For each metamodel, a set of example models
	 * can be provided.
	 * 
	 * @param files List of File elements pointing at ecore files or list of EPackages
	 * @param jsonFiles Map of XMI files containing models conforming to the previous metamodels. The key is the path to the
	 * ecore metamodel
	 * @deprecated
	 */
	public JsonComposer(List<File> files, HashMap<File, List<File>> jsonFiles) {
		this(files);
		this.jsonFiles = jsonFiles;
	}

	/**
	 * Saves the coverage information
	 * 
	 * @param resultPath The path where the converage information will be saved
	 */
	public void saveCoverage(File resultPath) {
		for(CoverageCreator coverageCreator : coverageCreators) 
			coverageCreator.save(resultPath);
	}
	
	/**
	 * Compose the JSON files
	 * 
	 * @param resultPath The path where the resulting metamodel will be stored
	 * @return The resulting metamodel as EPackage
	 */
	public EPackage compose(File resultPath) throws FileNotFoundException {
		EPackage finalPackage = compose();
		saveEPackage(finalPackage, resultPath);
		return finalPackage;
	}
	
	public EPackage compose() throws FileNotFoundException  {
		if(ePackages.size() == 0) 
			return null;

		EPackage finalPackage = EcoreFactory.eINSTANCE.createEPackage();
		finalPackage.setName("composed");
		finalPackage.setNsPrefix("composed");
		finalPackage.setNsURI("http://composed");

		registry = new HashMap<String, EClass>();
		List<EReference> referencesToCheck = new ArrayList<EReference>();
		coverageCreators = new ArrayList<CoverageCreator>();
		for(EPackage ePackage: ePackages) {
			CoverageCreator coverageCreator = new CoverageCreator(ePackage);

			for(EClassifier classifier : ePackage.getEClassifiers()) {
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
				cacheValues.put(duplicatedAttribute, getJSONValues(duplicatedAttribute.getName(), coverageCreator.getFile()));
			}
		}
		return newClass;
	}


	private EAttribute lookForSimilarAttribute(EClass existingClass, EAttribute otherAttribute, CoverageCreator coverageCreator) throws FileNotFoundException  {
		List<Object> jsonValues = getJSONValues(otherAttribute.getName(), coverageCreator.getFile());

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
	 * TODO: Change this!!
	 * 
	 * @param name
	 * @param service
	 * @return
	 * @throws FileNotFoundException
	 */
	private List<Object> getJSONValues(String name, File service) throws FileNotFoundException {
		List<Object> result = new ArrayList<Object>();
		if(jsonFiles == null) return result;

		ResourceSet rset = new ResourceSetImpl();
		List<File> files = jsonFiles.get(service);
		if(files == null) return result;

		File file = files.get(0);
		
		JsonElement rootElement = (new JsonParser()).parse(new JsonReader(new FileReader(file)));
		
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

	private EPackage loadEPackage(File file) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.getResource(URI.createFileURI(file.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		EPackage ePackage = (EPackage) res.getContents().get(0);
		return ePackage;
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
}
