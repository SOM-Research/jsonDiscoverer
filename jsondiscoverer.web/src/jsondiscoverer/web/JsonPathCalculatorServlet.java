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
import jsondiscoverer.JsonComposer;
import jsondiscoverer.JsonMultiDiscoverer;
import jsondiscoverer.JsonSourceSet;

/**
 * This servlet allows calculating paths between nodes of the discovered metamodel
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 * @version 2.0.0
 *
 */
@WebServlet("/calculatePath")
public class JsonPathCalculatorServlet extends JsonComposerServlet {
	@Override
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
			JsonMultiDiscoverer multiDiscoverer = new JsonMultiDiscoverer(sourceSet);
			multiDiscoverer.discover();
		}

		// 2. Composition
		JsonComposer composer = new JsonComposer(sourceSets);
		EPackage finalMetamodel = composer.compose(null);

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
		String diagram = builder.calculate(sourceEClass, targetEClass);
		
		// 5. Write the response
		PrintWriter out = response.getWriter();
		out.print(diagram);
	}
}
