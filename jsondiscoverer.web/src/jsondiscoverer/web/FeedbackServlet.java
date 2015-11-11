package jsondiscoverer.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jmx.snmp.Timestamp;

/**
 * Servlet to provide support for receiving feedback
 * <p>
 * Answers to POST calls, receiving as input a comment and the JSON document
 * that triggered the feedback. Optionally the images for metamodel/model can
 * also be attached
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/feedback")
public class FeedbackServlet extends AbstractJsonDiscoverer {
	private static final long serialVersionUID = 2391L;
	/**
	 * The ID for this servlet which will be used to access to the working directory 
	 */
	public static final String FEEDBACK_ID = "IdFeedback";

	/**
	 * Param to get the source of the feedback.
	 * <p>
	 * For instance: simplediscoverer, advanced discoverer, etc. It is set by 
	 * the client
	 */
	public static final String sourceParam = "source";
	/**
	 * The param holding the comment from the user
	 */
	public static final String commentParam = "comment";
	/**
	 * The param holding the JSON code for the feedback
	 */
	public static final String jsonParam = "json";
	/**
	 * The param to get the discovered metamodel (as picture) which is the subject of the feedback.
	 * <p>
	 * It is usually transferred in base64 encoding (as it was received like that by the client)
	 */
	public static final String metamodelParam = "metamodel";
	/**
	 * The param to get the discovered metamodel (as ECORE file) which is the subject of the feedback
	 * <p>
	 * It is usually transferred in base64 encoding (as it was received like that by the client)
	 */
	public static final String metamodelParamFile = "metamodelFile";
	/**
	 * The param to get the discovered model (as picture) which is the subject of the feedback
	 * <p>
	 * It is usually transferred in base64 encoding (as it was received like that by the client)
	 */
	public static final String modelParam = "model";
	/**
	 * The param to get the discovered model (as XMI file) which is the subject of the feedback
	 * <p>
	 * It is usually transferred in base64 encoding (as it was received like that by the client)
	 */
	public static final String modelParamFile = "modelFile";
	
	/** 
	 * Performs a POST call to this servlet.
	 * <p>
	 * Receives the feedback from the user, including his/her comment, the json document
	 * and optionally the images. 
	 * <p>
	 * 
	 * @param request The Request of the call
	 * @param response The Response to the call
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addResponseOptions(response);
		String source = request.getParameter(sourceParam);
		String comment = request.getParameter(commentParam);
		String jsonCode = request.getParameter(jsonParam);
		String metamodel = request.getParameter(metamodelParam);
		String metamodelFile = request.getParameter(metamodelParamFile);
		String model = request.getParameter(modelParam);
		String modelFile = request.getParameter(modelParamFile);
			

		String folderName = properties.getProperty(FEEDBACK_ID);
		File uniqueWorkingDir = new File(workingDir.getAbsolutePath() + File.separator + folderName);
		File tempFile = File.createTempFile("temp", ".feedback", uniqueWorkingDir);
		
		FileWriter fw = new FileWriter(tempFile);
		fw.write("## Timestamp:\n");
		fw.write((new Timestamp((new Date()).getTime())).toString() + "\n");
		fw.write("## Source:\n");
		fw.write(source + "\n");
		fw.write("## Comment:\n");
		fw.write(comment + "\n");
		fw.write("## jsonCode:\n");
		fw.write(jsonCode + "\n");
		fw.write("## metamodel:\n");
		fw.write(metamodel + "\n");
		fw.write("## metamodelFile:\n");
		fw.write(metamodelFile + "\n");
		fw.write("## model:\n");
		fw.write(model + "\n");
		fw.write("## modelFile:\n");
		fw.write(modelFile + "\n");
		fw.flush();
		fw.close();
	}
}
