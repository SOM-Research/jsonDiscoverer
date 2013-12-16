/**
 */
package fr.inria.atlanmod.json.discoverer.coverage.impl;

import fr.inria.atlanmod.json.discoverer.coverage.AttMapping;
import fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping;
import fr.inria.atlanmod.json.discoverer.coverage.Coverage;
import fr.inria.atlanmod.json.discoverer.coverage.CoverageFactory;
import fr.inria.atlanmod.json.discoverer.coverage.CoverageMapping;
import fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage;
import fr.inria.atlanmod.json.discoverer.coverage.RefMapping;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CoveragePackageImpl extends EPackageImpl implements CoveragePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass coverageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass coverageMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass refMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conceptMappingEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CoveragePackageImpl() {
		super(eNS_URI, CoverageFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link CoveragePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CoveragePackage init() {
		if (isInited) return (CoveragePackage)EPackage.Registry.INSTANCE.getEPackage(CoveragePackage.eNS_URI);

		// Obtain or create and register package
		CoveragePackageImpl theCoveragePackage = (CoveragePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CoveragePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CoveragePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCoveragePackage.createPackageContents();

		// Initialize created meta-data
		theCoveragePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCoveragePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CoveragePackage.eNS_URI, theCoveragePackage);
		return theCoveragePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCoverage() {
		return coverageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCoverage_Mappings() {
		return (EReference)coverageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCoverage_Input() {
		return (EReference)coverageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCoverage_Service() {
		return (EAttribute)coverageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCoverageMapping() {
		return coverageMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttMapping() {
		return attMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttMapping_Source() {
		return (EReference)attMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttMapping_Target() {
		return (EReference)attMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRefMapping() {
		return refMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRefMapping_Source() {
		return (EReference)refMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRefMapping_Target() {
		return (EReference)refMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConceptMapping() {
		return conceptMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConceptMapping_Source() {
		return (EReference)conceptMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConceptMapping_Target() {
		return (EReference)conceptMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoverageFactory getCoverageFactory() {
		return (CoverageFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		coverageEClass = createEClass(COVERAGE);
		createEReference(coverageEClass, COVERAGE__MAPPINGS);
		createEReference(coverageEClass, COVERAGE__INPUT);
		createEAttribute(coverageEClass, COVERAGE__SERVICE);

		coverageMappingEClass = createEClass(COVERAGE_MAPPING);

		attMappingEClass = createEClass(ATT_MAPPING);
		createEReference(attMappingEClass, ATT_MAPPING__SOURCE);
		createEReference(attMappingEClass, ATT_MAPPING__TARGET);

		refMappingEClass = createEClass(REF_MAPPING);
		createEReference(refMappingEClass, REF_MAPPING__SOURCE);
		createEReference(refMappingEClass, REF_MAPPING__TARGET);

		conceptMappingEClass = createEClass(CONCEPT_MAPPING);
		createEReference(conceptMappingEClass, CONCEPT_MAPPING__SOURCE);
		createEReference(conceptMappingEClass, CONCEPT_MAPPING__TARGET);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		attMappingEClass.getESuperTypes().add(this.getCoverageMapping());
		refMappingEClass.getESuperTypes().add(this.getCoverageMapping());
		conceptMappingEClass.getESuperTypes().add(this.getCoverageMapping());

		// Initialize classes and features; add operations and parameters
		initEClass(coverageEClass, Coverage.class, "Coverage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCoverage_Mappings(), this.getCoverageMapping(), null, "mappings", null, 1, -1, Coverage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCoverage_Input(), theEcorePackage.getEAttribute(), null, "input", null, 0, 1, Coverage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCoverage_Service(), ecorePackage.getEString(), "service", null, 0, 1, Coverage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(coverageMappingEClass, CoverageMapping.class, "CoverageMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(attMappingEClass, AttMapping.class, "AttMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttMapping_Source(), theEcorePackage.getEAttribute(), null, "source", null, 1, 1, AttMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttMapping_Target(), theEcorePackage.getEAttribute(), null, "target", null, 1, 1, AttMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(refMappingEClass, RefMapping.class, "RefMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRefMapping_Source(), theEcorePackage.getEReference(), null, "source", null, 1, 1, RefMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRefMapping_Target(), theEcorePackage.getEReference(), null, "target", null, 1, 1, RefMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conceptMappingEClass, ConceptMapping.class, "ConceptMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConceptMapping_Source(), theEcorePackage.getEClass(), null, "source", null, 1, 1, ConceptMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConceptMapping_Target(), theEcorePackage.getEClass(), null, "target", null, 1, 1, ConceptMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CoveragePackageImpl
