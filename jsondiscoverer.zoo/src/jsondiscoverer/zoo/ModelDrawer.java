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

package jsondiscoverer.zoo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emftools.emf2gv.graphdesc.GraphdescPackage;
import org.emftools.emf2gv.processor.core.StandaloneProcessor;

/**
 * This class traverses a folder and generates the pictures for each model found
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class ModelDrawer {
	/**
	 * The Logger to track all the execution traces
	 */
	private final static Logger LOGGER = Logger.getLogger(ModelDrawer.class.getName());

	/**
	 * The folder used as working directory to save temporary files
	 */
	private File workingDir;
	
	/**
	 * The path to the DOT executable
	 */
	private File dotPath;

	/**
	 * Constructor. Requires the working directory and the path to the DOT executable
	 * <p>
	 * Registers the metamodels and sets the log level
	 * 
	 * @param workingDir The folder where the temporary files will be stored
	 * @param dotPath The path to the DOT executable
	 */
	public ModelDrawer(File workingDir, File dotPath) {
		this.workingDir = workingDir;
		this.dotPath = dotPath;

		// Forcing the registry of packages
		EcorePackage.eINSTANCE.eClass();
		GraphdescPackage.eINSTANCE.eClass();
		
		LOGGER.setLevel(Level.OFF);
	}

	/**
	 * Main method to traverse a folder, discover and generate the pictures
	 * <p>
	 * This method call {@link ModelDrawer#traverseAndDrawFolder(File, boolean)} with 
	 * the overwrite param active.
	 * 
	 * @param targetFolder The folder to analyze
	 */
	public void traverseAndDrawFolder(File targetFolder) {
		traverseAndDrawFolder(targetFolder, true);
	}

	/**
	 * Main method to traverse a folder, discover and generate the pictures. 
	 * <p>
	 * The generation process can overwrite (or not) according to the param.
	 * <p>
	 * The process traverses the folder (if contains other folders, they are
	 * traversed recursively) and generates the model/metamodel for each JSON
	 * document found.
	 *  
	 * @param targetFolder The folder to analyze
	 * @param overwrite True if the results have to overwrite
	 */
	public void traverseAndDrawFolder(File targetFolder, boolean overwrite) {
		if(targetFolder == null || !targetFolder.isDirectory())
			throw new IllegalArgumentException("The target must be a folder");

		// Forcing the registry of packages
		EcorePackage.eINSTANCE.eClass();
		GraphdescPackage.eINSTANCE.eClass();

		for(File file : targetFolder.listFiles()) {
			if(file.isDirectory()) 
				traverseAndDrawFolder(file);
			else if (file.isFile() && file.getName().endsWith("ecore") && (overwrite || !pictureFile(file).exists())) {
				ResourceSet rset = new ResourceSetImpl();
				rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
				Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
				Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
				Resource res = rset.createResource(URI.createFileURI(file.getAbsolutePath()));

				try {
					res.load(null);
				} catch(Exception e) {
					LOGGER.severe("File could not be loaded : " + file.getAbsolutePath());
					e.printStackTrace();
				}

				List<EObject> elementsToUnset = new ArrayList();
				TreeIterator<EObject> treeIt = res.getAllContents();
				while(treeIt.hasNext()) {
					EObject eObject = treeIt.next();
					EStructuralFeature esf = eObject.eClass().getEStructuralFeature("eAnnotations");
					if(esf != null && eObject.eIsSet(esf)) {
						elementsToUnset.add(eObject);
					}
				}
				
				for(EObject eObject : elementsToUnset) {
					EStructuralFeature esf = eObject.eClass().getEStructuralFeature("eAnnotations");
					eObject.eUnset(esf);
				}
				
				List<EObject> elements = new ArrayList<>();
				treeIt = res.getAllContents();
				while(treeIt.hasNext()) {
					EObject eObject = treeIt.next();
					elements.add(eObject);
				}

				if(elements.size() > 0) {
					File resultingFile = pictureFile(file);
					draw(elements, resultingFile);
				} else {
					LOGGER.info("No elements for " + file.getAbsolutePath());
				}
			}
		}
	}

	/**
	 * Draws a picture out of a list of model elements (as {@link EObject}s)
	 * 
	 * @param elements List of model elements (as {@link EObject}s)
	 * @param resultingFile The file where the picture will be saved
	 */
	public void draw(List<EObject> elements, File resultingFile) {
		try {
			StandaloneProcessor.process(elements, null, workingDir, resultingFile.getAbsolutePath(), null, null, dotPath.getAbsolutePath(), true, false, "UTF-8", null, null, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates a File to store the JPG out of a model file
	 * 
	 * @param source The model file 
	 * @return A {@link File} for savnig the JPG file
	 */
	private File pictureFile(File source) {
		return new File(source.getAbsolutePath().substring(0, source.getAbsolutePath().lastIndexOf(".")) + ".jpg");
	}

}
