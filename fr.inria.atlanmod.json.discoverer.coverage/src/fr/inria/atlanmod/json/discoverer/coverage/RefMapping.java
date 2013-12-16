/**
 */
package fr.inria.atlanmod.json.discoverer.coverage;

import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ref Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.inria.atlanmod.json.discoverer.coverage.RefMapping#getSource <em>Source</em>}</li>
 *   <li>{@link fr.inria.atlanmod.json.discoverer.coverage.RefMapping#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#getRefMapping()
 * @model
 * @generated
 */
public interface RefMapping extends CoverageMapping {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(EReference)
	 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#getRefMapping_Source()
	 * @model required="true"
	 * @generated
	 */
	EReference getSource();

	/**
	 * Sets the value of the '{@link fr.inria.atlanmod.json.discoverer.coverage.RefMapping#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(EReference value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EReference)
	 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#getRefMapping_Target()
	 * @model required="true"
	 * @generated
	 */
	EReference getTarget();

	/**
	 * Sets the value of the '{@link fr.inria.atlanmod.json.discoverer.coverage.RefMapping#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EReference value);

} // RefMapping
