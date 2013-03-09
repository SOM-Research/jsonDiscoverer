package fr.inria.atlanmod.discoverer;

import java.io.File;
import java.io.IOException;
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
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import coverage.AttMapping;
import coverage.ConceptMapping;
import coverage.Coverage;
import coverage.CoverageMapping;
import coverage.CoveragePackage;
import coverage.RefMapping;

import fr.inria.atlanmod.JsonStandaloneSetup;
import fr.inria.atlanmod.json.ArrayValue;
import fr.inria.atlanmod.json.BooleanValue;
import fr.inria.atlanmod.json.JsonObject;
import fr.inria.atlanmod.json.JsonObjectValue;
import fr.inria.atlanmod.json.Model;
import fr.inria.atlanmod.json.NumberValue;
import fr.inria.atlanmod.json.Pair;
import fr.inria.atlanmod.json.StringValue;
import fr.inria.atlanmod.json.Value;

public class JsonMultiInjector {
	public static String INPUT_FILE1 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan2.json";
	public static String METAMODEL_FILE1 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan2.ecore";
	public static String COVERAGE_FILE1 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan2.coverage.xmi";

	public static String INPUT_FILE2 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan1/tan1A.json";
	public static String METAMODEL_FILE2 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan1/tan1.ecore";
	public static String COVERAGE_FILE2 = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan1/tan1.coverage.xmi";

	public static String COMPOSITE_METAMODEL_FILE = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan-composed.ecore";
	public static String COMPOSITE_OUTPUT_FILE = "C:/Users/useradm/eclipses/eclipse-juno/runtime-JSON/Test/tan-composed.xmi";

	private List<EObject> currentModel;

	public static void main(String[] args) {
		JsonStandaloneSetup.doSetup();

		JsonMultiInjector injector = new JsonMultiInjector();
		injector.multiInject(new File(INPUT_FILE1), new File(METAMODEL_FILE1), new File(COVERAGE_FILE1),
				new File(INPUT_FILE2), new File(METAMODEL_FILE2), new File(COVERAGE_FILE2),
				new File(COMPOSITE_METAMODEL_FILE), new File(COMPOSITE_OUTPUT_FILE));
	}


	public void multiInject(File jsonFile1, File metamodelFile1, File coverageFile1, File jsonFile2, File metamodelFile2, File coverageFile2, File compositeMetamodelFile, File compositeTargetFile) {
		Coverage coverage1 = loadCoverage(coverageFile1);
		Coverage coverage2 = loadCoverage(coverageFile2);

		currentModel = new ArrayList<EObject>();

		inject(jsonFile1, metamodelFile1, coverage1);
		inject(jsonFile2, metamodelFile2, coverage2);		

		saveModel(compositeTargetFile, currentModel);
	}

	protected void inject(File jsonFile, File metamodelFile, Coverage coverage) {
		Model jsonModel = loadJson(jsonFile);
		EPackage metamodel = loadMetamodel(metamodelFile);

		EClassifier eClassifier = metamodel.getEClassifier("Root");

		for(JsonObject jsonObject : jsonModel.getObjects()) {
			EObject eObject = instantiateEClassifier(eClassifier, jsonObject, null, coverage, metamodel);
		}

	}

	protected EObject instantiateEClassifier(EClassifier eClassifier, JsonObject jsonObject, EClassifier compositeEClassifier, Coverage coverage, EPackage metamodel) {
		EObject result = null;
		EObject compositeResult = null;

		if (eClassifier instanceof EClass) {
			EClass eClass = (EClass) eClassifier;
			result = EcoreUtil.create(eClass);

			EClass compositeEClass = (EClass) findCompositeEClass(eClass, coverage);
			EObject existingCompositeEObject = findExistingEObject(eClass, jsonObject, compositeEClass, coverage, metamodel);
			if(existingCompositeEObject != null) {
				return existingCompositeEObject;
			}

			compositeResult = EcoreUtil.create(compositeEClass);
			currentModel.add(compositeResult);

			for(Pair pair : jsonObject.getPairs()) {
				String pairId = pair.getString();
				Value value = pair.getValue();

				EStructuralFeature eStructuralFeature = eClass.getEStructuralFeature(pairId);
				if(eStructuralFeature != null) {
					if(value instanceof ArrayValue) {
						for(Value singleValue : ((ArrayValue) value).getValue()) {
							setStructuralFeature(result, eStructuralFeature, singleValue, compositeResult, coverage, metamodel);
						}
					} else {
						setStructuralFeature(result, eStructuralFeature, value, compositeResult, coverage, metamodel);
					}
				}
			}
		}

		return compositeResult;
	}

