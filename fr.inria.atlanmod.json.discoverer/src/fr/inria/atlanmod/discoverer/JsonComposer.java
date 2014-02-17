package fr.inria.atlanmod.discoverer;

import java.io.File;
import java.io.IOException;
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

/**
 * This class composes a set of ecore models representing json-based apis. Although some
 * of the calculations done here could be applied in a more-general process of composing
 * any ecore model, the current implementation is coupled to the data/structure of the
 * ecore models discovered by the JSONMultiDiscoverer.
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
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
	 * If resultPath is not null, serializes the metamodel into a file
	 * 
	 * @param resultPath
	 * @return
	 */
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
		if(resultPath != null)
			saveEPackage(result, resultPath);
		return result;
	}

	private boolean isSimilar(EClass sourceEClass, EClass targetEClass) { 
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
		for(String sourceName : sourceNames) 
			for(String targetName : targetNames) 
				if(sourceName.equals(targetName)) return true;
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
