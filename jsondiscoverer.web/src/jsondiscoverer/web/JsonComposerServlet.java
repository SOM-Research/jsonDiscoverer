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

import org.eclipse.emf.ecore.EPackage;

import jsondiscoverer.JsonComposer;
import jsondiscoverer.JsonMultiDiscoverer;
import jsondiscoverer.JsonSource;
import jsondiscoverer.JsonSourceSet;
import jsondiscoverer.util.GexfConverter;

/**
 * ervlet to access to the JSON composer
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 * @version 2.0.0
 *
 */
@WebServlet("/discoverComposition")
public class JsonComposerServlet extends AbstractJsonDiscoverer {
	private static final long serialVersionUID = 335L;
	// This pattern is used to analyze the params
	// The format is sources[JSON_SOURCE_NAME][SOMETHING]([])?([input|output])?
	// The important part is the JSON_SOURCE_NAME which provides the name of the parameter
	private static String paramsPattern = Pattern.quote("sources[") + "([a-zA-Z]*)"+ Pattern.quote("]") + 
			Pattern.quote("[") + "[\\$a-zA-Z]*" + Pattern.quote("]") + 
			"(" + Pattern.quote("[") + "[0-9]*" + Pattern.quote("]") + ")?" + 
			"(" + Pattern.quote("[") + "[a-zA-Z]*" + Pattern.quote("]") + ")?";


	protected EPackage composer(HashMap<String, List<String>> sources) {
		List<JsonSourceSet> sourceSets = new ArrayList<JsonSourceSet>();

		for(String sourceName : sources.keySet()) {
			List<String> sourcesList = sources.get(sourceName);
			JsonSource source = new JsonSource(sourceName);
			for(String json : sourcesList) {
				source.addJsonData(null, new StringReader(json));
			}
			JsonSourceSet sourceSet = new JsonSourceSet(sourceName+"Set");
			sourceSet.addJsonSource(source);
			sourceSets.add(sourceSet);
		}

		JsonComposer composer = new JsonComposer(sourceSets);
		EPackage result = composer.compose(new File(workingDir.getAbsoluteFile() + File.separator + "composed.ecore"));
		return result;		
	}

	protected List<JsonSourceSet> digestSources(HttpServletRequest request) {
		List<JsonSourceSet> result = new ArrayList<JsonSourceSet>();
		
		Pattern pattern = Pattern.compile(paramsPattern);
		HashMap<String, Integer> detectedSources = new HashMap<String, Integer>();
		for (Enumeration<String> e = request.getParameterNames() ; e.hasMoreElements() ;) {
			String paramName = e.nextElement();
			Matcher matcher = pattern.matcher(paramName);
			if(matcher.find()) {
				String sourceName = matcher.group(1);
				String number = matcher.group(2);
				String type = matcher.group(3);
				
				if(sourceName != null && number != null && type != null) {
					int numberValue = Integer.valueOf(number.substring(1, number.length()-1)).intValue();
					
					Integer maxDetected = detectedSources.get(sourceName);
					if(maxDetected == null || (maxDetected != null && maxDetected.intValue() < numberValue)) 
						detectedSources.put(sourceName, new Integer(numberValue));
				}
			}
		}
		
		for(String sourceName : detectedSources.keySet()) {
			JsonSourceSet sourceSet = new JsonSourceSet(sourceName+"Set");
			for(int i = 0; i <= detectedSources.get(sourceName).intValue(); i++) {
				String input = request.getParameter("sources[" + sourceName + "][jsonDefs][" + String.valueOf(i) + "][input]");
				String output = request.getParameter("sources[" + sourceName + "][jsonDefs][" + String.valueOf(i) + "][output]");
				JsonSource source = new JsonSource(sourceName);
				source.addJsonData(new StringReader(input), new StringReader(output));
				sourceSet.addJsonSource(source);
			}
			result.add(sourceSet);
		}

		return result;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addResponseOptions(response);
		// 1. Digesting the params
		List<JsonSourceSet> sourceSets = digestSources(request);
		if(sourceSets.size() == 0) throw new ServletException("No params in the call");
		
		// 2. Discovering the metamodel for each JsonSource
		for(JsonSourceSet sourceSet : sourceSets) {
			JsonMultiDiscoverer multiDiscoverer = new JsonMultiDiscoverer(sourceSet);
			multiDiscoverer.discover();
		}

		// 2. Composition
		JsonComposer composer = new JsonComposer(sourceSets);
		EPackage finalMetamodel = composer.compose(new File(workingDir.getAbsoluteFile() + File.separator + "composed.ecore"));

		// 3. Getting the graph
		String gexfString = GexfConverter.convert(finalMetamodel);

		// 4. Write the response
		PrintWriter out = response.getWriter();
		out.print(gexfString);
	}

}
