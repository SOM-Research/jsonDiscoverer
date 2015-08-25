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
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import jsondiscoverer.JsonInjector;
import jsondiscoverer.SingleJsonSource;

public class TestJsonInjector {

	@SuppressWarnings("unchecked")
	@Test
	public void testInject() throws FileNotFoundException {
		SingleJsonSource source = new SingleJsonSource("Stop");
		source.addJsonData(null, new FileReader(new File("./testData/injector/json.json")));
		
		JsonInjector injector = new JsonInjector(source);
		List<EObject> result = injector.inject();
		assertNotNull(result);
		assertTrue(result.size() > 0);
		for(EObject eObject : result) {
			assertNotNull(eObject);
			assertTrue(eObject.eClass().getName().equals("Stop"));
			assertEquals("CRQU4", eObject.eGet(eObject.eClass().getEStructuralFeature("codeLieu")));
			assertEquals("21 m", eObject.eGet(eObject.eClass().getEStructuralFeature("distance")));
			assertEquals("Place du Cirque", eObject.eGet(eObject.eClass().getEStructuralFeature("libelle")));
			
			List<EObject> lignes = (List<EObject>) eObject.eGet(eObject.eClass().getEStructuralFeature("ligne"));
			assertNotNull(lignes);
			assertTrue(lignes.size() == 2);
			for(EObject ligne : lignes) {
				String numLigne = (String) ligne.eGet(ligne.eClass().getEStructuralFeature("numLigne"));
				assertTrue(numLigne.equals("2") || numLigne.equals("C1"));
			}
			
		}
	}	
}
