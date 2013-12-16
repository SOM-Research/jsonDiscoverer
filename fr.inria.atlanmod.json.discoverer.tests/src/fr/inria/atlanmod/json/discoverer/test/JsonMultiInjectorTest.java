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

import fr.inria.atlanmod.discoverer.JsonMultiInjector;
import fr.inria.atlanmod.discoverer.SingleJsonSource;
import fr.inria.atlanmod.json.discoverer.coverage.Coverage;
import fr.inria.atlanmod.json.discoverer.coverage.util.CoverageCreator;

public class JsonMultiInjectorTest {

	public static String INPUT_FILE1 = "./json/tan2.json";
	public static String METAMODEL_FILE1 = "./json/tan2.ecore";
	public static String COVERAGE_FILE1 = "./json/tan2.coverage.xmi";

	public static String INPUT_FILE2 = "./json/group/tan1A.json";
	public static String METAMODEL_FILE2 = "./json/group/tan1.ecore";
	public static String COVERAGE_FILE2 = "./json/group/tan1.coverage.xmi";

	public static String COMPOSITE_OUTPUT_FILE = "tan-composed.xmi";


	public static void main(String[] args) throws FileNotFoundException {
		SingleJsonSource source1 = new SingleJsonSource("source1");
		source1.addJsonDef(new File(INPUT_FILE1));
		
		Coverage coverage1 = CoverageCreator.loadCoverage(new File(COVERAGE_FILE1));

		SingleJsonSource source2 = new SingleJsonSource("source2");
		source2.addJsonDef(new File(INPUT_FILE2));

		Coverage coverage2 = CoverageCreator.loadCoverage(new File(COVERAGE_FILE2));
		
		JsonMultiInjector injector = new JsonMultiInjector(source1, coverage1, source2, coverage2);
		injector.multiInject(new File(COMPOSITE_OUTPUT_FILE));
	}

}
