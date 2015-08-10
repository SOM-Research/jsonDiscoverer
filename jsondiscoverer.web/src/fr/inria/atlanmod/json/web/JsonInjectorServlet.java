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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.emf.ecore.EObject;

import com.google.gson.JsonObject;

import fr.inria.atlanmod.discoverer.JsonInjector;
import fr.inria.atlanmod.discoverer.SingleJsonSource;

@WebServlet("/injectModel")
public class JsonInjectorServlet extends AbstractJsonDiscoverer {
	public static final String version = "1.3";
	
	private static final long serialVersionUID = 6L;
	// The ID for this servlet which will be used to access to the working directory
	public static final String INJECTOR_ID = "IdInjector";

	/* 
	 * Performs a POST call and returns the a picture in BASE64 representing the model
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addResponseOptions(response);
		String jsonCode = request.getParameter(jsonParam);
		if(jsonCode == null || jsonCode.equals("")) throw new ServletException("No json data in the call");
				
		// 1. Inject the model
		SingleJsonSource source = new SingleJsonSource("Discovered");
		source.addJsonDef(jsonCode);
		JsonInjector injector = new JsonInjector(source); 
		List<EObject> eObjects = injector.inject(); 
		
		// 2. Get the picture
		String id = properties.getProperty(INJECTOR_ID);
		if(id == null) throw new ServletException("ID for injector not found in properties");
		
		File resultPath = drawModel(eObjects, id);		
		String resultImage = encodeToString(resultPath);
		resultPath.delete();
		
		// 3. Get the model in string
		String resultXMI = encodeToString(eObjects, id);
		
		// 4. Write the response
		response.setContentType("text/x-json;charset=UTF-8");   
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("image", resultImage);
		jsonResponse.addProperty("xmi", resultXMI);
		PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
	}
}
