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

import org.eclipse.emf.facet.widgets.celleditors.AbstractCellEditorComposite;
import org.eclipse.emf.facet.widgets.celleditors.ICompositeEditorFactory;
import org.eclipse.swt.widgets.Composite;


public class FileListCompositeFactory implements ICompositeEditorFactory<IFileList> {

	@Override
	public AbstractCellEditorComposite<IFileList> createCompositeEditor(Composite parent, int style) {
		return new FileListComposite(parent, style);
	}

	@Override
	public Class<IFileList> getHandledType() {
		return IFileList.class;
	}

}
