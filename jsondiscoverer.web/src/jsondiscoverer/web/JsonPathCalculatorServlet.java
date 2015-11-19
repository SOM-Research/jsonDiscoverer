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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import jsondiscoverer.CoreographyBuilder;
import jsondiscoverer.JsonAdvancedDiscoverer;
import jsondiscoverer.JsonComposer;
import jsondiscoverer.JsonSourceSet;

/**
 * This servlet allows calculating paths between nodes of the 
 * graph discovered by {@link JsonComposerServlet}.
 * <p>
 * Answers to POST HTTP calls. Source and target nodes ar given as input and
 * paths (if exists) are returned as sequence diagrams indicating the set of
 * calls to follow such path.
 * <p>
 * As the web application does not save the state, this servlet repeats the
 * functinality provided by {@link JsonComposerServlet}.
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/calculatePath")
public class JsonPathCalculatorServlet extends JsonComposerServlet {
	private static final long serialVersionUID = 333L;
	
    /** 
	 * Performs a POST call to this servlet.
	 * <p>
	 * Receives a set of JSON documents representing different JSON-based Web services 
	 * (see {@link JsonComposerServlet.paramsPattern})to know the pattern of this param) plus
	 * the source/target nodes.
	 * <p>
	 * Calculates a path between source/target nodes (using {@link CoreographyBuilder}) and returns
	 * a sequence diagram (if the path exists) indicanting the calls to perform to obtain the
	 * target node from the source node 
	 * <p> 
	 * 
	 * @param request The Request of the call
	 * @param response The Response to the call
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addResponseOptions(response);
		// 1. Digesting the params
		List<JsonSourceSet> sourceSets = digestSources(request);
		if(sourceSets.size() == 0) throw new ServletException("No params in the call");
		
		String sourceName = request.getParameter("source");
		if(sourceName == null) throw new ServletException("No source name in the call");
		
		String targetName = request.getParameter("target");
		if(targetName == null) throw new ServletException("No target name in the call");
		
		// 2. Discovering the metamodel for each JsonSource
		for(JsonSourceSet sourceSet : sourceSets) {
			JsonAdvancedDiscoverer multiDiscoverer = new JsonAdvancedDiscoverer(sourceSet);
			multiDiscoverer.discover();
		}

		// 2. Composition
		JsonComposer composer = new JsonComposer(sourceSets);
		EPackage finalMetamodel = composer.compose();

		EClass sourceEClass = null;
		EClass targetEClass = null;
		// 3. Locating source and target
		for(EClassifier eClassifier : finalMetamodel.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				if(eClass.getName().equals(sourceName)) {
					sourceEClass = eClass;
				}
				if(eClass.getName().equals(targetName)) {
					targetEClass = eClass;
				}
			}
		}
		if(sourceEClass == null || targetEClass == null) 
			throw new ServletException("The source or target elements were not found in the metamodel");
		
		// 4. Getting the path
		CoreographyBuilder builder = new CoreographyBuilder(finalMetamodel);
		try {
			String diagram = builder.calculate(sourceEClass, targetEClass);
			if(diagram == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			} else {
				// 5. Write the response
				PrintWriter out = response.getWriter();
				out.print(diagram);
			}
		} catch(IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
