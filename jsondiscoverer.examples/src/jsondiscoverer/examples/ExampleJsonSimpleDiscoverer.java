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


package jsondiscoverer.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.eclipse.emf.ecore.EPackage;

import jsondiscoverer.JsonSimpleDiscoverer;
import jsondiscoverer.JsonSource;
import jsondiscoverer.util.ModelHelper;

/**
 * Class with some example code to illustrate how to call the {@link JsonSimpleDiscoverer}. 
 * <p>
 * We recommend play with the code to learn how to use the toolset.
 * 
 * @author Javier Canovas (me@jlcanovas.es) 
 *
 */
public class ExampleJsonSimpleDiscoverer {
	/**
	 * Main method to launch the example
	 * 
	 * @throws FileNotFoundException Thrown if the file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		ExampleJsonSimpleDiscoverer.exampleDiscover();
		ExampleJsonSimpleDiscoverer.exampleRefine();
	}
	
	/**
	 * Launches the example
	 * 
	 * @throws FileNotFoundException Thrown if the file is not found
	 */
	public static void exampleDiscover() throws FileNotFoundException {
		JsonSource source = new JsonSource("example");
		source.addJsonData(null, new FileReader(new File("./exampleData/simpleDiscoverer/json1A.json")));

		JsonSimpleDiscoverer discoverer = new JsonSimpleDiscoverer();
		EPackage ePackage = discoverer.discover(source);
		
		ModelHelper.saveEPackage(ePackage, new File("./exampleData/simpleDiscoverer/exampleSimpleDiscover.ecore"));
	}

	public static void exampleRefine() throws FileNotFoundException {
		JsonSource source = new JsonSource("example");
		source.addJsonData(null, new FileReader(new File("./exampleData/simpleDiscoverer/json1A.json")));

		JsonSimpleDiscoverer discoverer = new JsonSimpleDiscoverer();
		EPackage ePackage0 = discoverer.discover(source);

		JsonSource source2 = new JsonSource("example");
		source2.addJsonData(null, new FileReader(new File("./exampleData/simpleDiscoverer/json1B.json")));

		discoverer = new JsonSimpleDiscoverer();
		EPackage ePackage = discoverer.refine(ePackage0, source2);
		
		ModelHelper.saveEPackage(ePackage, new File("./exampleData/simpleDiscoverer/exampleSimpleRefine.ecore"));
	}

}
