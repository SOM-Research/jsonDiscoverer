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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

/**
 * This class represent a JSON source, which is represented by a set of JSON
 * definitions with the same meaning.
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonSource {
	/**
	 * A representative name for this source
	 */
	private String name;
	/**
	 * List of JSON definitions
	 */
	protected List<JsonElement> jsonDefs;
	
	/**
	 * If required, this class can also store the metamodel of the set of JSON definitions
	 */
	private EPackage metamodel;
	
	
	public JsonSource(String name) {
		setName(name);
		this.jsonDefs = new ArrayList<JsonElement>();
	}
	/**
	 * Gets the set of JSON definitions linked to this source. 
	 * Warning: the returned list is mutable
	 * 
	 * @return
	 */
	protected List<JsonElement> getJsonDefs() {
		return jsonDefs;
	}
	
	public void addJsonDef(File file, String input) throws FileNotFoundException {
		if(file == null || !file.exists()) 
			throw new IllegalArgumentException("File cannot be null and must exist");
		if(input == null || input.equals("")) 
			throw new IllegalArgumentException("Argument cannot be null or empty");
		
		JsonElement inputElement = (new JsonParser()).parse(new JsonReader(new StringReader(input)));
		if(!inputElement.isJsonObject())
			throw new JsonParseException("The input value must be a valid JSON object. Received " + input);

		JsonElement rootElement = (new JsonParser()).parse(new JsonReader(new FileReader(file)));
		inputElement.getAsJsonObject().add(getName() + "Output", rootElement);
		
		getJsonDefs().add(inputElement);
	}
	
	/**
	 * Adds a new JSON definition from a file with the JSON
	 * 
	 * @param file
	 * @throws FileNotFoundException 
	 */
	public void addJsonDef(File file) throws FileNotFoundException {
		if(file == null || !file.exists()) 
			throw new IllegalArgumentException("File cannot be null and must exist");
		JsonElement rootElement = (new JsonParser()).parse(new JsonReader(new FileReader(file)));
		getJsonDefs().add(rootElement);
	}
	
	/**
	 * Adds a new JSON definition from a string with the JSON
	 * 
	 * @param file
	 * @throws FileNotFoundException 
	 */
	public void addJsonDef(String string) throws FileNotFoundException {
		if(string == null || string.equals("")) 
			throw new IllegalArgumentException("Argument cannot be null or empty");
		JsonElement rootElement = (new JsonParser()).parse(new JsonReader(new StringReader(string)));
		getJsonDefs().add(rootElement);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name == null || name.equals("")) 
			throw new IllegalArgumentException("Name cannot be null or empty");
		this.name = name;
	}

	public EPackage getMetamodel() {
		return metamodel;
	}

	public void setMetamodel(EPackage metamodel) {
		if(metamodel == null) 
			throw new IllegalArgumentException("Metamodel cannot be null");
		this.metamodel = metamodel;
	}
	
	
}
