/**
 */
package jsondiscoverer.coverage;

import org.eclipse.emf.ecore.EFactory;

/**
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * @see jsondiscoverer.coverage.CoveragePackage
 * @generated
 */
public interface CoverageFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * 
	 * @generated
	 */
	CoverageFactory eINSTANCE = jsondiscoverer.coverage.impl.CoverageFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Coverage</em>'.
	 * 
	 * @return a new object of class '<em>Coverage</em>'.
	 * @generated
	 */
	Coverage createCoverage();

	/**
	 * Returns a new object of class '<em>Att Mapping</em>'.
	 * 
	 * @return a new object of class '<em>Att Mapping</em>'.
	 * @generated
	 */
	AttMapping createAttMapping();

	/**
	 * Returns a new object of class '<em>Ref Mapping</em>'.
	 * 
	 * @return a new object of class '<em>Ref Mapping</em>'.
	 * @generated
	 */
	RefMapping createRefMapping();

	/**
	 * Returns a new object of class '<em>Concept Mapping</em>'.
	 * 
	 * @return a new object of class '<em>Concept Mapping</em>'.
	 * @generated
	 */
	ConceptMapping createConceptMapping();

	/**
	 * Returns the package supported by this factory.
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	CoveragePackage getCoveragePackage();

} //CoverageFactory
