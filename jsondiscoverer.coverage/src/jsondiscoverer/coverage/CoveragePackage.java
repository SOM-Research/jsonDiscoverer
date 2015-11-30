/**
 */
package jsondiscoverer.coverage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * 
 * @see jsondiscoverer.coverage.CoverageFactory
 * @model kind="package"
 * @generated
 */
public interface CoveragePackage extends EPackage {
	/**
	 * The package name.
	 * 
	 * @generated
	 */
	String eNAME = "coverage";

	/**
	 * The package namespace URI.
	 * 
	 * @generated
	 */
	String eNS_URI = "http://jsondiscoverer/coverage";

	/**
	 * The package namespace name.
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "coverage";

	/**
	 * The singleton instance of the package.
	 * 
	 * @generated
	 */
	CoveragePackage eINSTANCE = jsondiscoverer.coverage.impl.CoveragePackageImpl.init();

	/**
	 * The meta object id for the '{@link jsondiscoverer.coverage.impl.CoverageImpl <em>Coverage</em>}' class.
	 * 
	 * @see jsondiscoverer.coverage.impl.CoverageImpl
	 * @see jsondiscoverer.coverage.impl.CoveragePackageImpl#getCoverage()
	 * @generated
	 */
	int COVERAGE = 0;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * 
	 * @generated
	 * @ordered
	 */
	int COVERAGE__MAPPINGS = 0;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference.
	 * 
	 * @generated
	 * @ordered
	 */
	int COVERAGE__INPUT = 1;

	/**
	 * The feature id for the '<em><b>Service</b></em>' attribute.
	 * 
	 * @generated
	 * @ordered
	 */
	int COVERAGE__SERVICE = 2;

