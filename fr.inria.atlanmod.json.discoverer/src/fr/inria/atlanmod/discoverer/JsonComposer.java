package fr.inria.atlanmod.discoverer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class JsonComposer {
	final static double CLASS_MATCHING_THRESHOLD = 0.3;

	private List<JsonSourceSet> sourceSets;

	private final static Logger LOGGER = Logger.getLogger(JsonComposer.class.getName());

	public JsonComposer(List<JsonSourceSet> sourceSets) {
		this.sourceSets = sourceSets;
	}

	public EPackage compose(File resultPath) {
		EPackage result = EcoreFactory.eINSTANCE.createEPackage();

		HashMap<String, List<EClassifier>> cache = new HashMap<String, List<EClassifier>>();
		for(JsonSourceSet sourceSet : sourceSets) {
			EPackage sourceMetamodel = sourceSet.getMetamodel();
			cache.put(sourceMetamodel.getName(), sourceMetamodel.getEClassifiers());
		}

		Iterator<String> keyIt = cache.keySet().iterator();
		while(keyIt.hasNext()) {
			String key = keyIt.next();
			System.out.println("Key: " + key);
			List<EClassifier> eClassifiers = cache.get(key);

			Iterator<String> keyItToCompare = cache.keySet().iterator();
			while(keyItToCompare.hasNext()) {
				String keyToCompare = keyItToCompare.next();
				if(key != keyToCompare) {
					System.out.println("KeyToCompare: " + keyToCompare);
					List<EClassifier> eClassifiersToCompare = cache.get(keyToCompare);
					for(EClassifier sourceEclassifier : eClassifiers) {
						if (sourceEclassifier instanceof EClass) { // TODO Consider enumerations
							EClass sourceEclass = (EClass) sourceEclassifier;
							System.out.println("  Source class: " + sourceEclass.getName());
							for(EClassifier cachedEclassifier : eClassifiersToCompare) {
								if (cachedEclassifier instanceof EClass) {
									EClass cachedEclass = (EClass) cachedEclassifier;
									System.out.println("  Target class: " + cachedEclass.getName());
									if(isSimilar(cachedEclass, sourceEclass) && sourceEclass.getEStructuralFeature("mapping") == null) {
										EReference sourceTargetMapping = EcoreFactory.eINSTANCE.createEReference();
										sourceTargetMapping.setName("mapping");
										sourceTargetMapping.setEType(sourceEclass);
										cachedEclass.getEStructuralFeatures().add(sourceTargetMapping);

										EReference targetSourceMapping = EcoreFactory.eINSTANCE.createEReference();
										targetSourceMapping.setName("mapping");
										targetSourceMapping.setEType(cachedEclass);
										targetSourceMapping.setEOpposite(sourceTargetMapping);
										sourceEclass.getEStructuralFeatures().add(targetSourceMapping);
										System.out.println("Added mapping ref between " + cachedEclass.getName() + " and " + sourceEclass.getName());
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
		saveEPackage(result, resultPath);
		return result;
	}

	private boolean isSimilar(EClass cachedEclass, EClass sourceEClass) { 
		LOGGER.finer("Comparing " + cachedEclass.getName() + " with " + sourceEClass.getName());

		double matchingFeatures = 0;
		double totalRegisteredFeatures = cachedEclass.getEStructuralFeatures().size();
		for(EStructuralFeature cachedFeature : cachedEclass.getEStructuralFeatures()) {
			if(!cachedFeature.getName().equals("mapping")){
				for(EStructuralFeature sourceFeature : sourceEClass.getEStructuralFeatures()) {
					LOGGER.finest("  " + "Comparing " + cachedFeature.getName() + " with " + sourceFeature.getName());
					if(cachedFeature.getName().equals(sourceFeature.getName())) {
						matchingFeatures++;
						LOGGER.finest("    " + "Yes! Ratio now: " + matchingFeatures / totalRegisteredFeatures );
						break;
					}
				}
			}
		}
		double matchingRatio = matchingFeatures / totalRegisteredFeatures;
		if(matchingRatio > CLASS_MATCHING_THRESHOLD) {
			LOGGER.finer("  " + "Found " + cachedEclass + " as similar class to " + sourceEClass.getName() + " with ratio " + matchingRatio);
			return true;
		} else {
			LOGGER.finest("  " + "There is no class similar to " + sourceEClass.getName() + ". Ratio " + matchingRatio);
		}
		LOGGER.finer("  " + "Not found");
		return false;		
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
