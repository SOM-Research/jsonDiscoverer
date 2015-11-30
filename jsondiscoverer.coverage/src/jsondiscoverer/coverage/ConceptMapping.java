/**
 */
package jsondiscoverer.coverage;

import org.eclipse.emf.ecore.EClass;

/**
 * A representation of the model object '<em><b>Concept Mapping</b></em>'.
 *
 * <p>The following features are supported:</p>
 * <ul>
 *   <li>{@link jsondiscoverer.coverage.ConceptMapping#getSource <em>Source</em>}</li>
 *   <li>{@link jsondiscoverer.coverage.ConceptMapping#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see jsondiscoverer.coverage.CoveragePackage#getConceptMapping()
 * @model
 * @generated
 */
public interface ConceptMapping extends CoverageMapping {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * 
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(EClass)
	 * @see jsondiscoverer.coverage.CoveragePackage#getConceptMapping_Source()
	 * @model required="true"
	 * @generated
	 */
	EClass getSource();

	/**
	 * Sets the value of the '{@link jsondiscoverer.coverage.ConceptMapping#getSource <em>Source</em>}' reference.
	 * 
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(EClass value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * 
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EClass)
	 * @see jsondiscoverer.coverage.CoveragePackage#getConceptMapping_Target()
	 * @model required="true"
	 * @generated
	 */
	EClass getTarget();

	/**
	 * Sets the value of the '{@link jsondiscoverer.coverage.ConceptMapping#getTarget <em>Target</em>}' reference.
	 * 
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EClass value);

} // ConceptMapping
