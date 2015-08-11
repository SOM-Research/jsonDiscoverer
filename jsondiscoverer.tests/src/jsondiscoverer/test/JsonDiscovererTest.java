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

package jsondiscoverer.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import jsondiscoverer.JsonDiscoverer;
import jsondiscoverer.JsonSource;
import jsondiscoverer.zoo.ModelDrawer;

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
		source.addJsonData(null, new FileReader(new File(TEST_FILE)));

		JsonDiscoverer discoverer = new JsonDiscoverer();
		EPackage ePackage = discoverer.discover(source);

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
		JsonSource source2 = new JsonSource("test");
		source2.addJsonData(null, new FileReader(new File(TEST_FILE_2)));

		discoverer = new JsonDiscoverer();
		EPackage ePackage2 = discoverer.refine(ePackage, source2);

		res2 = rset.createResource(URI.createFileURI(RESULT_TEST_FILE_2));
		res2.getContents().add(ePackage2);
		try {
			res2.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ModelDrawer drawer = new ModelDrawer(
				new File("C:/Users/jcanovasi/git3/json-discoverer/jsondiscoverer.tests/json/group"),
				new File("C:/Program Files (x86)/Graphviz 2.28/bin/dot.exe"));
		List<EObject> toDraw = new ArrayList<>();

		for(EClassifier eClassifier : ePackage.getEClassifiers()){
			if (!(eClassifier instanceof EAnnotation) && (eClassifier instanceof EModelElement)) {
				EModelElement eModelElement = (EModelElement) eClassifier;
				eModelElement.getEAnnotations().clear();
			}
		}
		toDraw.add(ePackage);
		drawer.draw(toDraw, new File("./json/group/group.jpg"));
	}
}
