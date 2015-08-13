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
 * This simple class represents a set of JSON sources 
 * (i.e., several examples of different JSON-based API calls)
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 * @version 2.0.0
 *
 */
public class JsonSourceSet extends AbstractJsonSource {
	private HashMap<String, JsonSource> sources;

	public JsonSourceSet(String name) {
		super(name);
		this.sources = new HashMap<String, JsonSource>();
	}
	
	public void addJsonSource(JsonSource source) {
		if(source == null) 
			throw new IllegalArgumentException("Source cannot be null");
		this.sources.put(source.getName(), source);
	}
	
	public JsonSource getJsonSource(String key) {
		return sources.get(key);
	}
	
	public Collection<JsonSource> getJsonSources() {
		return sources.values();
	}
}
