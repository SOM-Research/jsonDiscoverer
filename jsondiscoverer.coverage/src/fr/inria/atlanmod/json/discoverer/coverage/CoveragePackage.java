/**
 */
package fr.inria.atlanmod.json.discoverer.coverage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see fr.inria.atlanmod.json.discoverer.coverage.CoverageFactory
 * @model kind="package"
 * @generated
 */
public interface CoveragePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "coverage";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://fr.inria.atlanmod/json/coverage";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "coverage";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CoveragePackage eINSTANCE = fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl.init();

	/**
	 * The meta object id for the '{@link fr.inria.atlanmod.json.discoverer.coverage.impl.CoverageImpl <em>Coverage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoverageImpl
	 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl#getCoverage()
	 * @generated
	 */
	int COVERAGE = 0;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE__MAPPINGS = 0;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE__INPUT = 1;

	/**
	 * The feature id for the '<em><b>Service</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE__SERVICE = 2;

	/**
	 * The number of structural features of the '<em>Coverage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link fr.inria.atlanmod.json.discoverer.coverage.impl.CoverageMappingImpl <em>Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoverageMappingImpl
	 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl#getCoverageMapping()
	 * @generated
	 */
	int COVERAGE_MAPPING = 1;

	/**
	 * The number of structural features of the '<em>Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_MAPPING_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.inria.atlanmod.json.discoverer.coverage.impl.AttMappingImpl <em>Att Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.AttMappingImpl
	 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl#getAttMapping()
	 * @generated
	 */
	int ATT_MAPPING = 2;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATT_MAPPING__SOURCE = COVERAGE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATT_MAPPING__TARGET = COVERAGE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Att Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATT_MAPPING_FEATURE_COUNT = COVERAGE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link fr.inria.atlanmod.json.discoverer.coverage.impl.RefMappingImpl <em>Ref Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.RefMappingImpl
	 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl#getRefMapping()
	 * @generated
	 */
	int REF_MAPPING = 3;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REF_MAPPING__SOURCE = COVERAGE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REF_MAPPING__TARGET = COVERAGE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Ref Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REF_MAPPING_FEATURE_COUNT = COVERAGE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link fr.inria.atlanmod.json.discoverer.coverage.impl.ConceptMappingImpl <em>Concept Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.ConceptMappingImpl
	 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl#getConceptMapping()
	 * @generated
	 */
	int CONCEPT_MAPPING = 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_MAPPING__SOURCE = COVERAGE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_MAPPING__TARGET = COVERAGE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Concept Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_MAPPING_FEATURE_COUNT = COVERAGE_MAPPING_FEATURE_COUNT + 2;


	/**
	 * Returns the meta object for class '{@link fr.inria.atlanmod.json.discoverer.coverage.Coverage <em>Coverage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Coverage</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.Coverage
	 * @generated
	 */
	EClass getCoverage();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.inria.atlanmod.json.discoverer.coverage.Coverage#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.Coverage#getMappings()
	 * @see #getCoverage()
	 * @generated
	 */
	EReference getCoverage_Mappings();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.atlanmod.json.discoverer.coverage.Coverage#getInput <em>Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Input</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.Coverage#getInput()
	 * @see #getCoverage()
	 * @generated
	 */
	EReference getCoverage_Input();

	/**
	 * Returns the meta object for the attribute '{@link fr.inria.atlanmod.json.discoverer.coverage.Coverage#getService <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Service</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.Coverage#getService()
	 * @see #getCoverage()
	 * @generated
	 */
	EAttribute getCoverage_Service();

	/**
	 * Returns the meta object for class '{@link fr.inria.atlanmod.json.discoverer.coverage.CoverageMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.CoverageMapping
	 * @generated
	 */
	EClass getCoverageMapping();

	/**
	 * Returns the meta object for class '{@link fr.inria.atlanmod.json.discoverer.coverage.AttMapping <em>Att Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Att Mapping</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.AttMapping
	 * @generated
	 */
	EClass getAttMapping();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.atlanmod.json.discoverer.coverage.AttMapping#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.AttMapping#getSource()
	 * @see #getAttMapping()
	 * @generated
	 */
	EReference getAttMapping_Source();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.atlanmod.json.discoverer.coverage.AttMapping#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.AttMapping#getTarget()
	 * @see #getAttMapping()
	 * @generated
	 */
	EReference getAttMapping_Target();

	/**
	 * Returns the meta object for class '{@link fr.inria.atlanmod.json.discoverer.coverage.RefMapping <em>Ref Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ref Mapping</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.RefMapping
	 * @generated
	 */
	EClass getRefMapping();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.atlanmod.json.discoverer.coverage.RefMapping#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.RefMapping#getSource()
	 * @see #getRefMapping()
	 * @generated
	 */
	EReference getRefMapping_Source();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.atlanmod.json.discoverer.coverage.RefMapping#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.RefMapping#getTarget()
	 * @see #getRefMapping()
	 * @generated
	 */
	EReference getRefMapping_Target();

	/**
	 * Returns the meta object for class '{@link fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping <em>Concept Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Concept Mapping</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping
	 * @generated
	 */
	EClass getConceptMapping();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping#getSource()
	 * @see #getConceptMapping()
	 * @generated
	 */
	EReference getConceptMapping_Source();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping#getTarget()
	 * @see #getConceptMapping()
	 * @generated
	 */
	EReference getConceptMapping_Target();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CoverageFactory getCoverageFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link fr.inria.atlanmod.json.discoverer.coverage.impl.CoverageImpl <em>Coverage</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoverageImpl
		 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl#getCoverage()
		 * @generated
		 */
		EClass COVERAGE = eINSTANCE.getCoverage();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COVERAGE__MAPPINGS = eINSTANCE.getCoverage_Mappings();

		/**
		 * The meta object literal for the '<em><b>Input</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COVERAGE__INPUT = eINSTANCE.getCoverage_Input();

		/**
		 * The meta object literal for the '<em><b>Service</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE__SERVICE = eINSTANCE.getCoverage_Service();

		/**
		 * The meta object literal for the '{@link fr.inria.atlanmod.json.discoverer.coverage.impl.CoverageMappingImpl <em>Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoverageMappingImpl
		 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl#getCoverageMapping()
		 * @generated
		 */
		EClass COVERAGE_MAPPING = eINSTANCE.getCoverageMapping();

		/**
		 * The meta object literal for the '{@link fr.inria.atlanmod.json.discoverer.coverage.impl.AttMappingImpl <em>Att Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.AttMappingImpl
		 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl#getAttMapping()
		 * @generated
		 */
		EClass ATT_MAPPING = eINSTANCE.getAttMapping();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATT_MAPPING__SOURCE = eINSTANCE.getAttMapping_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATT_MAPPING__TARGET = eINSTANCE.getAttMapping_Target();

		/**
		 * The meta object literal for the '{@link fr.inria.atlanmod.json.discoverer.coverage.impl.RefMappingImpl <em>Ref Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.RefMappingImpl
		 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl#getRefMapping()
		 * @generated
		 */
		EClass REF_MAPPING = eINSTANCE.getRefMapping();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REF_MAPPING__SOURCE = eINSTANCE.getRefMapping_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REF_MAPPING__TARGET = eINSTANCE.getRefMapping_Target();

		/**
		 * The meta object literal for the '{@link fr.inria.atlanmod.json.discoverer.coverage.impl.ConceptMappingImpl <em>Concept Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.ConceptMappingImpl
		 * @see fr.inria.atlanmod.json.discoverer.coverage.impl.CoveragePackageImpl#getConceptMapping()
		 * @generated
		 */
		EClass CONCEPT_MAPPING = eINSTANCE.getConceptMapping();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCEPT_MAPPING__SOURCE = eINSTANCE.getConceptMapping_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCEPT_MAPPING__TARGET = eINSTANCE.getConceptMapping_Target();

	}

} //CoveragePackage
