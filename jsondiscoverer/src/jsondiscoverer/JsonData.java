package jsondiscoverer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Simple class to keep the data of a particular call to a JSON service. This class holds
 * the input (as JSON) and the output (as JSON) of the service given such an input.
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
class JsonData {
	private JsonObject input;
	
	private JsonElement data;

	public JsonData(JsonObject input, JsonElement data) {
		this.input = input;
		this.data = data;
	}

	public JsonObject getInput() {
		return input;
	}

	public JsonElement getData() {
		return data;
	}
	
}
