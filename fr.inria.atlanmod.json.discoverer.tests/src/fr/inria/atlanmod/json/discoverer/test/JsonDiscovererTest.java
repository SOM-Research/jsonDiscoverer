package fr.inria.atlanmod.json.discoverer.test;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import fr.inria.atlanmod.JsonStandaloneSetup;
import fr.inria.atlanmod.discoverer.JsonDiscoverer;

public class JsonDiscovererTest {
	public static String TEST_FILE = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan1/tan1A.json";
	public static String RESULT_TEST_FILE = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan1/tan1B.ecore";
	
	public static String TEST_FILE_2 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan1/tan1B.json";
	public static String RESULT_TEST_FILE_2 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan1/tan1B.ecore";

	public static String JSON_TEST = " { \"codeLieu\":\"CRQU4\", \"libelle\":\"Place du Cirque\" }";

	HashMap<String, EClass> eClasses = new HashMap<String, EClass>();

	/**
	 * Just for testing purposes
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JsonStandaloneSetup.doSetup();

		JsonDiscoverer discoverer = new JsonDiscoverer();
		EPackage ePackage = discoverer.discoverMetamodel(JSON_TEST);
		
//		EPackage ePackage = discoverer.discoverMetamodel(new File(TEST_FILE));

		ResourceSet rset = new ResourceSetImpl();
		Resource res2 = rset.createResource(URI.createFileURI(RESULT_TEST_FILE));
		res2.getContents().add(ePackage);
		try {
			res2.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		
//		System.out.println("\nRefining\n");
//		
//		discoverer = new JsonDiscoverer();
//		EPackage ePackage2 = discoverer.refineMetamodel(new File(TEST_FILE_2), new File(RESULT_TEST_FILE));

//		res2 = rset.createResource(URI.createFileURI(RESULT_TEST_FILE));
//		res2.getContents().add(ePackage2);
//		try {
//			res2.save(null);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
