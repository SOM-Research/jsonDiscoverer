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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsondiscoverer.JsonSimpleDiscoverer;


/**
 * This class acts as proxy to get the json coming from other websites. 
 * <p> 
 * The result is filter to the first LIMIT_LINES to avoid overloading the server.
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/getJson")
public class ProxyServlet extends AbstractJsonDiscoverer {
	private static final long serialVersionUID = 133L;
	
	/**
	 * Name of the property holding the name of the parameter
	 */
	public static final String URL_PARAM = "UrlParameter";
	
	/**
	 * Name of the property holding the number of lines that the proxy should read
	 */
	public static final String LIMIT_CHARS = "LimitChars";
	
	/**
	 * The name of the param which will be received in the POST call
	 */
	private String urlParam = "";
	
	/**
	 * Limit of chars to read
	 */
	private int limitChar = 0;
	
	/**
	 * Uses the super class init method and additionally initializes {@link ProxyServlet#urlParam} and {@link ProxyServlet#limitChar}
	 * 
	 * @see jsondiscoverer.web.AbstractJsonDiscoverer#init()
	 */
	public void init() throws ServletException {
		super.init();
		try {
			Properties properties = new Properties();
			properties.load(getServletContext().getResourceAsStream("/WEB-INF/config.properties"));
			urlParam = properties.getProperty(URL_PARAM);
			limitChar = Integer.valueOf(properties.getProperty(LIMIT_CHARS)).intValue();
		} catch (Exception e) {
			throw new ServletException("Proxy servlet could not find the configuration");
		}
	}
	
    /** 
	 * Performs a POST call to this servlet.
	 * <p>
	 * Receives a URL (in the parameter set in {@link ProxyServlet#urlParam}).
	 * <p>
	 * Reads a number of bytes (as many as {@link ProxyServlet#limitChar} sets) from 
	 * the URL and returns them as text.
	 * 
	 * @param req The Request of the call
	 * @param resp The Response to the call
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addResponseOptions(resp);
		// Getting the URL from the request
		String urlString = req.getParameter(urlParam);
		
		// Calling the URL
		URL url = null;
		try {
			url = new URL(urlString);
		} catch(MalformedURLException e) {
			throw new ServletException(e.getMessage());
		}
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		int responseCode = connection.getResponseCode();
		
		StringBuffer response = new StringBuffer();
		if(responseCode == 200) {
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			int inputChar;
			int numCharsRead = 0;
			while (numCharsRead < limitChar && (inputChar = br.read()) != -1) { 
				response.append((char) inputChar);
				numCharsRead++;
			}
			if(numCharsRead >= limitChar && br.read() != -1) {
				response = new StringBuffer();
				response.append("[ { \"error\" : \"URL returned too much information. For security reasons, the content returned by the URL cannot exceed " + limitChar + " bytes \" } ]");
			}
			br.close();
		} else {
			response = new StringBuffer();
			response.append("[ { \"error\" : \"Error calling the URL\" } ]");
		}
		
		// Writing the response
		PrintWriter out = resp.getWriter();
        out.print(response.toString());
	}
}