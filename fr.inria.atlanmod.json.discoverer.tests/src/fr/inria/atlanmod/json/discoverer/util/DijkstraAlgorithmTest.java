package fr.inria.atlanmod.json.discoverer.util;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import fr.inria.atlanmod.discoverer.AnnotationHelper;
import fr.inria.atlanmod.discoverer.CoreographyBuilder;
import fr.inria.atlanmod.discoverer.util.DijkstraSolver;

public class DijkstraAlgorithmTest {
	public static void main(String[] args) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		ResourceSet rset = new ResourceSetImpl();
		Resource res1 = rset.getResource(URI.createFileURI("../fr.inria.atlanmod.json.discoverer.zoo/zooMini/zooMini.ecore"), true);
		try {
			res1.load(null); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		EPackage package1 = (EPackage) res1.getContents().get(0);
		
		EClass source = null;
		EClass target = null;
		
		for(EClassifier eClassifier : package1.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				if(eClass.getName().equals("pathCalculatorInput")) {
					source = eClass;
				}
				if(eClass.getName().equals("Ligne")) {
					target = eClass;
				}
				
			}
		}
		
		CoreographyBuilder builder = new CoreographyBuilder(package1);
		String result = builder.calculate(source, target);
		System.out.println(result);
	}
}
