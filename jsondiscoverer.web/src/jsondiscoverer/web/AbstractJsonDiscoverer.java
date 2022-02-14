/*******************************************************************************
 * Copyright (c) 2008, 2015
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Javier Canovas (me@jlcanovas.es) 
 *******************************************************************************/

package jsondiscoverer.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.helpers.IOUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emftools.emf2gv.graphdesc.AttributeFigure;
import org.emftools.emf2gv.graphdesc.ClassFigure;
import org.emftools.emf2gv.graphdesc.GVFigureDescription;
import org.emftools.emf2gv.graphdesc.GraphdescFactory;
import org.emftools.emf2gv.graphdesc.GraphdescPackage;
import org.emftools.emf2gv.graphdesc.Orientation;
import org.emftools.emf2gv.graphdesc.ReferenceFigure;
import org.emftools.emf2gv.processor.core.StandaloneProcessor;

/**
 * Abstract class to factor some common behavior among the JSON discoverers.
 * <p>In particular, this class helps to:</p>
 * <ul>
 * <li><b>Draw models</b>. Generates a picture (stored in a file) which is the 
 * renderization of a list of EObjects 
 * (see {@link AbstractJsonDiscoverer#drawModel(List, String)} 
 * and {@link AbstractJsonDiscoverer#drawModel(List, String, GVFigureDescription)}</li>
 * 
 * <li><b>Encode files to String</b>. Generates the Base64 representation of files 
 * (see {@link AbstractJsonDiscoverer#encodeToString(File)}, 
 * {@link AbstractJsonDiscoverer#encodeToString(EPackage, String)} 
 * and {@link AbstractJsonDiscoverer#encodeToString(List, String)}</li>
 * </ul>
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public abstract class AbstractJsonDiscoverer extends HttpServlet {
	private static final long serialVersionUID = 69L;

	/** The main path to the working dir (needed for generating the pictures) */
	public static File workingDir = null;

	/** The path to the Graphviz DOT execitable (needed for generating the pictures)*/
	public static String dotExePath = null;

	/** Version of the app*/
	public static String version = "";

	/** The jsonParam used for discovery (where the json code is stored)*/
	static String jsonParam = null;

	/** Server for the CORS*/
	static String serverURL = "";

	Properties properties = null;

	
	/**
	 * Initilizes the servlet class. THis implementation is used in any JSON discoverer servlet.
	 * <p>
	 * It performs the following steps:
	 * <ul>
	 * <li>Registers the ecore and xmi extensions in EMF to enable the model management</li>
	 * <li>Reads the properties file (called config.properties and located at WEB-INF folder)</li>
	 * </ul>
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		super.init();

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

		// Getting configuration
		String workingDirString = null;
		try {
			properties = new Properties();
			properties.load(getServletContext().getResourceAsStream("/WEB-INF/config.properties"));
			workingDirString = properties.getProperty("workingDir");
			dotExePath = properties.getProperty("dotExePath");
			jsonParam = properties.getProperty("JsonParameter");
			serverURL = properties.getProperty("serverURL");
			version = properties.getProperty("version");
			if(serverURL == null) 
				serverURL = "http://localhost:8080";
		} catch (IOException e) {
			throw new ServletException("Discover servlet could not find the configuration");
		}

		// We need a File (not a String)
		workingDir = new File(workingDirString);
		if(!workingDir.isDirectory()) throw new ServletException("The working dir does not exist");
	}


	/**
	 * Encodes a file into BASE64 format. 
	 * <p>
	 * In the context of the tool, this method is used to encode generated pictures
	 * (usually with @link {@link AbstractJsonDiscoverer#drawModel(List, String)}) into
	 * BASE64 format, which is easy to exchange with the web client.
	 * 
	 * @param path The path to the file to encode
	 * @return A String in BASE64
	 * @throws IOException Thrown when the file cannot be written
	 */
	String encodeToString(File path) throws IOException {
		if(path == null)
			throw new IllegalArgumentException("imagePath cannot be null");

		BufferedImage image = ImageIO.read(path);

		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "JPG", bos);
			byte[] imageBytes = bos.toByteArray();

			Encoder encoder = Base64.getEncoder();
			imageString = encoder.encodeToString(imageBytes);

			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}

	/**
	 * Saves the EPackage into a XMI and then read it to return it as String in Base64.
	 * <p>
	 * The workflow followed is:
	 * <ul>
	 * <li>Creates a temp file where the metamodel (as {@link EPackage}) will be stored</li>
	 * <li>Saves the metamodel in the temp file</li>
	 * <li>Reads the file and encodes it as base64</li>
	 * <li>Returns the {@link String} encoded as Base64</li>
	 * </ul>
	 * <p>
	 * Metamodels are saved in a directory that is configred by providing a value to the 
	 * corresponding property in the config.properties file. The key to retrieve the 
	 * property is provided in the para uniqueId. 
	 * 
	 * @param ePackage The metamodel (as {@link EPackage}
	 * @param uniqueId The key to retrieve the property in the config.properties file setting 
	 * the folder where the metamodel will be saved
	 * @return A {@link String} encoded in Base64 representing the metamodel
	 * @throws ServletException Something went wrong (usually IO-related problem)
	 */
	String encodeToString(EPackage ePackage,  String uniqueId) throws ServletException {
		if(ePackage == null)
			throw new IllegalArgumentException("ePackage cannot be null");
		if(uniqueId == null)
			throw new IllegalArgumentException("uniqueId cannot be null");

		File uniqueWorkingDir = new File(workingDir.getAbsolutePath() + File.separator + uniqueId);
		if(!uniqueWorkingDir.isDirectory()) throw new ServletException("The working dir could not be set:" + uniqueWorkingDir.getAbsolutePath());

		// 1. Getting a temp file
		File resultPath;
		try {
			resultPath = File.createTempFile("temp", ".xmi", uniqueWorkingDir);
		} catch (IOException e1) {
			throw new ServletException("Not possible to access to temp dir");
		}

		// 2. Saving into XMI
		ResourceSet rSet = new ResourceSetImpl();
		Resource res = rSet.createResource(URI.createFileURI(resultPath.getAbsolutePath()));
		try {
			res.getContents().add(ePackage);
			res.save(null);
		} catch (IOException e) {
			throw new ServletException("Not possible to save the Epackage", e);
		}

		// 3. Loading XMI into String
		String result;
		try {
			FileInputStream fis = new FileInputStream(resultPath);
			String content = IOUtils.readStringFromStream(fis);
			Encoder encoder = Base64.getEncoder();
			result = encoder.encodeToString(content.getBytes());
			fis.close();
		} catch (IOException e) {
			throw new ServletException("Error reading XMI to String", e);
		}

		return result;
	}

	/**
	 * Saves a list of EObjects into a XMI and then read it to return it as String in Base64. 
	 * Similar to {@link AbstractJsonDiscoverer#encodeToString(EPackage, String)}
	 * <p>
	 * The workflow followed is:
	 * <ul>
	 * <li>Creates a temp file where the list of {@link EObject}s will be stored</li>
	 * <li>Saves the list of {@link EObject}s in the temp file</li>
	 * <li>Reads the file and encodes it as base64</li>
	 * <li>Returns the {@link String} encoded as Base64</li>
	 * </ul>
	 * <p>
	 * The list of {@link EObject}s are saved in a directory that is configred by providing 
	 * a value to the corresponding property in the config.properties file. The key to retrieve
	 * the property is provided in the para uniqueId. 
	 * 
	 * @param elements The list of {@link EObject}s
	 * @param uniqueId The key to retrieve the property in the config.properties file setting 
	 * the folder where the list of {@link EObject}s will be saved
	 * @return A {@link String} encoded in Base64 representing the list 
	 * of {@link EObject}s
	 * @throws ServletException Something went wrong (usually IO-related problem)
	 */
	String encodeToString(List<EObject> elements,  String uniqueId) throws ServletException {
		if(elements == null)
			throw new IllegalArgumentException("elements cannot be null");
		if(uniqueId == null)
			throw new IllegalArgumentException("uniqueId cannot be null");

		File uniqueWorkingDir = new File(workingDir.getAbsolutePath() + File.separator + uniqueId);
		if(!uniqueWorkingDir.isDirectory()) throw new ServletException("The working dir could not be set:" + uniqueWorkingDir.getAbsolutePath());

		// 1. Getting a temp file
		File resultPath;
		try {
			resultPath = File.createTempFile("temp", ".xmi", uniqueWorkingDir);
		} catch (IOException e1) {
			throw new ServletException("Not possible to access to temp dir");
		}

		// 2. Saving into XMI
		ResourceSet rSet = new ResourceSetImpl();
		Resource res = rSet.createResource(URI.createFileURI(resultPath.getAbsolutePath()));
		try {
			res.getContents().addAll(elements);
			res.save(null);
		} catch (IOException e) {
			throw new ServletException("Not possible to save the Epackage", e);
		}

		// 3. Loading XMI into String
		String result;
		try {
			FileInputStream fis = new FileInputStream(resultPath);
			String content = IOUtils.readStringFromStream(fis);
			Encoder encoder = Base64.getEncoder();
			result = encoder.encodeToString(content.getBytes());
			fis.close();
		} catch (IOException e) {
			throw new ServletException("Error reading XMI to String", e);
		}

		return result;
	}

	/**
	 * Draws a model into a picture, stored a file.
	 * <p>The process follows these steps:</p>
	 * <ul>
	 * <li>First the name of the folder is retrieved from the properties file</li>
	 * <li>A temp file is created</li>
	 * <li>Then the model is rendered into a picture and stored in the temp file</li>
	 * </ul>
	 * <p>
	 * Files are stored in a folder configured in the config properties file. The
	 * name of the property is set by the uniqueId param.
	 * <p>The generation of the picture is delegated to the method {@link StandaloneProcessor#process(EObject, GVFigureDescription, File, String, org.emftools.emf2gv.processor.core.IProcessorCallback, org.emftools.emf2gv.processor.core.IEObjectIconProvider, String, boolean, boolean, String, List, org.emftools.emf2gv.processor.core.ILogger, org.eclipse.core.runtime.IProgressMonitor)}</p>
	 * <p>A graphical style has to be provided. See the documentation of <a href="https://marketplace.eclipse.org/content/emf-graphviz-emf2gv">EMF2GV</a> to know how to create it.</p>
	 * 
	 * @param elements List of elements (as {@link EObject}s) to be drawn
	 * @param uniqueId Id used to retrieve the folder name from the config properties file to store the file 
	 * @param graphDesc Graphical description according to <a href="https://marketplace.eclipse.org/content/emf-graphviz-emf2gv">EMF2GV</a> format
	 * @return The picture file
	 * @throws ServletException If any problem appears (usually IO-related problems)
	 */
	File drawModel(List<EObject> elements, String uniqueId, GVFigureDescription graphDesc) throws ServletException {
		if(elements == null)
			throw new IllegalArgumentException("elements cannot be null");
		if(uniqueId == null)
			throw new IllegalArgumentException("uniqueId cannot be null");

		EcorePackage.eINSTANCE.eClass();
		GraphdescPackage.eINSTANCE.eClass();

		File uniqueWorkingDir = new File(workingDir.getAbsolutePath() + File.separator + uniqueId);
		if(!uniqueWorkingDir.isDirectory()) throw new ServletException("The working dir could not be set:" + uniqueWorkingDir.getAbsolutePath());

		File resultPath;
		try {
			resultPath = File.createTempFile("temp", ".jpg", uniqueWorkingDir);
		} catch (IOException e1) {
			throw new ServletException("Not possible to access to temp dir");
		}

		try {
			StandaloneProcessor.process(elements, graphDesc, uniqueWorkingDir, resultPath.getAbsolutePath(), null, null, dotExePath, true, false, "UTF-8", null, null, null);
		} catch (CoreException e) {
			throw new ServletException("Not possible to generate the image");
		}

		return resultPath;
	}

	/**
	 * Draws a model into a picture following the standard graphical style.
	 * <p>
	 * This method relies on {@link AbstractJsonDiscoverer#drawModel(List, String, GVFigureDescription)}
	 * <p>
	 * Default graphical style will be used. The style is provided by
	 * <a href="https://marketplace.eclipse.org/content/emf-graphviz-emf2gv">EMF2GV</a>
	 * library, please refer to its documentation for further details.
	 * 
	 * @param elements List of elements (as {@link EObject}s) to be drawn
	 * @param uniqueId Id used to retrieve the folder name from the config properties file to store the file 
	 * @return The picture file
	 * @throws ServletException If any problem appears (usually IO-related problems)
	 */
	File drawModel(List<EObject> elements, String uniqueId) throws ServletException {
		// Default Style will be used
		return drawModel(elements, uniqueId, null);
	}

	/**
	 * Draws a model into a picture with a UML-like graphical style.
	 * <p>
	 * This method relies on {@link AbstractJsonDiscoverer#drawModel(List, String, GVFigureDescription)}
	 * <p>
	 * 
	 * @param elements List of elements (as {@link EObject}s) to be drawn
	 * @param ePackage The metamodel (as {@link EPackage}) which the element conform to
	 * @param uniqueId Id used to retrieve the folder name from the config properties file to store the file 
	 * @return The picture file
	 * @throws ServletException If any problem appears (usually IO-related problems)
	 */
	File drawObjectModel(List<EObject> elements, EPackage ePackage, String uniqueId) throws ServletException {
		// Main graphical description for the root
		GVFigureDescription gvFigureDescription = GraphdescFactory.eINSTANCE.createGVFigureDescription();
		gvFigureDescription.setOrientation(Orientation.LEFT_TO_RIGHT);
		gvFigureDescription.setAlignSameEClasses(true);
		gvFigureDescription.getEPackages().add(ePackage);

		// Graphical description for metaclass instances
		for(EClassifier eClassifier : ePackage.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				ClassFigure classFigure = GraphdescFactory.eINSTANCE.createClassFigure();
				classFigure.setEClass(eClass);

				for(EStructuralFeature eStructuralFeature : eClass.getEAllStructuralFeatures()) {
					if (eStructuralFeature instanceof EReference) {
						EReference eReference = (EReference) eStructuralFeature;
						ReferenceFigure referenceFigure = GraphdescFactory.eINSTANCE.createReferenceFigure();
						referenceFigure.setEReference(eReference);
						classFigure.getReferenceFigures().add(referenceFigure); 
					} else if (eStructuralFeature instanceof EAttribute) {
						EAttribute eAttribute = (EAttribute) eStructuralFeature;
						AttributeFigure attributeFigure = GraphdescFactory.eINSTANCE.createAttributeFigure();
						attributeFigure.setEAttribute(eAttribute);
						classFigure.getAttributeFigures().add(attributeFigure);
					}
				}
				gvFigureDescription.getClassFigures().add(classFigure);
			}
		}
		
		// To avoid validation problems, the GraphDescription has to be serialized :S
		Resource res = new ResourceImpl();
		res.getContents().add(ePackage);
		res.getContents().add(gvFigureDescription);

		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		rset.getPackageRegistry().put(GraphdescPackage.eNS_URI, GraphdescPackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("graphdesc", new XMIResourceFactoryImpl());

		try {
			File uniqueWorkingDir = new File(workingDir.getAbsolutePath() + File.separator + uniqueId);
			File tempGraphDesc = File.createTempFile("temp", ".graphdesc", uniqueWorkingDir);
			Resource res2 = rset.createResource(URI.createFileURI(tempGraphDesc.getAbsolutePath()));
			
			res2.getContents().add(ePackage);
			res2.getContents().add(gvFigureDescription);
			res2.save(null);
		} catch (IOException e) {
			throw new ServletException("Error generating the graphdesc file");
		}
		
		return drawModel(elements, uniqueId, gvFigureDescription);
	}

	/**
	 * Generic method to aoid CORS problems.
	 * <p>
	 * Adds the CORS headers related to access-control-allow settings
	 * <p>
	 * The value for the header Access-Control-Allow-Origin is parameterized
	 * with the value given in the config.properties with the key serverURL
	 * 
	 * @param response The response where the headers will be added
	 */
	protected void addResponseOptions(HttpServletResponse response) {
		if(response == null)
			throw new IllegalArgumentException("response cannot be null");

		response.setHeader("Access-Control-Allow-Origin", serverURL);
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		response.addHeader("Access-Control-Allow-Credentials", "true");
	}

	
	/**
	 * Overrides the method to add the CORS headers.
	 * <p>
	 * This method relies on {@link AbstractJsonDiscoverer#addResponseOptions(HttpServletResponse)}
	 * 
	 * @see javax.servlet.http.HttpServlet#doOptions(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		if(response == null)
			throw new IllegalArgumentException("response cannot be null");

		addResponseOptions(response);
	}
	
	/**
	 * Digest an exception message to remove the name of the exception and take
	 * the real message
	 * 
	 * @param message The undigested exception message
	 * @return The digested exception message
	 */
	protected String digestExceptionMessage(String message) {
		int colonIndex = message.indexOf(":");
		String cause = message;
		if(colonIndex > 0)
			cause = message.substring(colonIndex+2, message.length());
		return cause;
	}
}
