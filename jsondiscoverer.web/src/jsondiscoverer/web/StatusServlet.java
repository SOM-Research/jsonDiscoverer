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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Simple servlet to know the versions of other servlets
 * <p>
 * This servlet is used just as maintainance to check that everything is working
 * properly in the servlet side.
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/status")
public class StatusServlet extends AbstractJsonDiscoverer {
	private static final long serialVersionUID = 1983L;
	/**
	 * Returns the value of several options:
	 * <ul>
	 * <li>The value for the {@link AbstractJsonDiscoverer#serverURL}</li>
	 * <li>The working dir (stored in {@link AbstractJsonDiscoverer#workingDir}</li>
	 * <li>The ids used by the different servlets (see {@link JsonInjectorServlet#INJECTOR_ID}, {@link JsonSimpleDiscovererServlet#SIMPLEDISCOVERER_FOLDER}
	 * {@link JsonAdvancedDiscovererServlet#ADVANCEDDISCOVERER_FOLDER}).
	 * </ul>
	 * 
	 * @param req The Request of the call
	 * @param resp The Response to the call
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addResponseOptions(resp);
		PrintWriter out = resp.getWriter();
		out.println("CORS allow-origin: " + serverURL);
		out.println("working dir: " + ((workingDir.isDirectory()) ? "Exists" : "NOT EXISTS"));

		String injectorId = properties.getProperty(JsonInjectorServlet.INJECTOR_ID);
		if(injectorId == null || injectorId.equals("")) {
			out.println("injector dir: NOT EXISTS (neither the property)");
		} else {
			File injectorFile = new File(workingDir.getAbsolutePath() + File.separator + injectorId);
			out.println("injector dir: " + ((injectorFile.isDirectory()) ? "Exists" : "NOT EXISTS"));
		}

		String discovererId = properties.getProperty(JsonSimpleDiscovererServlet.SIMPLEDISCOVERER_FOLDER);
		if(discovererId == null || discovererId.equals("")) {
			out.println("discoverer dir: NOT EXISTS (neither the property)");
		} else {
			File discovererFile = new File(workingDir.getAbsolutePath() + File.separator + discovererId);
			out.println("discoverer dir: " + ((discovererFile.isDirectory()) ? "Exists" : "NOT EXISTS"));
		}

		String multidiscovererId = properties.getProperty(JsonAdvancedDiscovererServlet.ADVANCEDDISCOVERER_FOLDER);
		if(multidiscovererId == null || multidiscovererId.equals("")) {
			out.println("multidiscoverer dir: NOT EXISTS (neither the property)");
		} else {
			File multidiscovererFile = new File(workingDir.getAbsolutePath() + File.separator + multidiscovererId);
			out.println("multidiscoverer dir: " + ((multidiscovererFile.isDirectory()) ? "Exists" : "NOT EXISTS"));
		}

	}	

}
