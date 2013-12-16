/**
 */
package fr.inria.atlanmod.json.discoverer.coverage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concept Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping#getSource <em>Source</em>}</li>
 *   <li>{@link fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#getConceptMapping()
 * @model
 * @generated
 */
public interface ConceptMapping extends CoverageMapping {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(EClass)
	 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#getConceptMapping_Source()
	 * @model required="true"
	 * @generated
	 */
	EClass getSource();

	/**
	 * Sets the value of the '{@link fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(EClass value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EClass)
	 * @see fr.inria.atlanmod.json.discoverer.coverage.CoveragePackage#getConceptMapping_Target()
	 * @model required="true"
	 * @generated
	 */
	EClass getTarget();

	/**
	 * Sets the value of the '{@link fr.inria.atlanmod.json.discoverer.coverage.ConceptMapping#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EClass value);

} // ConceptMapping
