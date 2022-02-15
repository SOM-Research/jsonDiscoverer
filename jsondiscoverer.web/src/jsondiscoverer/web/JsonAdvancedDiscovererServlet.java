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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import com.google.gson.JsonObject;

import jsondiscoverer.JsonAdvancedDiscoverer;
import jsondiscoverer.JsonSource;
import jsondiscoverer.JsonSourceSet;

/**
 * Servlet providing acces to {@link JsonAdvancedDiscoverer}.
 * <p>
 * Answers to POST calls, receiving as input a JSON document including the set
 * of JSON documents representing different JSON-based Web services (see {@link JsonAdvancedDiscovererServlet#paramsPattern} 
 * to know the pattern of this param). A metamodel is discovered out of these
 * JSON documents. The discovered metamodel is returned as both image and xmi, 
 * both of them encoded as base64.
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/advancedDiscover")
public class JsonAdvancedDiscovererServlet extends AbstractJsonDiscoverer {
	private static final long serialVersionUID = 23L;
	
	/**
	 * The ID for this servlet which will be used to access to the working directory
	 */
	public static final String ADVANCEDDISCOVERER_FOLDER = "SubfolderAdvancedDiscoverer";
	
	/**
	 * Name of the folder where the temp files will be stored
	 */
	private static String folderName;

	/** This pattern is used to analyze the params
	  * The format is sources[JSON_SOURCE_NAME][SOMETHING]([])?
	  * The important part is the JSON_SOURCE_NAME which provides the name of the parameter */
	private static String paramsPattern = Pattern.quote("sources[") + "([a-zA-Z0-9]*)"+ Pattern.quote("][") + "[\\$a-zA-Z]*" + Pattern.quote("]") + "(" + Pattern.quote("[]") + ")?";
	
	/**
	 * Uses the super class init method and additionally initializes {@link JsonAdvancedDiscovererServlet#folderName}
	 * 
	 * @see jsondiscoverer.web.AbstractJsonDiscoverer#init()
	 */
	public void init() throws ServletException {
		super.init();
		folderName = properties.getProperty(ADVANCEDDISCOVERER_FOLDER);
		if(folderName == null) throw new ServletException("ID for Advanced Discoverer not found in properties");
	}
	
	/**
	 * Digest the parameters of the request according to the pattern defined in
	 * {@link JsonAdvancedDiscovererServlet#paramsPattern}
	 * 
	 * @param request The request from which the params are obtained
	 * @return The digested {@link JsonSourceSet}
	 */
	protected JsonSourceSet digestSources(HttpServletRequest request ) {
		Pattern pattern = Pattern.compile(paramsPattern);
		HashMap<String, List<String>> sources = new HashMap<String, List<String>>();

		for (Enumeration<String> e = request.getParameterNames() ; e.hasMoreElements() ;) {
			String paramName = e.nextElement();
			Matcher matcher = pattern.matcher(paramName);
			if(matcher.find()) {
				String sourceName = matcher.group(1);
				String[] valuesParam = request.getParameterValues("sources[" + sourceName + "][jsonDefs][]");
				List<String> sourcesList = sources.get(sourceName);
				if(sourcesList == null) sources.put(sourceName, (sourcesList = new ArrayList<String>()));
				for(String valueParam : valuesParam) {
					sourcesList.add(valueParam);
				}

			}
		}
		
		JsonSourceSet sourceSet = new JsonSourceSet("Composed");
		for (String sourceName : sources.keySet()) {
			List<String> sourcesList = sources.get(sourceName);
			JsonSource source = new JsonSource(sourceName);
			for(String json : sourcesList) {
				source.addJsonData(null, new StringReader(json));
			}
			sourceSet.addJsonSource(source);
		}
		
		return sourceSet;
	}
	
    /** 
	 * Performs a POST call to this servlet.
	 * <p>
	 * Receives a set of JSON documents representing different JSON-based Web services 
	 * (see {@link JsonAdvancedDiscovererServlet#paramsPattern} to know the pattern of this param)
	 * <p>
	 * Discovers a metamodel (using {@link JsonAdvancedDiscoverer}) and returns two params: 
	 * <ul>
	 * <li>An image of the metamodel encoded in base64</li>
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
		// 1. Digesting the params
		JsonSourceSet sourceSet = digestSources(request);
		if(sourceSet.getJsonSources().size() == 0) throw new ServletException("No params in the call");

		// 2. Composition
		JsonAdvancedDiscoverer multiDiscoverer = new JsonAdvancedDiscoverer(sourceSet);
		EPackage finalMetamodel = multiDiscoverer.discover();

		// 3. Get the picture
		if(folderName == null) throw new ServletException("ID for composer not found in properties");
		
		List<EObject> toDraw= new ArrayList<EObject>();
		toDraw.add(finalMetamodel);	
		File resultPath = drawModel(toDraw, folderName);		
		String resultImage = encodeToString(resultPath);
		resultPath.delete();
		
		// 4. Get the metamodel as string
		String resultXMI = encodeToString(finalMetamodel, folderName);
		
		// 4. Write the response
		// Building the response
		response.setContentType("text/x-json;charset=UTF-8");   
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("image", resultImage);
		jsonResponse.addProperty("xmi", resultXMI);
		PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
	}
}
