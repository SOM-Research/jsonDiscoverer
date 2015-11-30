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



package jsondiscoverer.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import jsondiscoverer.AnnotationHelper;

/**
 * This class converts a metamodel discoverer into a graph GEXF file. 
 * <p>
 * As the GEXF format is pretty simple, the transformation is done programmatically
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class GexfConverter {
	/**
	 * Converts a metamodel (as {@link EPackage} element) into a String which compliant to the GEXF format.
	 * 
	 * @param ePackage The metamodel (as {@link EPackage})
	 * @return A {@link String} compliant to the GEXF format
	 */
	public static String convert(EPackage ePackage) {
		StringBuffer result = new StringBuffer();
		Timestamp timeStamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
		
		result.append("<gexf xmlns=\"http://www.gexf.net/1.2draft\" version=\"1.2\" xmlns:viz=\"http://www.gexf.net/1.2draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.2draft http://www.gexf.net/1.2draft/gexf.xsd\">\n" +
				"\t<meta lastmodifieddate=\""+ timeStamp.toString() + "\">\n" +
				"\t\t<creator>JSON Discovorer</creator>\n" +
				"\t\t<description>Conversion from Ecore</description>\n" +
				"\t</meta>\n");
		
		result.append("\t<graph mode=\"static\" defaultedgetype=\"directed\">\n");

		result.append("\t\t<attributes class=\"node\">\n");
		result.append("\t\t\t<attribute id=\"at1\" title=\"type\" type=\"string\"></attribute>\n");
		result.append("\t\t\t<attribute id=\"at2\" title=\"sourceName\" type=\"string\"></attribute>\n");
		result.append("\t\t</attributes>\n");
		
		result.append("\t\t<attributes class=\"edge\">\n");
		result.append("\t\t\t<attribute id=\"at1\" title=\"type\" type=\"string\"></attribute>\n");
		result.append("\t\t</attributes>\n");
		
		result.append("\t\t<nodes>\n");
		
		StringBuffer attEdges = new StringBuffer();
		
		int nodeCounter = 0;
		int edgeCounter = 0;
		
		HashMap<String, String> nodeColors = new HashMap<String, String>();
		
		HashMap<String, String> nodes = new HashMap<String, String>();
		for(EClassifier eClassifier : ePackage.getEClassifiers() ){
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				String sourceName = AnnotationHelper.INSTANCE.getSourceName(eClass);
				
				String nodeName = String.valueOf(nodeCounter++);
				result.append("\t\t\t<node id =\"" + nodeName + "\" label=\"" + eClass.getName() + "\">\n");
				result.append("\t\t\t\t<attvalues>\n");
				result.append("\t\t\t\t\t<attvalue for=\"type\" value=\"concept\"></attvalue>\n");
				result.append("\t\t\t\t\t<attvalue for=\"sourceName\" value=\"" + sourceName + "\"></attvalue>\n");
				result.append("\t\t\t\t</attvalues>\n");
				
				String color = "r=\"255\" g=\"0\" b=\"0\"";
				if(sourceName != null) {
					String cachedColor = nodeColors.get(sourceName);
					if(cachedColor == null) {
						color = "r=\"" + String.valueOf((int) (Math.random() * 255)) +"\" g=\"" + String.valueOf((int) (Math.random() * 255)) +"\" b=\"" + String.valueOf((int) (Math.random() * 255)) +"\"";
						nodeColors.put(sourceName, color);
					} else {
						color = cachedColor;
					}
				} 
				
				if(eClass.getName().endsWith("Input")) {
					color = "r=\"255\" g=\"0\" b=\"0\"";
				}
				
				result.append("\t\t\t\t<viz:color " + color + "></viz:color>\n");
				result.append("\t\t\t</node>\n");
				nodes.put(eClass.getName(), nodeName);
				
				for(EStructuralFeature feature : eClass.getEStructuralFeatures()) {
					if (feature instanceof EAttribute) {
						EAttribute attribute = (EAttribute) feature;

						String attName = String.valueOf(nodeCounter++);
						result.append("\t\t\t<node id =\"" + attName + "\" label=\"" + attribute.getName() + "\">\n");
						result.append("\t\t\t\t<attvalues>\n");
						result.append("\t\t\t\t\t<attvalue for=\"type\" value=\"attribute\"></attvalue>\n");
						result.append("\t\t\t\t\t<attvalue for=\"sourceName\" value=\"" + sourceName + "\"></attvalue>\n");
						result.append("\t\t\t\t</attvalues>\n");
						result.append("\t\t\t\t<viz:color r=\"255\" g=\"255\" b=\"255\"></viz:color>\n");
						result.append("\t\t\t</node>\n");

						String edgeName = String.valueOf(edgeCounter++);
						attEdges.append("\t\t\t<edge id =\"" + edgeName + "\" source=\"" + nodeName + "\" target=\"" + attName + "\">\n");
						attEdges.append("\t\t\t\t<attvalues>\n");
						attEdges.append("\t\t\t\t\t<attvalue for=\"type\" value=\"attribute\"></attvalue>\n");
						attEdges.append("\t\t\t\t</attvalues>\n");
						attEdges.append("\t\t\t</edge>\n");
					}
				}
				
			}
		}
		result.append("\t\t</nodes>\n");
		
		result.append("\t\t<edges>\n");
		result.append(attEdges.toString());
		for(EClassifier eClassifier : ePackage.getEClassifiers() ){
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				List<String> alreadyVisited = new ArrayList<String>();
				for(EStructuralFeature eStructuralFeature : eClass.getEStructuralFeatures()) {
					if (eStructuralFeature instanceof EReference) {
						EReference eReference = (EReference) eStructuralFeature;
						String edgeName = String.valueOf(edgeCounter++);
						String sourceEdge = nodes.get(eClass.getName());
						String targetEdge = nodes.get(eReference.getEType().getName());
						if(sourceEdge != null && targetEdge != null && !alreadyVisited.contains(sourceEdge + "-" + targetEdge)) {
							result.append("\t\t\t<edge id =\"" + edgeName + "\" source=\"" + sourceEdge + "\" target=\"" + targetEdge + "\">\n");
							result.append("\t\t\t\t<attvalues>\n");
							result.append("\t\t\t\t\t<attvalue for=\"type\" value=\"reference\"></attvalue>\n");
							result.append("\t\t\t\t</attvalues>\n");
							result.append("\t\t\t</edge>\n");
							alreadyVisited.add(sourceEdge + "-" + targetEdge);
						}
					}
				}
			}
		}
		result.append("\t\t</edges>\n");
		result.append("\t</graph>\n");
		result.append("\t</gexf>\n");		
		return result.toString();
	}
}
