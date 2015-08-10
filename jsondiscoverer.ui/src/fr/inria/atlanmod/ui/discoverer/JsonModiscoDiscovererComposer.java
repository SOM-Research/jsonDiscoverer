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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.modisco.infra.discovery.core.IDiscoverer;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.ui.PlatformUI;

import fr.inria.atlanmod.discoverer.JsonMultiDiscoverer;
import fr.inria.atlanmod.discoverer.JsonSource;
import fr.inria.atlanmod.discoverer.JsonSourceSet;

public class JsonModiscoDiscovererComposer extends JsonModiscoDiscoverer implements IDiscoverer<IFile> {

	@Override
	public void discoverElement(IFile source, IProgressMonitor monitor) throws DiscoveryException {
		monitor.beginTask("Composing Ecore models", 1000);

		monitor.subTask("Selecting the Ecore file to compose");
		JsonDialog dialog = new JsonDialog("Select the Ecore model for composing", source);
		PlatformUI.getWorkbench().getDisplay().syncExec(dialog);	
		String path = dialog.getResult();
		monitor.worked(250);

		monitor.subTask("Composing models");
		File sourceFile1 = new File(source.getRawLocationURI());
		File sourceFile2 = new File(path);
		try {
			JsonSource source1 = new JsonSource("source1");
			source1.addJsonDef(sourceFile1);
			JsonSource source2 = new JsonSource("source2");
			source2.addJsonDef(sourceFile2);
			
			JsonSourceSet sourceSet = new JsonSourceSet("composed");
			sourceSet.addJsonSource(source1);
			sourceSet.addJsonSource(source2);
			JsonMultiDiscoverer composer = new JsonMultiDiscoverer(sourceSet);
			File targetFile = new File(path.substring(0, path.lastIndexOf("."))+ "-composed.ecore");
			composer.discover(targetFile);
		} catch (FileNotFoundException e1) {
			throw new DiscoveryException(e1.getMessage());
		}
		monitor.worked(750);


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

	@Override
	public boolean isApplicableTo(final IFile source) {
		if(source.getFileExtension().toLowerCase().equals(ECORE_EXTENSION)) return true;
		return false;
	}



}
