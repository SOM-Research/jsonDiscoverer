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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.eclipse.emf.ecore.EPackage;

import jsondiscoverer.util.ModelHelper;

/**
 * Class with some example code to illustrate how to call the {@link JsonDiscoverer}. 
 * 
 * @author Javier Canovas (me@jlcanovas.es) 
 *
 */
public class ExampleJsonDiscoverer {
	
	public static void main(String[] args) throws FileNotFoundException {
		ExampleJsonDiscoverer.exampleDiscover();
		ExampleJsonDiscoverer.exampleRefine();
	}
	
	public static void exampleDiscover() throws FileNotFoundException {
		JsonSource source = new JsonSource("example");
		source.addJsonData(null, new FileReader(new File("./exampleData/discoverer/json1A.json")));

		JsonDiscoverer discoverer = new JsonDiscoverer();
		EPackage ePackage = discoverer.discover(source);
		
		ModelHelper.saveEPackage(ePackage, new File("./exampleData/discoverer/exampleDiscover.ecore"));
	}

	public static void exampleRefine() throws FileNotFoundException {
		JsonSource source = new JsonSource("example");
		source.addJsonData(null, new FileReader(new File("./exampleData/discoverer/json1A.json")));

		JsonDiscoverer discoverer = new JsonDiscoverer();
		EPackage ePackage0 = discoverer.discover(source);

		JsonSource source2 = new JsonSource("example");
		source2.addJsonData(null, new FileReader(new File("./exampleData/discoverer/json1B.json")));

		discoverer = new JsonDiscoverer();
		EPackage ePackage = discoverer.refine(ePackage0, source2);
		
		ModelHelper.saveEPackage(ePackage, new File("./exampleData/discoverer/exampleRefine.ecore"));
	}

}
