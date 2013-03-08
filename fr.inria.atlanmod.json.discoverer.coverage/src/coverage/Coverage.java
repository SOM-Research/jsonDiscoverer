/**
 */
package coverage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Coverage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link coverage.Coverage#getMappings <em>Mappings</em>}</li>
 *   <li>{@link coverage.Coverage#getInput <em>Input</em>}</li>
 * </ul>
 * </p>
 *
 * @see coverage.CoveragePackage#getCoverage()
 * @model
 * @generated
 */
public interface Coverage extends EObject {
	/**
	 * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link coverage.CoverageMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings</em>' containment reference list.
	 * @see coverage.CoveragePackage#getCoverage_Mappings()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<CoverageMapping> getMappings();

	/**
	 * Returns the value of the '<em><b>Input</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input</em>' reference.
	 * @see #setInput(EAttribute)
	 * @see coverage.CoveragePackage#getCoverage_Input()
	 * @model
	 * @generated
	 */
	EAttribute getInput();

	/**
	 * Sets the value of the '{@link coverage.Coverage#getInput <em>Input</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input</em>' reference.
	 * @see #getInput()
	 * @generated
	 */
	void setInput(EAttribute value);

} // Coverage
