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
