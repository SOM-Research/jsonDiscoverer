/**
 */
package jsondiscoverer.coverage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * A representation of the model object '<em><b>Coverage</b></em>'.
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link jsondiscoverer.coverage.Coverage#getMappings <em>Mappings</em>}</li>
 *   <li>{@link jsondiscoverer.coverage.Coverage#getInput <em>Input</em>}</li>
 *   <li>{@link jsondiscoverer.coverage.Coverage#getService <em>Service</em>}</li>
 * </ul>
 *
 * @see jsondiscoverer.coverage.CoveragePackage#getCoverage()
 * @model
 * @generated
 */
public interface Coverage extends EObject {
	/**
	 * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link jsondiscoverer.coverage.CoverageMapping}.
	 * 
	 * @return the value of the '<em>Mappings</em>' containment reference list.
	 * @see jsondiscoverer.coverage.CoveragePackage#getCoverage_Mappings()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<CoverageMapping> getMappings();

	/**
	 * Returns the value of the '<em><b>Input</b></em>' reference.
	 * 
	 * @return the value of the '<em>Input</em>' reference.
	 * @see #setInput(EAttribute)
	 * @see jsondiscoverer.coverage.CoveragePackage#getCoverage_Input()
	 * @model
	 * @generated
	 */
	EAttribute getInput();

	/**
	 * Sets the value of the '{@link jsondiscoverer.coverage.Coverage#getInput <em>Input</em>}' reference.
	 * 
	 * @param value the new value of the '<em>Input</em>' reference.
	 * @see #getInput()
	 * @generated
	 */
	void setInput(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Service</b></em>' attribute.
	 * 
	 * @return the value of the '<em>Service</em>' attribute.
	 * @see #setService(String)
	 * @see jsondiscoverer.coverage.CoveragePackage#getCoverage_Service()
	 * @model
	 * @generated
	 */
	String getService();

	/**
	 * Sets the value of the '{@link jsondiscoverer.coverage.Coverage#getService <em>Service</em>}' attribute.
	 * 
	 * @param value the new value of the '<em>Service</em>' attribute.
	 * @see #getService()
	 * @generated
	 */
	void setService(String value);

} // Coverage
