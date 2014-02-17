package fr.inria.atlanmod.discoverer;

import java.util.Collection;
import java.util.HashMap;

/**
 * This class represents a set of JSON sources (i.e., several examples of JSON-based API calls)
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
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
