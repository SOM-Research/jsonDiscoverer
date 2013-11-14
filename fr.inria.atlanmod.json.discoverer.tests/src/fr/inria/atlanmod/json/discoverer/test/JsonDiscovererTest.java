package fr.inria.atlanmod.json.discoverer.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import fr.inria.atlanmod.discoverer.JsonDiscoverer;
import fr.inria.atlanmod.discoverer.JsonSource;

public class JsonDiscovererTest {
	public static String TEST_FILE = "./json/group/tan1A.json";
	public static String RESULT_TEST_FILE = "./result.ecore";
	
	public static String TEST_FILE_2 = "./json/group/tan1B.json";
	public static String RESULT_TEST_FILE_2 = "./refined.ecore";

	public static String JSON_TEST = " { \"codeLieu\":\"CRQU4\", \"libelle\":\"Place du Cirque\" }";

	HashMap<String, EClass> eClasses = new HashMap<String, EClass>();

	/**
	 * Just for testing purposes
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException {
		JsonSource source = new JsonSource("test");
		source.addJsonDef(new File(TEST_FILE));
		
		JsonDiscoverer discoverer = new JsonDiscoverer();
		EPackage ePackage = discoverer.discoverMetamodel(source);
		
		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		
		Resource res2 = rset.createResource(URI.createFileURI(RESULT_TEST_FILE));
		res2.getContents().add(ePackage); 
		try {
			res2.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nRefining\n");
		JsonSource source2 = new JsonSource("test2");
		source2.addJsonDef(new File(TEST_FILE_2));
		
		discoverer = new JsonDiscoverer();
		EPackage ePackage2 = discoverer.refineMetamodel(ePackage, source2);

		res2 = rset.createResource(URI.createFileURI(RESULT_TEST_FILE));
		res2.getContents().add(ePackage2);
		try {
			res2.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
