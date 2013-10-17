package fr.inria.atlanmod.json.discoverer.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.inria.atlanmod.JsonStandaloneSetup;
import fr.inria.atlanmod.discoverer.JsonComposer;

public class JsonComposerTest {

	public static String TEST_FILE_1 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan1/tan1.ecore";
	public static String TEST_JSON_FILE_1 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan1/tan1A.json";
	public static String TEST_FILE_2 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan2.ecore";
	public static String TEST_JSON_FILE_2 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan2.json";
	public static String RESULT = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan-composed.ecore";

	public static void main(String[] args) {
		JsonStandaloneSetup.doSetup();

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

		JsonComposer composer = new JsonComposer(fileList, examples);
		composer.compose(new File(RESULT));
	}
}
