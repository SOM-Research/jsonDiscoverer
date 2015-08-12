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

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

/**
 * This class represents a JSON source: a set of JSON documents with the same meaning
 * (i.e., set of JSON documents returned by the same JSON-based Web API)
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class JsonSource extends AbstractJsonSource {
	/**
	 * List of JSON documents
	 */
	protected List<JsonData> jsonData;
	
	/**
	 * Sets if the set of JSON documents are the result of computing an input
	 * JSON document (to support JSON-based Web APIs)
	 */
	public boolean withInput; 
	
	public JsonSource(String name) {
		super(name);
		this.jsonData = new ArrayList<JsonData>();
		this.withInput = false;
	}
	
	/**
	 * Gets the set of JSON documents linked to this source. 
	 * Warning: the returned list is mutable
	 * 
	 * @return The set of JSON documents (as {@link JsonData}
	 */
	protected List<JsonData> getJsonData() {
		return jsonData;
	}

	public boolean includesInput() {
		return this.withInput;
	}
	
	/**
	 * Adds a new JSON document as well as the input JSON document to get such a document (if given). 
	 * The input must be provided as a valid JSON object.
	 * 
	 * @param input The {@link Reader} from which obtain JSON document used as input
	 * @param output The {@link Reader} from which obtain the JSON document
	 * @throws IllegalArgumentException If reader is null 
	 * @return The {@link JsonData} with the JSON document and the input
	 */
	private JsonData buildJsonData(Reader input, Reader output) {
		if(output == null) 
			throw new IllegalArgumentException("The new document cannot be null and must exist");
		
		JsonObject inputJsonObject = null;
		if(input != null) {
			JsonElement inputElement = (new JsonParser()).parse(new JsonReader(input));
			if(!inputElement.isJsonObject())
				throw new JsonParseException("The input value must be a valid JSON object. Received " + input);
			inputJsonObject = inputElement.getAsJsonObject();
		}
		
		JsonElement rootElement = (new JsonParser()).parse(new JsonReader(output));
		JsonData data = new JsonData(inputJsonObject, rootElement);
		return data;
	}
	
	/**
	 * Adds a new JSON document as well as the input JSON document to get such a document. 
	 * 
	 * The input (if given) must be provided as a valid JSON object. 
	 * 
	 * Warning: Once the source has been created with input (or without), subsequent 
	 * calls must also include (or not) inputs
	 * 
	 * @param input The {@link Reader} from which obtain the JSON document used as input
	 * @param output The {@link Reader} from which obtain the JSON document
	 * @throws IllegalArgumentException If input is null
	 * @throws IllegalStateException If the JSON source was initially created to not hold input data
	 */
	public void addJsonData(Reader input, Reader output) {
		if(this.jsonData.size() > 0 && this.withInput == false && input != null) 
			throw new IllegalStateException("This JSON source was initially created to hold JSON data *without* input");
		if(this.jsonData.size() > 0 && this.withInput == true && input == null) 
			throw new IllegalStateException("This JSON source was initially created to hold JSON data *with* input");
		if(this.jsonData.size() == 0) 
			this.withInput = (input == null) ? false : true;
		
		JsonData data = buildJsonData(input, output);
		getJsonData().add(data);
	}
	
	/**
	 * Generates a list of JSON objects according to the {@link JsonData} of this source.
	 * 
	 * - If the source DOES include inputs, the list will include the set of input elements as roots for the 
	 * {@link JsonData}.
	 * - If the source DOES NOT include inputs, the list will include all the objects from {@link JsonData}
	 * 
	 * @return The list of {@link JsonObject}s
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
	
}
