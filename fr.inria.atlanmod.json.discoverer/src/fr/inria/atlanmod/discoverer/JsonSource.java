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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
public class JsonSource extends AbstractJsonSource {
	/**
	 * List of JSON definitions
	 */
	protected List<JsonData> jsonData;
	
	public boolean withInput; 
	
	public JsonSource(String name) {
		super(name);
		this.jsonData = new ArrayList<JsonData>();
		this.withInput = false;
	}
	/**
	 * Gets the set of JSON definitions linked to this source. 
	 * Warning: the returned list is mutable
	 * 
	 * @return
	 */
	protected List<JsonData> getJsonData() {
		return jsonData;
	}
	
	/**
	 * Adds a new JSON definition from a file with the JSON as well as the input to get such a
	 * definition. The input must be provided as a valid JSON object.
	 * 
	 * Warning: If the JSON source already includes JSON Data, the provision of input must match
	 * 
	 * @param file
	 * @param input
	 * @throws FileNotFoundException
	 */
	public void addJsonDef(File file, String input) throws FileNotFoundException {
		if(file == null || !file.exists()) 
			throw new IllegalArgumentException("File cannot be null and must exist");
		if(input == null || input.equals("")) 
			throw new IllegalArgumentException("Argument cannot be null or empty");
		if(this.jsonData.size() > 0 && this.withInput == false) 
			throw new IllegalStateException("This JSON source was created to hold JSON data *without* input");
		
		JsonElement inputElement = (new JsonParser()).parse(new JsonReader(new StringReader(input)));
		if(!inputElement.isJsonObject())
			throw new JsonParseException("The input value must be a valid JSON object. Received " + input);

		JsonElement rootElement = (new JsonParser()).parse(new JsonReader(new FileReader(file)));
		JsonData data = new JsonData(inputElement.getAsJsonObject(), rootElement);
		getJsonData().add(data);
		this.withInput = true;
	}
		
	/**
	 * Adds a new JSON definition from a string with the JSON and a provided input
	 * 
	 * @param file
	 * @param input
	 * @throws FileNotFoundException 
	 */
	public void addJsonDef(String string, String input) {
		if(string == null || string.equals("")) 
			throw new IllegalArgumentException("Argument cannot be null or empty");
		if(this.jsonData.size() > 0 && this.withInput == true) 
			throw new IllegalStateException("This JSON source was created to hold JSON data *with* input");
		if(input == null || input.equals("")) 
			throw new IllegalArgumentException("Argument cannot be null or empty");
		

		JsonElement inputElement = (new JsonParser()).parse(new JsonReader(new StringReader(input)));
		if(!inputElement.isJsonObject())
			throw new JsonParseException("The input value must be a valid JSON object. Received " + input);

		JsonElement rootElement = (new JsonParser()).parse(new JsonReader(new StringReader(string)));
		JsonData data = new JsonData(inputElement.getAsJsonObject(), rootElement);
		getJsonData().add(data);
		this.withInput = true;
	}	
	
	/**
	 * Adds a new JSON definition from a file with the JSON
	 * 
	 * Warning: If the JSON source already includes JSON Data, the provision of input must match
	 * 
	 * @param file
	 * @throws FileNotFoundException 
	 */
	public void addJsonDef(File file) throws FileNotFoundException {
		if(file == null || !file.exists()) 
			throw new IllegalArgumentException("File cannot be null and must exist");
		if(this.jsonData.size() > 0 && this.withInput == true) 
			throw new IllegalStateException("This JSON source was created to hold JSON data *with* input");
		JsonElement rootElement = (new JsonParser()).parse(new JsonReader(new FileReader(file)));
		JsonData data = new JsonData(null, rootElement);
		getJsonData().add(data);
		this.withInput = false;
	}
	
	/**
	 * Adds a new JSON definition from a string with the JSON
	 * 
	 * @param file
	 * @throws FileNotFoundException 
	 */
	public void addJsonDef(String string) {
		if(string == null || string.equals("")) 
			throw new IllegalArgumentException("Argument cannot be null or empty");
		if(this.jsonData.size() > 0 && this.withInput == true) 
			throw new IllegalStateException("This JSON source was created to hold JSON data *with* input");
		JsonElement rootElement = (new JsonParser()).parse(new JsonReader(new StringReader(string)));
		JsonData data = new JsonData(null, rootElement);
		getJsonData().add(data);
		this.withInput = false;
	}	
	
	/**
	 * Computes a list of JSON objects according to the data of this source.
	 * If the source includes inputs, the list will include the set of input elements as roots for the 
	 * JSON data of each source provided.
	 * If the source does not include inputs, the listt will include all the objects from the JSON data
	 * of each source provided.
	 * 
	 * @return
	 */
	public List<JsonObject> getSourceDigested() {
		List<JsonObject> result = new ArrayList<JsonObject>();
		if(this.withInput == true) {
			for(JsonData data : this.getJsonData()) {
				JsonObject inputElement = data.getInput();
				JsonElement outputElement = data.getData();
				inputElement.getAsJsonObject().add(getName() + "Output", outputElement);
				result.add(inputElement);
			}
		} else {
			for(JsonData data : this.getJsonData()) {
				JsonElement outputElement = data.getData();
				if (outputElement.isJsonArray()) {
					for(int i = 0; i < outputElement.getAsJsonArray().size(); i++)
						if(outputElement.getAsJsonArray().get(i).isJsonObject())
							result.add(outputElement.getAsJsonArray().get(i).getAsJsonObject());
				} else if(outputElement.isJsonObject()) {
					result.add(outputElement.getAsJsonObject());
				} 
			}
		}
		return result;
	}
	
	public boolean includesInput() {
		return this.withInput;
	}
}
