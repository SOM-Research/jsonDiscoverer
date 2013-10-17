package fr.inria.atlanmod.discoverer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import coverage.util.CoverageCreator;
import fr.inria.atlanmod.json.JsonObject;
import fr.inria.atlanmod.json.JsonObjectValue;
import fr.inria.atlanmod.json.Model;
import fr.inria.atlanmod.json.Pair;
import fr.inria.atlanmod.json.StringValue;

/**
 * Performs a composition between metamodels obtained from JSON files
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonComposer {
	private List<File> files;
	private HashMap<String, EClass> registry;
	HashMap<File, List<File>> jsonFiles;
	HashMap<EAttribute, List<Object>> cacheValues;

	public JsonComposer(List<File> files) {
		this.files = files;
		this.cacheValues = new HashMap<EAttribute, List<Object>>();
	}

	public JsonComposer(List<File> files, HashMap<File, List<File>> jsonFiles) {
		this(files);
		this.jsonFiles = jsonFiles;
	}

	public void compose(File resultPath) {
		if(files.size() == 0) 
			return;

		EPackage finalPackage = EcoreFactory.eINSTANCE.createEPackage();
		finalPackage.setName("composed");
		finalPackage.setNsPrefix("composed");
		finalPackage.setNsURI("http://composed");

		registry = new HashMap<String, EClass>();
		List<EReference> referencesToCheck = new ArrayList<EReference>();
		List<CoverageCreator> coverageCreators = new ArrayList<CoverageCreator>();
		for(File file: files) {
			CoverageCreator coverageCreator = new CoverageCreator(file);

			EPackage singlePackage = loadEPackage(file);
			for(EClassifier classifier : singlePackage.getEClassifiers()) {
				if (classifier instanceof EClass) {
					EClass eClass = (EClass) classifier;
					EClass registryElement = registry.get(eClass.getName());
					if(registryElement == null) {
						EClass duplicatedEClass = duplicateEClass(eClass, coverageCreator);
						registry.put(eClass.getName(), duplicatedEClass);
						coverageCreator.createConceptMapping(eClass, duplicatedEClass);
						eClass = duplicatedEClass;
					} else {
						coverageCreator.createConceptMapping(eClass, registryElement);
						composeAttributes(registryElement, eClass, coverageCreator);
						composeReferences(registryElement, eClass, coverageCreator);
						eClass = registryElement;
					}
					for(EStructuralFeature otherFeature : eClass.getEStructuralFeatures()) 
						if (otherFeature instanceof EReference) 
							referencesToCheck.add((EReference) otherFeature);

				}
			}

			coverageCreators.add(coverageCreator);
		}

		for(EClass eClass : registry.values()) {
			finalPackage.getEClassifiers().add(eClass);
		}

		EClass unknown = EcoreFactory.eINSTANCE.createEClass();
		unknown.setName("Unknown");
		boolean unknownUsed = false;

		for(EReference reference : referencesToCheck) {
			EClassifier referredEClassifier = reference.getEType();
			if (referredEClassifier instanceof EClass) {
				EClass referredEClass = (EClass) referredEClassifier;
				EClass registryElement = registry.get(referredEClass.getName());
				if(registryElement == null) {
					reference.setEType(unknown);
					System.out.println("Reference " + reference.getName() + " with unknown type");
					unknownUsed = true;
				} else {
					reference.setEType(registryElement);
					System.out.println("Reference " + reference.getName() + " re-assigned");
				}
			}
		}

		if(unknownUsed) {
			finalPackage.getEClassifiers().add(unknown);
		}


		saveEPackage(finalPackage, resultPath);

		for(CoverageCreator coverageCreator : coverageCreators) 
			coverageCreator.save(resultPath);

	}

	private void composeAttributes(EClass existingClass, EClass otherClass, CoverageCreator coverageCreator) {
		for(EStructuralFeature otherFeature : otherClass.getEStructuralFeatures()) {
			if (otherFeature instanceof EAttribute) {
				EAttribute otherAttribute = (EAttribute) otherFeature;
				EStructuralFeature existingFeature = existingClass.getEStructuralFeature(otherAttribute.getName());
				if(existingFeature == null) {
					EAttribute similarAttribute = lookForSimilarAttribute(existingClass, otherAttribute, coverageCreator);
					if(similarAttribute == null) {
						EAttribute newAttribute = duplicateAttribute(otherAttribute);
						existingClass.getEStructuralFeatures().add(newAttribute);
						System.out.println("Attribute " + newAttribute.getName() + " added");
						existingFeature = newAttribute;
					} else {
						System.out.println("Attribute similar found: " + similarAttribute.getName());
						existingFeature = similarAttribute;
					}
				} else {
					existingFeature.setEType(EcorePackage.Literals.ESTRING);
					System.out.println("Attribute " + existingFeature.getName() + " refined to String");
				}				
				coverageCreator.createAttMapping(otherAttribute, (EAttribute) existingFeature);
			}
		}

	}

	private void composeReferences(EClass existingClass, EClass otherClass, CoverageCreator coverageCreator) {
		for(EStructuralFeature otherFeature : otherClass.getEStructuralFeatures()) {
			if (otherFeature instanceof EReference) {
				EReference otherReference = (EReference) otherFeature;
				EStructuralFeature existingFeature = existingClass.getEStructuralFeature(otherReference.getName());
				if(existingFeature == null) {
					EReference newReference = duplicateReference(otherReference);
					existingClass.getEStructuralFeatures().add(newReference);
					System.out.println("Reference " + newReference.getName() + " added");
					existingFeature = newReference;
					coverageCreator.createRefMapping(otherReference, (EReference) existingFeature);
				} 			
			}
		}

	}

	private EAttribute duplicateAttribute(EAttribute otherAttribute) {
		EAttribute newAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		newAttribute.setName(otherAttribute.getName());
		newAttribute.setEType(otherAttribute.getEType());
		newAttribute.setUpperBound(otherAttribute.getUpperBound());
		newAttribute.setLowerBound(otherAttribute.getLowerBound());
		return newAttribute;
	}

	private EReference duplicateReference(EReference otherReference) {
		EReference newReference = EcoreFactory.eINSTANCE.createEReference();
		newReference.setName(otherReference.getName());
		newReference.setEType(otherReference.getEType());
		newReference.setUpperBound(otherReference.getUpperBound());
		newReference.setLowerBound(otherReference.getLowerBound());
		return newReference;		
	}

	private EClass duplicateEClass(EClass otherClass, CoverageCreator coverageCreator) {
		EClass newClass = EcoreFactory.eINSTANCE.createEClass();
		newClass.setName(otherClass.getName());
		newClass.setAbstract(otherClass.isAbstract());

		for(EStructuralFeature otherFeature : otherClass.getEStructuralFeatures()) {
			if (otherFeature instanceof EReference) {
				EReference duplicatedReference = duplicateReference((EReference) otherFeature);
				newClass.getEStructuralFeatures().add(duplicatedReference);
				coverageCreator.createRefMapping((EReference) otherFeature, duplicatedReference);	
			} else if (otherFeature instanceof EAttribute) {
				EAttribute duplicatedAttribute = duplicateAttribute((EAttribute) otherFeature);
				newClass.getEStructuralFeatures().add(duplicatedAttribute);
				coverageCreator.createAttMapping((EAttribute) otherFeature, duplicatedAttribute);
				cacheValues.put(duplicatedAttribute, getJSONValues(duplicatedAttribute.getName(), coverageCreator.getFile()));
			}
		}
		return newClass;
	}


	private EAttribute lookForSimilarAttribute(EClass existingClass, EAttribute otherAttribute, CoverageCreator coverageCreator) {
		List<Object> jsonValues = getJSONValues(otherAttribute.getName(), coverageCreator.getFile());

		Iterator<EAttribute> it = cacheValues.keySet().iterator();
		while(it.hasNext()) {
			EAttribute eAttribute = it.next();
			for(Object o : cacheValues.get(eAttribute)) {
				if (o instanceof String) {
					String stringValue = (String) o;
					for(Object o2 : jsonValues) {
						if (o2 instanceof String) {
							String stringValue2 = (String) o2;
							if(stringValue.equals(stringValue2)) 
								return eAttribute;
						}
					}
				}
			}
		}

		return null;
	}

	private List<Object> getJSONValues(String name, File service) {
		List<Object> result = new ArrayList<Object>();
		if(jsonFiles == null) return result;

		ResourceSet rset = new ResourceSetImpl();
		List<File> files = jsonFiles.get(service);
		if(files == null) return result;

		File file = files.get(0);
		Resource res = rset.getResource(URI.createFileURI(file.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Model model = (Model) res.getContents().get(0);

		for(JsonObject jsonObject : model.getObjects()) {
			for(Pair pair : jsonObject.getPairs()) {
				String key = pair.getString();
				if(key.equals(name) && pair.getValue() instanceof StringValue) {
					result.add(((StringValue) pair.getValue()).getValue());
				} else if (pair.getValue() instanceof JsonObjectValue) {
					JsonObject jsonObject2 = ((JsonObjectValue) pair.getValue()).getValue();
					for(Pair pair2 : jsonObject2.getPairs()) {
						String key2 = pair2.getString();
						if(key2.equals(name) && pair2.getValue() instanceof StringValue) {
							result.add(((StringValue) pair2.getValue()).getValue());
						} // TODO make this recursive!						
					}
				}

			}
		}

		return result;
	}

	private EPackage loadEPackage(File file) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.getResource(URI.createFileURI(file.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		EPackage ePackage = (EPackage) res.getContents().get(0);
		return ePackage;
	}

	private void saveEPackage(EPackage ePackage, File resultPath) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.createResource(URI.createFileURI(resultPath.getAbsolutePath()));

		try {
			res.getContents().add(ePackage);
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
