package coverage.util;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import coverage.AttMapping;
import coverage.ConceptMapping;
import coverage.Coverage;
import coverage.CoverageFactory;
import coverage.RefMapping;

public class CoverageCreator {
	private Coverage coverage;
	private File file;
	
	public CoverageCreator(File file) {
		coverage = CoverageFactory.eINSTANCE.createCoverage();
		coverage.setService(file.getName());
		this.file = file;
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
	
	public Coverage getCoverage() {
		return coverage;
	}
	
	public File getFile() {
		return file;
	}
	
	public void save(File composite) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res1 = rset.getResource(URI.createFileURI(file.getAbsolutePath()), true);
		try {
			res1.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Resource res2 = rset.getResource(URI.createFileURI(composite.getAbsolutePath()), true);
		try {
			res2.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Resource res3 = rset.createResource(URI.createFileURI(file.getAbsolutePath().substring(0,file.getAbsolutePath().indexOf("."))+".coverage.xmi"));
		try {
			res3.getContents().add(coverage);
			res3.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
