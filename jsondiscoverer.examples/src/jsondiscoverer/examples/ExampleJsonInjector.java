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
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import jsondiscoverer.JsonInjector;
import jsondiscoverer.SingleJsonSource;
import jsondiscoverer.util.ModelHelper;

/**
 * Class with some example code to illustrate how to call the {@link JsonInjector}. 
 * <p>
 * We recommend play with the code to learn how to use the toolset.
 * 
 * @author Javier Canovas (me@jlcanovas.es) 
 *
 */
public class ExampleJsonInjector {
	
	/**
	 * Main method to launch the example
	 * 
	 * @param args The main args. None is needed
	 * @throws FileNotFoundException Thrown if the file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		ExampleJsonInjector.exampleInject();
	}
	
	/**
	 * Launches the example
	 * 
	 * @throws FileNotFoundException Thrown if the file is not found
	 */
	public static void exampleInject() throws FileNotFoundException {
		SingleJsonSource source = new SingleJsonSource("Stop");
		source.addJsonData(null, new FileReader(new File("./exampleData/injector/json.json")));
		
		JsonInjector injector = new JsonInjector(source);
		List<EObject> result = injector.inject();
		
		ModelHelper.saveModel(result, new File("./exampleData/injector/exampleInjector.xmi"));
	}
	
}
