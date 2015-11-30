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


package jsondiscoverer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import jsondiscoverer.util.DijkstraSolver;

/**
 * 
 * 
 * This class generates a sequence diagram describing how two JSON-based Web APIs can be composed.
 * <p>
 * The main entry method is {@link CoreographyBuilder#calculate(EClass, EClass)}, which performs 
 * the following actions:
 * <ol>
 * <li>Calculate the shortest path between each node in the model (see {@link DijkstraSolver}) </li>
 * <li>Retrieve the shortest path for a given source/target nodes</li>
 * <li>Generate the {@link String} representing the sequence diagram</li>
 * </ol>
 * <p>pñ'-
 * 
 * The sequence diagram is represented as a {@link String} to be transformed into a picture
 * by means of the tool <a href="http://bramp.github.io/js-sequence-diagrams">js-sequene-diagrams</a>
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class CoreographyBuilder {
	/**
	 * The general APIs domain discovered
	 */
	private EPackage domain;

	/**
	 * Constructs a new {@link CoreographyBuilder} element given a metamodel (as {@link EPackage}
	 * 
	 * @param ePackage The metamodel (as {@link EPackage}
	 */
	public CoreographyBuilder(EPackage ePackage) {
		if(ePackage == null) 
			throw new IllegalArgumentException("The ePackage cannot be null");
		
		this.domain = ePackage;
	}

	
	
	