	/**
	 * The number of structural features of the '<em>Coverage</em>' class.
	 * 
	 * @generated
	 * @ordered
	 */
	int COVERAGE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link jsondiscoverer.coverage.impl.CoverageMappingImpl <em>Mapping</em>}' class.
	 * 
	 * @see jsondiscoverer.coverage.impl.CoverageMappingImpl
	 * @see jsondiscoverer.coverage.impl.CoveragePackageImpl#getCoverageMapping()
	 * @generated
	 */
	int COVERAGE_MAPPING = 1;

	/**
	 * The number of structural features of the '<em>Mapping</em>' class.
	 * 
	 * @generated
	 * @ordered
	 */
	int COVERAGE_MAPPING_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link jsondiscoverer.coverage.impl.AttMappingImpl <em>Att Mapping</em>}' class.
	 * 
	 * @see jsondiscoverer.coverage.impl.AttMappingImpl
	 * @see jsondiscoverer.coverage.impl.CoveragePackageImpl#getAttMapping()
	 * @generated
	 */
	int ATT_MAPPING = 2;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * 
	 * @generated
	 * @ordered
	 */
	int ATT_MAPPING__SOURCE = COVERAGE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * 
	 * @generated
	 * @ordered
	 */
	int ATT_MAPPING__TARGET = COVERAGE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Att Mapping</em>' class.
	 * 
	 * @generated
	 * @ordered
	 */
	int ATT_MAPPING_FEATURE_COUNT = COVERAGE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link jsondiscoverer.coverage.impl.RefMappingImpl <em>Ref Mapping</em>}' class.
	 * 
	 * @see jsondiscoverer.coverage.impl.RefMappingImpl
	 * @see jsondiscoverer.coverage.impl.CoveragePackageImpl#getRefMapping()
	 * @generated
	 */
	int REF_MAPPING = 3;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * 
	 * @generated
	 * @ordered
	 */
	int REF_MAPPING__SOURCE = COVERAGE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * 
	 * @generated
	 * @ordered
	 */
	int REF_MAPPING__TARGET = COVERAGE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Ref Mapping</em>' class.
	 * 
	 * @generated
	 * @ordered
	 */
	int REF_MAPPING_FEATURE_COUNT = COVERAGE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link jsondiscoverer.coverage.impl.ConceptMappingImpl <em>Concept Mapping</em>}' class.
	 * 
	 * @see jsondiscoverer.coverage.impl.ConceptMappingImpl
	 * @see jsondiscoverer.coverage.impl.CoveragePackageImpl#getConceptMapping()
	 * @generated
	 */
	int CONCEPT_MAPPING = 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * 
	 * @generated
	 * @ordered
	 */
	int CONCEPT_MAPPING__SOURCE = COVERAGE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * 
	 * @generated
	 * @ordered
	 */
	int CONCEPT_MAPPING__TARGET = COVERAGE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Concept Mapping</em>' class.
	 * 
	 * @generated
	 * @ordered
	 */
	int CONCEPT_MAPPING_FEATURE_COUNT = COVERAGE_MAPPING_FEATURE_COUNT + 2;


	/**
	 * Returns the meta object for class '{@link jsondiscoverer.coverage.Coverage <em>Coverage</em>}'.
	 * 
	 * @return the meta object for class '<em>Coverage</em>'.
	 * @see jsondiscoverer.coverage.Coverage
	 * @generated
	 */
	EClass getCoverage();

	/**
	 * Returns the meta object for the containment reference list '{@link jsondiscoverer.coverage.Coverage#getMappings <em>Mappings</em>}'.
	 * 
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see jsondiscoverer.coverage.Coverage#getMappings()
	 * @see #getCoverage()
	 * @generated
	 */
	EReference getCoverage_Mappings();

	/**
	 * Returns the meta object for the reference '{@link jsondiscoverer.coverage.Coverage#getInput <em>Input</em>}'.
	 * 
	 * @return the meta object for the reference '<em>Input</em>'.
	 * @see jsondiscoverer.coverage.Coverage#getInput()
	 * @see #getCoverage()
	 * @generated
	 */
	EReference getCoverage_Input();

	/**
	 * Returns the meta object for the attribute '{@link jsondiscoverer.coverage.Coverage#getService <em>Service</em>}'.
	 * 
	 * @return the meta object for the attribute '<em>Service</em>'.
	 * @see jsondiscoverer.coverage.Coverage#getService()
	 * @see #getCoverage()
	 * @generated
	 */
	EAttribute getCoverage_Service();

	/**
	 * Returns the meta object for class '{@link jsondiscoverer.coverage.CoverageMapping <em>Mapping</em>}'.
	 * 
	 * @return the meta object for class '<em>Mapping</em>'.
	 * @see jsondiscoverer.coverage.CoverageMapping
	 * @generated
	 */
	EClass getCoverageMapping();

	/**
	 * Returns the meta object for class '{@link jsondiscoverer.coverage.AttMapping <em>Att Mapping</em>}'.
	 * 
	 * @return the meta object for class '<em>Att Mapping</em>'.
	 * @see jsondiscoverer.coverage.AttMapping
	 * @generated
	 */
	EClass getAttMapping();

	/**
	 * Returns the meta object for the reference '{@link jsondiscoverer.coverage.AttMapping#getSource <em>Source</em>}'.
	 * 
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see jsondiscoverer.coverage.AttMapping#getSource()
	 * @see #getAttMapping()
	 * @generated
	 */
	EReference getAttMapping_Source();

	/**
	 * Returns the meta object for the reference '{@link jsondiscoverer.coverage.AttMapping#getTarget <em>Target</em>}'.
	 * 
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see jsondiscoverer.coverage.AttMapping#getTarget()
	 * @see #getAttMapping()
	 * @generated
	 */
	EReference getAttMapping_Target();

	/**
	 * Returns the meta object for class '{@link jsondiscoverer.coverage.RefMapping <em>Ref Mapping</em>}'.
	 * 
	 * @return the meta object for class '<em>Ref Mapping</em>'.
	 * @see jsondiscoverer.coverage.RefMapping
	 * @generated
	 */
	EClass getRefMapping();

	/**
	 * Returns the meta object for the reference '{@link jsondiscoverer.coverage.RefMapping#getSource <em>Source</em>}'.
	 * 
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see jsondiscoverer.coverage.RefMapping#getSource()
	 * @see #getRefMapping()
	 * @generated
	 */
	EReference getRefMapping_Source();

	/**
	 * Returns the meta object for the reference '{@link jsondiscoverer.coverage.RefMapping#getTarget <em>Target</em>}'.
	 * 
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see jsondiscoverer.coverage.RefMapping#getTarget()
	 * @see #getRefMapping()
	 * @generated
	 */
	EReference getRefMapping_Target();

	/**
	 * Returns the meta object for class '{@link jsondiscoverer.coverage.ConceptMapping <em>Concept Mapping</em>}'.
	 * 
	 * @return the meta object for class '<em>Concept Mapping</em>'.
	 * @see jsondiscoverer.coverage.ConceptMapping
	 * @generated
	 */
	EClass getConceptMapping();

	/**
	 * Returns the meta object for the reference '{@link jsondiscoverer.coverage.ConceptMapping#getSource <em>Source</em>}'.
	 * 
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see jsondiscoverer.coverage.ConceptMapping#getSource()
	 * @see #getConceptMapping()
	 * @generated
	 */
	EReference getConceptMapping_Source();

	/**
	 * Returns the meta object for the reference '{@link jsondiscoverer.coverage.ConceptMapping#getTarget <em>Target</em>}'.
	 * 
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see jsondiscoverer.coverage.ConceptMapping#getTarget()
	 * @see #getConceptMapping()
	 * @generated
	 */
	EReference getConceptMapping_Target();

	/**
	 * Returns the factory that creates the instances of the model.
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CoverageFactory getCoverageFactory();

	/**
	 * 
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link jsondiscoverer.coverage.impl.CoverageImpl <em>Coverage</em>}' class.
		 * 
		 * @see jsondiscoverer.coverage.impl.CoverageImpl
		 * @see jsondiscoverer.coverage.impl.CoveragePackageImpl#getCoverage()
		 * @generated
		 */
		EClass COVERAGE = eINSTANCE.getCoverage();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * 
		 * @generated
		 */
		EReference COVERAGE__MAPPINGS = eINSTANCE.getCoverage_Mappings();

		/**
		 * The meta object literal for the '<em><b>Input</b></em>' reference feature.
		 * 
		 * @generated
		 */
		EReference COVERAGE__INPUT = eINSTANCE.getCoverage_Input();

		/**
		 * The meta object literal for the '<em><b>Service</b></em>' attribute feature.
		 * 
		 * @generated
		 */
		EAttribute COVERAGE__SERVICE = eINSTANCE.getCoverage_Service();

		/**
		 * The meta object literal for the '{@link jsondiscoverer.coverage.impl.CoverageMappingImpl <em>Mapping</em>}' class.
		 * 
		 * @see jsondiscoverer.coverage.impl.CoverageMappingImpl
		 * @see jsondiscoverer.coverage.impl.CoveragePackageImpl#getCoverageMapping()
		 * @generated
		 */
		EClass COVERAGE_MAPPING = eINSTANCE.getCoverageMapping();

		/**
		 * The meta object literal for the '{@link jsondiscoverer.coverage.impl.AttMappingImpl <em>Att Mapping</em>}' class.
		 * 
		 * @see jsondiscoverer.coverage.impl.AttMappingImpl
		 * @see jsondiscoverer.coverage.impl.CoveragePackageImpl#getAttMapping()
		 * @generated
		 */
		EClass ATT_MAPPING = eINSTANCE.getAttMapping();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * 
		 * @generated
		 */
		EReference ATT_MAPPING__SOURCE = eINSTANCE.getAttMapping_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * 
		 * @generated
		 */
		EReference ATT_MAPPING__TARGET = eINSTANCE.getAttMapping_Target();

		/**
		 * The meta object literal for the '{@link jsondiscoverer.coverage.impl.RefMappingImpl <em>Ref Mapping</em>}' class.
		 * 
		 * @see jsondiscoverer.coverage.impl.RefMappingImpl
		 * @see jsondiscoverer.coverage.impl.CoveragePackageImpl#getRefMapping()
		 * @generated
		 */
		EClass REF_MAPPING = eINSTANCE.getRefMapping();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * 
		 * @generated
		 */
		EReference REF_MAPPING__SOURCE = eINSTANCE.getRefMapping_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * 
		 * @generated
		 */
		EReference REF_MAPPING__TARGET = eINSTANCE.getRefMapping_Target();

		/**
		 * The meta object literal for the '{@link jsondiscoverer.coverage.impl.ConceptMappingImpl <em>Concept Mapping</em>}' class.
		 * 
		 * @see jsondiscoverer.coverage.impl.ConceptMappingImpl
		 * @see jsondiscoverer.coverage.impl.CoveragePackageImpl#getConceptMapping()
		 * @generated
		 */
		EClass CONCEPT_MAPPING = eINSTANCE.getConceptMapping();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * 
		 * @generated
		 */
		EReference CONCEPT_MAPPING__SOURCE = eINSTANCE.getConceptMapping_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * 
		 * @generated
		 */
		EReference CONCEPT_MAPPING__TARGET = eINSTANCE.getConceptMapping_Target();

	}

} //CoveragePackage
