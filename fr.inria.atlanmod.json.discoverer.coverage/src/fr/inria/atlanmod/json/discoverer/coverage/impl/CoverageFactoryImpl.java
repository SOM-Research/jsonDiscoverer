/**
 */
package fr.inria.atlanmod.json.discoverer.coverage.impl;

import fr.inria.atlanmod.json.discoverer.coverage.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CoverageFactoryImpl extends EFactoryImpl implements CoverageFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CoverageFactory init() {
		try {
			CoverageFactory theCoverageFactory = (CoverageFactory)EPackage.Registry.INSTANCE.getEFactory(CoveragePackage.eNS_URI);
			if (theCoverageFactory != null) {
				return theCoverageFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CoverageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoverageFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CoveragePackage.COVERAGE: return createCoverage();
			case CoveragePackage.ATT_MAPPING: return createAttMapping();
			case CoveragePackage.REF_MAPPING: return createRefMapping();
			case CoveragePackage.CONCEPT_MAPPING: return createConceptMapping();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Coverage createCoverage() {
		CoverageImpl coverage = new CoverageImpl();
		return coverage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttMapping createAttMapping() {
		AttMappingImpl attMapping = new AttMappingImpl();
		return attMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RefMapping createRefMapping() {
		RefMappingImpl refMapping = new RefMappingImpl();
		return refMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConceptMapping createConceptMapping() {
		ConceptMappingImpl conceptMapping = new ConceptMappingImpl();
		return conceptMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoveragePackage getCoveragePackage() {
		return (CoveragePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CoveragePackage getPackage() {
		return CoveragePackage.eINSTANCE;
	}

} //CoverageFactoryImpl
