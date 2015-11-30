/**
 */
package jsondiscoverer.coverage;

import org.eclipse.emf.ecore.EAttribute;

/**
 * A representation of the model object '<em><b>Att Mapping</b></em>'.
 *
 * <p>The following features are supported:</p>
 * <ul>
 *   <li>{@link jsondiscoverer.coverage.AttMapping#getSource <em>Source</em>}</li>
 *   <li>{@link jsondiscoverer.coverage.AttMapping#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see jsondiscoverer.coverage.CoveragePackage#getAttMapping()
 * @model
 * @generated
 */
public interface AttMapping extends CoverageMapping {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * 
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(EAttribute)
	 * @see jsondiscoverer.coverage.CoveragePackage#getAttMapping_Source()
	 * @model required="true"
	 * @generated
	 */
	EAttribute getSource();

	/**
	 * Sets the value of the '{@link jsondiscoverer.coverage.AttMapping#getSource <em>Source</em>}' reference.
	 * 
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * 
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EAttribute)
	 * @see jsondiscoverer.coverage.CoveragePackage#getAttMapping_Target()
	 * @model required="true"
	 * @generated
	 */
	EAttribute getTarget();

	/**
	 * Sets the value of the '{@link jsondiscoverer.coverage.AttMapping#getTarget <em>Target</em>}' reference.
	 * 
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EAttribute value);

} // AttMapping
