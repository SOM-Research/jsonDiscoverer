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
 * Common implementation for JSON sources
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 * @version 2.0.0
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