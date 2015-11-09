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

import jsondiscoverer.JsonAdvancedDiscoverer;
import jsondiscoverer.JsonSource;
import jsondiscoverer.JsonSourceSet;
import jsondiscoverer.util.ModelHelper;

/**
 * Class with some example code to illustrate how to call the {@link JsonAdvancedDiscoverer}. 
 * <p>
 * We recommend play with the code to learn how to use the toolset.
 * 
 * @author Javier Canovas (me@jlcanovas.es) 
 *
 */
public class ExampleJsonAdvancedDiscoverer {

	/**
	 * Main method to launch the example
	 * 
	 * @throws FileNotFoundException Thrown if the file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		ExampleJsonAdvancedDiscoverer.exampleDiscover();
	}
	
	/**
	 * Launches the example
	 * 
	 * @throws FileNotFoundException Thrown if the file is not found
	 */
	public static void exampleDiscover() throws FileNotFoundException  {
		JsonSource source1 = new JsonSource("stopPosition");
		source1.addJsonData(null, new FileReader(new File("./exampleData/advancedDiscoverer/source1/json1.json")));
		
		JsonSource source2 = new JsonSource("waitingTimeStop");
		source2.addJsonData(null, new FileReader(new File("./exampleData/advancedDiscoverer/source2/json1.json")));

		JsonSource source3 = new JsonSource("horariesStopLineDirection");
		source3.addJsonData(null, new FileReader(new File("./exampleData/advancedDiscoverer/source3/json1.json")));
				
		JsonSourceSet sourceSet = new JsonSourceSet("composed");
		sourceSet.addJsonSource(source1);
		sourceSet.addJsonSource(source2);
		sourceSet.addJsonSource(source3);
		
		JsonAdvancedDiscoverer composer = new JsonAdvancedDiscoverer(sourceSet);
		EPackage ePackage = composer.discover();
		
		ModelHelper.saveEPackage(ePackage, new File("./exampleData/advancedDiscoverer/exampleAdvancedDiscoverer.ecore"));
		
		
	}

}
