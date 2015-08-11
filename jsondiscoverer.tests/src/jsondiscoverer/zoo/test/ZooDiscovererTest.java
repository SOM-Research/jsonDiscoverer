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

public class ZooDiscovererTest {
	public static void main(String[] args) {
		ZooDiscoverer zooDiscoverer = new ZooDiscoverer(new File("../jsondiscoverer.zoo/zooMini"));
		zooDiscoverer.discover(true);
		
		ModelDrawer drawer = new ModelDrawer(
				new File("C:/Users/jcanovasi/git3/json-discoverer/jsondiscoverer.zoo/workingDir"), 
				new File("C:/Program Files (x86)/Graphviz 2.28/bin/dot.exe"));
		drawer.traverseAndDrawFolder(new File("../jsondiscoverer.zoo/zooMini"), true);
	}
}
