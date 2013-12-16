/**
 */
package fr.inria.atlanmod.json.discoverer.coverage;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage
 * @generated
 */
public interface CoverageFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CoverageFactory eINSTANCE = fr.inria.atlanmod.json.discoverer.coverage.impl.CoverageFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Coverage</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Coverage</em>'.
	 * @generated
	 */
	Coverage createCoverage();

	/**
	 * Returns a new object of class '<em>Att Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Att Mapping</em>'.
	 * @generated
	 */
	AttMapping createAttMapping();

	/**
	 * Returns a new object of class '<em>Ref Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ref Mapping</em>'.
	 * @generated
	 */
	RefMapping createRefMapping();

	/**
	 * Returns a new object of class '<em>Concept Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Concept Mapping</em>'.
	 * @generated
	 */
	ConceptMapping createConceptMapping();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CoveragePackage getCoveragePackage();

} //CoverageFactory
