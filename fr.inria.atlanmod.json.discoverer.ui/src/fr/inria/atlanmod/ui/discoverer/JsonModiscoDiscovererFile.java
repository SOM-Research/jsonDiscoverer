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

import fr.inria.atlanmod.discoverer.JsonDiscoverer;
import fr.inria.atlanmod.discoverer.JsonSource;

/**
 * Discovers a metamodel from a json file
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonModiscoDiscovererFile extends JsonModiscoDiscoverer implements IDiscoverer<org.eclipse.core.resources.IFile>  {
	
	public boolean isApplicableTo(final IFile source) {
		if(source.getFileExtension().toLowerCase().equals(EXTENSION)) return true;
		return false;
	}

	@Override
	public void discoverElement(IFile source, IProgressMonitor monitor)	throws DiscoveryException {
		File sourceFile = new File(source.getRawLocationURI());
		File targetFile = new File(sourceFile.getAbsolutePath().substring(0, sourceFile.getAbsolutePath().lastIndexOf("."))+ ".ecore");

		
		JsonDiscoverer discoverer = new JsonDiscoverer();
		EPackage ePackage;
		try {
			JsonSource jsonSource = new JsonSource("discovered");
			jsonSource.addJsonDef(sourceFile);
			
			ePackage = discoverer.discoverMetamodel(jsonSource);
		} catch (FileNotFoundException e1) {
			throw new DiscoveryException(e1.getMessage());
		}
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.createResource(URI.createFileURI(targetFile.getAbsolutePath()));
		res.getContents().add(ePackage);
		
		try {
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
			throw new DiscoveryException(e.getMessage());
		}
		
		if (source.getParent() instanceof IResource) {
			try {
				((IResource) source.getParent()).refreshLocal(IResource.DEPTH_INFINITE,	new NullProgressMonitor());
			} catch (CoreException e) {
				throw new DiscoveryException(e);
			}
		}
	}

//	@Override
//	protected void basicDiscoverElement(final IFile source, final IProgressMonitor monitor) throws DiscoveryException {
//		
//		JsonDiscoverer discoverer = new JsonDiscoverer();
//		EPackage ePackage = discoverer.discoverMetamodel(new File(source.getRawLocationURI()));
//		
//		createTargetModel();
//		Resource res = getTargetModel();
//		res.getContents().add(ePackage);
//		
//		try {
//			res.save(null);
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new DiscoveryException(e.getMessage());
//		}
//
//	}

}
