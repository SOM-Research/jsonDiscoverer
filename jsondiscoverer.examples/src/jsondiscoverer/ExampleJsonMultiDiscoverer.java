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
 * Class with some example code to illustrate how to call the {@link JsonMultiDiscoverer}. 
 * 
 * @author Javier Canovas (me@jlcanovas.es) 
 *
 */
public class ExampleJsonMultiDiscoverer {

	public static void main(String[] args) throws FileNotFoundException {
		ExampleJsonMultiDiscoverer.exampleDiscover();
	}
	
	public static void exampleDiscover() throws FileNotFoundException  {
		JsonSource source1 = new JsonSource("stopPosition");
		source1.addJsonData(null, new FileReader(new File("./exampleData/multidiscoverer/source1/json1.json")));
		
		JsonSource source2 = new JsonSource("waitingTimeStop");
		source2.addJsonData(null, new FileReader(new File("./exampleData/multidiscoverer/source2/json1.json")));

		JsonSource source3 = new JsonSource("horariesStopLineDirection");
		source3.addJsonData(null, new FileReader(new File("./exampleData/multidiscoverer/source3/json1.json")));
				
		JsonSourceSet sourceSet = new JsonSourceSet("composed");
		sourceSet.addJsonSource(source1);
		sourceSet.addJsonSource(source2);
		sourceSet.addJsonSource(source3);
		
		JsonMultiDiscoverer composer = new JsonMultiDiscoverer(sourceSet);
		EPackage ePackage = composer.discover();
		
		ModelHelper.saveEPackage(ePackage, new File("./exampleData/multidiscoverer/exampleMultidiscoverer.ecore"));
		
		
	}

}
