/*******************************************************************************
 * Copyright (c) 2008, 2015
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Javier Canovas (me@jlcanovas.es) 
 *******************************************************************************/

package jsondiscoverer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jsondiscoverer.coverage.ConceptMapping;
import jsondiscoverer.coverage.util.CoverageCreator;
import jsondiscoverer.util.ModelHelper;

/**
 * Performs a discovery process among several {@link JsonSource}s (i.e., receiving a 
 * {@link JsonSourceSet} as input). The resulting metamodels are combined to obtain a 
 * general one.
 * <p>
 * In the context of the JSON discoverer, the {@link JsonAdvancedDiscoverer} is used to
 * discover the metamodel out of a set of JSON-based Web services of the same API.
 * <p>
 * Note that this process is actually a metamodel composition process, where metamodels 
 * discovered from a set of {@link JsonSource}s are analyzed to create a new one covering
 * all of them. 
 * <p>
 * Thus, the advanced discovery process follows these steps:
 * <ol>
 * <li> Receive a set of {@link JsonSource}s (as {@link JsonSourceSet}</li>
 * <li> Discover the metamodel for each {@link JsonSource}</li>
 * <li> Analyze the discovered metamodels and create a new one covering them</li>
 * <li> As a result, a metamodel plus a set of {@link CoverageCreator}s are returned</li>
 * </ol>
 * <p>
 * This implementation does not depend on Xtext
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class JsonAdvancedDiscoverer {
	/**
	 * Default prefix for the discovered metamodel
	 */
	private static final String DEFAULT_NS_URI = "http://jsondiscoverer/discovered/";
	/**
	 * Default NS URI for the discovered metamodel
	 */
	private static final String DEFAULT_NS_PREFIX = "composed";
	/**
	 * Used to log all the activity
	 */
	private final static Logger LOGGER = Logger.getLogger(JsonAdvancedDiscoverer.class.getName());
	
	/**
	 * Threshold to consider when two metamodel classes are similar. 
	 * <p>
	 * To calculate the ratio, the number of matching attributes/refereces in the
	 * two metamodel classes are compared. Thus, two metamodel classes with matching
	 * attributes/references will give a ratio of 1
	 */
	final static double CLASS_MATCHING_THRESHOLD = 0.3;
	
	/**
	 * The set of JsonSources to use in the discovery process
	 */
	private JsonSourceSet sourceSet;
	
	/**
	 * Map with the set of EClasses discovered in the resulting metamodel
	 */
	private HashMap<String, EClass> registry;
	
	/**
	 * Map with the coverage for each JsonSource
	 */
	private HashMap<JsonSource, CoverageCreator> coverageCreators;
	
	/**
	 * Values for discovered values
	 */
	HashMap<EAttribute, List<Object>> cacheValues;
	
	/**
	 * Constructs a new {@link JsonAdvancedDiscoverer} with a set of {@link JsonSourceSet} elements
	 * 
	 * @param sourceSet A {@link JsonSourceSet} to represent a set of JSON documents 
	 */
	public JsonAdvancedDiscoverer(JsonSourceSet sourceSet) {
		if(sourceSet == null) 
			throw new IllegalArgumentException("SourceSet cannot be null");
		else if(sourceSet.getJsonSources().size() == 0) 
			throw new IllegalArgumentException("At least 1 source is required");

		this.sourceSet = sourceSet;
		this.cacheValues = new HashMap<EAttribute, List<Object>>();
		
		// All the sources must include the metamodel
		// If not included, we call the JSONDiscoverer for each one
		for(JsonSource jsonSource : this.sourceSet.getJsonSources()) {
			if(jsonSource.getMetamodel() == null) {
				JsonSimpleDiscoverer discoverer = new JsonSimpleDiscoverer();
				// By default the discovered metamodel is stored in the JsonSource
				discoverer.discover(jsonSource);
			}
		}
		
		LOGGER.setLevel(Level.OFF);
	}
		
	/**
	 * Saves the coverage information
	 * 
	 * @param resultPaths The path where the coverage information will be saved
	 */
	public void saveCoverage(List<File> resultPaths) {
		if(resultPaths == null) 
			throw new IllegalArgumentException("ResultPath has to be provided");
		if(resultPaths.size() == coverageCreators.size()) {
			int i = 0;
			for(CoverageCreator coverageCreator : coverageCreators.values()) {
				coverageCreator.save(resultPaths.get(i++));
			}
		}
		else {
			throw new IllegalArgumentException("The size of the paths must match the size of coverage "
					+ "files (it is: " + coverageCreators.size() + ")");
		}
	}
	
	/**
	 * Launches the advanced discover process
	 * 
	 * @param resultPath The path where the resulting metamodel will be stored
	 * @return The resulting metamodel as EPackage
	 * @throws FileNotFoundException There is no file to read from
	 */
	public EPackage discover(File resultPath) throws FileNotFoundException {
		if(resultPath == null) 
			throw new IllegalArgumentException("ResultPath has to be provided");
		
		EPackage result = discover();
		ModelHelper.saveEPackage(result, resultPath);
		return result;
	}
	
	/**
	 * Launches the advanced discover process
	 * 
	 * @return The resulting metamodel as EPackage
	 */
	public EPackage discover() {
		// Creating the resulting metamodel (the one gathering all the info)
		EPackage finalPackage = EcoreFactory.eINSTANCE.createEPackage();
		finalPackage.setName(sourceSet.getName());
		finalPackage.setNsPrefix(DEFAULT_NS_PREFIX + sourceSet.getName().charAt(0));
		finalPackage.setNsURI(DEFAULT_NS_URI + sourceSet.getName());
		LOGGER.finer("Package created");

		// Initializing variables
		registry = new HashMap<String, EClass>();
		List<EReference> referencesToCheck = new ArrayList<EReference>();
		coverageCreators = new HashMap<JsonSource, CoverageCreator>();
		
		// Iterating the JsonSouces
		// At the end of this loop, we have the set of discovered classes 
		for(JsonSource jsonSource : this.sourceSet.getJsonSources()) {
			LOGGER.finer("Analizing JSON source: " + jsonSource.getName());
			CoverageCreator coverageCreator = new CoverageCreator(jsonSource.getName(), jsonSource.getMetamodel(), finalPackage);

			for(EClassifier classifier : jsonSource.getMetamodel().getEClassifiers()) {
				if (classifier instanceof EClass) {
					EClass eClass = (EClass) classifier;
					LOGGER.finer("Analizing " + eClass.getName());
					EClass registryElement = lookForSimilarEClass(eClass);
					if(registryElement == null) {
						// No similar class was found, creating a new EClass in the resulting metamodel
						LOGGER.finer("  " + eClass.getName() + " class being duplicated...");
						EClass duplicatedEClass = duplicateEClass(eClass, coverageCreator); 
						registry.put(eClass.getName(), duplicatedEClass);
						coverageCreator.createConceptMapping(eClass, duplicatedEClass);
						eClass = duplicatedEClass;
						LOGGER.finer("  " + eClass.getName() + " class duplicated and added");
					} else {
						// Similar class found, classes are merged
						LOGGER.finer("  " + eClass.getName() + " class being composed with " + registryElement.getName() + "...");
						coverageCreator.createConceptMapping(eClass, registryElement);
						composeEClass(registryElement, eClass, coverageCreator);
						LOGGER.finer("  " + eClass.getName() + " class composed with " + registryElement.getName());
						eClass = registryElement;
					}
					// References are stored to be checked at the end
					for(EStructuralFeature otherFeature : eClass.getEStructuralFeatures()) 
						if (otherFeature instanceof EReference) 
							referencesToCheck.add((EReference) otherFeature);

				}
			}
			coverageCreators.put(jsonSource, coverageCreator);
		}

		// Adding the discovered classes in the resulting metamodel 
		for(EClass eClass : registry.values()) {
			finalPackage.getEClassifiers().add(eClass);
		}

		// Unknown class for those elements not discovered
		EClass unknown = EcoreFactory.eINSTANCE.createEClass();
		unknown.setName("Unknown");
		boolean unknownUsed = false;

		// Checking references and connecting elements
		for(EReference reference : referencesToCheck) {
			EClassifier referredEClassifier = reference.getEType();
			if (referredEClassifier instanceof EClass) {
				EClass referredEClass = (EClass) referredEClassifier;
				EClass registryElement = registry.get(referredEClass.getName());
				if(registryElement == null) {
					ConceptMapping mapping = null;
					for(CoverageCreator coverageCreator : coverageCreators.values()) {
						mapping = coverageCreator.getConceptMappingFromSource(referredEClass);
						if(mapping != null)
							break;
					}
					if(mapping != null) {
						EClass mappedClass = mapping.getTarget();
						reference.setEType(mappedClass);
						LOGGER.finer("Reference " + reference.getName() + " re-assigned to " + mappedClass.getName());
					} else {
						reference.setEType(unknown);
						LOGGER.finer("Reference " + reference.getName() + " with unknown type");
						unknownUsed = true;
					}
				} else {
					reference.setEType(registryElement);
					LOGGER.finer("Reference " + reference.getName() + " re-assigned");
				}
			}
		}

		if(unknownUsed) {
			finalPackage.getEClassifiers().add(unknown);
		}
		
		AnnotationHelper.INSTANCE.calculateCoverage(finalPackage);
		this.sourceSet.setMetamodel(finalPackage);
		return finalPackage;
	}

	/**
	 * Compose the attributes of two {@link EClass}es.
	 * 
	 * @param existingClass Discovered {@link EClass} 
	 * @param otherClass {@link EClass} coming from previously discovered process, to compose with the existing one
	 * @param coverageCreator Coverage manager to track changes
	 */
	private void composeAttributes(EClass existingClass, EClass otherClass, CoverageCreator coverageCreator) {
		if(existingClass == null) 
			throw new IllegalArgumentException("The discovered class (existingClass param) is required");
		if(otherClass == null) 
			throw new IllegalArgumentException("The class to compare with (otherClass param) is required");
		if(coverageCreator == null) 
			throw new IllegalArgumentException("The coverage creator is required");
		
		// Iterate over the structural features of the other class (to be composed into the existingClass)
		for(EStructuralFeature otherFeature : otherClass.getEStructuralFeatures()) {
			// Only attributes
			if (otherFeature instanceof EAttribute) {
				EAttribute otherAttribute = (EAttribute) otherFeature;
				EStructuralFeature existingFeature = existingClass.getEStructuralFeature(otherAttribute.getName());
				
				if(existingFeature == null) {
					// If the existing class DOES NOT have an attribute with same name
					// We look for a similar attribute in the existing eclass
					EAttribute similarAttribute = lookForSimilarAttribute(existingClass, otherAttribute, coverageCreator);
					if(similarAttribute == null) {
						// The existing class DOES NOT have a similar attribute -> Creating a new one
						EAttribute newAttribute = duplicateAttribute(otherAttribute);
						existingClass.getEStructuralFeatures().add(newAttribute);

						AnnotationHelper.INSTANCE.increaseTotalFound(newAttribute);
						AnnotationHelper.INSTANCE.registerName(newAttribute, otherAttribute.getName());
						AnnotationHelper.INSTANCE.registerInclusion(newAttribute, otherClass.getName());
						
						LOGGER.finer("    " + "Attribute " + newAttribute.getName() + " added");
						existingFeature = newAttribute;
					} else {
						// The existing class DOES have a similar attribute -> Do nothing
						LOGGER.finer("    " + "Attribute similar found: " + similarAttribute.getName());
						existingFeature = similarAttribute;

						AnnotationHelper.INSTANCE.increaseTotalFound(similarAttribute);
						AnnotationHelper.INSTANCE.registerName(similarAttribute, otherAttribute.getName());
						AnnotationHelper.INSTANCE.registerInclusion(similarAttribute, otherClass.getName());
					}
					coverageCreator.createAttMapping(otherAttribute, (EAttribute) existingFeature);
				} else if (existingFeature instanceof EAttribute) {
					// If the existing class DOES have an attribute with same name -> Change the type to String (as fallback)
					existingFeature.setEType(EcorePackage.Literals.ESTRING);
					LOGGER.finer("    " + "Attribute " + existingFeature.getName() + " refined to String");

					AnnotationHelper.INSTANCE.increaseTotalFound(existingFeature);
					AnnotationHelper.INSTANCE.registerInclusion(existingFeature, otherClass.getName());
					AnnotationHelper.INSTANCE.registerName(existingFeature, otherAttribute.getName());
					coverageCreator.createAttMapping(otherAttribute, (EAttribute) existingFeature);
				}			
			}
		}

	}

	/**
	 * Compose the references of two {@link EClass}es.
	 * 
	 * @param existingClass Discovered {@link EClass} 
	 * @param otherClass {@link EClass} coming from previously discovered process, to compose with the existing one
	 * @param coverageCreator Coverage manager to track changes
	 */
	private void composeReferences(EClass existingClass, EClass otherClass, CoverageCreator coverageCreator) {
		if(existingClass == null) 
			throw new IllegalArgumentException("The discovered class (existingClass param) is required");
		if(otherClass == null) 
			throw new IllegalArgumentException("The class to compare with (otherClass param) is required");
		if(coverageCreator == null) 
			throw new IllegalArgumentException("The coverage creator is required");
		
		// Iterate over the structural features
		for(EStructuralFeature otherFeature : otherClass.getEStructuralFeatures()) {
			// Only references
			if (otherFeature instanceof EReference) {
				EReference otherReference = (EReference) otherFeature;
				EStructuralFeature existingFeature = existingClass.getEStructuralFeature(otherReference.getName());
				if(existingFeature == null) {
					// If the existing class DOES NOT have a reference with same name -> Duplication
					EReference newReference = duplicateReference(otherReference);
					existingClass.getEStructuralFeatures().add(newReference);
					LOGGER.finer("    " + "Reference " + newReference.getName() + " added");
					existingFeature = newReference;
					coverageCreator.createRefMapping(otherReference, (EReference) existingFeature);

					AnnotationHelper.INSTANCE.increaseTotalFound(newReference);
					AnnotationHelper.INSTANCE.registerName(newReference, otherReference.getName());
					AnnotationHelper.INSTANCE.registerInclusion(newReference, otherClass.getName());
				} else {
					// If the existing class DOES have a reference with same name -> Do nothing
					AnnotationHelper.INSTANCE.increaseTotalFound(existingFeature);
					AnnotationHelper.INSTANCE.registerName(existingFeature, otherReference.getName());
					AnnotationHelper.INSTANCE.registerInclusion(existingFeature, otherClass.getName());
				}
			}
		}
	}

	/**
	 * Creates a new {@link EAttribute} and initializes it with basic information
	 * coming from another one
	 * 
	 * @param otherAttribute Attribute to duplicate
	 * @return Duplicated attribute
	 */
	private EAttribute duplicateAttribute(EAttribute otherAttribute) {
		if(otherAttribute == null) 
			throw new IllegalArgumentException("The attribute to be duplicated is required");
		
		EAttribute newAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		newAttribute.setName(otherAttribute.getName());
		newAttribute.setEType(otherAttribute.getEType());
		newAttribute.setUpperBound(otherAttribute.getUpperBound());
		newAttribute.setLowerBound(otherAttribute.getLowerBound());

		return newAttribute;
	}

	/**
	 * Creates a new {@link EReference} and initializes it with basic information
	 * coming from another one
	 * 
	 * @param otherReference Reference to duplicate
	 * @return Duplicated reference
	 */
	private EReference duplicateReference(EReference otherReference) {
		if(otherReference == null) 
			throw new IllegalArgumentException("The reference to be duplicated is required");
		
		EReference newReference = EcoreFactory.eINSTANCE.createEReference();
		newReference.setName(otherReference.getName());
		newReference.setEType(otherReference.getEType());
		newReference.setUpperBound(otherReference.getUpperBound());
		newReference.setLowerBound(otherReference.getLowerBound());
		return newReference;		
	}

	/**
	 * Duplicates a Class having into consideration its structural features.
	 * 
	 * @param otherClass Class to duplicate
	 * @param coverageCreator Coverage manage to track changes
	 * @return Duplicated class
	 */
	private EClass duplicateEClass(EClass otherClass, CoverageCreator coverageCreator) {
		if(otherClass == null) 
			throw new IllegalArgumentException("The class to be duplicated is required");
		if(coverageCreator == null) 
			throw new IllegalArgumentException("The coverageCreator is required");
		
		EClass newClass = EcoreFactory.eINSTANCE.createEClass();
		newClass.setName(otherClass.getName());
		newClass.setAbstract(otherClass.isAbstract());

		for(EStructuralFeature otherFeature : otherClass.getEStructuralFeatures()) {
			if (otherFeature instanceof EReference) {
				LOGGER.finer("    " + "Duplicating " + otherFeature.getName() + " reference");
				EReference duplicatedReference = duplicateReference((EReference) otherFeature);
				newClass.getEStructuralFeatures().add(duplicatedReference);
				coverageCreator.createRefMapping((EReference) otherFeature, duplicatedReference);	

				AnnotationHelper.INSTANCE.increaseTotalFound(duplicatedReference);
				AnnotationHelper.INSTANCE.registerName(duplicatedReference, duplicatedReference.getName());
				AnnotationHelper.INSTANCE.registerInclusion(duplicatedReference, newClass.getName());
			} else if (otherFeature instanceof EAttribute) {
				LOGGER.finer("    " + "Duplicating " + otherFeature.getName() + " attribute");
				EAttribute duplicatedAttribute = duplicateAttribute((EAttribute) otherFeature);
				newClass.getEStructuralFeatures().add(duplicatedAttribute);
				coverageCreator.createAttMapping((EAttribute) otherFeature, duplicatedAttribute);
				cacheValues.put(duplicatedAttribute, getJSONValues(duplicatedAttribute, coverageCreator.getName()));

				AnnotationHelper.INSTANCE.increaseTotalFound(duplicatedAttribute);
				AnnotationHelper.INSTANCE.registerName(duplicatedAttribute, duplicatedAttribute.getName());
				AnnotationHelper.INSTANCE.registerInclusion(duplicatedAttribute, newClass.getName());
			}
			AnnotationHelper.INSTANCE.increaseTotalFound(otherFeature);
			AnnotationHelper.INSTANCE.registerName(otherFeature, otherFeature.getName());
			AnnotationHelper.INSTANCE.registerInclusion(otherFeature, newClass.getName());
		}

		AnnotationHelper.INSTANCE.increaseTotalFound(newClass);
		AnnotationHelper.INSTANCE.registerName(newClass, newClass.getName());
		AnnotationHelper.INSTANCE.registerSourceName(newClass, sourceSet.getName());
		return newClass;
	}
	
	/**
	 * Composes two {@link EClass}es 
	 * 
	 * @param existingClass Discovered {@link EClass} 
	 * @param otherClass {@link EClass} coming from previously discovered process, to compose with the existing one
	 * @param coverageCreator Coverage manager to track changes
	 */
	private void composeEClass(EClass existingClass, EClass otherClass, CoverageCreator coverageCreator) {
		if(existingClass == null) 
			throw new IllegalArgumentException("The discovered class (existingClass param) is required");
		if(otherClass == null) 
			throw new IllegalArgumentException("The class to compare with (otherClass param) is required");
		if(coverageCreator == null) 
			throw new IllegalArgumentException("The coverage creator is required");
		
		composeAttributes(existingClass, otherClass, coverageCreator);
		composeReferences(existingClass, otherClass, coverageCreator);
		AnnotationHelper.INSTANCE.registerName(existingClass, otherClass.getName());
		AnnotationHelper.INSTANCE.increaseTotalFound(existingClass);
	}

	/**
	 * Looks for a potential already discovered {@link EClass} which may match with the one given
	 * as input. First, a class with the same name is searched in the set of discovered classes. 
	 * If not found, a class with a ratio of same structural features (same name) higher than 
	 * {@link CLASS_MATCHING_THRESHOLD} is searched. If not found, returns null.
	 * 
	 * Input elements (represented as {@link EClass}es named "Input") are not considered
	 * 
	 * @param existingClass {@link EClass} to check with already discovered EClasses
	 * @return The similar class (as {@link EClass})
	 */
	private EClass lookForSimilarEClass(EClass existingClass) { 
		if(existingClass == null) 
			throw new IllegalArgumentException("The class (existingClass param) is required");
		
		LOGGER.finer("  " + "Looking for classes similar to " + existingClass.getName());
		
		// If it is the input class, ignore it!
		if(existingClass.getName().endsWith("Input")) 
			return null;
		
		EClass registryElement = registry.get(existingClass.getName());
		if(registryElement != null) {
			// First case: matching names
			LOGGER.finer("    " + "Found matching name");
			return registryElement;
		} else {
			// Second case: searching a class with similar structura features
			for(EClass registeredClass : registry.values()) {
				double totalRegisteredFeatures = registeredClass.getEStructuralFeatures().size();
				double matchingFeatures = 0;
				for(EStructuralFeature registeredFeature : registeredClass.getEStructuralFeatures()) {
					for(EStructuralFeature existingFeature : existingClass.getEStructuralFeatures()) {
						LOGGER.finest("      " + "Comparing " + registeredFeature.getName() + " with " + existingFeature.getName());
						if(registeredFeature.getName().equals(existingFeature.getName())) {
							LOGGER.finest("        " + "Yes! Ratio now: " + matchingFeatures / totalRegisteredFeatures );
							matchingFeatures++;
							break;
						}
					}
				}
				double matchingRatio = matchingFeatures / totalRegisteredFeatures;
				if(matchingRatio > CLASS_MATCHING_THRESHOLD) {
					LOGGER.finer("    " + "Found similar class " + registeredClass.getName() + " with ratio " + matchingRatio);
					return registeredClass;
				} else {
					LOGGER.finest("    " + "Class " + registeredClass.getName() + " no similar with ratio " + matchingRatio);
				}
			}
		}
		LOGGER.finer("    " + "Not found");
		return null;		
	}

	/**
	 * Looks for a similar attribute in an existing {@link EClass}. Only names are considered.
	 * 
	 * @param existingClass Class in which the attribute is search
	 * @param otherAttribute The attribute to compare
	 * @param coverageCreator The coverage manage to track changes
	 * @return The similar attribute (null if not found)
	 */
	private EAttribute lookForSimilarAttribute(EClass existingClass, EAttribute otherAttribute, CoverageCreator coverageCreator)  {
		if(existingClass == null) 
			throw new IllegalArgumentException("The class (existingClass param) is required");
		if(otherAttribute == null) 
			throw new IllegalArgumentException("The attribute to compare with (otherAttribute param) is required");
		if(coverageCreator == null) 
			throw new IllegalArgumentException("The coverage creator is required");
		
		List<Object> jsonValues = getJSONValues(otherAttribute, coverageCreator.getName());

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
	 * @param eAttribute The contained attribute
	 * @param sourceName The name acting as key
	 * @return The list of matching values
	 */
	private List<Object> getJSONValues(EAttribute eAttribute, String sourceName) {
		if(eAttribute == null) 
			throw new IllegalArgumentException("EAttribute cannot be null");
		if(sourceName == null || sourceName.equals("")) 
			throw new IllegalArgumentException("sourceName cannot be null or empty");
		
		List<Object> result = new ArrayList<Object>();
		List<JsonData> jsonDefs = this.sourceSet.getJsonSource(sourceName).getJsonData();
		if(jsonDefs.size() == 0) return result;
		JsonElement rootElement = jsonDefs.get(0).getData(); // TODO Consider all of them!
		
		List<JsonObject> elements = new ArrayList<JsonObject>();
		if (rootElement.isJsonArray()) {
			LOGGER.finest("Several objects found");
			for(int i = 0; i < rootElement.getAsJsonArray().size(); i++)
				if(rootElement.getAsJsonArray().get(i).isJsonObject())
					elements.add(rootElement.getAsJsonArray().get(i).getAsJsonObject());
		} else if(rootElement.isJsonObject()) {
			LOGGER.finest("Only one object found");
			elements.add(rootElement.getAsJsonObject());
		} else {
			LOGGER.finest("The root element was " + rootElement.getClass().getName());
			LOGGER.finest("It is: " + rootElement.getAsString());
		}
		
		String containmentElementName = eAttribute.eContainer().eClass().getName();
		String attributeName = eAttribute.getName();
		
		if(containmentElementName.equals(sourceName)) {
			// If the containmentElement name matches with the sourceName, 
			// we have to deal with the root elements of the JSON
			for(JsonObject jsonObject : elements) {
				Iterator<Map.Entry<String, JsonElement>> pairs = jsonObject.entrySet().iterator();
				while(pairs.hasNext()) {
					Map.Entry<String, JsonElement> pair = pairs.next();
					String key = pair.getKey();
					
					if(key.equals(attributeName) && pair.getValue().isJsonPrimitive() && pair.getValue().getAsJsonPrimitive().isString()) {
						result.add(pair.getValue().getAsJsonPrimitive().toString());
					} 
				}
			}
		} else {
			// Else, take the objects inside the JSON
			for(JsonObject jsonObject : elements) {
				Iterator<Map.Entry<String, JsonElement>> pairs = jsonObject.entrySet().iterator();
				while(pairs.hasNext()) {
					Map.Entry<String, JsonElement> pair = pairs.next();
					String key = pair.getKey();
					String jsonElementName = digestId(key);
					if (pair.getValue().isJsonObject() && jsonElementName.equals(containmentElementName)) {
						JsonObject jsonObject2 = pair.getValue().getAsJsonObject();
						Iterator<Map.Entry<String, JsonElement>> pairs2 = jsonObject2.entrySet().iterator();
						while(pairs2.hasNext()) {
							Map.Entry<String, JsonElement> pair2 = pairs2.next();
							String key2 = pair2.getKey();
							if(key2.equals(attributeName) && pair2.getValue().isJsonPrimitive() && pair2.getValue().getAsJsonPrimitive().isString()) {
								result.add(pair2.getValue().getAsJsonPrimitive().toString());
							} // TODO make this recursive!						
						}
					}
				}
			}
		}
		
		
		return result;
	}

	/**
	 * Disgests an identifier (changes the first letter to uppercase and removes the possible trailing "s")
	 * 
	 * @param id Identifier to digest
	 * @return Digested identifier
	 */
	private String digestId(String id) {
		if(id == null) 
			throw new IllegalArgumentException("The id cannot be null");

		String result = id;
		if(result.endsWith("s")) 
			result = result.substring(0, result.length()-1);
		result = result.substring(0, 1).toUpperCase() + result.substring(1, result.length()); 
		return result;
	}
	
}
