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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Simple class to keep track of the data of a particular call to a JSON service. 
 * <p> 
 * This class holds the input (as JSON) and the output (as JSON) of the service 
 * given such an input.
 * <p>
 * In the context of JSON discoverer, this class is used in {@link JsonSource} elements
 * to represent the data for JSON-based Web services.
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
class JsonData {
	/**
	 * The JSON input to get the data. 
	 * Note that it can be null
	 */
	private JsonObject input;
	
	/**
	 * The JSON data
	 */
	private JsonElement data;

	/**
	 * Constructus a new {@link JsonData} elements given an input (optional)
	 * and the output data.
	 * 
	 * @param input The input data
	 * @param data The (output) data
	 */
	public JsonData(JsonObject input, JsonElement data) {
		if(data == null) 
			throw new IllegalArgumentException("The data cannot be null");
		
		this.input = input;
		this.data = data;
	}

	/**
	 * Returns the input fo the {@link JsonData}
	 * 
	 * @return The input (as {@link JsonObject}
	 */
	public JsonObject getInput() {
		return input;
	}

	/**
	 * Returns the output fo the {@link JsonData}
	 * 
	 * @return The output (as {@link JsonObject}
	 */
	public JsonElement getData() {
		return data;
	}
}
