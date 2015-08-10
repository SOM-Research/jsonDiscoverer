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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class JsonDialog implements Runnable {
	private IFile file;
	private String result;
	private String title;
	
	public JsonDialog(String title, IFile file) {
		this.title = title;
		this.file = file;
	}
	
	@Override
	public void run() {
	    Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IWorkspaceRoot root = file.getWorkspace().getRoot();
		
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText(title);
		dialog.setFilterExtensions(new String[] {"*.ecore"} );
		dialog.setFilterPath(root.getLocation().toOSString());
		result = dialog.open();
	}
	
	String getResult() {
		return result;
	}
	
}
