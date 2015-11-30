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

package jsondiscoverer.examples.zoo;

import java.io.File;
import java.io.FileNotFoundException;

import jsondiscoverer.zoo.ModelDrawer;
import jsondiscoverer.zoo.ZooDiscoverer;

/**
 * This class shows how to call {@link ZooDiscoverer} and {@link ModelDrawer}
 * <p>
 * A link to the DOT executable is needed (see {@link ExampleZooDiscoverer#DOT_EXE}
 * <p>
 * We recommend play with the code to learn how to use the toolset.
 * 
 * @author Javier Canovas (me@jlcanovas.es) 
 *
 */
public class ExampleZooDiscoverer {
	/**
	 * Directory where the DOT executable can be found
	 */
	private static final String DOT_EXE = "C:/Program Files (x86)/Graphviz 2.28/bin/dot.exe"; // Path to DOT executable
	
	/**
	 * Working directory where the files can be stored
	 */
	private static final String WORKING_DIR = "./exampleData/workingDir";
	
	/**
	 * Main method to launch the example
	 * 
	 * @param args The main args. None is needed
	 * @throws FileNotFoundException Thrown if the file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		ExampleZooDiscoverer.exampleDiscover();
	}
	
	/**
	 * Launches the example
	 * 
	 * @throws FileNotFoundException Thrown if the file is not found
	 */	
	public static void exampleDiscover() throws FileNotFoundException {
		ZooDiscoverer zooDiscoverer = new ZooDiscoverer(new File("./exampleData/zooDiscoverer"));
		zooDiscoverer.discover(true);
		
		ModelDrawer drawer = new ModelDrawer(
				new File(WORKING_DIR), 
				new File(DOT_EXE));
		drawer.traverseAndDrawFolder(new File("./exampleData/zooDiscoverer"), true);
	}
}
