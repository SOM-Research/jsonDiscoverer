/**
 */
package fr.inria.atlanmod.json;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Null Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.inria.atlanmod.json.NullValue#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.inria.atlanmod.json.JsonPackage#getNullValue()
 * @model
 * @generated
 */
public interface NullValue extends Value
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(String)
   * @see fr.inria.atlanmod.json.JsonPackage#getNullValue_Value()
   * @model
   * @generated
   */
  String getValue();

  /**
   * Sets the value of the '{@link fr.inria.atlanmod.json.NullValue#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(String value);

} // NullValue
