package fr.inria.atlanmod.discoverer.util;

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
 * two nodes. This code has been adapted from the one published by Lars Vogel
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class DijkstraSolver {
	private final List<EClass> nodes;
	private final List<EReference> edges;
	private Set<EClass> settledNodes;
	private Set<EClass> unSettledNodes;
	private Map<EClass, EClass> predecessors;
	private Map<EClass, Integer> distance;

	/**
	 * Constructs a new Dijkstra solver taking all the EClasses as nodes and their
	 * EReferences as references
	 * 
	 * @param ePackage
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

	private int getDistance(EClass node, EClass target) {
		for (EReference edge : edges) {
			if (edge.getEContainingClass().equals(node) && edge.getEType().equals(target)) {
				return 1;
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<EClass> getNeighbors(EClass node) {
		List<EClass> neighbors = new ArrayList<EClass>();
		for (EReference edge : edges) { 
			if(edge.getEContainingClass().equals(node) && !isSettled((EClass) edge.getEType())) {
				neighbors.add((EClass) edge.getEType());
			}
		}
		return neighbors;
	}

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

	private boolean isSettled(EClass vertex) {
		return settledNodes.contains(vertex);
	}

	private int getShortestDistance(EClass destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}
	
	/**
	 * Gets the path to a node
	 * 
	 * @param target
	 * @return
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
