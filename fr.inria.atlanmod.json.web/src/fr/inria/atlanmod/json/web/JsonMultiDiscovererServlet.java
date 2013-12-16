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

import fr.inria.atlanmod.discoverer.JsonMultiDiscoverer;
import fr.inria.atlanmod.discoverer.JsonSource;
import fr.inria.atlanmod.discoverer.JsonSourceSet;

/**
 * Main servlet to provide access to the composer
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
@WebServlet("/multiDiscover")
public class JsonMultiDiscovererServlet extends AbstractJsonDiscoverer {
	public static final String version = "1.0";
	
	private static final long serialVersionUID = 23L;
	
	// The ID for this servlet which will be used to access to the working directory
	private static final String MULTIDISCOVERER_ID = "IdMultiDiscoverer";

	// This pattern is used to analyze the params
	// The format is sources[JSON_SOURCE_NAME][SOMETHING]([])?
	// The important part is the JSON_SOURCE_NAME which provides the name of the parameter
	private static String paramsPattern = Pattern.quote("sources[") + "([a-zA-Z]*)"+ Pattern.quote("][") + "[\\$a-zA-Z]*" + Pattern.quote("]") + "(" + Pattern.quote("[]") + ")?";
	
	/* 
	 * Performs a POST call and returns a String in base64 with the picture of the metamodel
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Disgesting the params
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
		if(sources.size() == 0) throw new ServletException("No params in the call");

		// 2. Discovery
		JsonSourceSet sourceSet = new JsonSourceSet("toCompose");
		for (String sourceName : sources.keySet()) {
			List<String> sourcesList = sources.get(sourceName);
			JsonSource source = new JsonSource(sourceName);
			for(String json : sourcesList) {
				source.addJsonDef(json);
			}
			sourceSet.addJsonSource(source);
		}
		
		// 3. Composition
		JsonMultiDiscoverer multiDiscoverer = new JsonMultiDiscoverer(sourceSet);
		EPackage finalMetamodel = multiDiscoverer.discover();

		// 4. Get the picture
		String id = properties.getProperty(MULTIDISCOVERER_ID);
		if(id == null) throw new ServletException("ID for composer not found in properties");
		
		List<EObject> toDraw= new ArrayList<EObject>();
		toDraw.add(finalMetamodel);	
		File resultPath = drawModel(toDraw, id);		
		String resultImage = encodeToString(resultPath);
		resultPath.delete();
		
		// 4. Write the response
		PrintWriter out = response.getWriter();
		out.print(resultImage);
	}
}
