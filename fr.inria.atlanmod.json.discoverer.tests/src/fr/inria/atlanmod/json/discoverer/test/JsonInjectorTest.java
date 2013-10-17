package fr.inria.atlanmod.json.discoverer.test;

import java.io.File;

import fr.inria.atlanmod.JsonStandaloneSetup;
import fr.inria.atlanmod.discoverer.JsonInjector;

public class JsonInjectorTest {

	public static String INPUT_FILE = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan2.json";
	public static String OUTPUT_FILE = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan2.json.xmi";
	public static String METAMODEL_FILE = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan2.ecore";


	public static void main(String[] args) {
		JsonStandaloneSetup.doSetup();

		JsonInjector injector = new JsonInjector();
		injector.inject(new File(INPUT_FILE), new File(METAMODEL_FILE), new File(OUTPUT_FILE));
	}
}