/**
	 * Searches for a possible path between two metamodel classes (as {@link EClass} elements), if 
	 * exists, a sequence diagram is returned as {@link String}.
	 * <p>
	 * This method applies first the Dijsktra algorithm to calculate all the possible shortest 
	 * path for a given source node (see {@link DijkstraSolver}). Then, the shortest path with regard
	 * to a target node is retrieve (if exists) and finally the sequence diagram is generated.
	 * <p>
	 * The sequence diagram is represented as a {@link String} to be transformed into a picture
	 * by means of the tool <a href="http://bramp.github.io/js-sequence-diagrams">js-sequene-diagrams</a>
	 * 
	 * @param source The source node (as {@link EClass})
	 * @param target The target node (as {@link EClass})
	 * @return The path between the nodes represented as a sequence diagram
	 */
	public String calculate(EClass source, EClass target) {
		if(source == null)
			throw new IllegalArgumentException("The source cannot be null");
		if(target == null)
			throw new IllegalArgumentException("The target cannot be null");
		if(!source.getName().endsWith("Input"))
			throw new IllegalArgumentException("The source element has to be the Input of an API");
		
		DijkstraSolver algorithm = new DijkstraSolver(domain);
		algorithm.execute(source);
		List<EClass> result = algorithm.getPath(target);
		if(result == null)
			return null;

		String resultString = "";
		EClass last = null;
		String lastSourceName = null;
		boolean multiValued = false;
		for(EClass eClass : result){
			if(last != null) 
				for(EReference reference : last.getEAllReferences()) 
					if(reference.getEType() == eClass && reference.isMany())
						multiValued = true;

			String sourceName = AnnotationHelper.INSTANCE.getSourceName(eClass);
			if(!sourceName.equals(lastSourceName)) {
				if(last != null) {
					String response = lastSourceName.toUpperCase() + "-->" + "User:";
					String outputAttributes = "";
					for(EAttribute attribute : last.getEAllAttributes()) {
						outputAttributes += attribute.getName() + " : " + attribute.getEType().getName().toUpperCase() + ", ";
					}
					outputAttributes = outputAttributes.substring(0, outputAttributes.length()-2);
					resultString += response + "response" + "(" + outputAttributes + ")\n";
				}

				String call = "User->" + sourceName.toUpperCase() + ":";
				String inputAttributes = "";
				for(EAttribute attribute : eClass.getEAllAttributes()) {
					inputAttributes += attribute.getName() + " : " + attribute.getEType().getName() + ", ";
				}
				inputAttributes = inputAttributes.substring(0, inputAttributes.length()-2);
				if(multiValued) {
					resultString += "Note right of User:LOOP\n";
					multiValued = false;
				}
				if(last != null) {
					String mapping = discoverMapping(last, eClass);
					if(mapping != null && !mapping.equals("")) 
						resultString += "Note right of User: " + mapping + "\n";
				}
				resultString += call + eClass.getName().substring(0, eClass.getName().indexOf("Input")) + "(" + inputAttributes + ")\n";
				lastSourceName = sourceName;
			}
			last = eClass;
			lastSourceName = (last == null) ? "" : AnnotationHelper.INSTANCE.getSourceName(last);
		}

		String response = lastSourceName.toUpperCase() + "-->" + "User:";
		String outputAttributes = "";
		for(EAttribute attribute : last.getEAllAttributes()) {
			outputAttributes += attribute.getName() + " : " + attribute.getEType().getName().toUpperCase() + ", ";
		}
		outputAttributes = outputAttributes.substring(0, outputAttributes.length()-2);
		resultString += response + "response" + "(" + outputAttributes + ")";
		return resultString;
	}

	/**
	 * Searches for mappings between two metamodel class (as {@link EClass} elements)
	 * <p>
	 * Attributes of each metamodel class are traversed and, if they are similar (calculated
	 * with method {@link CoreographyBuilder#isSimilar(EAttribute, EAttribute)}) a mapping between
	 * such attributes is created.
	 * <p>
	 * Mappings are currently represented as {@link String}s following the pattern:
	 * <p>
	 * [source attribute name] -&gt; [target attribute name]
	 * <p>
	 * If several mappings are located, the resulting {@link String} is a comma-separated result 
	 * 
	 * @param source The source metamodel class (as {@link EClass})
	 * @param target The target metamodel class (as {@link EClass})
	 * @return A comma-separated {@link String} with the discovered mappings
	 */
	public String discoverMapping(EClass source, EClass target) {
		if(source == null) 
			throw new IllegalArgumentException("A source has to be provided");
		if(target == null) 
			throw new IllegalArgumentException("A target has to be provided");
		
		String result = "";

		List<EAttribute> visited = new ArrayList<EAttribute>();
		for(EAttribute sourceAttribute : source.getEAllAttributes()) 
			for(EAttribute targetAttribute : target.getEAllAttributes()) 
				if(!visited.contains(sourceAttribute) && !visited.contains(targetAttribute) && isSimilar(sourceAttribute, targetAttribute)) {
					result += sourceAttribute.getName() + " -> " + targetAttribute.getName() + ", ";
					visited.add(sourceAttribute);
					visited.add(targetAttribute);
				}

		if(!result.equals("")) 
			result = result.substring(0, result.length() - 2);

		return result;
	}

	/**
	 * Calculates if two attributes (as {@link EAttribute} elements) are similar. 
	 * <p>
	 * The similarity is checked according to number of matching characters between
	 * the two given {@link String}s. Currently the threshold to be considered similar
	 * is 0.25.
	 * 
	 * @param source The source {@link EAttribute}
	 * @param target The target {@link EAttribute}
	 * @return True if the two {@link EAttribute} are considered similar
	 */
	private boolean isSimilar(EAttribute source, EAttribute target) {
		if(source == null) 
			throw new IllegalArgumentException("A source has to be provided");
		if(target == null) 
			throw new IllegalArgumentException("A target has to be provided");
		
		if(source.getName().equals(target.getName())) return true;

		String sourceName = source.getName();
		String targetName = target.getName();

		if(sourceName.length() == targetName.length()) {
			int length = sourceName.length();
			int matchingChars = 0;
			for(int i = 0; i < length; i++) {
				char s = sourceName.charAt(i);
				char t = targetName.charAt(i);
				if(s == t) matchingChars++;
			}
			if((double) matchingChars / (double) length >= 0.25) return true;
		}
		return false;
	}

}
