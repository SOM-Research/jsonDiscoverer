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

/**
 * Utility class con basic model actions.
 * <p>
 * In particular, the class helps to:
 * <ul>
 * <li>Load/Save metamodels (see {@link ModelHelper#loadEPackage(File)} and {@link ModelHelper#saveEPackage(EPackage, File)}</li>
 * <li>Save models (see {@link ModelHelper#saveModel(List, File)})</li>
 * </ul>
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class ModelHelper {
	/**
	 * Saves a metamodel (as {@link EPackage}) into a file
	 * 
	 * @param ePackage The metamodel
	 * @param resultPath The path to the file
	 */
	public static void saveEPackage(EPackage ePackage, File resultPath) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.createResource(URI.createFileURI(resultPath.getAbsolutePath()));

		try {
			res.getContents().add(ePackage);
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * Loads  a metamodel (as {@link EPackage}) from a file
	 * 
	 * @param path The path to the file to read the metamodel
	 * @return The metamodel
	 */
	public static EPackage loadEPackage(File path) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.getResource(URI.createFileURI(path.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (EPackage) res.getContents().get(0);
	}

	/**
	 * Saves a model (as a list of {@link EObject}s) into a file
	 * 
	 * @param elements List of model elements (as {@link EObject}s)
	 * @param resultPath The path to write the model
	 */
	public static void saveModel(List<EObject> elements, File resultPath) {
		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource res2 = rset.createResource(URI.createFileURI(resultPath.getAbsolutePath()));
		
		for(EObject eObject : elements) {
			res2.getContents().add(eObject); 
		}

		try {
			res2.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
