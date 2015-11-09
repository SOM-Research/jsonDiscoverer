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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import jsondiscoverer.JsonSimpleDiscoverer;
import jsondiscoverer.JsonSource;

/**
 * Servlet providing providing access to {@link JsonSimpleDiscoverer}.
 * <p>
 * The servlet answers to POST HTTP calls. Receives as input a JSON document from which
 * a metamodel is discovered. The discovered metamodel is returned as both image and xmi, 
 * both of them encoded as base64.
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/simpleDiscoverMetamodel")
public class JsonSimpleDiscovererServlet extends AbstractJsonDiscoverer {
	private static final long serialVersionUID = 83L;
	
	/** 
	 * The ID for this servlet which will be used to access to the working directory 
	 */
	public static final String SIMPLEDISCOVERER_FOLDER = "SubfolderSimpleDiscoverer";
	
	/**
	 * Name of the folder where the temp files will be stored
	 */
	private static String folderName;

	
	/**
	 * Uses the super class init method and additionally initializes {@link JsonSimpleDiscovererServlet#folderName}
	 * 
	 * @see jsondiscoverer.web.AbstractJsonDiscoverer#init()
	 */
	public void init() throws ServletException {
		super.init();
		folderName = properties.getProperty(SIMPLEDISCOVERER_FOLDER);
		if(folderName == null) throw new ServletException("ID for Simple Discoverer not found in properties");
	}
	
    /** 
	 * Performs a POST call to this servlet.
	 * <p>
	 * Receives a JSON document (in the parameter set in {@link AbstractJsonDiscoverer#jsonParam})
	 * <p>
	 * Discovers a metamodel (using {@link JsonSimpleDiscoverer}) and returns two params: 
	 * <ul>
	 * <li>An image of the metamodel encoded in base64</li>
	 * <li>The XMI of the metamodel encoded as base64</li>
	 * </ul>
	 * <p>
	 * 
	 * @param request The Request of the call
	 * @param response The Response to the call
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addResponseOptions(response);
		String jsonCode = request.getParameter(jsonParam);
		if(jsonCode == null || jsonCode.equals("")) throw new ServletException("No json data in the call");
		
		EPackage resultMetamodel = discoverMetamodel(jsonCode);

		String resultImage = discoverMetamodelBase64(resultMetamodel);
		String resultXMI = encodeToString(resultMetamodel, folderName);

		// Building the response
		response.setContentType("text/x-json;charset=UTF-8");   
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("image", resultImage);
		jsonResponse.addProperty("xmi", resultXMI);
		PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
	}
	
	/**
	 * Discover a metamodel from a JSON document.
	 * <p> 
	 * THe method uses {@link JsonSimpleDiscoverer} to discover the metamodel (as {@link EPackage})
	 * 
	 * @param jsonCode The JSON document
	 * @return The Epackage The metamodel
	 * @throws ServletException Usually triggered with IO-related issues
	 */
	private EPackage discoverMetamodel(String jsonCode) throws ServletException {
		// Discovering
		JsonSimpleDiscoverer discoverer = new JsonSimpleDiscoverer();
		JsonSource source = new JsonSource("Discovered");
		try {
			source.addJsonData(null, new StringReader(jsonCode));
		} catch(JsonSyntaxException e) {
			throw new ServletException(e.getMessage());
		}
		EPackage discoveredModel = discoverer.discover(source);
		
		return discoveredModel;
	}
	
	
	
	/**
	 * Converts a EPackage into a picture and saves it in disk.
	 * <p>
	 * The method relies on {@link AbstractJsonDiscoverer#drawModel(List, String)}
	 * 
	 * @param ePackage The metamodel
	 * @return The file with the picture 
	 * @throws IOException Something went wrong doing IO things
	 */
	private File convertToImage(EPackage ePackage) throws ServletException {
		List<EObject> toDraw= new ArrayList<EObject>();
		toDraw.add(ePackage);	
		
		String id = properties.getProperty(SIMPLEDISCOVERER_FOLDER);
		if(id == null) throw new ServletException("ID for discoverer not found in properties");
		
		File resultPath = drawModel(toDraw, id);

		return resultPath;
	}
	
	
	
	/**
	 * Performs the discovery and returns a picture encoded in BASE64 with the 
	 * picture representing the discovered metamodel
	 * <p>
	 * The method relies on {@link JsonSimpleDiscovererServlet#convertToImage(EPackage)} and
	 * {@link JsonSimpleDiscovererServlet#encodeToString(File)}
	 * 
	 * @param ePackage The metamodel
	 * @return A String encoded in base64 representing the picture of the metamodel
	 * @throws IOException Something went wrong with IO
	 */
	private String discoverMetamodelBase64(EPackage ePackage) throws ServletException {
		File resultPath = convertToImage(ePackage);
		String resultImage;
		try {
			resultImage = encodeToString(resultPath);
		} catch (IOException e) {
			throw new ServletException("Not possible to encode");
		}
		resultPath.delete(); 
		return resultImage;
	}
	
	
}
