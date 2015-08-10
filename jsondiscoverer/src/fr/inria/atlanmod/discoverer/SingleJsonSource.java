package fr.inria.atlanmod.discoverer;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class extends JsonSource class and only allow JSON sources with one JSON definition
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class SingleJsonSource extends JsonSource {

	public SingleJsonSource(String name) {
		super(name);
	}
	
	@Override
	public void addJsonDef(File file) throws FileNotFoundException {
		if(getJsonData().size() > 0) 
			throw new IllegalStateException("SingleJsonSource can have only one source");
		super.addJsonDef(file);
	}
	
	@Override
	public void addJsonDef(String string) {
		if(getJsonData().size() > 0) 
			throw new IllegalStateException("SingleJsonSource can have only one source");
		super.addJsonDef(string);
	}
}
