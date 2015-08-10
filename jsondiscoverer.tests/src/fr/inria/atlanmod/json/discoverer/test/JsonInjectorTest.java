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
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import fr.inria.atlanmod.discoverer.JsonInjector;
import fr.inria.atlanmod.discoverer.SingleJsonSource;

public class JsonInjectorTest {

	public static String INPUT_FILE = "./json/issues/cyril.json";
	public static String OUTPUT_FILE = "./json/issues/cyril.xmi";
	public static String METAMODEL_FILE = "./cyril.ecore";


	public static void main(String[] args) throws FileNotFoundException {
		SingleJsonSource source = new SingleJsonSource("test");
		source.addJsonDef(new File(INPUT_FILE));
		
		JsonInjector injector = new JsonInjector(source);
		List<EObject> result = injector.inject();
		saveModel(result, OUTPUT_FILE);
	}
	
	public static void saveModel(List<EObject> elements, String resultPath) {
		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource res2 = rset.createResource(URI.createFileURI(resultPath));
		
		for(EObject eObject : elements) {
			res2.getContents().add(eObject); 
		}

		try {
			res2.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveMetamodel(EPackage ePackage, String resultPath) {
		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		
		Resource res2 = rset.createResource(URI.createFileURI(resultPath));
		res2.getContents().add(ePackage); 
		try {
			res2.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
