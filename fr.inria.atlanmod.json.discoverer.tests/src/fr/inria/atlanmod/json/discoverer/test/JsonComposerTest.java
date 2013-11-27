/*******************************************************************************
 * Copyright (c) 2008, 2013
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Javier Canovas (javier.canovas@inria.fr) 
 *******************************************************************************/

package fr.inria.atlanmod.json.discoverer.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import fr.inria.atlanmod.discoverer.JsonComposer;
import fr.inria.atlanmod.discoverer.JsonSource;

public class JsonComposerTest {

	public static String TEST_FILE_1 = "./json/group/tan1.ecore";
	public static String TEST_JSON_FILE_1 = "./json/group/tan1A.json";
	public static String COVERAGE_FILE1 = "./json/group/tan1.coverage.xmi";
	
	public static String TEST_FILE_2 = "./json/tan2.ecore";
	public static String TEST_JSON_FILE_2 = "./json/tan2.json";
	public static String COVERAGE_FILE2 = "./json/tan2.coverage.xmi";
	
	public static String RESULT = "./tan-composed.ecore";

	public static void main(String[] args) throws FileNotFoundException  {

		JsonSource source1 = new JsonSource("source1");
		source1.addJsonDef(new File(TEST_JSON_FILE_1));

		JsonSource source2 = new JsonSource("source2");
		source2.addJsonDef(new File(TEST_JSON_FILE_2));
				
		List<File> coveragePaths = new ArrayList<File>();
		coveragePaths.add(new File(COVERAGE_FILE1));
		coveragePaths.add(new File(COVERAGE_FILE2));

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		
		List<JsonSource> sources = new ArrayList<JsonSource>();
		sources.add(source1);
		sources.add(source2);
		
		JsonComposer composer = new JsonComposer(sources);
		composer.compose("composed", new File(RESULT));
		composer.saveCoverage(coveragePaths);
	}
}
