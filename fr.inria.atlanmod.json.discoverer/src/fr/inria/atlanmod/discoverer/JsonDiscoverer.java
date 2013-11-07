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
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List; 
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
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
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

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
	}
	
	/**
	 * Discover a metamodel from scratch using a json String
	 * 
	 * @param jsonString The json text
	 * @return The JSON model
	 */
	public EPackage discoverMetamodel(String jsonString) {
		JsonElement element = (new JsonParser()).parse(new JsonReader(new StringReader(jsonString)));
		return discoverMetamodel(element);
	}

	/**
	 * Discover a metamodel from scratch using an existing json file
	 * 
	 * @param sourceFile (the json file)
	 * @return EPackage containing the EClasses discovered
	 * @throws FileNotFoundException 
	 */
	public EPackage discoverMetamodel(File sourceFile) throws FileNotFoundException {
		JsonElement element = (new JsonParser()).parse(new JsonReader(new FileReader(sourceFile)));
		return discoverMetamodel(element);
	}
	
	/**
	 * Launches the metamodel discoverer from a JSON model. Only the JSON objects included (both 
	 * if it is a single JSON object or an array of JSON objects) will be considered
	 * 
	 * @param model The JSON model
	 * @return the Epackage (Ecore model)
	 */
	private EPackage discoverMetamodel(JsonElement rootElement) {
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
		
		// Launching discoverer
		discoverMetaclasses(elements);

		// Default package
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("DiscoveredPackage");
		ePackage.setNsURI("http://fr.inria.atlanmod/discovered");
		ePackage.setNsPrefix("disco");

		ePackage.getEClassifiers().addAll(geteClasses().values());

		return ePackage;
	}
	

	/**
	 * Discover the metaclasses for a list of JsonObjects
	 * 
	 * @param jsonObjects
	 */
	public void discoverMetaclasses(List<JsonObject> jsonObjects) {
		LOGGER.fine("Received " + jsonObjects.size() + " json objects to discover");
		for(JsonObject jsonObject : jsonObjects) {
			discoverMetaclass("Root", jsonObject);
		}
	}

	/**
	 * Discover the metaclass for a JsonObject
	 * 
	 * @param id Unique identifier for the JsonObject 
	 * @param jsonObject the JsonObject
	 * @return Discovered EClass
	 */
	public EClass discoverMetaclass(String id, JsonObject jsonObject) {
		EClass eClass = eClasses.get(id);
		if(eClass != null) {
			LOGGER.finer("Refining " + id);
			eClass = refineMetaclass(eClass, jsonObject);
		} else {
			LOGGER.finer("Creating " + id);
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

		eClasses.put(id, eClass);

		LOGGER.fine("Metaclass created with name " + id);
		Iterator<Map.Entry<String, JsonElement>> pairs = jsonObject.entrySet().iterator();
		LOGGER.finer("Iterating over " + jsonObject.entrySet().size() + " pairs");
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
		LOGGER.fine("Refining metaclass " + eClass.getName());
		
		Iterator<Map.Entry<String, JsonElement>> pairs = jsonObject.entrySet().iterator();
		while(pairs.hasNext()) {
			Map.Entry<String, JsonElement> pair = pairs.next();
			
			String pairId = pair.getKey();
			JsonElement value = pair.getValue();

			EStructuralFeature eStructuralFeature = null;
			if((eStructuralFeature = eClass.getEStructuralFeature(pairId)) != null) {
				if (eStructuralFeature instanceof EAttribute) {
					EAttribute eAttribute = (EAttribute) eStructuralFeature;
					if(eAttribute.getEType() != mapType(pairId, value)) {
						LOGGER.fine("Attribute " + eAttribute.getName() + " typed to String due to conflicts");
						eAttribute.setEType(EcorePackage.Literals.ESTRING);
					} else {
						LOGGER.fine("No conflicts with attribute " + eAttribute.getName());
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
			eClass.getEStructuralFeatures().add(eStructuralFeature);
			LOGGER.fine(eStructuralFeature.getClass().getSimpleName() + " created with name " + pairId + " type " + eStructuralFeature.getEType().getName() + " and lower bound " + lowerBound);
		}
	}
	
	/**
	 * Refines an exisiting metamodel (giving the file) from a json file
	 * 
	 * @param sourceFile
	 * @param existingMetamodel
	 * @return
	 */
	public EPackage refineMetamodel(File sourceFile, File existingMetamodel) throws FileNotFoundException {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.getResource(URI.createFileURI(existingMetamodel.getAbsolutePath()), true);
		
		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		EPackage ePackage = (EPackage) res.getContents().get(0);
		
		return refineMetamodel(sourceFile, ePackage);
	}
	
	/**
	 * Refines an existing metamodel (giving the EPackage) from a json string
	 * 
	 * @param toRefine
	 * @param newPackage
	 * @return
	 */
	public EPackage refineMetamodel(String jsonString, EPackage toRefine) {
		for(EClassifier eClassifier : toRefine.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				eClasses.put(eClass.getName(), eClass);
				LOGGER.fine("added " + eClass.getName());
			}
		}
		return discoverMetamodel(jsonString);
	}
	
	/**
	 * Refines an exisiting metamodel (giving the EPackage) from a json file
	 * 
	 * @param sourceFile
	 * @param ePackage
	 * @return
	 */
	public EPackage refineMetamodel(File sourceFile, EPackage ePackage) throws FileNotFoundException {	
		for(EClassifier eClassifier : ePackage.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				eClasses.put(eClass.getName(), eClass);
				LOGGER.fine("added " + eClass.getName());
			}
		}
		
		return discoverMetamodel(sourceFile);
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
			if(arrayValue.size() > 0)
				return mapType(digestId(id), arrayValue.get(0)); // TODO: Consider all the list
		} else if (value.isJsonObject()) {
			return discoverMetaclass(digestId(id), value.getAsJsonObject());
		} else {
		}
		return EcorePackage.Literals.ESTRING;
	}

	private String digestId(String id) {
		String result = id;
		if(result.endsWith("s")) 
			result = result.substring(0, result.length()-1);
		result = result.substring(0, 1).toUpperCase() + result.substring(1, result.length()); 
		return result;
	}

	public HashMap<String, EClass> geteClasses() {
		return eClasses;
	}


}
