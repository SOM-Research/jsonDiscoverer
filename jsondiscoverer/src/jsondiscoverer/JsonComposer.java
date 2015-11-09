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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;

import jsondiscoverer.util.ModelHelper;

/**
 * This class composes a set of ecore models representing JSON-based apis. 
 * <p>
 * Although some of the calculations done here could be applied in a more-general process 
 * of composing any ecore model, the current implementation is coupled to the data/structure 
 * of the ecore models discovered by the {@link JsonAdvancedDiscoverer}. 
 * <p>
 * In the context of the JSON discoverer, the {@link JsonComposer} is used to
 * discover the metamodel out of a set of JSON-based APIs
 * <p>
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class JsonComposer {
	/**
	 * The set of {@link JsonSourceSet}s to use in the discovery process
	 */
	private List<JsonSourceSet> sourceSets;
	/**
	 * Used to log all the activity
	 */
	private final static Logger LOGGER = Logger.getLogger(JsonComposer.class.getName());

	/**
	 * Threshold to consider when two attributes. 
	 * <p>
	 * To calculate the ratio, the number of matching attributes in the
	 * two metamodel classes are compared. Thus, two metamodel classes with matching
	 * attributes will give a ratio of 1
	 */
	private static final double ATTRIBUTE_MATCHING_THRESHOLD = 0.3;
	
	/**
	 * Threshold to consider when two metamodel classes are similar. 
	 * <p>
	 * To calculate the ratio, the number of matching attributes/refereces in the
	 * two metamodel classes are compared. Thus, two metamodel classes with matching
	 * attributes/references will give a ratio of 1
	 */
	private static final double CLASS_MATCHING_THRESHOLD = 0.3;

	/**
	 * Constructs a new {@link JsonComposer} with a list of {@link JsonSourceSet}s
	 * 
	 * @param sourceSets A list of {@link JsonSourceSet}s
	 */
	public JsonComposer(List<JsonSourceSet> sourceSets) {
		this.sourceSets = sourceSets;
	}

	/**
	 * Compose the sourceSet's received in the constructor and returns the composed metamodel. 
	 * Serializes the resulting metamodel.
	 * 
	 * @param resultPath The path where the resulting metamodel will be stored
	 * @return The resulting metamodel as EPackage
	 * @throws FileNotFoundException There is no file to read from
	 */
	public EPackage compose(File resultPath) throws FileNotFoundException {
		if(resultPath == null) 
			throw new IllegalArgumentException("The file cannot be null");
		
		EPackage result = compose();
		ModelHelper.saveEPackage(result, resultPath);
		return result;
	}
	
	/**
	 * Compose the sourceSet's received in the constructor and returns the composed metamodel. 
	 * 
	 * @return The composed metamodel
	 */
	public EPackage compose() {
		EPackage result = EcoreFactory.eINSTANCE.createEPackage();

		HashMap<String, List<EClassifier>> cache = new HashMap<String, List<EClassifier>>();
		for(JsonSourceSet sourceSet : sourceSets) {
			EPackage sourceMetamodel = sourceSet.getMetamodel();
			if(sourceMetamodel == null) {
				LOGGER.finer("The JSON source set called " + sourceSet.getName() + " didn't included metamodel, discovering now...");
				JsonAdvancedDiscoverer advancedDiscoverer = new JsonAdvancedDiscoverer(sourceSet);
				sourceMetamodel = advancedDiscoverer.discover();
			}
			cache.put(sourceMetamodel.getName(), sourceMetamodel.getEClassifiers());
		}

		Iterator<String> keyIt = cache.keySet().iterator();
		while(keyIt.hasNext()) {
			String key = keyIt.next();
			LOGGER.finest("Key: " + key);
			List<EClassifier> eClassifiers = cache.get(key);

			Iterator<String> keyItToCompare = cache.keySet().iterator();
			while(keyItToCompare.hasNext()) {
				String keyToCompare = keyItToCompare.next();
				if(key != keyToCompare) {
					LOGGER.finest("KeyToCompare: " + keyToCompare);
					List<EClassifier> eClassifiersToCompare = cache.get(keyToCompare);
					for(EClassifier sourceEclassifier : eClassifiers) {
						if (sourceEclassifier instanceof EClass) { // TODO Consider enumerations
							EClass sourceEclass = (EClass) sourceEclassifier;
							LOGGER.finest("  Source class: " + sourceEclass.getName());
							for(EClassifier cachedEclassifier : eClassifiersToCompare) {
								if (cachedEclassifier instanceof EClass) {
									EClass targetEclass = (EClass) cachedEclassifier;
									LOGGER.finest("  Target class: " + targetEclass.getName());
									if(isSimilar(sourceEclass, targetEclass) && sourceEclass.getEStructuralFeature("mapping") == null) {
										EReference sourceTargetMapping = EcoreFactory.eINSTANCE.createEReference();
										sourceTargetMapping.setName("mapping");
										sourceTargetMapping.setEType(sourceEclass);
										targetEclass.getEStructuralFeatures().add(sourceTargetMapping);

										EReference targetSourceMapping = EcoreFactory.eINSTANCE.createEReference();
										targetSourceMapping.setName("mapping");
										targetSourceMapping.setEType(targetEclass);
										targetSourceMapping.setEOpposite(sourceTargetMapping);
										sourceEclass.getEStructuralFeatures().add(targetSourceMapping);
										LOGGER.finest("Added mapping ref between " + targetEclass.getName() + " and " + sourceEclass.getName());
									}
								}
							}
						}
					}
				}
			}
		}

		for(JsonSourceSet sourceSet : sourceSets) {
			EPackage sourceMetamodel = sourceSet.getMetamodel();
			result.getEClassifiers().addAll(sourceMetamodel.getEClassifiers());
		}
		return result;
	}

	/**
	 * Compares if two metamodel classes (as {@link EClass} elements) are similar. 
	 * <p>
	 * The comparison takes into consideration the names of attributes/refrences and counts 
	 * the ones that are similar. The ratio of similar vs. different is calculated and if it higher
	 * that {@link JsonComposer#ATTRIBUTE_MATCHING_THRESHOLD}, the two classes are considered similar
	 * 
	 * @param sourceEClass The source {@link EClass}
	 * @param targetEClass The target {@link EClass}
	 * @return Boolean indicating if both classes are similar
	 */
	private boolean isSimilar(EClass sourceEClass, EClass targetEClass) { 
		if(sourceEClass == null) 
			throw new IllegalArgumentException("The sourceEClass cannot be null");
		if(targetEClass == null) 
			throw new IllegalArgumentException("The targetEClass cannot be null");
		
		LOGGER.finer("Comparing " + sourceEClass.getName() + " with " + targetEClass.getName());

		double matchingFeatures = 0;
		double totalRegisteredFeatures = sourceEClass.getEStructuralFeatures().size();
		for(EStructuralFeature sourceFeature : sourceEClass.getEStructuralFeatures()) {
			double sourceTotalFound = AnnotationHelper.INSTANCE.getTotalFound(sourceFeature);
			double sourceRatioTotalFound = AnnotationHelper.INSTANCE.getRatioTotalFound(sourceFeature);
			if(!sourceFeature.getName().equals("mapping")){
				for(EStructuralFeature targetFeature : targetEClass.getEStructuralFeatures()) {
					List<String> sourceNames = AnnotationHelper.INSTANCE.getAka(sourceFeature);
					List<String> targetNames = AnnotationHelper.INSTANCE.getAka(targetFeature);
					LOGGER.finest("  " + "Comparing " + sourceNames.toString() + " with " + targetNames.toString());
					if(nameIsSimilar(sourceNames, targetNames)) {
						LOGGER.finest("    " + "Same name!");
						double targetTotalFound = AnnotationHelper.INSTANCE.getTotalFound(targetFeature);
						double targetRatioTotalFound = AnnotationHelper.INSTANCE.getRatioTotalFound(targetFeature);

						double similarity = (sourceTotalFound + targetTotalFound) / ((sourceTotalFound / sourceRatioTotalFound) + (targetTotalFound / targetRatioTotalFound));
						if(similarity >= ATTRIBUTE_MATCHING_THRESHOLD) {
							LOGGER.finest("    " + "Attribute similars!");
							matchingFeatures++;
							LOGGER.finest("    " + "Ratio now: " + matchingFeatures / totalRegisteredFeatures );
							break;
						} else {
							LOGGER.finest("    " + "Attribute similarity not enough");
						}
					}
				}
			}
		}
		
		double matchingRatio = matchingFeatures / totalRegisteredFeatures;
		if(matchingRatio > CLASS_MATCHING_THRESHOLD) {
			LOGGER.finer("  " + "Found " + targetEClass.getName() + " as similar class to " + sourceEClass.getName() + " with ratio " + matchingRatio);
			return true;
		} else {
			LOGGER.finest("  " + targetEClass.getName() + " is not similar to " + sourceEClass.getName() + ". Ratio " + matchingRatio);
		}
		return false;		
	}
	
	/**
	 * Compares two list of {@link String} and returns true when two similar {@link String}s
	 * are found. 
	 * 
	 * @param sourceNames Source list of {@link String}
	 * @param targetNames Target list of {@link String}
	 * @return True if at least two equal {@link String}s (one in each list) are found.
	 */
	private boolean nameIsSimilar(List<String> sourceNames, List<String> targetNames) {
		if(sourceNames == null) 
			throw new IllegalArgumentException("The sourceNames cannot be null");
		if(targetNames == null) 
			throw new IllegalArgumentException("The targetNames cannot be null");
		
		for(String sourceName : sourceNames) 
			for(String targetName : targetNames) 
				if(sourceName.equals(targetName)) return true;
		return false;
	}

}
