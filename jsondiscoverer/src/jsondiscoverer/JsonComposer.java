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
 * This class composes a set of ecore models representing json-based apis. Although some
 * of the calculations done here could be applied in a more-general process of composing
 * any ecore model, the current implementation is coupled to the data/structure of the
 * ecore models discovered by the {@link JSONAdvancedDsicoverer} 
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 * @version 2.0.0
 *
 */
public class JsonComposer {

	private List<JsonSourceSet> sourceSets;

	private final static Logger LOGGER = Logger.getLogger(JsonComposer.class.getName());

	private static final double ATTRIBUTE_MATCHING_THRESHOLD = 0.3;
	private static final double CLASS_MATCHING_THRESHOLD = 0.3;

	public JsonComposer(List<JsonSourceSet> sourceSets) {
		this.sourceSets = sourceSets;
	}

	/**
	 * Compose the sourceSet's received in the constructor and returns the composed metamodel. 
	 * Serializes the resulting metamodel.
	 * 
	 * @param resultPath The path where the resulting metamodel will be stored
	 * @return The resulting metamodel as EPackage
	 * @throws FileNotFoundException
	 */
	public EPackage discover(File resultPath) throws FileNotFoundException {
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
