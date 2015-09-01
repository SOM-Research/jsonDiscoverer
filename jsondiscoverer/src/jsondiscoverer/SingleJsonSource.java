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

import java.io.FileNotFoundException;
import java.io.Reader;

/**
 * This class extends JsonSource class and only allows for JSON sources
 * with ONE JSON document and WITHOUT input
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class SingleJsonSource extends JsonSource {

	public SingleJsonSource(String name) {
		super(name);
	}
	
	public void addJsonData(Reader output) throws FileNotFoundException {
		if(output == null) 
			throw new IllegalArgumentException("output cannot be null");
		
		if(getJsonData().size() > 0) 
			throw new IllegalStateException("SingleJsonSource can have only one source");
		super.addJsonData(null, output);
	}
}
