package fr.inria.atlanmod.json.discoverer.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import fr.inria.atlanmod.discoverer.JsonComposer;

public class JsonComposerTest {

	public static String TEST_FILE_1 = "./json/group/tan1.ecore";
	public static String TEST_JSON_FILE_1 = "./json/group/tan1A.json";
	public static String TEST_FILE_2 = "./json/tan2.ecore";
	public static String TEST_JSON_FILE_2 = "./json/tan2.json";
	public static String RESULT = "./tan-composed.ecore";

	public static void main(String[] args) throws FileNotFoundException  {

		ArrayList<File> fileList = new ArrayList<File>();
		File file1 = new File(TEST_FILE_1);
		fileList.add(file1);
		File file2 = new File(TEST_FILE_2);
		fileList.add(file2);

		HashMap<File, List<File>> examples = new HashMap<File, List<File>>();
		List<File> list1 = new ArrayList<File>();
		list1.add(new File(TEST_JSON_FILE_1));
		examples.put(file1, list1);
		List<File> list2 = new ArrayList<File>();
		list2.add(new File(TEST_JSON_FILE_2));
		examples.put(file2, list2);


		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		
		JsonComposer composer = new JsonComposer(fileList, examples);
		composer.compose(new File(RESULT));
	}
}
