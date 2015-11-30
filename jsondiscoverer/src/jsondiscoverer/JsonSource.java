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
 * This class represents a JSON source. A JSON source is represented by
 * a set of JSON documents with the same meaning (i.e., set of JSON documents 
 * returned by the same JSON-based Web API). 
 * <p>
 * In the context of JSON Discoverer, it is used to model a single JSON-based Web service.
 * <p>
 * JSON documents are represented as {@link JsonData} elements. 
 * <p>
 * The set of JSON documents can be retrieved by calling the method {@link JsonSource#getJsonData()}.
 * <p>
 * As {@link JsonData} elements can include input elements (to represent which JSON data
 * has triggered such JSON document), a {@link JsonSource} can include {@link JsonData} elements with
 * or without input. To know whether the {@link JsonData} elements included in the {@link JsonSource}
 * have input elements, you have to call the method {@link JsonSource#withInput}.
 * <p>
 * Note that all the {@link JsonData} elements included in a {@link JsonSource} have to include (or 
 * not include) inputs.
 * <p>
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
	
	/**
	 * Builds a new JsonSource with a name. Once created, the JsonSource has to 
	 * be populated by calling {@link JsonSource#addJsonData(Reader, Reader)}.
	 * 
	 * @param name The name of the JsonSource
	 */
	public JsonSource(String name) {
		super(name);
		this.jsonData = new ArrayList<JsonData>();
		this.withInput = false;
	}
	
	/**
	 * Gets the set of {@link JsonData} elements linked to this source. 
	 * Warning: the returned list is mutable
	 * 
	 * @return The set of JSON documents (as {@link JsonData}
	 */
	protected List<JsonData> getJsonData() {
		return jsonData;
	}

	/**
	 * Indicates if the JsonSource includes input elements. 
	 * <p>
	 * Note that all of them have to include (or not include) input elements
	 * 
	 * @return Boolean indicating the status
	 */
	public boolean includesInput() {
		return this.withInput;
	}
	
	/**
	 * Builds a {@link JsonData} element out of a JSON document representing the input and another
	 * JSON document representing the output. 
	 * <p>
	 * The input/output must be provided as a valid JSON objects.
	 * 
	 * @param input The {@link Reader} from which obtain JSON document used as input (optional)
	 * @param output The {@link Reader} from which obtain the JSON document
	 * @throws IllegalArgumentException If any reader is null 
	 * @return The {@link JsonData} with the JSON document and the input
	 */
	private JsonData buildJsonData(Reader input, Reader output) {
		if(output == null) 
			throw new IllegalArgumentException("The JSON document cannot be null and must exist");
		
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
	 * Adds a new JSON document as well as the input JSON document (optional) to get such a document. 
	 * <p>
	 * The input/output must be provided as a valid JSON objects.
	 * <p>
	 * Warning: Once the source has been created with input (or without), subsequent 
	 * calls must also include (or not) inputs
	 * 
	 * @param input The {@link Reader} from which obtain the JSON document used as input (optional)
	 * @param output The {@link Reader} from which obtain the JSON document
	 * @throws IllegalArgumentException If input is null
	 * @throws IllegalStateException If the JSON source was initially created to not hold input data
	 */
	public void addJsonData(Reader input, Reader output) {
		if(output == null) 
			throw new IllegalArgumentException("output cannot be null");
		
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
	 * <ul>
	 * <li>If the source DOES include inputs, the list will include the set of input elements as roots for the
	 * {@link JsonData}. For instance:</li> 
	 * </ul>
	 * 
	 * <pre>
	 * - [input JSON element 1]
	 *   +-- Output
	 *       +-- [output JSON element 1]  
	 * - [input JSON element 2]
	 *   +-- Output
	 *       +-- [output JSON element 2]  
	 * -...
	 * </pre>
	 * <ul>
	 * <li>If the source DOES NOT include inputs, the list will include all the objects from {@link JsonData}. For instance</li>
	 * </ul>
	 * <pre>
	 * - [output JSON element 1]  
	 * - [output JSON element 2] 
	 * - [output JSON element 3] 
	 * -...
	 * </pre>
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
