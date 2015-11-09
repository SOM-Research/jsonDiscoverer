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



package jsondiscoverer.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Collects and launches all the test classes
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ TestJsonSimpleDiscoverer.class, TestJsonInjector.class, TestAdvancedDiscoverer.class, TestJsonComposer.class})
public class AllTests {

}
