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

package fr.inria.atlanmod.ui.discoverer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.modisco.infra.discovery.core.IDiscoverer;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.ui.PlatformUI;

import fr.inria.atlanmod.discoverer.JsonDiscoverer;
import fr.inria.atlanmod.discoverer.JsonSource;

/**
 * Enriches an existing json metamodel from a json file
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonModiscoDiscovererFragment extends JsonModiscoDiscoverer implements IDiscoverer<org.eclipse.core.resources.IFile> {

	@Override
	public boolean isApplicableTo(IFile source) {
		if(source.getFileExtension().toLowerCase().equals(EXTENSION)) return true;
		return false;
	}
	
	@Override
	public void discoverElement(IFile source, IProgressMonitor monitor)	throws DiscoveryException {
		monitor.beginTask("Enriching Ecore model", 1000);
		
		// Selecting the other model to compose
		monitor.subTask("Selecting the Ecore file to enrich");
		JsonDialog dialog = new JsonDialog("Select the Ecore file to enrich", source);
		PlatformUI.getWorkbench().getDisplay().syncExec(dialog);	
		String path = dialog.getResult();
		monitor.worked(250);
		
		monitor.subTask("Loading the other ecore model");
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.getResource(URI.createFileURI(path), true);
		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
			monitor.done();
			throw new DiscoveryException(e.getMessage());
		}
		EPackage ePackage = (EPackage) res.getContents().get(0);
		monitor.worked(250);

		monitor.subTask("Enriching model");
		JsonDiscoverer discoverer = new JsonDiscoverer();
		File sourceFile = new File(source.getRawLocationURI());
		File targetFile = new File(path.substring(0, path.lastIndexOf("."))+ "-enriched.ecore");
		EPackage ePackageRefined;
		try {
			JsonSource jsonSource = new JsonSource("discovered");
			jsonSource.addJsonDef(sourceFile);
			ePackageRefined = discoverer.refineMetamodel(ePackage, jsonSource);
		} catch (FileNotFoundException e1) {
			throw new DiscoveryException(e1.getMessage());
		}
		monitor.worked(250);
		
		monitor.subTask("Saving enriched model");
		ResourceSet rsetRefined = new ResourceSetImpl();
		Resource resRefined = rsetRefined.createResource(URI.createFileURI(targetFile.getAbsolutePath()));
		resRefined.getContents().add(ePackageRefined);
		try {
			resRefined.save(null);
		} catch (IOException e) {
			e.printStackTrace();
			monitor.done();
			throw new DiscoveryException(e.getMessage());
		}
		monitor.worked(250);

		if (source.getParent() instanceof IResource) {
			try {
				((IResource) source.getParent()).refreshLocal(IResource.DEPTH_INFINITE,	new NullProgressMonitor());
			} catch (CoreException e) {
				monitor.done();
				throw new DiscoveryException(e);
			}
		}
		monitor.done();

	}

}
