/**
 */
package fr.inria.atlanmod.json;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.inria.atlanmod.json.Model#getObjects <em>Objects</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.inria.atlanmod.json.JsonPackage#getModel()
 * @model
 * @generated
 */
public interface Model extends EObject
{
  /**
   * Returns the value of the '<em><b>Objects</b></em>' containment reference list.
   * The list contents are of type {@link fr.inria.atlanmod.json.JsonObject}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Objects</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Objects</em>' containment reference list.
   * @see fr.inria.atlanmod.json.JsonPackage#getModel_Objects()
   * @model containment="true"
   * @generated
   */
  EList<JsonObject> getObjects();

} // Model
