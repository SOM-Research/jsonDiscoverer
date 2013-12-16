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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fr.inria.atlanmod.json.discoverer.coverage.AttMapping;
import fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping;
import fr.inria.atlanmod.json.discoverer.coverage.Coverage;
import fr.inria.atlanmod.json.discoverer.coverage.CoverageMapping;
import fr.inria.atlanmod.json.discoverer.coverage.RefMapping;

/**
 * Injects a model wich information coming from several JSON files
 * This implementation does not depend on Xtext
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonMultiInjector {
	private List<EObject> currentModel;
	private SingleJsonSource source1, source2;
	private Coverage coverage1, coverage2;

	private final static Logger LOGGER = Logger.getLogger(JsonMultiInjector.class.getName());
	
	public JsonMultiInjector(SingleJsonSource source1, Coverage coverage1, SingleJsonSource source2, Coverage coverage2) {
		if(source1 == null || source1.getJsonData().size() == 0)
			throw new IllegalArgumentException("Source1 cannot be null or have not json definitions");
		if(coverage1 == null) 
			throw new IllegalArgumentException("Coverage1 cannot be null");
		if(source2 == null || source2.getJsonData().size() == 0)
			throw new IllegalArgumentException("Source2 cannot be null or have not json definitions");
		if(coverage2 == null) 
			throw new IllegalArgumentException("Coverage2 cannot be null");
		
		this.source1 = source1;
		this.coverage1 = coverage1;
		this.source2 = source2;
		this.coverage2 = coverage2;
	}
	
	public void multiInject(File resultFile) throws FileNotFoundException {
		currentModel = new ArrayList<EObject>();

		inject(source1.getJsonData().get(0).getData(), source1.getMetamodel(), coverage1);
		inject(source2.getJsonData().get(0).getData(), source2.getMetamodel(), coverage2);		

		saveModel(resultFile, currentModel);
	}

	protected void inject(JsonElement rootElement, EPackage metamodel, Coverage coverage) throws FileNotFoundException {
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

		EClassifier eClassifier = metamodel.getEClassifier("Root");
		for(JsonObject jsonObject : elements) {
			EObject eObject = instantiateEClassifier(eClassifier, jsonObject, null, coverage, metamodel);
		}

	}

	protected EObject instantiateEClassifier(EClassifier eClassifier, JsonObject jsonObject, EClassifier compositeEClassifier, Coverage coverage, EPackage metamodel) {
		EObject result = null;
		EObject compositeResult = null;

		if (eClassifier instanceof EClass) {
			EClass eClass = (EClass) eClassifier;
			result = EcoreUtil.create(eClass);

			EClass compositeEClass = (EClass) findCompositeEClass(eClass, coverage);
			EObject existingCompositeEObject = findExistingEObject(eClass, jsonObject, compositeEClass, coverage, metamodel);
			if(existingCompositeEObject != null) {
				return existingCompositeEObject;
			}

			compositeResult = EcoreUtil.create(compositeEClass);
			currentModel.add(compositeResult);


			Iterator<Map.Entry<String, JsonElement>> pairs = jsonObject.entrySet().iterator();
			while(pairs.hasNext()) {
				Map.Entry<String, JsonElement> pair = pairs.next();
				
				String pairId = pair.getKey();
				JsonElement value = pair.getValue();

				EStructuralFeature eStructuralFeature = eClass.getEStructuralFeature(pairId);
				if(eStructuralFeature != null) {
					if(value.isJsonArray()) {
						for(int i = 0; i < value.getAsJsonArray().size(); i++) {
							JsonElement singleValue = value.getAsJsonArray().get(i);
							if(singleValue.isJsonObject()) // TODO What happens if it is not a jsonObject?
								setStructuralFeature(result, eStructuralFeature, singleValue, compositeResult, coverage, metamodel);
						}
					} else {
						setStructuralFeature(result, eStructuralFeature, value, compositeResult, coverage, metamodel);
					}
				}
			}
		}

		return compositeResult;
	}

	private EObject findExistingEObject(EClass eClass, JsonObject jsonObject, EClass compositeEClass, Coverage coverage, EPackage metamodel) {
		List<EClass> existingEclasses = new ArrayList<EClass>();
		for(EObject object : currentModel) {
			EClass existingEClass = object.eClass();
			if(existingEClass.getName().equals(compositeEClass.getName())) {
				existingEclasses.add(existingEClass);
				
				Iterator<Map.Entry<String, JsonElement>> pairs = jsonObject.entrySet().iterator();
				while(pairs.hasNext()) {
					Map.Entry<String, JsonElement> pair = pairs.next();
					
					if (pair.getValue().isJsonPrimitive() && pair.getValue().getAsJsonPrimitive().isString()) {
						String existingPairId = pair.getKey();
						String existingValue = pair.getValue().getAsJsonPrimitive().toString();
						EStructuralFeature existingEStructuralFeature = eClass.getEStructuralFeature(existingPairId);
						if (existingEStructuralFeature instanceof EAttribute && existingEStructuralFeature.getEType().equals(EcorePackage.Literals.ESTRING)) {
							EAttribute existingEAttribute = (EAttribute) existingEStructuralFeature;
							EAttribute compositeEAttribute = (EAttribute) findCompositeEAttribute(existingEAttribute, coverage);
							if(compositeEAttribute != null) {
								for(EStructuralFeature objectEAttribute : existingEClass.getEStructuralFeatures()) {
									if(objectEAttribute.getName().equals(compositeEAttribute.getName())) {
										String compositeValue = (String) object.eGet(objectEAttribute);
										if(existingValue.equals(compositeValue))
											return object;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	protected void setStructuralFeature(EObject result, EStructuralFeature eStructuralFeature, JsonElement value, EObject compositeResult, Coverage coverage, EPackage metamodel) {
		if (eStructuralFeature instanceof EAttribute) {
			EAttribute eAttribute = (EAttribute) eStructuralFeature;
			EAttribute compositeEAttribute = (EAttribute) findCompositeEAttribute(eAttribute, coverage);
			if(compositeEAttribute != null) {
				if(eStructuralFeature.getUpperBound() == -1) {
					EList<Object> set = (EList<Object>) compositeResult.eGet(compositeEAttribute);
					set.add(digestValue(value));
				} else {
					compositeResult.eSet(compositeEAttribute, digestValue(value));
				}
			}
		} else if(eStructuralFeature instanceof EReference) {
			EReference eReference = (EReference) eStructuralFeature;
			EReference compositeEReference = findCompositeEReference(eReference, coverage);
			if(value.isJsonObject()) {
				JsonObject childJsonObject = value.getAsJsonObject();
				String childClassName = eReference.getEType().getName();
				EClassifier eChildClassifier = metamodel.getEClassifier(childClassName);
				if(eChildClassifier != null) {
					EClass compositeEClass = (EClass) findCompositeEClass((EClass) eChildClassifier, coverage);
					if(compositeEClass != null) {
						EObject child = instantiateEClassifier(eChildClassifier, childJsonObject, compositeEClass, coverage, metamodel);
						if(compositeEReference.getUpperBound() == -1) {
							EList<Object> set = (EList<Object>) compositeResult.eGet(compositeEReference);
							set.add(child);
						} else {
							compositeResult.eSet(compositeEReference, child);
						}
					}
				}
			}
		}
	}

	private EClass findCompositeEClass(EClass object, Coverage coverage) {
		for(CoverageMapping mapping : coverage.getMappings()) {
			if (mapping instanceof ConceptMapping) {
				ConceptMapping conceptMapping = (ConceptMapping) mapping;
				if(conceptMapping.getSource().getName().equals(object.getName())) 
					return conceptMapping.getTarget();	
			}
		}
		return null;
	}

	private EAttribute findCompositeEAttribute(EAttribute object, Coverage coverage) {
		for(CoverageMapping mapping : coverage.getMappings()) {
			if (mapping instanceof AttMapping) {
				AttMapping attMapping = (AttMapping) mapping;
				if(attMapping.getSource().getName().equals(object.getName())) 
					return attMapping.getTarget();	
			}
		}
		return null;
	}

	private EReference findCompositeEReference(EReference object, Coverage coverage) {
		for(CoverageMapping mapping : coverage.getMappings()) {
			if (mapping instanceof RefMapping) {
				RefMapping refMapping = (RefMapping) mapping;
				if(refMapping.getSource().getName().equals(object.getName())) 
					return refMapping.getTarget();		
			}
		}
		return null;
	}

	protected Object digestValue(JsonElement value) {
		if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
			return value.getAsJsonPrimitive().getAsString();
		} else if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
			return new Integer(value.getAsJsonPrimitive().getAsNumber().intValue());
		} else if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
			return value.getAsJsonPrimitive().getAsBoolean() ? Boolean.TRUE : Boolean.FALSE;
		} else {
			return null;
		}
	}

	protected void saveModel(File targetFile, List<EObject> eObjects) {
		ResourceSet rset = new ResourceSetImpl();		
		Resource res = rset.createResource(URI.createFileURI(targetFile.getAbsolutePath()));

		try {
			for(EObject eObject : eObjects) {
				res.getContents().add(eObject);
			}
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
