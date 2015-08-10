package fr.inria.atlanmod.discoverer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;

/**
 * This class provides some helper methods to fill in the metadata in the discovered models
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class AnnotationHelper {
	public static AnnotationHelper INSTANCE = new AnnotationHelper();

	private static final String TOTAL_FOUND_TAG = "totalFound";

	private static final String COVERAGE_TAG = "coverage";

	private static final String RATIO_TOTAL_FOUND_TAG = "ratioTotalFound";

	private static final String AKA_TAG = "AlsoKnownAs";

	private static final String FOUND_IN_TAG = "foundIn";

	private static final String SOURCE_NAME_TAG = "source";

	private AnnotationHelper() {}

	public EAnnotation getCoverageAnnotation(EModelElement modelElement) {
		EAnnotation annotation = modelElement.getEAnnotation(COVERAGE_TAG);
		if(annotation == null) {
			annotation = EcoreFactory.eINSTANCE.createEAnnotation();
			annotation.setSource(COVERAGE_TAG);
			annotation.getDetails().put(TOTAL_FOUND_TAG, "0");
			modelElement.getEAnnotations().add(annotation);
		}
		return annotation;
	}

	public EAnnotation getFoundInAnnotation(EModelElement modelElement) {
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		EAnnotation namesAnnotation = annotation.getEAnnotation(FOUND_IN_TAG);
		if(namesAnnotation == null) {
			namesAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			namesAnnotation.setSource(FOUND_IN_TAG);
			annotation.getEAnnotations().add(namesAnnotation);
		}
		return namesAnnotation;
	}

	public EAnnotation getAkaAnnotation(EModelElement modelElement) {
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		EAnnotation akaAnnotation = annotation.getEAnnotation(AKA_TAG);
		if(akaAnnotation == null) {
			akaAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			akaAnnotation.setSource(AKA_TAG);
			annotation.getEAnnotations().add(akaAnnotation);
		}
		return akaAnnotation;
	}

	public void increaseTotalFound(EModelElement modelElement) {
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		if(annotation != null) {
			String currentCounter = annotation.getDetails().get(TOTAL_FOUND_TAG);
			if(currentCounter != null) {
				annotation.getDetails().put(TOTAL_FOUND_TAG, String.valueOf(Integer.valueOf(currentCounter).intValue() + 1));
			}
		} 
	}

	public void calculateCoverage(EPackage ePackage) {
		for(EClassifier eClassifier : ePackage.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				EAnnotation eClassAnnotation = getCoverageAnnotation(eClass);

				int eClassCounter = Integer.valueOf(eClassAnnotation.getDetails().get(TOTAL_FOUND_TAG)).intValue();
				for(EStructuralFeature eStructuralFeature : eClass.getEStructuralFeatures()) {
					EAnnotation eStructuralFeatureAnnotation = getCoverageAnnotation(eStructuralFeature);
					int eStructuralFeatureCounter = Integer.valueOf(eStructuralFeatureAnnotation.getDetails().get(TOTAL_FOUND_TAG)).intValue();
					double ratio = ((double) eStructuralFeatureCounter) / ((double) eClassCounter);
					eStructuralFeatureAnnotation.getDetails().put(RATIO_TOTAL_FOUND_TAG, String.valueOf(ratio));
				}
			}
		}
	}

	public void registerInclusion(EModelElement modelElement, String name) {
		EAnnotation annotation = getFoundInAnnotation(modelElement);
		String times = annotation.getDetails().get(name);
		if(times == null) {
			annotation.getDetails().put(name, "1");
		} else {
			annotation.getDetails().put(name, String.valueOf(Integer.valueOf(times).intValue()+ 1));
		}
	}

	public void registerName(EModelElement modelElement, String name) {
		EAnnotation annotation = getAkaAnnotation(modelElement);
		String times = annotation.getDetails().get(name);
		if(times == null) {
			annotation.getDetails().put(name, "1");
		} else {
			annotation.getDetails().put(name, String.valueOf(Integer.valueOf(times).intValue()+ 1));
		}
	}

	public int getTotalFound(EModelElement modelElement) {
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		return (annotation.getDetails().get(TOTAL_FOUND_TAG) != null) ? Integer.valueOf(annotation.getDetails().get(TOTAL_FOUND_TAG)).intValue() : 0;
	}

	public double getRatioTotalFound(EModelElement modelElement) {
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		return (annotation.getDetails().get(RATIO_TOTAL_FOUND_TAG) != null) ? Double.valueOf(annotation.getDetails().get(RATIO_TOTAL_FOUND_TAG)).doubleValue() : 0.0;
	}

	public List<String> getAka(EModelElement modelElement) {
		EAnnotation annotation = getAkaAnnotation(modelElement);
		List<String> result = new ArrayList<String>();
		Iterator<String> keyIt = annotation.getDetails().keySet().iterator();
		while(keyIt.hasNext()) {
			String key = keyIt.next();
			result.add(key);
		}
		return result;
	}

	public void registerSourceName(EClass modelElement, String name) {
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		annotation.getDetails().put(SOURCE_NAME_TAG, name);
	}
	
	public String getSourceName(EClass modelElement) {
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		return annotation.getDetails().get(SOURCE_NAME_TAG);
	}
	
	/**
	 * Remove all the annotations. Be careful, the method returns the same epackage.
	 * 
	 * @param ePackage
	 * @return
	 */
	public EPackage cleanAnnotations(EPackage ePackage) {
		for(EClassifier eClassifier : ePackage.getEClassifiers()) {
			eClassifier.getEAnnotations().clear();
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				for(EStructuralFeature eStructuralFeature : eClass.getEStructuralFeatures()) {
					eStructuralFeature.getEAnnotations().clear();
				}
			}
		}
		return ePackage;
	}

}

