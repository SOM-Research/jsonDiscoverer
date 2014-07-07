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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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

/**
 * This class performs the injection process (obtaining models from JSON files)
 * This implementation does not depend on Xtext
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonInjector {
	EPackage metamodel = null;
	private final static Logger LOGGER = Logger.getLogger(JsonInjector.class.getName());

	private SingleJsonSource jsonSource;

	public JsonInjector(SingleJsonSource jsonSource) {
		this.jsonSource = jsonSource;

		LOGGER.setLevel(Level.OFF);
	}

	/**
	 * Injects a model from a JSON Source. Only the first one will be considered (if several)
	 * 
	 * @param jsonSource
	 * @return
	 */
	public List<EObject> inject() {
		if(jsonSource == null)
			throw new IllegalArgumentException("The source cannot be null");

		JsonDiscoverer discoverer = new JsonDiscoverer();
		EPackage ePackage = discoverer.discoverMetamodel(jsonSource);
		JsonElement rootElement = jsonSource.getJsonData().get(0).getData();
		return inject(rootElement, ePackage);		
	}

	/**
	 * Injects a model conforming to the metamodel from a set of Json Objects
	 * 
	 * @param rootElement
	 * @param ePackage
	 * @return
	 */
	private List<EObject> inject(JsonElement rootElement, EPackage ePackage) {
		// Getting the JSON objects
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

		// Getting the root element
		metamodel = ePackage;
		EClassifier eClassifier = metamodel.getEClassifier(jsonSource.getName()); 

		List<EObject> eObjects = new ArrayList<EObject>();
		for(JsonObject jsonObject : elements) {
			EObject eObject = instantiateEClassifier(eClassifier, jsonObject);
			eObjects.add(eObject);
		}

		return eObjects;
	}

	protected EObject instantiateEClassifier(EClassifier eClassifier, JsonObject jsonObject) {
		EObject result = null;

		if (eClassifier instanceof EClass) {
			LOGGER.finer("Instantiating class " + eClassifier.getName());
			EClass eClass = (EClass) eClassifier;
			result = EcoreUtil.create(eClass);

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
							setStructuralFeature(result, eStructuralFeature, singleValue);
						}
					} else {
						setStructuralFeature(result, eStructuralFeature, value);
					}
				}
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	protected void setStructuralFeature(EObject result, EStructuralFeature eStructuralFeature, JsonElement value) {
		if(value.isJsonArray()) {
			LOGGER.finer("Detected array in array for " + eStructuralFeature.getName());

			for(int i = 0; i < value.getAsJsonArray().size(); i++) {
				JsonElement singleValue = value.getAsJsonArray().get(i);
				setStructuralFeature(result, eStructuralFeature, singleValue);
			}
		} else {
			LOGGER.finer("Setting feature " + eStructuralFeature.getName());
			if (eStructuralFeature instanceof EAttribute) {
				EAttribute eAttribute = (EAttribute) eStructuralFeature;
				if(eStructuralFeature.getUpperBound() == -1) {
					EList<Object> set = (EList<Object>) result.eGet(eAttribute);
					set.add(digestValue(eAttribute, value));
				} else {
					result.eSet(eAttribute, digestValue(eAttribute, value));
				}
			} else if(eStructuralFeature instanceof EReference) {
				EReference eReference = (EReference) eStructuralFeature;
				if(value.isJsonObject()) {
					JsonObject childJsonObject = value.getAsJsonObject();
					String childClassName = eReference.getEType().getName();
					EClassifier eChildClassifier = metamodel.getEClassifier(childClassName);
					if(eChildClassifier != null) {
						EObject child = instantiateEClassifier(eChildClassifier, childJsonObject);
						if(eStructuralFeature.getUpperBound() == -1) {
							EList<Object> set = (EList<Object>) result.eGet(eReference);
							set.add(child);
						} else {
							result.eSet(eReference, child);
						}
					}
				}
			}
		}
	}

	protected Object digestValue(EAttribute eAttribute, JsonElement value) {
		if (eAttribute.getEType().equals(EcorePackage.Literals.ESTRING)) {
			if(value.isJsonArray() || value.isJsonObject()) return ""; // TODO Improve discovery process to deal with this
			else return value.getAsJsonPrimitive().getAsString();
		} else if (eAttribute.getEType().equals(EcorePackage.Literals.EINT)) {
			return new Integer(value.getAsJsonPrimitive().getAsNumber().intValue());
		} else if (eAttribute.getEType().equals(EcorePackage.Literals.EBOOLEAN)) {
			return value.getAsJsonPrimitive().getAsBoolean() ? Boolean.TRUE : Boolean.FALSE;
		} else {
			return null;
		}
	}

	protected EPackage loadMetamodel(File metamodelFile) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.getResource(URI.createFileURI(metamodelFile.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (EPackage) res.getContents().get(0);
	}

}
