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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.emf.ecore.EPackage;

import jsondiscoverer.JsonComposer;
import jsondiscoverer.JsonSimpleDiscoverer;
import jsondiscoverer.JsonSource;
import jsondiscoverer.JsonSourceSet;
import jsondiscoverer.util.ModelHelper;

/**
 * Class with some example code to illustrate how to call the {@link JsonComposer} 
 * <p>
 * We recommend play with the code to learn how to use the toolset.
 * 
 * @author Javier Canovas (me@jlcanovas.es) 
 *
 */
public class ExampleJsonComposer {
	/**
	 * Main method to launch the example
	 * 
	 * @param args The main args. None is needed
	 * @throws FileNotFoundException Thrown if the file is not found
	 * @throws IOException Thrown if any problem IO-related is found
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ExampleJsonComposer.exampleCompose();
	}
	
	/**
	 * Launches the example
	 * 
	 * @throws FileNotFoundException Thrown if the file is not found
	 * @throws IOException Thrown if any problem IO-related is found
	 */
	public static void exampleCompose() throws FileNotFoundException, IOException {
		// 1. Discovering the domain for the first JSON
		Properties api1Info = new Properties();
		api1Info.load(new FileReader("./exampleData/composer/api1/source1/info.properties"));
		String shortname1 = api1Info.getProperty("shortname");

		JsonSource api1Source1 = new JsonSource(shortname1);
		Properties api1Source1Properties = new Properties();
		api1Source1Properties.load(new FileReader("./exampleData/composer/api1/source1/json1.properties"));
		api1Source1.addJsonData(
				new StringReader(api1Source1Properties.getProperty("input")), 
				new FileReader(new File("./exampleData/composer/api1/source1/json1.json")));
		
		JsonSimpleDiscoverer api1Discoverer = new JsonSimpleDiscoverer();
		EPackage ePackageApi1 = api1Discoverer.discover(api1Source1);
		ModelHelper.saveEPackage(ePackageApi1, new File("./exampleData/composer/exampleComposer-api1.ecore"));
		
		JsonSourceSet api1SourceSet = new JsonSourceSet(shortname1);
		api1SourceSet.addJsonSource(api1Source1);

		// 2. Discovering the domain for the second JSON
		Properties api2Info = new Properties();
		api1Info.load(new FileReader("./exampleData/composer/api2/source1/info.properties"));
		String shortname2 = api1Info.getProperty("shortname");

		JsonSource api2Source1 = new JsonSource(shortname2);
		Properties api2Source1Properties = new Properties();
		api2Source1Properties.load(new FileReader("./exampleData/composer/api2/source1/json1.properties"));
		api2Source1.addJsonData(
				new StringReader(api2Source1Properties.getProperty("input")), 
				new FileReader(new File("./exampleData/composer/api2/source1/json1.json")));
		
		JsonSimpleDiscoverer api2Discoverer = new JsonSimpleDiscoverer();
		EPackage ePackageApi2 = api2Discoverer.discover(api2Source1);
		ModelHelper.saveEPackage(ePackageApi2, new File("./exampleData/composer/exampleComposer-api2.ecore"));

		// 3. Discovering the general domain
		JsonSourceSet api2SourceSet = new JsonSourceSet(shortname2);
		api2SourceSet.addJsonSource(api2Source1);
		
		List<JsonSourceSet> sourceSetList = new ArrayList<JsonSourceSet>();
		sourceSetList.add(api1SourceSet);
		sourceSetList.add(api2SourceSet);
		
		JsonComposer composer = new JsonComposer(sourceSetList);
		EPackage composed = composer.compose();
		ModelHelper.saveEPackage(composed, new File("./exampleData/composer/exampleComposer-composed.ecore"));
		
	}

}
