package fr.inria.atlanmod.discoverer;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

public class GexfConverter {
	public static String convert(EPackage ePackage) {
		StringBuffer result = new StringBuffer();
		Timestamp timeStamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
		
		result.append("<gexf xmlns=\"http://www.gexf.net/1.2draft\" version=\"1.2\">\n" +
				"\t<meta lastmodifieddate=\""+ timeStamp.toString() + "\">\n" +
				"\t\t<creator>JSON Discovorer</creator>\n" +
				"\t\t<description>Conversion from Ecore</description>\n" +
				"\t</meta>\n");
		
		result.append("\t<graph mode=\"static\" defaultedgetype=\"directed\">\n");

		result.append("\t\t<nodes>\n");
		int nodeCounter = 0;
		HashMap<String, String> nodes = new HashMap<String, String>();
		for(EClassifier eClassifier : ePackage.getEClassifiers() ){
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				String nodeName = "n" + nodeCounter++;
				result.append("\t\t\t<node id =\"" + nodeName + "\" label=\"" + eClass.getName() + "\"/>\n");
				nodes.put(eClass.getName(), nodeName);
			}
		}
		result.append("\t\t</nodes>\n");
		
		result.append("\t\t<edges>\n");
		int edgeCounter = 0;
		for(EClassifier eClassifier : ePackage.getEClassifiers() ){
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				for(EStructuralFeature eStructuralFeature : eClass.getEStructuralFeatures()) {
					if (eStructuralFeature instanceof EReference) {
						EReference eReference = (EReference) eStructuralFeature;
						String edgeName = "e" + edgeCounter++;
						String sourceEdge = nodes.get(eClass.getName());
						String targetEdge = nodes.get(eReference.getEType().getName());
						if(sourceEdge != null && targetEdge != null)
							result.append("\t\t\t<edge id =\"" + edgeName + "\" source=\"" + sourceEdge + "\" target=\"" + targetEdge + "\"/>\n");
					}
				}
			}
		}
		result.append("\t\t</edges>\n");
		result.append("\t</graph>");
		result.append("\t</gexf>");		
		return result.toString();
	}
}
