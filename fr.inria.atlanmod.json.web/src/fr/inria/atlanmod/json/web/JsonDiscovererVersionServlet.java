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
		PrintWriter out = resp.getWriter();
		out.println("Discoverer servlet version: " + JsonDiscovererServlet.version);
		out.println("Injector servlet version: " + JsonInjectorServlet.version);
		out.println("Composer servlet version: " + JsonComposerServlet.version);
		
	}	

}
