package fr.inria.atlanmod.discoverer;

import org.eclipse.emf.ecore.EPackage;

/**
 * Common implementation for JSON sources
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public abstract class AbstractJsonSource {

	/**
	 * A representative name for this source
	 */
	private String name;
	/**
	 * If required, this class can also store the metamodel of the set of JSON definitions
	 */
	private EPackage metamodel;

	public AbstractJsonSource(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name == null || name.equals("")) 
			throw new IllegalArgumentException("Name cannot be null or empty");
		this.name = name;
	}

	public EPackage getMetamodel() {
		return metamodel;
	}

	public void setMetamodel(EPackage metamodel) {
		if(metamodel == null) 
			throw new IllegalArgumentException("Metamodel cannot be null");
		this.metamodel = metamodel;
	}

}