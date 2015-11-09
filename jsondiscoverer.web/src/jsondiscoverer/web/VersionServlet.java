package jsondiscoverer.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Super simple servlet to know the version running in the server
 * <p>
 * Answers to GET calls and returns a text including the value
 * of the version param in the config.properties file
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/version")
public class VersionServlet extends AbstractJsonDiscoverer {
	private static final long serialVersionUID = 699L;

	/**
	 * Returns the value of the version param in the config.properties file
	 * 
	 * @param req The Request of the call
	 * @param resp The Response to the call
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addResponseOptions(resp);
        JsonObject response = new JsonObject();

        // Building the response
        response.addProperty("version", version);
        resp.setContentType("text/x-json;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        pw.append(response.toString());
    }
}

