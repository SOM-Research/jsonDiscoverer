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



package jsondiscoverer.util.test;

import static org.junit.Assert.*;

import java.io.File;
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
import org.junit.Test;

import jsondiscoverer.CoreographyBuilder;

public class TestCoreographyBuilder {
	
	public static String EXPECTED = "User->GOOGLEMAPS:pathCalculator(origin : EString, destination : EString, waypoints : EString, sensor : EBoolean)\n" +
		"GOOGLEMAPS-->User:response(lat : ESTRING, lng : ESTRING)\n" +
		"Note right of User:LOOP\n" +
		"Note right of User: lat -> lat, lng -> lon\n" +
		"User->TAN:stopPosition(lat : EInt, lon : EInt)\n" +
		"TAN-->User:response(numLigne : ESTRING, directionSens1 : ESTRING, directionSens2 : ESTRING, accessible : EBOOLEAN, etatTrafic : EINT, libelleTrafic : ESTRING, typeLigne : EINT)";	

	@Test
	public void testCalculate() {
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		ResourceSet rset = new ResourceSetImpl();
		Resource res1 = rset.getResource(URI.createFileURI("./testData/coreographyBuilder/zoo.ecore"), true);
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
		assertEquals(EXPECTED, result);
	}

}
