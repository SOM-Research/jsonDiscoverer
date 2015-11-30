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



package jsondiscoverer.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This class applies the Dijkstra algorithm to get the shortest path between
 * two nodes. 
 * <p>
 * Applied to EMF models, nodes are {@link EClass} elements and links are {@link EReference} elements. 
 * We consider the distance between two nodes as the number of {@link EReference}s existing between
 * such nodes (or infinite if there is not a path). 
 * <p>
 * This code has been adapted from the one published by Lars Vogel published 
 * <a href="http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html">here</a>
 * 
 * @author Javier Canovas (me@jlcanovas.es)
 *
 */
public class DijkstraSolver {
	/**
	 * The list of nodes of the graph
	 */
	private final List<EClass> nodes;
	/**
	 * The list of edges of the graph
	 */
	private final List<EReference> edges;
	/**
	 * Visited nodes
	 */
	private Set<EClass> settledNodes;
	/**
	 * Unvisited nodes
	 */
	private Set<EClass> unSettledNodes;
	/**
	 * The list of predecesors in the set of calculated paths
	 */
	private Map<EClass, EClass> predecessors;
	/**
	 * Distance matrix between each pair of nodes
	 */
	private Map<EClass, Integer> distance;

	/**
	 * Constructs a new Dijkstra solver taking all the {@link EClass} elements as nodes and 
	 * their {@link EReference}s as references/links between the nodes.
	 * 
	 * @param ePackage The metamodel (as {@link EPackage})
	 */
	public DijkstraSolver(EPackage ePackage) {
		this.nodes = new ArrayList<EClass>();
		this.edges = new ArrayList<EReference>();
		
		for(EClassifier eClassifier : ePackage.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				nodes.add(eClass);
				
				for(EStructuralFeature feature : eClass.getEStructuralFeatures()) {
					if (feature instanceof EReference) {
						EReference eReference = (EReference) feature;
						edges.add(eReference);
					}
				}
			}
		}
	}

	/**
	 * Executes the algorithm to create the auciliar table with all the possible
	 * shortest paths
	 * 
	 * @param source The source element of the path (as {@link EClass})
	 */
	public void execute(EClass source) {
		settledNodes = new HashSet<EClass>();
		unSettledNodes = new HashSet<EClass>();
		distance = new HashMap<EClass, Integer>();
		predecessors = new HashMap<EClass, EClass>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			EClass node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	/**
	 * Looks for the minimal distance between two nodes given a source node (as {@link EClass} elements)
	 * 
	 * @param node The source node to start the calculation (as {@link EClass})
	 */
	private void findMinimalDistances(EClass node) {
		List<EClass> adjacentNodes = getNeighbors(node);
		for (EClass target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node)	+ getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	/**
	 * Returns the distance between two nodes (as {@link EClass} elements)
	 * 
	 * @param node The sourcce node (as {@link EClass})
	 * @param target The target node (as {@link EClass})
	 * @return The distance between the two nodes (a value between 1 and {@link Integer#MAX_VALUE})
	 */
	private int getDistance(EClass node, EClass target) {
		for (EReference edge : edges) {
			if (edge.getEContainingClass().equals(node) && edge.getEType().equals(target)) {
				return 1;
			}
		}
		throw new RuntimeException("Should not happen");
	}

	/**
	 * Returns the list of neighbors for a given node. 
	 * <p>
	 * As we deal with {@link EClass} elements, the neighbors of an {@link EClass} are all the {@link EClass}
	 * elements linked with at least one {@link EReference}.
	 * 
	 * @param node The target node
	 * @return The list of neighbors (as {@link EClass} elements)
	 */
	private List<EClass> getNeighbors(EClass node) {
		List<EClass> neighbors = new ArrayList<EClass>();
		for (EReference edge : edges) { 
			if(edge.getEContainingClass().equals(node) && !isSettled((EClass) edge.getEType())) {
				neighbors.add((EClass) edge.getEType());
			}
		}
		return neighbors;
	}

	/**
	 * Obtained the nearest node of a set of nodes (as {@link EClass}es)
	 * 
	 * @param vertexes The set of nodes (as {@link EClass})
	 * @return The nearest node (as {@link EClass})
	 */
	private EClass getMinimum(Set<EClass> vertexes) {
		EClass minimum = null;
		for (EClass vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	/**
	 * Checks if a node has already been visited by the algorithm 
	 * 
	 * @param vertex The node (as {@link EClass})
	 * @return True if the node has already been visited
	 */
	private boolean isSettled(EClass vertex) {
		return settledNodes.contains(vertex);
	}

	/**
	 * Calculates the shortest distance with regard to a ndoe (as {@link EClass})
	 * 
	 * @param destination The target node (as {@link EClass})
	 * @return The distance (a value between 1 and {@link Integer#MAX_VALUE})
	 */
	private int getShortestDistance(EClass destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}
	
	/**
	 * Gets the path to a node as a list of {@link EClass} elements to be crossed.
	 * 
	 * @param target The target element to visit (as {@link EClass})
	 * @return List of {@link EClass} elements
	 */
	public LinkedList<EClass> getPath(EClass target) {
		LinkedList<EClass> path = new LinkedList<EClass>();
		EClass step = target;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}

} 
