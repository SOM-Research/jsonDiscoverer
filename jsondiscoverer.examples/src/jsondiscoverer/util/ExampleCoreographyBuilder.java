/*******************************************************************************
 * Copyright (c) 2008, 2015
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Javier Canovas (me@jlcanovas.es) 
 *******************************************************************************/


package jsondiscoverer.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import jsondiscoverer.CoreographyBuilder;

/**
 * Example implementation to illustrate how to use the CoreographyBuilder
 * 
 * The result of is a text file formatted for the sequence diagram generator located
 * at http://bramp.github.io/js-sequence-diagrams/
 * 
 * @author Javier Canovas (me@jlcanovas.es) 
 *
 */
public class ExampleCoreographyBuilder {
	public static void main(String[] args) throws FileNotFoundException {
		ExampleCoreographyBuilder.exampleCalculate();
	}
	
	public static void exampleCalculate() throws FileNotFoundException {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		ResourceSet rset = new ResourceSetImpl();
		Resource res1 = rset.getResource(URI.createFileURI("./exampleData/coreographyBuilder/zoo.ecore"), true);
		try {
			res1.load(null); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		EPackage package1 = (EPackage) res1.getContents().get(0);
		
		EClass source = null;
		EClass target = null;
		
		for(EClassifier eClassifier : package1.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				if(eClass.getName().equals("pathCalculatorInput")) {
					source = eClass;
				}
				if(eClass.getName().equals("Ligne")) {
					target = eClass;
				}
				
			}
		}
		
		CoreographyBuilder builder = new CoreographyBuilder(package1);
		String result = builder.calculate(source, target);
		PrintWriter pw = new PrintWriter(new File("./exampleData/coreographyBuilder/builder.txt"));
		pw.print(result);
		pw.close();
		
	}
}
