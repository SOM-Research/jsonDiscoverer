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

package fr.inria.atlanmod.json.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emftools.emf2gv.graphdesc.GraphdescPackage;
import org.emftools.emf2gv.processor.core.StandaloneProcessor;

import sun.misc.BASE64Encoder;

/**
 * Abstract class to factor some common behavior
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public abstract class AbstractJsonDiscoverer extends HttpServlet {
	private static final long serialVersionUID = 69L;

	// The main path to the working dir (needed for generating the pictures)
	public static File workingDir = null;

	// The path to the Graphviz DOT execitable (needed for generating the pictures)
	public static String dotExePath = null;

	// The jsonParam used for discovery (where the json code is stored)
	static String jsonParam = null;

	// Server for the CORS
	String serverURL = "";

	Properties properties = null;

	@Override
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
	 * Encodes a JPG picture into the BASE64 format
	 * 
	 * @param imagePath
	 * @return
	 * @throws IOException
	 */
	String encodeToString(File imagePath) throws IOException {
		BufferedImage image = ImageIO.read(imagePath);

		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "JPG", bos);
			byte[] imageBytes = bos.toByteArray();

			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);

			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}
	
	/**
	 * Saves the EPackage into a XMI and then read it to return it as String in Base64
	 * 
	 * @param ePackage
	 * @param uniqueId
	 * @return
	 * @throws ServletException
	 */
	String encodeToString(EPackage ePackage,  String uniqueId) throws ServletException {
		File uniqueWorkingDir = new File(workingDir.getAbsolutePath() + File.separator + uniqueId);
		if(!uniqueWorkingDir.isDirectory()) throw new ServletException("The working dir could not be set:" + uniqueWorkingDir.getAbsolutePath());

		// Getting a temp file
		File resultPath;
		try {
			resultPath = File.createTempFile("temp", ".xmi", uniqueWorkingDir);
		} catch (IOException e1) {
			throw new ServletException("Not possible to access to temp dir");
		}
		
		// Saving into XMI
		ResourceSet rSet = new ResourceSetImpl();
		Resource res = rSet.createResource(URI.createFileURI(resultPath.getAbsolutePath()));
		try {
			res.getContents().add(ePackage);
			res.save(null);
		} catch (IOException e) {
			throw new ServletException("Not possible to save the Epackage", e);
		}
		
		// Loading XMI into String
		String result;
		try {
			FileInputStream fis = new FileInputStream(resultPath);
			String content = IOUtils.readStringFromStream(fis);
			BASE64Encoder encoder = new BASE64Encoder();
			result = encoder.encode(content.getBytes());
			fis.close();
		} catch (IOException e) {
			throw new ServletException("Error reading XMI to String", e);
		}
		
		return result;
	}
	
	/**
	 * Saves a list of EObjects into a XMI and then read it to return it as String in Base64
	 * 
	 * @param ePackage
	 * @param uniqueId
	 * @return
	 * @throws ServletException
	 */
	String encodeToString(List<EObject> elements,  String uniqueId) throws ServletException {
		File uniqueWorkingDir = new File(workingDir.getAbsolutePath() + File.separator + uniqueId);
		if(!uniqueWorkingDir.isDirectory()) throw new ServletException("The working dir could not be set:" + uniqueWorkingDir.getAbsolutePath());

		// Getting a temp file
		File resultPath;
		try {
			resultPath = File.createTempFile("temp", ".xmi", uniqueWorkingDir);
		} catch (IOException e1) {
			throw new ServletException("Not possible to access to temp dir");
		}
		
		// Saving into XMI
		ResourceSet rSet = new ResourceSetImpl();
		Resource res = rSet.createResource(URI.createFileURI(resultPath.getAbsolutePath()));
		try {
			res.getContents().addAll(elements);
			res.save(null);
		} catch (IOException e) {
			throw new ServletException("Not possible to save the Epackage", e);
		}
		
		// Loading XMI into String
		String result;
		try {
			FileInputStream fis = new FileInputStream(resultPath);
			String content = IOUtils.readStringFromStream(fis);
			BASE64Encoder encoder = new BASE64Encoder();
			result = encoder.encode(content.getBytes());
			fis.close();
		} catch (IOException e) {
			throw new ServletException("Error reading XMI to String", e);
		}
		
		return result;
	}
	
	/**
	 * Draws a model into a picture. To avoid file access problems, an unique id has to be 
	 * provided. A new directory using such id will be created.
	 * 
	 * @param elements Elements to be drawn
	 * @param uniqueId Id of the process asking for the generation 
	 * @return
	 * @throws ServletException
	 */
	File drawModel(List<EObject> elements, String uniqueId) throws ServletException {
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
			StandaloneProcessor.process(elements, null, uniqueWorkingDir, resultPath.getAbsolutePath(), null, null, dotExePath, true, false, "UTF-8", null, null, null);
		} catch (CoreException e) {
			throw new ServletException("Not possible to generate the image");
		}

		return resultPath;
	}

	/**
	 * Adds the CORS headers
	 * 
	 * @param response
	 */
	protected void addResponseOptions(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", serverURL);
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		response.addHeader("Access-Control-Allow-Credentials", "true");
	}

	@Override
	protected void doOptions(HttpServletRequest arg0, HttpServletResponse response)	throws ServletException, IOException {
		addResponseOptions(response);
	}
}
