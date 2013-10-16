package fr.inria.atlanmod.json.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.emftools.emf2gv.graphdesc.GraphdescPackage;
import org.emftools.emf2gv.processor.core.StandaloneProcessor;

import sun.misc.BASE64Encoder;

import fr.inria.atlanmod.JsonStandaloneSetup;
import fr.inria.atlanmod.discoverer.JsonDiscoverer;

/**
 * Main class that provides the main access to the JSON discoverer
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@WebServlet("/discover")
public class JsonDiscovererServlet extends HttpServlet {
	public static File workingDir = null;
	public static String dotExePath = null;
	private static String jsonParam = null;
	private static final long serialVersionUID = 1L;
	       
    public JsonDiscovererServlet() {
        super();
    	
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();

    	JsonStandaloneSetup.doSetup();
    	
    	// Getting configuration
    	String workingDirString = null;
    	try {
        	Properties properties = new Properties();
			properties.load(getServletContext().getResourceAsStream("/WEB-INF/config.properties"));
			workingDirString = properties.getProperty("workingDir");
			dotExePath = properties.getProperty("dotExePath");
			jsonParam = properties.getProperty("parameter");
		} catch (IOException e) {
			throw new ServletException("Discover servlet could not find the configuration");
		}
    	
    	// We need a File (not a String)
		workingDir = new File(workingDirString);
		if(!workingDir.isDirectory()) throw new ServletException("The working dir does not exist");
    }

	/* 
	 * Performs a POST call to this servlet. The JSON_CODE parameter is queried to get the JSON code to
	 * be discovered. The Discovered model is then transformed into a BASE64 image to be used in the web.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonCode = request.getParameter(jsonParam);
		String resultImage = discoverMetamodelBase64(jsonCode);
		PrintWriter out = response.getWriter();
        out.print(resultImage);
	}
	
	/**
	 * Discover a metamodel and returns the File of the picture representing
	 * the metamode
	 * 
	 * @param jsonCode
	 * @return
	 * @throws IOException
	 */
	private File discoverMetamodel(String jsonCode) throws ServletException {
		// Discovering
		JsonDiscoverer discoverer = new JsonDiscoverer();
		EPackage discoveredModel = discoverer.discoverMetamodel(jsonCode);
		
		// Drawing the discovered model
		EcorePackage.eINSTANCE.eClass();
		GraphdescPackage.eINSTANCE.eClass();
				
		List<EObject> toDraw= new ArrayList<EObject>();
		toDraw.add(discoveredModel);		

		File resultPath;
		try {
			resultPath = File.createTempFile("temp", ".jpg", workingDir);
		} catch (IOException e1) {
			throw new ServletException("Not possible to acces to temp dir");
		}
		
		try {
			StandaloneProcessor.process(toDraw, null, workingDir, resultPath.getAbsolutePath(), null, null, dotExePath, true, false, "UTF-8", null, null, null);
		} catch (CoreException e) {
			throw new ServletException("Not possible to generate the image");
		}

		return resultPath;
	}
	
	/**
	 * Performs the discovery and returns a picture encoded in BASE64 with the 
	 * pciture representing the discovered metamodel
	 * 
	 * @param jsonCode
	 * @return
	 * @throws IOException
	 */
	private String discoverMetamodelBase64(String jsonCode) throws ServletException {
		File resultPath = discoverMetamodel(jsonCode);
		String resultImage;
		try {
			resultImage = encodeToString(resultPath);
		} catch (IOException e) {
			throw new ServletException("Not possible to encode");
		}
		resultPath.delete();
		return resultImage;
	}
	
	/**
	 * Encodes a JPG picture into the BASE64 format
	 * 
	 * @param imagePath
	 * @return
	 * @throws IOException
	 */
	private String encodeToString(File imagePath) throws IOException {
		BufferedImage image = ImageIO.read(imagePath);
		
	    String imageString = null;
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();

	    try {
	        ImageIO.write(image, "JPG", bos);
	        byte[] imageBytes = bos.toByteArray();

	        BASE64Encoder encoder = new BASE64Encoder();
	        imageString = encoder.encode(imageBytes);

	        bos.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return imageString;
	}
	
}
