package fr.inria.atlanmod.json.discoverer.coverage.util;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import fr.inria.atlanmod.json.discoverer.coverage.AttMapping;
import fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping;
import fr.inria.atlanmod.json.discoverer.coverage.Coverage;
import fr.inria.atlanmod.json.discoverer.coverage.CoverageFactory;
import fr.inria.atlanmod.json.discoverer.coverage.CoverageMapping;
import fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage;
import fr.inria.atlanmod.json.discoverer.coverage.RefMapping;

/**
 * This class allows managing coverage information for a pair of model/metamodel
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class CoverageCreator {
	private Coverage coverage;
	private EPackage sourcePackage;
	private EPackage targetPackage;
	private String name;
		
	public CoverageCreator(String name, EPackage sourcePackage, EPackage targetPackage) {
		if(name == null || name.equals(""))
			throw new IllegalArgumentException("Name cannot be null or empty");
		if(sourcePackage == null)
			throw new IllegalArgumentException("sourcePackage cannot be null");
		if(targetPackage == null)
			throw new IllegalArgumentException("targetPackage cannot be null");
		
		this.name = name;
		this.sourcePackage = sourcePackage;
		this.targetPackage = targetPackage;
		this.coverage = CoverageFactory.eINSTANCE.createCoverage();
	}
	
	public void createConceptMapping(EClass source, EClass target) {
		ConceptMapping conceptMapping = CoverageFactory.eINSTANCE.createConceptMapping();
		conceptMapping.setSource(source);
		conceptMapping.setTarget(target);
		coverage.getMappings().add(conceptMapping);
	}

	public void createAttMapping(EAttribute source, EAttribute target) {
		AttMapping attMapping = CoverageFactory.eINSTANCE.createAttMapping();
		attMapping.setSource(source);
		attMapping.setTarget(target);
		coverage.getMappings().add(attMapping);
	}

	public void createRefMapping(EReference source, EReference target) {
		RefMapping refMapping = CoverageFactory.eINSTANCE.createRefMapping();
		refMapping.setSource(source);
		refMapping.setTarget(target);
		coverage.getMappings().add(refMapping);
	}
	
	public ConceptMapping getConceptMappingFromSource(EClass source) {
		for(CoverageMapping mapping : coverage.getMappings()) {
			if (mapping instanceof ConceptMapping) {
				ConceptMapping conceptMapping = (ConceptMapping) mapping;
				if(conceptMapping.getSource().equals(source)) 
					return conceptMapping;
			}
		}
		return null;
	}
	
	public Coverage getCoverage() {
		return coverage;
	}
	
	public String getName() {
		return name;
	}

	public void save(File composite) {
		ResourceSet rset = new ResourceSetImpl();
//		EPackage.Registry.INSTANCE.put(sourcePackage.getNsURI(), sourcePackage);
//		EPackage.Registry.INSTANCE.put(targetPackage.getNsURI(), targetPackage);
//		rset.setPackageRegistry(EPackage.Registry.INSTANCE);
//		rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
//		rset.getResource(URI.createURI(sourcePackage.getNsURI()), true);
//		rset.getResource(URI.createURI(targetPackage.getNsURI()), true);
		Resource res = rset.createResource(URI.createFileURI(composite.getAbsolutePath()));
		
		
		try {
			res.getContents().add(sourcePackage);
			res.getContents().add(targetPackage);
			res.getContents().add(coverage);
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Coverage loadCoverage(File file) {
		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(CoveragePackage.eNS_URI, CoveragePackage.eINSTANCE);
		rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new EcoreResourceFactoryImpl());

		Resource res = rset.getResource(URI.createFileURI(file.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Coverage coverage = (Coverage) res.getContents().get(0);
		return coverage;
	}
}
