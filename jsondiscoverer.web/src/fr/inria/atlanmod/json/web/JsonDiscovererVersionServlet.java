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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Simple servlet to know the versions of the other servlets
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
@WebServlet("/version")
public class JsonDiscovererVersionServlet extends AbstractJsonDiscoverer {
	private static final long serialVersionUID = 1983L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addResponseOptions(resp);
		PrintWriter out = resp.getWriter();
		out.println("CORS allow-origin: " + serverURL);
		out.println("Discoverer servlet version: " + JsonDiscovererServlet.version);
		out.println("Injector servlet version: " + JsonInjectorServlet.version);
		out.println("MultiDiscoverer servlet version: " + JsonMultiDiscovererServlet.version);
		out.println("Composer servlet version: " + JsonComposerServlet.version);
		out.println("Path Calculator servlet version: " + JsonPathCalculatorServlet.version);
		out.println("Proxy servlet version: " + ProxyServlet.version);

		out.println("working dir: " + ((workingDir.isDirectory()) ? "Exists" : "NOT EXISTS"));

		String injectorId = properties.getProperty(JsonInjectorServlet.INJECTOR_ID);
		if(injectorId == null || injectorId.equals("")) {
			out.println("injector dir: NOT EXISTS (neither the property)");
		} else {
			File injectorFile = new File(workingDir.getAbsolutePath() + File.separator + injectorId);
			out.println("injector dir: " + ((injectorFile.isDirectory()) ? "Exists" : "NOT EXISTS"));
		}

		String discovererId = properties.getProperty(JsonDiscovererServlet.DISCOVERER_ID);
		if(discovererId == null || discovererId.equals("")) {
			out.println("discoverer dir: NOT EXISTS (neither the property)");
		} else {
			File discovererFile = new File(workingDir.getAbsolutePath() + File.separator + discovererId);
			out.println("discoverer dir: " + ((discovererFile.isDirectory()) ? "Exists" : "NOT EXISTS"));
		}

		String multidiscovererId = properties.getProperty(JsonMultiDiscovererServlet.MULTIDISCOVERER_ID);
		if(multidiscovererId == null || multidiscovererId.equals("")) {
			out.println("multidiscoverer dir: NOT EXISTS (neither the property)");
		} else {
			File multidiscovererFile = new File(workingDir.getAbsolutePath() + File.separator + multidiscovererId);
			out.println("multidiscoverer dir: " + ((multidiscovererFile.isDirectory()) ? "Exists" : "NOT EXISTS"));
		}

	}	

}
