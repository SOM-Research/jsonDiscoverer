/**
 */
package jsondiscoverer.coverage;

import org.eclipse.emf.ecore.EReference;

/**
 * A representation of the model object '<em><b>Ref Mapping</b></em>'.
 *
 * <p>The following features are supported:</p>
 * <ul>
 *   <li>{@link jsondiscoverer.coverage.RefMapping#getSource <em>Source</em>}</li>
 *   <li>{@link jsondiscoverer.coverage.RefMapping#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see jsondiscoverer.coverage.CoveragePackage#getRefMapping()
 * @model
 * @generated
 */
public interface RefMapping extends CoverageMapping {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * 
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(EReference)
	 * @see jsondiscoverer.coverage.CoveragePackage#getRefMapping_Source()
	 * @model required="true"
	 * @generated
	 */
	EReference getSource();

	/**
	 * Sets the value of the '{@link jsondiscoverer.coverage.RefMapping#getSource <em>Source</em>}' reference.
	 * 
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(EReference value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * 
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EReference)
	 * @see jsondiscoverer.coverage.CoveragePackage#getRefMapping_Target()
	 * @model required="true"
	 * @generated
	 */
	EReference getTarget();

	/**
	 * Sets the value of the '{@link jsondiscoverer.coverage.RefMapping#getTarget <em>Target</em>}' reference.
	 * 
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EReference value);

} // RefMapping
