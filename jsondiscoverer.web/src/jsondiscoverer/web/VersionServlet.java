package jsondiscoverer.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Super simple servlet to know the versio running in the server
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/version")
public class VersionServlet extends AbstractJsonDiscoverer {
	private static final long serialVersionUID = 699L;

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

