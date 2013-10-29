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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.spec.ECParameterSpec;
import java.util.HashMap;
import java.util.List;

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

import fr.inria.atlanmod.json.ArrayValue;
import fr.inria.atlanmod.json.BooleanValue;
import fr.inria.atlanmod.json.JsonObject;
import fr.inria.atlanmod.json.JsonObjectValue;
import fr.inria.atlanmod.json.Model;
import fr.inria.atlanmod.json.NumberValue;
import fr.inria.atlanmod.json.Pair;
import fr.inria.atlanmod.json.StringValue;
import fr.inria.atlanmod.json.Value;


/**
 * Main class to discover/refine metamodels (ecore file) from json definitions (json file).
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonDiscoverer {
	HashMap<String, EClass> eClasses = new HashMap<String, EClass>();
	
	/**
	 * Discover a metamodel from scratch using a json String
	 * 
	 * @param jsonString The json text
	 * @return The JSON model
	 */
	public EPackage discoverMetamodel(String jsonString) {
		Model model = getModel(jsonString);
		return discoverMetamodel(model);
	}

	/**
	 * Objtains a Json Model from a JSON string
	 * 
	 * @param jsonString
	 * @return
	 */
	private Model getModel(String jsonString) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.createResource(URI.createURI("dummy:/example.json"));
		InputStream in = new ByteArrayInputStream(jsonString.getBytes());

		try {
			res.load(in, rset.getLoadOptions());
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		Model model = (Model) res.getContents().get(0);
		return model;
	}
	
	/**
	 * Discover a metamodel from scratch using an existing json file
	 * 
	 * @param sourceFile (the json file)
	 * @return EPackage containing the EClasses discovered
	 */
	public EPackage discoverMetamodel(File sourceFile) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.getResource(URI.createFileURI(sourceFile.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Model model = (Model) res.getContents().get(0);
		return discoverMetamodel(model);
	}
	
	/**
	 * Launches the metamodel discoverer from a JSON model
	 * 
	 * @param model The JSON model
	 * @return the Epackage (Ecore model)
	 */
	private EPackage discoverMetamodel(Model model) {
		// Launching discoverer
		discoverMetaclasses(model.getObjects());

		// Default package
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("DiscoveredPackage");
		ePackage.setNsURI("http://fr.inria.atlanmod/discovered");
		ePackage.setNsPrefix("disco");

		ePackage.getEClassifiers().addAll(geteClasses().values());

		return ePackage;
	}
	
	/**
	 * Refines an exisiting metamodel (giving the file) from a json file
	 * 
	 * @param sourceFile
	 * @param existingMetamodel
	 * @return
	 */
	public EPackage refineMetamodel(File sourceFile, File existingMetamodel) {
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
	 * Refines an exisiting metamodel (giving the EPackage) from a json string
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
//				System.out.println("added " + eClass.getName());
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
	public EPackage refineMetamodel(File sourceFile, EPackage ePackage) {	
		for(EClassifier eClassifier : ePackage.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				eClasses.put(eClass.getName(), eClass);
//				System.out.println("added " + eClass.getName());
			}
		}
		
		return discoverMetamodel(sourceFile);
	}
	

	/**
	 * Discover the metaclasses for a list of JsonObjects
	 * 
	 * @param jsonObjects
	 */
	public void discoverMetaclasses(List<JsonObject> jsonObjects) {
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
			// If already created, refine
			eClass = refineMetaclass(eClass, jsonObject);
		} else {
			// If it's first time, create
			eClass = createMetaclass(id, jsonObject);
		}
		return eClass;
	}


	/**
	 * Refines the attributes and references of an existing eclass
	 * from a new jsonObject definition. 
	 * 
	 * @param eClass
	 * @param jsonObject
	 */
	private EClass refineMetaclass(EClass eClass, JsonObject jsonObject) {
//		System.out.println("Refining metaclass " + eClass.getName());
		
		for(Pair pair : jsonObject.getPairs()) {
			String pairId = pair.getString();
			Value value = pair.getValue();

			EStructuralFeature eStructuralFeature = null;
			if((eStructuralFeature = eClass.getEStructuralFeature(pairId)) != null) {
				if (eStructuralFeature instanceof EAttribute) {
					EAttribute eAttribute = (EAttribute) eStructuralFeature;
					if(eAttribute.getEType() != mapType(pairId, value)) {
//						System.out.println("Attribute " + eAttribute.getName() + " typed to String due to conflicts");
						eAttribute.setEType(EcorePackage.Literals.ESTRING);
					} else {
//						System.out.println("No conflicts with attribute " + eAttribute.getName());
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

//		System.out.println("Metaclass created with name " + id);
		for(Pair pair : jsonObject.getPairs()) {
			String pairId = pair.getString();
			Value value = pair.getValue();

			createStructuralFeature(pairId, value, 1, eClass);
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
	private void createStructuralFeature(String pairId, Value value, int lowerBound, EClass eClass) {
		EStructuralFeature eStructuralFeature = null;
		EClassifier type = mapType(pairId, value);
		
		if(type instanceof EDataType) {
			eStructuralFeature = EcoreFactory.eINSTANCE.createEAttribute();
		} else {
			eStructuralFeature = EcoreFactory.eINSTANCE.createEReference();
			((EReference) eStructuralFeature).setContainment(true);
		}
		
		if(value instanceof ArrayValue) {
			eStructuralFeature.setUpperBound(-1);
		}

		if(eStructuralFeature != null) {
			eStructuralFeature.setName(pairId);
			eStructuralFeature.setLowerBound(lowerBound);
			eStructuralFeature.setEType(mapType(pairId, value)); 
			eClass.getEStructuralFeatures().add(eStructuralFeature);
//			System.out.println(eStructuralFeature.getClass().getSimpleName() + " created with name " + pairId + " type " + eStructuralFeature.getEType().getName() + " and lower bound " + lowerBound);
		}
	}

	/**
	 * Maps json types into ecore types
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	private EClassifier mapType(String id, Value value) {
		if (value instanceof StringValue) {
			return EcorePackage.Literals.ESTRING; 
		} else if (value instanceof NumberValue) {
			return EcorePackage.Literals.EINT; 
		} else if (value instanceof BooleanValue) {
			return EcorePackage.Literals.EBOOLEAN; 
		} else if (value instanceof ArrayValue) {
			ArrayValue arrayValue = (ArrayValue) value;
			return mapType(digestId(id), arrayValue.getValue().get(0)); // TODO: Consider all the list
		} else if (value instanceof JsonObjectValue) {
			return discoverMetaclass(digestId(id), ((JsonObjectValue) value).getValue());
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
