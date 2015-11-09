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

import org.eclipse.emf.ecore.EPackage;

/**
 * This class gathers the common behavior to represent JSON sources. 
 * <p>
 * In particular, it helps to:
 * <ul>
 * <li>Keep track of the name of the JSON source (see {@link AbstractJsonSource#getName()} and 
 * {@link AbstractJsonSource#setName(String)})</li>
 * <li>Keep track of the metamodel discovered (see {@link AbstractJsonSource#getMetamodel()} and
 * {@link AbstractJsonSource#setMetamodel(EPackage)})
 * </ul>
 * 
 * 
 * @author Javier Canovas (me@jlcanovas.es)
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

	/**
	 * Constructs a new {@link AbstractJsonSource} element with a name
	 * 
	 * @param name The name for the JSON source
	 */
	public AbstractJsonSource(String name) {
		if(name == null || name.equals("")) 
			throw new IllegalArgumentException("Name cannot be null or empty");
		this.name = name;
	}

	/**
	 * Returns the name of the JSON source
	 * 
	 * @return The name of the JSON source
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the JSON source
	 * 
	 * @param name The name of the JSON source
	 */
	public void setName(String name) {
		if(name == null || name.equals("")) 
			throw new IllegalArgumentException("Name cannot be null or empty");
		this.name = name;
	}

	/**
	 * Gets the metamodel linked to the JSON source
	 * 
	 * @return The metamodel (as {@link EPackage})
	 */
	public EPackage getMetamodel() {
		return metamodel;
	}

	/**
	 * Sets the metamodel linked to the JSON source
	 * 
	 * @param metamodel The metamodel (as {@link EPackage})
	 */
	public void setMetamodel(EPackage metamodel) {
		if(metamodel == null) 
			throw new IllegalArgumentException("Metamodel cannot be null");
		this.metamodel = metamodel;
	}

}