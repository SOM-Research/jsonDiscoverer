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
 * This class provides some helper methods to fill in the metadata in the discovered models.
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class AnnotationHelper {
	/**
	 * {@link AnnotationHelper} is used as singleton, this is the instance
	 */
	public static AnnotationHelper INSTANCE = new AnnotationHelper();

	// Tags used in the coverage model
	/**
	 * Tag used for the total number of found elements
	 * <p>
	 * Added to each attribute/reference to count the times it has been found
	 */
	private static final String TOTAL_FOUND_TAG = "totalFound";
	/**
	 * Tag used for referring to the coverage
	 * <p>
	 * The value indicates the number of clases including such attribute/reference
	 */
	private static final String COVERAGE_TAG = "coverage";
	/**
	 * Tag used for the annotation related to the ratio of total found
	 * <p>
	 * This value is the ratio version of the {@link AnnotationHelper#TOTAL_FOUND_TAG}
	 */
	private static final String RATIO_TOTAL_FOUND_TAG = "ratioTotalFound";
	/**
	 * Tag used for the annotation of the a.k.a. found elements
	 * <p>
	 * This annotation allows tracking the set of names used for an attribute/reference/class
	 */
	private static final String AKA_TAG = "AlsoKnownAs";
	/**
	 * Tag used to keep track on where an elements has been found
	 */
	private static final String FOUND_IN_TAG = "foundIn";
	/**
	 * Tag used to keep track of which source the element has been found
	 */
	private static final String SOURCE_NAME_TAG = "source";

	/**
	 * Constructor. As this class is singleton, no need to be public.
	 */
	private AnnotationHelper() {}

	/**
	 * Access to the annotation for {@link AnnotationHelper#COVERAGE_TAG}. To be edited afterwards.
	 * 
	 * @param modelElement The model element from which the annotation has to be retrieved
	 * @return The {@link EAnnotation} element
	 */
	public EAnnotation getCoverageAnnotation(EModelElement modelElement) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		
		EAnnotation annotation = modelElement.getEAnnotation(COVERAGE_TAG);
		if(annotation == null) {
			annotation = EcoreFactory.eINSTANCE.createEAnnotation();
			annotation.setSource(COVERAGE_TAG);
			annotation.getDetails().put(TOTAL_FOUND_TAG, "0");
			modelElement.getEAnnotations().add(annotation);
		}
		return annotation;
	}

	/**
	 * Access to the annotation for {@link AnnotationHelper#FOUND_IN_TAG}. To be edited afterwards.
	 * 
	 * @param modelElement The model element from which the annotation has to be retrieved
	 * @return The {@link EAnnotation} element
	 */
	public EAnnotation getFoundInAnnotation(EModelElement modelElement) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		EAnnotation namesAnnotation = annotation.getEAnnotation(FOUND_IN_TAG);
		if(namesAnnotation == null) {
			namesAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			namesAnnotation.setSource(FOUND_IN_TAG);
			annotation.getEAnnotations().add(namesAnnotation);
		}
		return namesAnnotation;
	}

	/**
	 * Access to the annotation for {@link AnnotationHelper#AKA_TAG}. To be edited afterwards.
	 * 
	 * @param modelElement The model element from which the annotation has to be retrieved
	 * @return The {@link EAnnotation} element
	 */
	public EAnnotation getAkaAnnotation(EModelElement modelElement) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		EAnnotation akaAnnotation = annotation.getEAnnotation(AKA_TAG);
		if(akaAnnotation == null) {
			akaAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			akaAnnotation.setSource(AKA_TAG);
			annotation.getEAnnotations().add(akaAnnotation);
		}
		return akaAnnotation;
	}

	/**
	 * Accesses to the annotation for {@link AnnotationHelper#TOTAL_FOUND_TAG} and increases it. 
	 * 
	 * @param modelElement The model element from which the annotation has to be retrieved
	 */
	public void increaseTotalFound(EModelElement modelElement) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		if(annotation != null) {
			String currentCounter = annotation.getDetails().get(TOTAL_FOUND_TAG);
			if(currentCounter != null) {
				annotation.getDetails().put(TOTAL_FOUND_TAG, String.valueOf(Integer.valueOf(currentCounter).intValue() + 1));
			}
		} 
	}

	/**
	 * Calculate the value for the annotation {@link AnnotationHelper#RATIO_TOTAL_FOUND_TAG}.
	 * 
	 * @param ePackage The model element from which the annotation has to be retrieved
	 */
	public void calculateCoverage(EPackage ePackage) {
		if(ePackage == null) 
			throw new IllegalArgumentException("The ePackage cannot be null");
		
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

	/**
	 * Keeps track of where an attribute/reference has been found 
	 * <p>
	 * This situation arises when two classes have been merged and therefore their attributes as well. 
	 * To keep track of from where each atribute is coming, the tag indicates the name of the origin class
	 * 
	 * @param modelElement  The model element from which the annotation has to be retrieved
	 * @param name The name of the containing class
	 */
	public void registerInclusion(EModelElement modelElement, String name) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		if(name == null) 
			throw new IllegalArgumentException("The name cannot be null");
		
		EAnnotation annotation = getFoundInAnnotation(modelElement);
		String times = annotation.getDetails().get(name);
		if(times == null) {
			annotation.getDetails().put(name, "1");
		} else {
			annotation.getDetails().put(name, String.valueOf(Integer.valueOf(times).intValue()+ 1));
		}
	}

	/**
	 * Register a new name for a {@link EModelElement}. 
	 * <p>
	 * This is used when, in a discovery process, two clases/attributes/references are considered similar 
	 * and merged. This tag helps keeping track of all the names of the model element.
	 * 
	 * @param modelElement  The model element from which the annotation has to be retrieved
	 * @param name The extra name of the element
	 */
	public void registerName(EModelElement modelElement, String name) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		if(name == null) 
			throw new IllegalArgumentException("The name cannot be null");
		
		EAnnotation annotation = getAkaAnnotation(modelElement);
		String times = annotation.getDetails().get(name);
		if(times == null) {
			annotation.getDetails().put(name, "1");
		} else {
			annotation.getDetails().put(name, String.valueOf(Integer.valueOf(times).intValue()+ 1));
		}
	}
	
	/**
	 * Access to the annotation for {@link AnnotationHelper#TOTAL_FOUND_TAG}. To be edited afterwards.
	 * 
	 * @param modelElement The model element from which the annotation has to be retrieved
	 * @return The {@link EAnnotation} element
	 */
	public int getTotalFound(EModelElement modelElement) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		return (annotation.getDetails().get(TOTAL_FOUND_TAG) != null) ? Integer.valueOf(annotation.getDetails().get(TOTAL_FOUND_TAG)).intValue() : 0;
	}
	
	/**
	 * Access to the annotation for {@link AnnotationHelper#RATIO_TOTAL_FOUND_TAG}. To be edited afterwards.
	 * 
	 * @param modelElement The model element from which the annotation has to be retrieved
	 * @return The {@link EAnnotation} element
	 */
	public double getRatioTotalFound(EModelElement modelElement) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		return (annotation.getDetails().get(RATIO_TOTAL_FOUND_TAG) != null) ? Double.valueOf(annotation.getDetails().get(RATIO_TOTAL_FOUND_TAG)).doubleValue() : 0.0;
	}

	/**
	 * Obtains the list of {@link String}s with all the a.k.a. values for a {@link EModelElement}
	 * 
	 * @param modelElement The model element to query
	 * @return The list of {@link String}s
	 */
	public List<String> getAka(EModelElement modelElement) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		
		EAnnotation annotation = getAkaAnnotation(modelElement);
		List<String> result = new ArrayList<String>();
		Iterator<String> keyIt = annotation.getDetails().keySet().iterator();
		while(keyIt.hasNext()) {
			String key = keyIt.next();
			result.add(key);
		}
		return result;
	}

	/**
	 * Set the value for the {@link AnnotationHelper#SOURCE_NAME_TAG} tag, which keeps track of the 
	 * name of the JSON source
	 * 
	 * @param modelElement The model element to set
	 * @param name The name of the JSON source
	 */
	public void registerSourceName(EClass modelElement, String name) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		if(name == null) 
			throw new IllegalArgumentException("The name cannot be null");
		
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		annotation.getDetails().put(SOURCE_NAME_TAG, name);
	}
	
	/**
	 * Obtains the value of the {@link AnnotationHelper#SOURCE_NAME_TAG} tag, which keeps track of the 
	 * name of the JSON source
	 * 
	 * @param modelElement The model element to set
	 * @return The value of the {@link AnnotationHelper#SOURCE_NAME_TAG} tag
	 */
	public String getSourceName(EClass modelElement) {
		if(modelElement == null) 
			throw new IllegalArgumentException("The modelElement cannot be null");
		
		EAnnotation annotation = getCoverageAnnotation(modelElement);
		return annotation.getDetails().get(SOURCE_NAME_TAG);
	}
	
	/**
	 * Remove all the annotations. Be careful, the method returns the same epackage.
	 * 
	 * @param ePackage The metamodel to clean (as {@link EPackage}
	 * @return The cleaned metamodel (as {@link EPackage}
	 */
	public EPackage cleanAnnotations(EPackage ePackage) {
		if(ePackage == null) 
			throw new IllegalArgumentException("The ePackage cannot be null");
		
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

