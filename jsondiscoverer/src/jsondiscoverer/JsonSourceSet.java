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

import java.util.Collection;
import java.util.HashMap;

/**
 * This class is used to represent a set of {@link JsonSource} elements.
 * <p>
 * In the context of JSON Discoverer, it is used to model an API, which is composed of 
 * several JSON-based Web services. As so, the {@link JsonSourceSet} includes a set of
 * {@link JsonSource} elements and therefore each one collects the JSON documents for 
 * each JSON-based Web service.
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class JsonSourceSet extends AbstractJsonSource {
	/**
	 * The set of {@link JsonSource} elements collected by this class
	 */
	private HashMap<String, JsonSource> sources;

	/**
	 * Constructs a new {@link JsonSourceSet} with a name
	 * 
	 * @param name The name of the {@link JsonSourceSet}
	 */
	public JsonSourceSet(String name) {
		super(name);
		this.sources = new HashMap<String, JsonSource>();
	}
	
	/**
	 * Adds a {@link JsonSource} element to this {@link JsonSourceSet}.
	 * <p>
	 * In the context of JSON Discoverer, a {@link JsonSource} is considered a JSON-based
	 * Web service.
	 * 
	 * @param source The {@link JsonSource} element 
	 */
	public void addJsonSource(JsonSource source) {
		if(source == null) 
			throw new IllegalArgumentException("Source cannot be null");
		
		this.sources.put(source.getName(), source);
	}
	
	/**
	 * Returns the {@link JsonSource} elements for a given key
	 * 
	 * @param key The name of the {@link JsonSource}
	 * @return the {@link JsonSource}
	 */
	public JsonSource getJsonSource(String key) {
		return sources.get(key);
	}
	
	/**
	 * Returns all the {@link JsonSource} elements of this {@link JsonSourceSet}
	 *  
	 * @return Collection of {@link JsonSource}
	 */
	public Collection<JsonSource> getJsonSources() {
		return sources.values();
	}
}
