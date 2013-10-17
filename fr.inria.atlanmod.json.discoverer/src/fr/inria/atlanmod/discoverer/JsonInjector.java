package fr.inria.atlanmod.discoverer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.inria.atlanmod.json.ArrayValue;
import fr.inria.atlanmod.json.BooleanValue;
import fr.inria.atlanmod.json.JsonObject;
import fr.inria.atlanmod.json.JsonObjectValue;
import fr.inria.atlanmod.json.Model;
import fr.inria.atlanmod.json.NumberValue;
import fr.inria.atlanmod.json.Pair;
import fr.inria.atlanmod.json.StringValue;
import fr.inria.atlanmod.json.Value;

/**
 * This class performs the injection process (obtaining models from JSON files)
 * 
 * @author Javier Canovas (javier.canovas@inria.fr)
 *
 */
public class JsonInjector {
	EPackage metamodel = null;

	/**
	 * Injects the model conforming to a metamodel from a JSON file and 
	 * stores it into a File
	 * 
	 * @param sourceFile
	 * @return
	 */
	public void inject(File jsonFile, File metamodelFile, File targetFile) {
		List<EObject> eObjects = inject(jsonFile, metamodelFile);
		saveModel(targetFile, eObjects);
	}

	/**
	 * Injects the model conforming to a metamodel from a JSON file
	 * 
	 * @param jsonFile
	 * @param metamodelFile
	 * @return
	 */
	public List<EObject> inject(File jsonFile, File metamodelFile) {
		Model jsonModel = loadJson(jsonFile);
		metamodel = loadMetamodel(metamodelFile);

		EClassifier eClassifier = metamodel.getEClassifier("Root");

		List<EObject> eObjects = new ArrayList<EObject>();
		for(JsonObject jsonObject : jsonModel.getObjects()) {
			EObject eObject = instantiateEClassifier(eClassifier, jsonObject);
			eObjects.add(eObject);
		}

		return eObjects;
	}

	/**
	 * Injects the model conforming to a metamodel from a JSON file
	 * 
	 * @param jsonFile
	 * @param metamodelFile
	 * @return
	 */
	public List<EObject> inject(String jsonString, EPackage ePackage) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.createResource(URI.createURI("dummy:/example.json"));
		InputStream in = new ByteArrayInputStream(jsonString.getBytes());
		try {
			res.load(in, rset.getLoadOptions());
		} catch (IOException e) {
			e.printStackTrace();
		}		
		Model jsonModel = (Model) res.getContents().get(0);

		metamodel = ePackage;
		EClassifier eClassifier = metamodel.getEClassifier("Root");

		List<EObject> eObjects = new ArrayList<EObject>();
		for(JsonObject jsonObject : jsonModel.getObjects()) {
			EObject eObject = instantiateEClassifier(eClassifier, jsonObject);
			eObjects.add(eObject);
		}

		return eObjects;
	}

	protected EObject instantiateEClassifier(EClassifier eClassifier, JsonObject jsonObject) {
		EObject result = null;

		if (eClassifier instanceof EClass) {
			EClass eClass = (EClass) eClassifier;
			result = EcoreUtil.create(eClass);

			for(Pair pair : jsonObject.getPairs()) {
				String pairId = pair.getString();
				Value value = pair.getValue();

				EStructuralFeature eStructuralFeature = eClass.getEStructuralFeature(pairId);
				if(eStructuralFeature != null) {
					if(value instanceof ArrayValue) {
						for(Value singleValue : ((ArrayValue) value).getValue()) {
							setStructuralFeature(result, eStructuralFeature, singleValue);
						}
					} else {
						setStructuralFeature(result, eStructuralFeature, value);
					}
				}
			}
		}

		return result;
	}

	protected void setStructuralFeature(EObject result, EStructuralFeature eStructuralFeature, Value value) {
		if (eStructuralFeature instanceof EAttribute) {
			EAttribute eAttribute = (EAttribute) eStructuralFeature;
			if(eStructuralFeature.getUpperBound() == -1) {
				EList<Object> set = (EList<Object>) result.eGet(eAttribute);
				set.add(digestValue(value));
			} else {
				result.eSet(eAttribute, digestValue(value));
			}
		} else if(eStructuralFeature instanceof EReference) {
			EReference eReference = (EReference) eStructuralFeature;
			if(value instanceof JsonObjectValue) {
				JsonObject childJsonObject = ((JsonObjectValue) value).getValue();
				String childClassName = eReference.getEType().getName();
				EClassifier eChildClassifier = metamodel.getEClassifier(childClassName);
				if(eChildClassifier != null) {
					EObject child = instantiateEClassifier(eChildClassifier, childJsonObject);
					if(eStructuralFeature.getUpperBound() == -1) {
						EList<Object> set = (EList<Object>) result.eGet(eReference);
						set.add(child);
					} else {
						result.eSet(eReference, child);
					}
				}
			}
		}
	}

	protected Object digestValue(Value value) {
		if (value instanceof StringValue) {
			StringValue stringValue = (StringValue) value;
			return stringValue.getValue();
		} else if (value instanceof NumberValue) {
			NumberValue numberValue = (NumberValue) value;
			return numberValue.getValue();
		} else if (value instanceof BooleanValue) {
			BooleanValue booleanValue = (BooleanValue) value;
			return booleanValue.getValue().equals("true") ? Boolean.TRUE : Boolean.FALSE;
		} else {
			return null;
		}
	}

	protected void saveModel(File targetFile, List<EObject> eObjects) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.createResource(URI.createFileURI(targetFile.getAbsolutePath()));

		try {
			for(EObject eObject : eObjects) {
				res.getContents().add(eObject);
			}
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Model loadJson(File jsonFile) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.getResource(URI.createFileURI(jsonFile.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (Model) res.getContents().get(0);
	}

	protected EPackage loadMetamodel(File metamodelFile) {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.getResource(URI.createFileURI(metamodelFile.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (EPackage) res.getContents().get(0);
	}

}
