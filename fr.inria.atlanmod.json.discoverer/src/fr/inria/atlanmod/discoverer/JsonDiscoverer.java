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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Main class to discover/refine metamodels (ecore file) from json definitions (json file).
 * This implementation does not depend on Xtext
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonDiscoverer {
	HashMap<String, EClass> eClasses = new HashMap<String, EClass>();

	private final static Logger LOGGER = Logger.getLogger(JsonDiscoverer.class.getName());

	public JsonDiscoverer() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

		LOGGER.setLevel(Level.OFF);
	}

	/**
	 * Launches the metamodel discoverer from a JSON model. The method received a JsonSource 
	 * element which includes the set of JSON definitions to be considered. 
	 * 
	 * At the end, the discovered metamodel is returned and also stored in the JsonSource
	 * received as param
	 * 
	 * @param source
	 * @return Epackage (Ecore model)
	 */
	public EPackage discoverMetamodel(JsonSource source) {
		if(source == null) 
			throw new IllegalArgumentException("Source cannot be null");
		else if(source.getJsonData().size() == 0) 
			throw new IllegalArgumentException("The source must include, at least, one JSON definition.");

		List<JsonObject> elements = source.getSourceDigested();

		LOGGER.fine("[discoverMetamodel] Received " + elements.size() + " json objects to discover");

		String sourceName = (source.includesInput()) ? source.getName() + "Input" : source.getName();

		for(JsonObject jsonObject : elements) {
			discoverMetaclass(sourceName, jsonObject);
		}

		// Default package
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName(source.getName());
		ePackage.setNsURI("http://fr.inria.atlanmod/discovered/" + source.getName());
		ePackage.setNsPrefix("disco" + source.getName().charAt(0));
		ePackage.getEClassifiers().addAll(geteClasses().values());
		source.setMetamodel(ePackage);
		
		// Calculating coverage
		AnnotationHelper.INSTANCE.calculateCoverage(ePackage);

		return ePackage;
	}

	/**
	 * Refines an existing metamodel with new JSON definitions coming from a new JsonSource
	 * 
	 * @param toRefine
	 * @param source
	 * @return
	 */
	public EPackage refineMetamodel(EPackage toRefine, JsonSource source) {
		for(EClassifier eClassifier : toRefine.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				eClasses.put(eClass.getName(), eClass);
				LOGGER.fine("added " + eClass.getName());
			}
		}
		return discoverMetamodel(source);
	}

	/**
	 * Discover the metaclass for a JsonObject
	 * 
	 * @param id Unique identifier for the JsonObject 
	 * @param jsonObject the JsonObject
	 * @return Discovered EClass
	 */
	private EClass discoverMetaclass(String id, JsonObject jsonObject) {
		EClass eClass = eClasses.get(id);
		if(eClass != null) {
			LOGGER.finer("[discoverMetaclass] Refining " + id);
			eClass = refineMetaclass(eClass, jsonObject);
		} else {
			LOGGER.finer("[discoverMetaclass] Creating " + id);
			eClass = createMetaclass(id, jsonObject);
		}
		return eClass;
	}

	/**
	 * Creates a new metaclass form scratch. Takes a jsonobject as input and 
	 * an identifier.
	 * 
	 * @param id
	 * @param jsonObject
	 */
	private EClass createMetaclass(String id, JsonObject jsonObject) {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(id);
		AnnotationHelper.INSTANCE.increaseTotalFound(eClass);

		eClasses.put(id, eClass);

		LOGGER.fine("[createMetaclass] Metaclass created with name " + id);
		Iterator<Map.Entry<String, JsonElement>> pairs = jsonObject.entrySet().iterator();
		LOGGER.finer("[createMetaclass] Iterating over " + jsonObject.entrySet().size() + " pairs");
		while(pairs.hasNext()) {
			Map.Entry<String, JsonElement> pair = pairs.next();

			String pairId = pair.getKey();
			JsonElement value = pair.getValue();

			createStructuralFeature(pairId, value, 1, eClass);
		}
		return eClass;
	}

	/**
	 * Refines the attributes and references of an existing eclass
	 * from a new JsonObject definition. 
	 * 
	 * @param eClass
	 * @param JsonObject
	 */
	private EClass refineMetaclass(EClass eClass, JsonObject jsonObject) {
		LOGGER.fine("[refineMetaclass] Refining metaclass " + eClass.getName());
		AnnotationHelper.INSTANCE.increaseTotalFound(eClass);

		Iterator<Map.Entry<String, JsonElement>> pairs = jsonObject.entrySet().iterator();
		while(pairs.hasNext()) {
			Map.Entry<String, JsonElement> pair = pairs.next();

			String pairId = pair.getKey();
			JsonElement value = pair.getValue();

			EStructuralFeature eStructuralFeature = null;
			if((eStructuralFeature = eClass.getEStructuralFeature(pairId)) != null) {
				AnnotationHelper.INSTANCE.increaseTotalFound(eStructuralFeature);
				if (eStructuralFeature instanceof EAttribute) {
					EAttribute eAttribute = (EAttribute) eStructuralFeature;
					if(eAttribute.getEType() != mapType(pairId, value)) {
						LOGGER.fine("[refineMetaclass] Attribute " + eAttribute.getName() + " typed to String due to conflicts");
						eAttribute.setEType(EcorePackage.Literals.ESTRING);
					} else {
						LOGGER.fine("[refineMetaclass] No conflicts with attribute " + eAttribute.getName());
					}
				} else if (eStructuralFeature instanceof EReference) {

				}
			} else {
				createStructuralFeature(pairId, value, 0, eClass);
			}
		}

		return eClass;
	}

	/**
	 * Creates a new structuralFeature out from a pairId/Value
	 * 
	 * @param pairId
	 * @param value
	 * @param eClass
	 */
	private void createStructuralFeature(String pairId, JsonElement value, int lowerBound, EClass eClass) {
		EStructuralFeature eStructuralFeature = null;
		EClassifier type = mapType(pairId, value);

		if(type instanceof EDataType) {
			eStructuralFeature = EcoreFactory.eINSTANCE.createEAttribute();
		} else {
			eStructuralFeature = EcoreFactory.eINSTANCE.createEReference();
			((EReference) eStructuralFeature).setContainment(true);
		}

		if(value.isJsonArray()) {
			eStructuralFeature.setUpperBound(-1);
		}

		if(eStructuralFeature != null) {
			eStructuralFeature.setName(pairId);
			eStructuralFeature.setLowerBound(lowerBound);
			eStructuralFeature.setEType(mapType(pairId, value)); 
			AnnotationHelper.INSTANCE.increaseTotalFound(eStructuralFeature);
			eClass.getEStructuralFeatures().add(eStructuralFeature);
			LOGGER.fine("[createStructuralFeature] " + eStructuralFeature.getClass().getSimpleName() + " created with name " + pairId + " type " + eStructuralFeature.getEType().getName() + " and lower bound " + lowerBound);
		}
	}

	/**
	 * Maps json types into ecore types
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	private EClassifier mapType(String id, JsonElement value) {
		if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
			return EcorePackage.Literals.ESTRING; 
		} else if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
			return EcorePackage.Literals.EINT; 
		} else if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
			return EcorePackage.Literals.EBOOLEAN; 
		} else if (value.isJsonArray()) {
			JsonArray arrayValue = value.getAsJsonArray();
			if(arrayValue.size() > 0) {
				EClassifier generalArrayType = mapType(digestId(id), arrayValue.get(0));
				for(int i = 1; i < arrayValue.size(); i++) {
					JsonElement arrayElement = arrayValue.get(i);
					EClassifier arrayType = mapType(digestId(id), arrayElement);
					if(generalArrayType != arrayType) {
						LOGGER.finer("[mapType] Detected array multi-typed, using fallback type (String) for " + id);
						return EcorePackage.Literals.ESTRING; 
					}
				}
				return generalArrayType;
			}
		} else if (value.isJsonObject()) {
			return discoverMetaclass(digestId(id), value.getAsJsonObject());
		} 
		LOGGER.finer("[mapType] Type not discovererd for " + id);
		return EcorePackage.Literals.ESTRING;
	}

	private String digestId(String id) {
		String result = id;
		if(result.length() > 1 && result.endsWith("s")) 
			result = result.substring(0, result.length()-1);
		result = result.substring(0, 1).toUpperCase() + result.substring(1, result.length()); 
		return result;
	}

	private HashMap<String, EClass> geteClasses() {
		return eClasses;
	}
}
