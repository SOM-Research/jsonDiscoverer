/*******************************************************************************
 * Copyright (c) 2008, 2013
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Javier Canovas (me@jlcanovas.es) 
 *******************************************************************************/

package jsondiscoverer.zoo.test;

import java.io.File;

import jsondiscoverer.zoo.ModelDrawer;
import jsondiscoverer.zoo.ZooDiscoverer;

/**
 * This class shows how to call {@link ZooDiscoverer} and {@link ModelDrawer}
 * 
 * A link to the DOT executable is needed
 * 
 * @author Javier Canovas (me@jlcanovas.es) 
 *
 */
public class ExampleZooDiscoverer {
	private static final String DOT_EXE = "C:/Program Files (x86)/Graphviz 2.28/bin/dot.exe"; // Path to DOT executable
	private static final String WORKING_DIR = "./exampleData/workingDir";

	public static void main(String[] args) {
		ExampleZooDiscoverer.exampleDiscover();
	}
	
	public static void exampleDiscover() {
		ZooDiscoverer zooDiscoverer = new ZooDiscoverer(new File("./exampleData/zooDiscoverer"));
		zooDiscoverer.discover(true);
		
		ModelDrawer drawer = new ModelDrawer(
				new File(WORKING_DIR), 
				new File(DOT_EXE));
		drawer.traverseAndDrawFolder(new File("./exampleData/zooDiscoverer"), true);
	}
}
