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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.google.gson.JsonObject;

import fr.inria.atlanmod.discoverer.JsonDiscoverer;
import fr.inria.atlanmod.discoverer.JsonSource;

/**
 * Main class that provides the main access to the JSON discoverer
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/discoverMetamodel")
public class JsonDiscovererServlet extends AbstractJsonDiscoverer {
	public static final String version = "1.2";
	
	private static final long serialVersionUID = 83L;
	
	// The ID for this servlet which will be used to access to the working directory
	public static final String DISCOVERER_ID = "IdDiscoverer";
	
    /* 
	 * Performs a POST call to this servlet. The JSON_CODE parameter is queried to get the JSON code to
	 * be discovered. The Discovered model is then transformed into a BASE64 image to be used in the web.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addResponseOptions(response);
		String jsonCode = request.getParameter(jsonParam);
		if(jsonCode == null || jsonCode.equals("")) throw new ServletException("No json data in the call");
		
		EPackage resultMetamodel = discoverMetamodel(jsonCode);

		String id = properties.getProperty(DISCOVERER_ID);
		String resultImage = discoverMetamodelBase64(resultMetamodel);
		String resultXMI = encodeToString(resultMetamodel, id);

		// Building the response
		response.setContentType("text/x-json;charset=UTF-8");   
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("image", resultImage);
		jsonResponse.addProperty("xmi", resultXMI);
		PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
	}
	
	/**
	 * Discover a metamodel from JSON
	 * 
	 * @param jsonCode
	 * @return The Epackage
	 * @throws ServletException
	 */
	private EPackage discoverMetamodel(String jsonCode) throws ServletException {
		// Discovering
		JsonDiscoverer discoverer = new JsonDiscoverer();
		JsonSource source = new JsonSource("Discovered");
		source.addJsonDef(jsonCode);
		EPackage discoveredModel = discoverer.discoverMetamodel(source);
		
		return discoveredModel;
	}
	
	
	
	/**
	 * Converts a EPackage into a picture and saves it in disk
	 * 
	 * @param jsonCode
	 * @return
	 * @throws IOException
	 */
	private File convertToImage(EPackage ePackage) throws ServletException {
		List<EObject> toDraw= new ArrayList<EObject>();
		toDraw.add(ePackage);	
		
		String id = properties.getProperty(DISCOVERER_ID);
		if(id == null) throw new ServletException("ID for discoverer not found in properties");
		
		File resultPath = drawModel(toDraw, id);

		return resultPath;
	}
	
	
	
	/**
	 * Performs the discovery and returns a picture encoded in BASE64 with the 
	 * pciture representing the discovered metamodel
	 * 
	 * @param jsonCode
	 * @return
	 * @throws IOException
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
