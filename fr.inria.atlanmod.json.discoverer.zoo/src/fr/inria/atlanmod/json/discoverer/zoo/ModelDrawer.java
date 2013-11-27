package fr.inria.atlanmod.json.discoverer.zoo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emftools.emf2gv.graphdesc.GraphdescPackage;
import org.emftools.emf2gv.processor.core.StandaloneProcessor;

public class ModelDrawer {
	private final static Logger LOGGER = Logger.getLogger(ModelDrawer.class.getName());

	private File workingDir;
	private File dotPath;

	public ModelDrawer(File workingDir, File dotPath) {
		this.workingDir = workingDir;
		this.dotPath = dotPath;
	}

	public void traverseAndDrawFolder(File targetFolder) {
		if(targetFolder == null || !targetFolder.isDirectory())
			throw new IllegalArgumentException("The target must be a folder");

		// Forcing the registry of packages
		EcorePackage.eINSTANCE.eClass();
		GraphdescPackage.eINSTANCE.eClass();

		for(File file : targetFolder.listFiles()) {
			if(file.isDirectory()) 
				traverseAndDrawFolder(file);
			else if (file.isFile() && file.getName().endsWith("ecore")) {
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

				List<EObject> elements = new ArrayList<>();
				TreeIterator<EObject> treeIt = res.getAllContents();
				while(treeIt.hasNext()) {
					EObject eObject = treeIt.next();
					elements.add(eObject);
				}

				if(elements.size() > 0) {
					File resultingFile = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".")) + ".jpg");
					try {
						StandaloneProcessor.process(elements, null, workingDir, resultingFile.getAbsolutePath(), null, null, dotPath.getAbsolutePath(), true, false, "UTF-8", null, null, null);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				} else {
					LOGGER.info("No elements for " + file.getAbsolutePath());
				}
			}
		}
	}

}
