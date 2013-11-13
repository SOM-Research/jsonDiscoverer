package fr.inria.atlanmod.json.discoverer.test;

import java.io.File;
import java.io.FileNotFoundException;

import fr.inria.atlanmod.discoverer.JsonMultiInjector;

public class JsonMultiInjectorTest {

	public static String INPUT_FILE1 = "./json/tan2.json";
	public static String METAMODEL_FILE1 = "./json/tan2.ecore";
	public static String COVERAGE_FILE1 = "./json/tan2.coverage.xmi";

	public static String INPUT_FILE2 = "./json/group/tan1A.json";
	public static String METAMODEL_FILE2 = "./json/group/tan1.ecore";
	public static String COVERAGE_FILE2 = "./json/group/tan1.coverage.xmi";

	public static String COMPOSITE_METAMODEL_FILE = "./json/tan-composed.ecore";
	public static String COMPOSITE_OUTPUT_FILE = "tan-composed.xmi";


	public static void main(String[] args) throws FileNotFoundException {
		JsonMultiInjector injector = new JsonMultiInjector();
		injector.multiInject(new File(INPUT_FILE1), new File(METAMODEL_FILE1), new File(COVERAGE_FILE1),
				new File(INPUT_FILE2), new File(METAMODEL_FILE2), new File(COVERAGE_FILE2),
				new File(COMPOSITE_METAMODEL_FILE), new File(COMPOSITE_OUTPUT_FILE));
	}

}
