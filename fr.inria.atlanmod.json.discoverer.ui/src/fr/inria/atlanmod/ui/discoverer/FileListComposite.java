package fr.inria.atlanmod.ui.discoverer;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.facet.widgets.celleditors.AbstractCellEditorComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

import fr.inria.atlanmod.discoverer.JsonComposer;

public class FileListComposite extends AbstractCellEditorComposite<IFileList> {

	private final Button button;
	private Text text = null;
	private IFileList fileList = null;
	
	public FileListComposite(final Composite parent) {
		this(parent, SWT.NONE);
	}
	
	public FileListComposite(final Composite parent, final int style) {
		super(parent);
		
		GridLayout gd = new GridLayout(2, false);
		gd.marginHeight = 0;
		gd.marginWidth = 0;
		gd.horizontalSpacing = 0;
		setLayout(gd);

		this.text = new Text(this, style);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.button = new Button(this, SWT.PUSH);
		this.button.setText("..."); //$NON-NLS-1$
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, true);
		this.button.setLayoutData(data);

		this.button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();		
				FileDialog dialog = new FileDialog(getShell(), SWT.MULTI);
				dialog.setFilterExtensions(new String[] {"*.ecore"} );
				dialog.setFilterPath(root.getLocation().toOSString());
				String result = dialog.open();
				String[] filePaths = dialog.getFileNames();
				String selected = "";
				for(String filePath : filePaths) {
					System.out.println("--> " + filePath);
					selected += filePath + " ";
				}
				fireCommit();
				text.setText(selected);
			}
		});
		
		this.text.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				fireChanged();
			}
		});

		
	}

	@Override
	public IFileList getValue() {
		ArrayList<File> fileList = new ArrayList<File>();
		fileList.add(new File(JsonComposer.TEST_FILE_1));
		fileList.add(new File(JsonComposer.TEST_FILE_2));
		return (IFileList) fileList;
	}

	@Override
	public void setValue(IFileList arg0) {
		System.out.println("Set called");
		
	}
	
	
}
