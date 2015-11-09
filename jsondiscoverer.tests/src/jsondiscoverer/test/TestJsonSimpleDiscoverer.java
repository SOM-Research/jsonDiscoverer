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


package jsondiscoverer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Test;

import jsondiscoverer.JsonSimpleDiscoverer;
import jsondiscoverer.JsonSource;

/**
 * Test case for {@link JsonSimpleDiscoverer}
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class TestJsonSimpleDiscoverer {
	
	/**
	 * Test case for {@link JsonSimpleDiscoverer#discover(JsonSource)}
	 * 
	 * @throws FileNotFoundException Thrown if the file is not found 
	 */
	@Test
	public void testDiscover() throws FileNotFoundException {
		JsonSource source = new JsonSource("Stop");
		source.addJsonData(null, new FileReader(new File("./testData/simpleDiscoverer/json1A.json")));

		JsonSimpleDiscoverer discoverer = new JsonSimpleDiscoverer();
		EPackage ePackage = discoverer.discover(source);

		EClass ligneClass = (EClass) ePackage.getEClassifier("Ligne");
		assertNotNull(ligneClass);
		assertNotNull(ligneClass.getEStructuralFeature("numLigne"));
		assertEquals("EString", ligneClass.getEStructuralFeature("numLigne").getEType().getName());
		assertEquals(1, ligneClass.getEStructuralFeature("numLigne").getUpperBound());
		assertEquals(1, ligneClass.getEStructuralFeature("numLigne").getLowerBound());
		
		EClass stopClass = (EClass) ePackage.getEClassifier("Stop");
		assertNotNull(stopClass);
		assertNotNull(stopClass.getEStructuralFeature("codeLieu"));
		assertEquals("EString", stopClass.getEStructuralFeature("codeLieu").getEType().getName());
		assertEquals(1, stopClass.getEStructuralFeature("codeLieu").getUpperBound());
		assertEquals(1, stopClass.getEStructuralFeature("codeLieu").getLowerBound());
		
		assertNotNull(stopClass.getEStructuralFeature("libelle"));
		assertEquals("EString", stopClass.getEStructuralFeature("libelle").getEType().getName());
		assertEquals(1, stopClass.getEStructuralFeature("libelle").getUpperBound());
		assertEquals(1, stopClass.getEStructuralFeature("libelle").getLowerBound());
		
		assertNotNull(stopClass.getEStructuralFeature("distance"));
		assertEquals("EString", stopClass.getEStructuralFeature("distance").getEType().getName());
		assertEquals(1, stopClass.getEStructuralFeature("distance").getUpperBound());
		assertEquals(1, stopClass.getEStructuralFeature("distance").getLowerBound());
		
		assertNotNull(stopClass.getEStructuralFeature("ligne"));
		assertEquals("Ligne", stopClass.getEStructuralFeature("ligne").getEType().getName());
		assertEquals(-1, stopClass.getEStructuralFeature("ligne").getUpperBound());
		assertEquals(1, stopClass.getEStructuralFeature("ligne").getLowerBound());
	}

	@Test
	public void testRefine() throws FileNotFoundException {
		JsonSource source = new JsonSource("Stop");
		source.addJsonData(null, new FileReader(new File("./testData/simpleDiscoverer/json1A.json")));

		JsonSimpleDiscoverer discoverer = new JsonSimpleDiscoverer();
		EPackage ePackage0 = discoverer.discover(source);

		JsonSource source2 = new JsonSource("test");
		source2.addJsonData(null, new FileReader(new File("./testData/simpleDiscoverer/json1B.json")));

		discoverer = new JsonSimpleDiscoverer();
		EPackage ePackage = discoverer.refine(ePackage0, source2);
		
		EClass ligneClass = (EClass) ePackage.getEClassifier("Ligne");
		assertNotNull(ligneClass);
		assertNotNull(ligneClass.getEStructuralFeature("numLigne"));
		assertEquals("EString", ligneClass.getEStructuralFeature("numLigne").getEType().getName());
		assertEquals(1, ligneClass.getEStructuralFeature("numLigne").getUpperBound());
		assertEquals(1, ligneClass.getEStructuralFeature("numLigne").getLowerBound());
		
		EClass stopClass = (EClass) ePackage.getEClassifier("Stop");
		assertNotNull(stopClass);
		assertNotNull(stopClass.getEStructuralFeature("codeLieu"));
		assertEquals("EString", stopClass.getEStructuralFeature("codeLieu").getEType().getName());
		assertEquals(1, stopClass.getEStructuralFeature("codeLieu").getUpperBound());
		assertEquals(1, stopClass.getEStructuralFeature("codeLieu").getLowerBound());
		
		assertNotNull(stopClass.getEStructuralFeature("libelle"));
		assertEquals("EString", stopClass.getEStructuralFeature("libelle").getEType().getName());
		assertEquals(1, stopClass.getEStructuralFeature("libelle").getUpperBound());
		assertEquals(1, stopClass.getEStructuralFeature("libelle").getLowerBound());
		
		assertNotNull(stopClass.getEStructuralFeature("distance"));
		assertEquals("EString", stopClass.getEStructuralFeature("distance").getEType().getName());
		assertEquals(1, stopClass.getEStructuralFeature("distance").getUpperBound());
		assertEquals(1, stopClass.getEStructuralFeature("distance").getLowerBound());
		
		assertNotNull(stopClass.getEStructuralFeature("ligne"));
		assertEquals("Ligne", stopClass.getEStructuralFeature("ligne").getEType().getName());
		assertEquals(-1, stopClass.getEStructuralFeature("ligne").getUpperBound());
		assertEquals(1, stopClass.getEStructuralFeature("ligne").getLowerBound());
	}

}
