package fr.inria.atlanmod.discoverer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import fr.inria.atlanmod.discoverer.util.DijkstraSolver;

public class CoreographyBuilder {
	private EPackage domain;

	public CoreographyBuilder(EPackage ePackage) {
		this.domain = ePackage;
	}

	public String calculate(EClass source, EClass target) {
		if(source == null)
			throw new IllegalArgumentException("The source cannot be null");
		if(target == null)
			throw new IllegalArgumentException("The target cannot be null");
		
		DijkstraSolver algorithm = new DijkstraSolver(domain);
		algorithm.execute(source);
		List<EClass> result = algorithm.getPath(target);

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

	public String discoverMapping(EClass source, EClass target) {
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

	private boolean isSimilar(EAttribute source, EAttribute target) {
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
