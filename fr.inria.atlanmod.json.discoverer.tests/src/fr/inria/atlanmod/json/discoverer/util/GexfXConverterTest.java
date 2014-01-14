package fr.inria.atlanmod.json.discoverer.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import fr.inria.atlanmod.discoverer.util.GexfConverter;

public class GexfXConverterTest {
	public static void main(String[] args) throws FileNotFoundException {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		ResourceSet rset = new ResourceSetImpl();
		Resource res1 = rset.getResource(URI.createFileURI("../fr.inria.atlanmod.json.discoverer.zoo/zooMini/zooMini.ecore"), true);
//		Resource res1 = rset.getResource(URI.createFileURI("../fr.inria.atlanmod.json.discoverer.zoo/zooBig/zooBig.ecore"), true);
		try {
			res1.load(null); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		EPackage package1 = (EPackage) res1.getContents().get(0);
		
		String result = GexfConverter.convert(package1);
		PrintWriter pw = new PrintWriter(new File("../fr.inria.atlanmod.json.discoverer.zoo/zooMini/zooMini.gexf"));
//		PrintWriter pw = new PrintWriter(new File("../fr.inria.atlanmod.json.discoverer.zoo/zooBig/zooBig.gexf"));
		pw.print(result);
		pw.close();
	}
}
