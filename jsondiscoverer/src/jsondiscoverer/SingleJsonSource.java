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

/**
 * This class extends JsonSource class and only allows for JSON sources
 * with ONE JSON document and WITHOUT input.
 * <p>
 * In the context of the JSON Discoverer, it is used in the {@link JsonInjector} to 
 * inject the model out of a JSON document without input.
 *  * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class SingleJsonSource extends JsonSource {

	/**
	 * Constructs a {@link SingleJsonSource} element
	 * 
	 * @param name The name of the {@link SingleJsonSource}
	 */
	public SingleJsonSource(String name) {
		super(name);
	}
	
	/**
	 * Adds a new JSON document to the source.
	 * <p>
	 * In the context of the JSON discoverer, the provided JSON document represents
	 * the output of a JSON-based Web service   
	 * <p>
	 * The data must be provided as a valid JSON object.
	 * 
	 * @param output The JSON document
	 */
	public void addJsonData(Reader output) {
		if(output == null) 
			throw new IllegalArgumentException("output cannot be null");
		if(getJsonData().size() > 0) 
			throw new IllegalStateException("SingleJsonSource can have only one source");
		
		super.addJsonData(null, output);
	}
}
