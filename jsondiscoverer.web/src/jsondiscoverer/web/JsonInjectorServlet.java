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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import jsondiscoverer.JsonInjector;
import jsondiscoverer.SingleJsonSource;

/**
 * Servlet providing access to {@link JsonInjector}
 * <p>
 * The servlet answers to POST HTTP calls. Receives as input a JSON document from which
 * a model is discovered. The discovered model is returned as both image and xmi, 
 * both of them encoded as base64.
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/simpleDiscoverModel")
public class JsonInjectorServlet extends AbstractJsonDiscoverer {
	private static final long serialVersionUID = 6L;
	/**
	 * The ID for this servlet which will be used to access to the working directory 
	 */
	public static final String INJECTOR_ID = "IdInjector";

	/** 
	 * Performs a POST call to this servlet.
	 * <p>
	 * Receives a JSON document (in the parameter set in {@link AbstractJsonDiscoverer#jsonParam})
	 * <p>
	 * Discovers a model (using {@link JsonInjector}) and returns two params: 
	 * <ul>
	 * <li>An image of the model encoded in base64</li>
	 * <li>The XMI of the metamodel encoded as base64</li>
	 * </ul>
	 * <p>
	 * 
	 * @param request The Request of the call
	 * @param response The Response to the call
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addResponseOptions(response);
		String jsonCode = request.getParameter(jsonParam);
		if(jsonCode == null || jsonCode.equals("")) throw new ServletException("No json data in the call");

		try {
			// 1. Inject the model
			SingleJsonSource source = new SingleJsonSource("Discovered");
			source.addJsonData(null, new StringReader(jsonCode));
			JsonInjector injector = new JsonInjector(source); 
			List<EObject> eObjects = injector.inject(); 
			EPackage ePackage = injector.getEPackage();

			// 2. Get the picture
			String id = properties.getProperty(INJECTOR_ID);
			if(id == null) throw new ServletException("ID for injector not found in properties");

			File resultPath = drawObjectModel(eObjects, ePackage, id);
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
		} catch(JsonSyntaxException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter out = response.getWriter();
			out.print(digestExceptionMessage(e.getMessage()));
		} catch(ServletException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			PrintWriter out = response.getWriter();
			out.print(digestExceptionMessage(e.getMessage()));
		}
	}
}
