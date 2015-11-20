package jsondiscoverer.coverage.util;

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

import jsondiscoverer.coverage.AttMapping;
import jsondiscoverer.coverage.ConceptMapping;
import jsondiscoverer.coverage.Coverage;
import jsondiscoverer.coverage.CoverageFactory;
import jsondiscoverer.coverage.CoverageMapping;
import jsondiscoverer.coverage.CoveragePackage;
import jsondiscoverer.coverage.RefMapping;

/**
 * This class allows managing coverage information for a pair of model/metamodel.
 * <p>It allows creating coverage models easily by providing extra functioalities to
 * create mapping (concept, attribute and references mappings) and load/save coverage
 * models</p> 
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 */
public class CoverageCreator {
	/**
	 * The coverage model managed by this class
	 */
	private Coverage coverage;
	/**
	 * The source metamodel being tracked (as {@link EPackage})
	 */
	private EPackage sourcePackage;
	/**
	 * The target metamodel being tracked (as {@link EPackage})
	 */
	private EPackage targetPackage;
	/**
	 * The name for this coverage model
	 */
	private String name;
		
	/**
	 * Constructs a {@link CoverageCreator} with a name, a source/target metamodels (as {@link EPackage}s)
	 * 
	 * @param name The name for this model
	 * @param sourcePackage The source metamodel (as {@link EPackage})
	 * @param targetPackage The target metamodel (as {@link EPackage})
	 */
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

	/**
	 * Creates a concept mapping for two references (source and target)
	 * 
	 * @param source The source {@link EClass}
	 * @param target The target {@link EClass}
	 */
	public void createConceptMapping(EClass source, EClass target) {
		ConceptMapping conceptMapping = CoverageFactory.eINSTANCE.createConceptMapping();
		conceptMapping.setSource(source);
		conceptMapping.setTarget(target);
		coverage.getMappings().add(conceptMapping);
	}

	/**
	 * Creates a attribute mapping for two references (source and target)
	 * 
	 * @param source The source {@link EAttribute}
	 * @param target The target {@link EAttribute}
	 */
	public void createAttMapping(EAttribute source, EAttribute target) {
		AttMapping attMapping = CoverageFactory.eINSTANCE.createAttMapping();
		attMapping.setSource(source);
		attMapping.setTarget(target);
		coverage.getMappings().add(attMapping);
	}

	/**
	 * Creates a reference mapping for two references (source and target)
	 * 
	 * @param source The source {@link EReference}
	 * @param target The target {@link EReference}
	 */
	public void createRefMapping(EReference source, EReference target) {
		RefMapping refMapping = CoverageFactory.eINSTANCE.createRefMapping();
		refMapping.setSource(source);
		refMapping.setTarget(target);
		coverage.getMappings().add(refMapping);
	}
	
	/**
	 * Returns the mapping of this coverage model whose source matches with the
	 * concept (as {@link EClass}) given as param
	 * 
	 * @param source The concept to compare (as {@link EClass})
	 * @return The mapping for such concept
	 */
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
	
	/**
	 * Returns the coverage model hold by this class
	 * 
	 * @return The coverage model
	 */
	public Coverage getCoverage() {
		return coverage;
	}
	
	/**
	 * Returns the name of the coverage model
	 * 
	 * @return The name as {@link String}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Saves a coverage model into a file
	 * 
	 * @param composite The {@link File} to store the model
	 */
	public void save(File composite) {
		ResourceSet rset = new ResourceSetImpl();
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
	
	/**
	 * Load a coverage model from a file
	 *  
	 * @param file The {@link File} to read
	 * @return The coverage model
	 */
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