	private EObject findExistingEObject(EClass eClass, JsonObject jsonObject, EClass compositeEClass, Coverage coverage, EPackage metamodel) {
		List<EClass> existingEclasses = new ArrayList<EClass>();
		for(EObject object : currentModel) {
			EClass existingEClass = object.eClass();
			if(existingEClass.getName().equals(compositeEClass.getName())) {
				existingEclasses.add(existingEClass);
				for(Pair pair : jsonObject.getPairs()) {
					if (pair.getValue() instanceof StringValue) {
						String existingPairId = pair.getString();
						String existingValue = ((StringValue) pair.getValue()).getValue();
						EStructuralFeature existingEStructuralFeature = eClass.getEStructuralFeature(existingPairId);
						if (existingEStructuralFeature instanceof EAttribute && existingEStructuralFeature.getEType().equals(EcorePackage.Literals.ESTRING)) {
							EAttribute existingEAttribute = (EAttribute) existingEStructuralFeature;
							EAttribute compositeEAttribute = (EAttribute) findCompositeEAttribute(existingEAttribute, coverage);
							if(compositeEAttribute != null) {
								for(EStructuralFeature objectEAttribute : existingEClass.getEStructuralFeatures()) {
									if(objectEAttribute.getName().equals(compositeEAttribute.getName())) {
										String compositeValue = (String) object.eGet(objectEAttribute);
										if(existingValue.equals(compositeValue))
											return object;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	protected void setStructuralFeature(EObject result, EStructuralFeature eStructuralFeature, Value value, EObject compositeResult, Coverage coverage, EPackage metamodel) {
		if (eStructuralFeature instanceof EAttribute) {
			EAttribute eAttribute = (EAttribute) eStructuralFeature;
			EAttribute compositeEAttribute = (EAttribute) findCompositeEAttribute(eAttribute, coverage);
			if(compositeEAttribute != null) {
				if(eStructuralFeature.getUpperBound() == -1) {
					EList<Object> set = (EList<Object>) compositeResult.eGet(compositeEAttribute);
					set.add(digestValue(value));
				} else {
					compositeResult.eSet(compositeEAttribute, digestValue(value));
				}
			}
		} else if(eStructuralFeature instanceof EReference) {
			EReference eReference = (EReference) eStructuralFeature;
			EReference compositeEReference = findCompositeEReference(eReference, coverage);
			if(value instanceof JsonObjectValue) {
				JsonObject childJsonObject = ((JsonObjectValue) value).getValue();
				String childClassName = eReference.getEType().getName();
				EClassifier eChildClassifier = metamodel.getEClassifier(childClassName);
				if(eChildClassifier != null) {
					EClass compositeEClass = (EClass) findCompositeEClass((EClass) eChildClassifier, coverage);
					if(compositeEClass != null) {
						EObject child = instantiateEClassifier(eChildClassifier, childJsonObject, compositeEClass, coverage, metamodel);
						if(compositeEReference.getUpperBound() == -1) {
							EList<Object> set = (EList<Object>) compositeResult.eGet(compositeEReference);
							set.add(child);
						} else {
							compositeResult.eSet(compositeEReference, child);
						}
					}
				}
			}
		}
	}

	private EClass findCompositeEClass(EClass object, Coverage coverage) {
		for(CoverageMapping mapping : coverage.getMappings()) {
			if (mapping instanceof ConceptMapping) {
				ConceptMapping conceptMapping = (ConceptMapping) mapping;
				if(conceptMapping.getSource().getName().equals(object.getName())) 
					return conceptMapping.getTarget();	
			}
		}
		return null;
	}

	private EAttribute findCompositeEAttribute(EAttribute object, Coverage coverage) {
		for(CoverageMapping mapping : coverage.getMappings()) {
			if (mapping instanceof AttMapping) {
				AttMapping attMapping = (AttMapping) mapping;
				if(attMapping.getSource().getName().equals(object.getName())) 
					return attMapping.getTarget();	
			}
		}
		return null;
	}

	private EReference findCompositeEReference(EReference object, Coverage coverage) {
		for(CoverageMapping mapping : coverage.getMappings()) {
			if (mapping instanceof RefMapping) {
				RefMapping refMapping = (RefMapping) mapping;
				if(refMapping.getSource().getName().equals(object.getName())) 
					return refMapping.getTarget();		
			}
		}
		return null;
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

	public Coverage loadCoverage(File file) {
		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(CoveragePackage.eNS_URI, CoveragePackage.eINSTANCE);
		rset.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());

		// Testing
		Resource aux1 = rset.getResource(URI.createFileURI(METAMODEL_FILE1), true);
		try {
			aux1.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Resource aux2 = rset.getResource(URI.createFileURI(METAMODEL_FILE2), true);
		try {
			aux2.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Resource aux3 = rset.getResource(URI.createFileURI(COMPOSITE_METAMODEL_FILE), true);
		try {
			aux3.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Resource res = rset.getResource(URI.createFileURI(file.getAbsolutePath()), true);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Coverage coverage = (Coverage) res.getContents().get(0);
		return coverage;
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
}
