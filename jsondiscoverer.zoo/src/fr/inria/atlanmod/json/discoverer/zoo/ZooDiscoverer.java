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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.google.gson.JsonParseException;

import fr.inria.atlanmod.discoverer.JsonComposer;
import fr.inria.atlanmod.discoverer.JsonDiscoverer;
import fr.inria.atlanmod.discoverer.JsonInjector;
import fr.inria.atlanmod.discoverer.JsonMultiDiscoverer;
import fr.inria.atlanmod.discoverer.JsonSource;
import fr.inria.atlanmod.discoverer.JsonSourceSet;
import fr.inria.atlanmod.discoverer.SingleJsonSource;

/**
 * This class traverses a given path to discover the metamodels for JSON files.
 * 
 * The path has to conform to a particular structure. Thus, the root path has to be
 * composed by a set of folder (one per API), where each folder contains subfolders 
 * (one per JSON source). 
 * 
 * For each JSON source, it is expected to find a "info.properties" file which the "name"
 * and "shortname" properties describing the source. Furthermore, a set of JSON files
 * have to be included in the JSON source. Each JSON file name must start with "json" 
 * string and it is expected both a .json * and a .properties file (with the same name), 
 * including the json text and the property "call" to invoke the service.
 * 
 * This is and example of such an organization: 
 * 
 * - RootPath
 *   +-- API1
 *       +-- source1
 *           +-- info.properties
 *           +-- json1.json
 *           +-- json1.properties
 *           +-- json2.json
 *           +-- json2.properties
 *           +-- ...
 *       +-- sourcen
 *           +-- ...     
 *   +-- API2
 *       +-- ...
 *   +-- APIn
 *       +-- ...
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class ZooDiscoverer {
	private final static Logger LOGGER = Logger.getLogger(ZooDiscoverer.class.getName());

	private File rootPath;

	public ZooDiscoverer(File rootPath) {
		if(rootPath == null || !rootPath.isDirectory()) 
			throw new IllegalArgumentException("The rootPath must be a directory");
		this.rootPath = rootPath;
	}

	public void discover() {
		discover(true); 
	}

	public void discover(boolean overwrite) {
		List<JsonSourceSet> sourceSets = new ArrayList<>();
		for(File parentFile : rootPath.listFiles()) {
			if(parentFile.isDirectory()) {
				// Each API in ZOO directory
				File resultingPath = new File(parentFile.getAbsoluteFile() + File.separator + parentFile.getName() + ".ecore"); 
				if(overwrite || !resultingPath.exists()) {
					JsonSourceSet sourceSet = new JsonSourceSet(parentFile.getName());
					for(File sourceFile : parentFile.listFiles()) {
						if(sourceFile.isDirectory()) {
							// Each Source
							try { 
								JsonSource discoveredSource = discoverSource(sourceFile, overwrite);
								if(discoveredSource != null)
									sourceSet.addJsonSource(discoveredSource);
							} catch(Exception e) {
								LOGGER.severe(e.getMessage());
								e.printStackTrace();
							}
						}
					}
					// Composing metamodels
					JsonMultiDiscoverer multidiscoverer = new JsonMultiDiscoverer(sourceSet);
					try {
						multidiscoverer.discover(resultingPath);
					} catch (FileNotFoundException e) {
						LOGGER.severe(e.getMessage());
					}
					sourceSets.add(sourceSet);
				} else {
					LOGGER.info("Composed metamodel for " + parentFile.getName() + " already generated");
				}
			}
		}
		JsonComposer composer = new JsonComposer(sourceSets);
		File finalResultPath = new File(rootPath.getAbsoluteFile() + File.separator + rootPath.getName() + ".ecore");  
		composer.compose(finalResultPath);

	}

	private JsonSource discoverSource(File rootPath, boolean overwrite) throws FileNotFoundException, IOException {
		if(!rootPath.isDirectory())
			throw new IllegalArgumentException("The file must be a directory");

		Properties properties = null;
		List<File> jsonFiles = new ArrayList<>();
		for(File file : rootPath.listFiles()) {
			if(file.isFile() && file.getName().equals("info.properties")) {
				properties = new Properties();
				properties.load(new FileReader(file));
			}
			if(file.isFile() && file.getName().startsWith("json") && file.getName().substring(file.getName().lastIndexOf("."), file.getName().length()).equals(".json")) {
				jsonFiles.add(file);
			}
		}


		if(properties != null && jsonFiles.size() > 0) {
			File resultingPath = new File(rootPath.getAbsoluteFile() + File.separator + rootPath.getName() + ".ecore"); 
			String shortname = properties.getProperty("shortname");

			LOGGER.info("SOURCE: " + shortname + " with " + jsonFiles.size() + " sources");
			LOGGER.info(" - resulting: " + resultingPath.getAbsolutePath());

			JsonSource source = new JsonSource(shortname);
			for(File jsonFile : jsonFiles) {
				LOGGER.info(" - json source: " + jsonFile.getAbsolutePath());
				File jsonPropertiesFile = new File(jsonFile.getAbsolutePath().substring(0, jsonFile.getAbsolutePath().lastIndexOf(".")) + ".properties");
				try {
					if(jsonPropertiesFile.exists()) {
						Properties jsonProperties = new Properties();
						jsonProperties.load(new FileReader(jsonPropertiesFile));
						String inputJson = jsonProperties.getProperty("input");
						if(inputJson != null) {
							source.addJsonDef(jsonFile, inputJson);
						} else {
							source.addJsonDef(jsonFile);
						}
					} else {
						source.addJsonDef(jsonFile);
					}
				} catch (JsonParseException e) {
					LOGGER.info("Error deadling with source " + jsonFile.getName());
					throw e;
				}
			}

			// 1. Getting the metamodel
			EPackage metamodel = null;
			if(overwrite || !resultingPath.exists()) {
				JsonDiscoverer discoverer = new JsonDiscoverer();
				metamodel = discoverer.discoverMetamodel(source);
				saveEcore(metamodel, resultingPath);
			} else if(resultingPath.exists()) {
				metamodel = loadEcore(resultingPath);
				LOGGER.info("Metamodel for " + rootPath.getName() + " already generated");
			}

			// 2. Getting the individual model for each JSON
			for(File jsonFile : jsonFiles) {
				File singleSourceResultPath = new File(rootPath.getAbsoluteFile() + File.separator + jsonFile.getName() + ".xmi"); 

				if(overwrite || !singleSourceResultPath.exists()) {
					SingleJsonSource singleSource = new SingleJsonSource(shortname);
					singleSource.setMetamodel(metamodel);
					singleSource.addJsonDef(jsonFile);

					JsonInjector injector = new JsonInjector(singleSource);
					List<EObject> result = injector.inject();

					saveModel(result, singleSourceResultPath);
				} else {
					LOGGER.info("Model for " + jsonFile.getName() + " already generated");
				}
			}

			return source;
		}

		return null;
	}

	private void saveEcore(EPackage metamodel, File target) {
		if(target == null)
			throw new IllegalArgumentException("The file cannot be null");
		if(metamodel == null)
			throw new IllegalArgumentException("The metamodel cannot be null");

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

	private EPackage loadEcore(File target) {
		if(target == null)
			throw new IllegalArgumentException("The file cannot be null");

		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource res = rset.getResource(URI.createFileURI(target.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		EPackage ePackage = (EPackage) res.getContents().get(0);
		return ePackage;
	}

	private static void saveModel(List<EObject> elements, File target) {
		if(elements == null)
			throw new IllegalArgumentException("Elements to serialize the model cannot be null");
		if(target == null)
			throw new IllegalArgumentException("The file cannot be null");

		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource res2 = rset.createResource(URI.createFileURI(target.getAbsolutePath()));

		for(EObject eObject : elements) {
			if(eObject != null)
				res2.getContents().add(eObject); 
		}

		try {
			res2.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
