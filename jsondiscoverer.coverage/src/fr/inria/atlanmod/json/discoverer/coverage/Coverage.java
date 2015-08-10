/**
 */
package fr.inria.atlanmod.json.discoverer.coverage;

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
 *   <li>{@link fr.inria.atlanmod.json.discoverer.coverage.Coverage#getMappings <em>Mappings</em>}</li>
 *   <li>{@link fr.inria.atlanmod.json.discoverer.coverage.Coverage#getInput <em>Input</em>}</li>
 *   <li>{@link fr.inria.atlanmod.json.discoverer.coverage.Coverage#getService <em>Service</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#getCoverage()
 * @model
 * @generated
 */
public interface Coverage extends EObject {
	/**
	 * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link fr.inria.atlanmod.json.discoverer.coverage.CoverageMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings</em>' containment reference list.
	 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#getCoverage_Mappings()
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
	 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#getCoverage_Input()
	 * @model
	 * @generated
	 */
	EAttribute getInput();

	/**
	 * Sets the value of the '{@link fr.inria.atlanmod.json.discoverer.coverage.Coverage#getInput <em>Input</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input</em>' reference.
	 * @see #getInput()
	 * @generated
	 */
	void setInput(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Service</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service</em>' attribute.
	 * @see #setService(String)
	 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#getCoverage_Service()
	 * @model
	 * @generated
	 */
	String getService();

	/**
	 * Sets the value of the '{@link fr.inria.atlanmod.json.discoverer.coverage.Coverage#getService <em>Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service</em>' attribute.
	 * @see #getService()
	 * @generated
	 */
	void setService(String value);

} // Coverage
