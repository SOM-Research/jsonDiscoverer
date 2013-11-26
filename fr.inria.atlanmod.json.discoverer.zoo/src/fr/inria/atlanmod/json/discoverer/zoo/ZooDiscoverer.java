package fr.inria.atlanmod.json.discoverer.zoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import fr.inria.atlanmod.discoverer.JsonDiscoverer;
import fr.inria.atlanmod.discoverer.JsonSource;

public class ZooDiscoverer {
	private final static Logger LOGGER = Logger.getLogger(ZooDiscoverer.class.getName());

	public static void main(String[] args) {
		File rootPath = new File("./zoo");

		// ZOO root
		for(File parentFile : rootPath.listFiles()) {
			if(parentFile.isDirectory()) {
				// Each entry in ZOO directory
				for(File sourceFile : parentFile.listFiles()) {
					if(sourceFile.isDirectory()) {
						// Each Source
						try { 
							discoverSource(sourceFile);
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}


	}

	private static void discoverSource(File rootPath) throws FileNotFoundException, IOException {
		if(!rootPath.isDirectory())
			throw new IllegalArgumentException("The file must be a directory");

		Properties properties = null;
		List<File> jsonFiles = new ArrayList<>();
		for(File file : rootPath.listFiles()) {
			if(file.isFile() && file.getName().equals("info.properties")) {
				properties = new Properties();
				properties.load(new FileReader(file));
			}
			if(file.isFile() && file.getName().startsWith("source") && file.getName().substring(file.getName().lastIndexOf("."), file.getName().length()).equals(".json")) {
				jsonFiles.add(file);
			}
		}


		if(properties != null && jsonFiles.size() > 0) {
			File resultingPath = new File(rootPath.getAbsoluteFile() + File.separator + "source.ecore"); 
			String shortname = properties.getProperty("shortname");

			LOGGER.info("SOURCE: " + shortname + " with " + jsonFiles.size() + " sources");
			LOGGER.info(" - path: " + rootPath.getAbsolutePath());
			LOGGER.info(" - json: " + resultingPath.getAbsolutePath());

			JsonSource source = new JsonSource(shortname);
			for(File jsonFile : jsonFiles) 
				source.addJsonDef(jsonFile);

			JsonDiscoverer discoverer = new JsonDiscoverer();
			EPackage metamodel = discoverer.discoverMetamodel(source);
			saveEcore(metamodel, resultingPath);
		}

	}
	private static void saveEcore(EPackage metamodel, File target) {
		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource res = rset.createResource(URI.createFileURI(target.getAbsolutePath()));
		res.getContents().add(metamodel); 
		try {
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
