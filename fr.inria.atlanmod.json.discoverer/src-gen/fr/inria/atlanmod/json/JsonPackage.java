/**
 */
package fr.inria.atlanmod.json;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see fr.inria.atlanmod.json.JsonFactory
 * @model kind="package"
 * @generated
 */
public interface JsonPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "json";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.inria.fr/atlanmod/Json";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "json";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  JsonPackage eINSTANCE = fr.inria.atlanmod.json.impl.JsonPackageImpl.init();

  /**
   * The meta object id for the '{@link fr.inria.atlanmod.json.impl.ModelImpl <em>Model</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.inria.atlanmod.json.impl.ModelImpl
   * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getModel()
   * @generated
   */
  int MODEL = 0;

  /**
   * The feature id for the '<em><b>Objects</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__OBJECTS = 0;

  /**
   * The number of structural features of the '<em>Model</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.inria.atlanmod.json.impl.JsonObjectImpl <em>Object</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.inria.atlanmod.json.impl.JsonObjectImpl
   * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getJsonObject()
   * @generated
   */
  int JSON_OBJECT = 1;

  /**
   * The feature id for the '<em><b>Pairs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_OBJECT__PAIRS = 0;

  /**
   * The number of structural features of the '<em>Object</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_OBJECT_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.inria.atlanmod.json.impl.PairImpl <em>Pair</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.inria.atlanmod.json.impl.PairImpl
   * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getPair()
   * @generated
   */
  int PAIR = 2;

  /**
   * The feature id for the '<em><b>String</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAIR__STRING = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAIR__VALUE = 1;

  /**
   * The number of structural features of the '<em>Pair</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAIR_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link fr.inria.atlanmod.json.impl.ValueImpl <em>Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.inria.atlanmod.json.impl.ValueImpl
   * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getValue()
   * @generated
   */
  int VALUE = 3;

  /**
   * The number of structural features of the '<em>Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link fr.inria.atlanmod.json.impl.StringValueImpl <em>String Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.inria.atlanmod.json.impl.StringValueImpl
   * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getStringValue()
   * @generated
   */
  int STRING_VALUE = 4;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>String Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.inria.atlanmod.json.impl.NumberValueImpl <em>Number Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.inria.atlanmod.json.impl.NumberValueImpl
   * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getNumberValue()
   * @generated
   */
  int NUMBER_VALUE = 5;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NUMBER_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Number Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NUMBER_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.inria.atlanmod.json.impl.JsonObjectValueImpl <em>Object Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.inria.atlanmod.json.impl.JsonObjectValueImpl
   * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getJsonObjectValue()
   * @generated
   */
  int JSON_OBJECT_VALUE = 6;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_OBJECT_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Object Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_OBJECT_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.inria.atlanmod.json.impl.ArrayValueImpl <em>Array Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.inria.atlanmod.json.impl.ArrayValueImpl
   * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getArrayValue()
   * @generated
   */
  int ARRAY_VALUE = 7;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Array Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.inria.atlanmod.json.impl.BooleanValueImpl <em>Boolean Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.inria.atlanmod.json.impl.BooleanValueImpl
   * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getBooleanValue()
   * @generated
   */
  int BOOLEAN_VALUE = 8;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Boolean Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.inria.atlanmod.json.impl.NullValueImpl <em>Null Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.inria.atlanmod.json.impl.NullValueImpl
   * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getNullValue()
   * @generated
   */
  int NULL_VALUE = 9;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NULL_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Null Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NULL_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;


  /**
   * Returns the meta object for class '{@link fr.inria.atlanmod.json.Model <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Model</em>'.
   * @see fr.inria.atlanmod.json.Model
   * @generated
   */
  EClass getModel();

  /**
   * Returns the meta object for the containment reference list '{@link fr.inria.atlanmod.json.Model#getObjects <em>Objects</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Objects</em>'.
   * @see fr.inria.atlanmod.json.Model#getObjects()
   * @see #getModel()
   * @generated
   */
  EReference getModel_Objects();

  /**
   * Returns the meta object for class '{@link fr.inria.atlanmod.json.JsonObject <em>Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Object</em>'.
   * @see fr.inria.atlanmod.json.JsonObject
   * @generated
   */
  EClass getJsonObject();

  /**
   * Returns the meta object for the containment reference list '{@link fr.inria.atlanmod.json.JsonObject#getPairs <em>Pairs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Pairs</em>'.
   * @see fr.inria.atlanmod.json.JsonObject#getPairs()
   * @see #getJsonObject()
   * @generated
   */
  EReference getJsonObject_Pairs();

  /**
   * Returns the meta object for class '{@link fr.inria.atlanmod.json.Pair <em>Pair</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Pair</em>'.
   * @see fr.inria.atlanmod.json.Pair
   * @generated
   */
  EClass getPair();

  /**
   * Returns the meta object for the attribute '{@link fr.inria.atlanmod.json.Pair#getString <em>String</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>String</em>'.
   * @see fr.inria.atlanmod.json.Pair#getString()
   * @see #getPair()
   * @generated
   */
  EAttribute getPair_String();

  /**
   * Returns the meta object for the containment reference '{@link fr.inria.atlanmod.json.Pair#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.inria.atlanmod.json.Pair#getValue()
   * @see #getPair()
   * @generated
   */
  EReference getPair_Value();

  /**
   * Returns the meta object for class '{@link fr.inria.atlanmod.json.Value <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Value</em>'.
   * @see fr.inria.atlanmod.json.Value
   * @generated
   */
  EClass getValue();

  /**
   * Returns the meta object for class '{@link fr.inria.atlanmod.json.StringValue <em>String Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>String Value</em>'.
   * @see fr.inria.atlanmod.json.StringValue
   * @generated
   */
  EClass getStringValue();

  /**
   * Returns the meta object for the attribute '{@link fr.inria.atlanmod.json.StringValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see fr.inria.atlanmod.json.StringValue#getValue()
   * @see #getStringValue()
   * @generated
   */
  EAttribute getStringValue_Value();

  /**
   * Returns the meta object for class '{@link fr.inria.atlanmod.json.NumberValue <em>Number Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Number Value</em>'.
   * @see fr.inria.atlanmod.json.NumberValue
   * @generated
   */
  EClass getNumberValue();

  /**
   * Returns the meta object for the attribute '{@link fr.inria.atlanmod.json.NumberValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see fr.inria.atlanmod.json.NumberValue#getValue()
   * @see #getNumberValue()
   * @generated
   */
  EAttribute getNumberValue_Value();

  /**
   * Returns the meta object for class '{@link fr.inria.atlanmod.json.JsonObjectValue <em>Object Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Object Value</em>'.
   * @see fr.inria.atlanmod.json.JsonObjectValue
   * @generated
   */
  EClass getJsonObjectValue();

  /**
   * Returns the meta object for the containment reference '{@link fr.inria.atlanmod.json.JsonObjectValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.inria.atlanmod.json.JsonObjectValue#getValue()
   * @see #getJsonObjectValue()
   * @generated
   */
  EReference getJsonObjectValue_Value();

  /**
   * Returns the meta object for class '{@link fr.inria.atlanmod.json.ArrayValue <em>Array Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Array Value</em>'.
   * @see fr.inria.atlanmod.json.ArrayValue
   * @generated
   */
  EClass getArrayValue();

  /**
   * Returns the meta object for the containment reference list '{@link fr.inria.atlanmod.json.ArrayValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Value</em>'.
   * @see fr.inria.atlanmod.json.ArrayValue#getValue()
   * @see #getArrayValue()
   * @generated
   */
  EReference getArrayValue_Value();

  /**
   * Returns the meta object for class '{@link fr.inria.atlanmod.json.BooleanValue <em>Boolean Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Boolean Value</em>'.
   * @see fr.inria.atlanmod.json.BooleanValue
   * @generated
   */
  EClass getBooleanValue();

  /**
   * Returns the meta object for the attribute '{@link fr.inria.atlanmod.json.BooleanValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see fr.inria.atlanmod.json.BooleanValue#getValue()
   * @see #getBooleanValue()
   * @generated
   */
  EAttribute getBooleanValue_Value();

  /**
   * Returns the meta object for class '{@link fr.inria.atlanmod.json.NullValue <em>Null Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Null Value</em>'.
   * @see fr.inria.atlanmod.json.NullValue
   * @generated
   */
  EClass getNullValue();

  /**
   * Returns the meta object for the attribute '{@link fr.inria.atlanmod.json.NullValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see fr.inria.atlanmod.json.NullValue#getValue()
   * @see #getNullValue()
   * @generated
   */
  EAttribute getNullValue_Value();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  JsonFactory getJsonFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link fr.inria.atlanmod.json.impl.ModelImpl <em>Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.inria.atlanmod.json.impl.ModelImpl
     * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getModel()
     * @generated
     */
    EClass MODEL = eINSTANCE.getModel();

    /**
     * The meta object literal for the '<em><b>Objects</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL__OBJECTS = eINSTANCE.getModel_Objects();

    /**
     * The meta object literal for the '{@link fr.inria.atlanmod.json.impl.JsonObjectImpl <em>Object</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.inria.atlanmod.json.impl.JsonObjectImpl
     * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getJsonObject()
     * @generated
     */
    EClass JSON_OBJECT = eINSTANCE.getJsonObject();

    /**
     * The meta object literal for the '<em><b>Pairs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JSON_OBJECT__PAIRS = eINSTANCE.getJsonObject_Pairs();

    /**
     * The meta object literal for the '{@link fr.inria.atlanmod.json.impl.PairImpl <em>Pair</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.inria.atlanmod.json.impl.PairImpl
     * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getPair()
     * @generated
     */
    EClass PAIR = eINSTANCE.getPair();

    /**
     * The meta object literal for the '<em><b>String</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PAIR__STRING = eINSTANCE.getPair_String();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PAIR__VALUE = eINSTANCE.getPair_Value();

    /**
     * The meta object literal for the '{@link fr.inria.atlanmod.json.impl.ValueImpl <em>Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.inria.atlanmod.json.impl.ValueImpl
     * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getValue()
     * @generated
     */
    EClass VALUE = eINSTANCE.getValue();

    /**
     * The meta object literal for the '{@link fr.inria.atlanmod.json.impl.StringValueImpl <em>String Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.inria.atlanmod.json.impl.StringValueImpl
     * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getStringValue()
     * @generated
     */
    EClass STRING_VALUE = eINSTANCE.getStringValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STRING_VALUE__VALUE = eINSTANCE.getStringValue_Value();

    /**
     * The meta object literal for the '{@link fr.inria.atlanmod.json.impl.NumberValueImpl <em>Number Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.inria.atlanmod.json.impl.NumberValueImpl
     * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getNumberValue()
     * @generated
     */
    EClass NUMBER_VALUE = eINSTANCE.getNumberValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NUMBER_VALUE__VALUE = eINSTANCE.getNumberValue_Value();

    /**
     * The meta object literal for the '{@link fr.inria.atlanmod.json.impl.JsonObjectValueImpl <em>Object Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.inria.atlanmod.json.impl.JsonObjectValueImpl
     * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getJsonObjectValue()
     * @generated
     */
    EClass JSON_OBJECT_VALUE = eINSTANCE.getJsonObjectValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JSON_OBJECT_VALUE__VALUE = eINSTANCE.getJsonObjectValue_Value();

    /**
     * The meta object literal for the '{@link fr.inria.atlanmod.json.impl.ArrayValueImpl <em>Array Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.inria.atlanmod.json.impl.ArrayValueImpl
     * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getArrayValue()
     * @generated
     */
    EClass ARRAY_VALUE = eINSTANCE.getArrayValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ARRAY_VALUE__VALUE = eINSTANCE.getArrayValue_Value();

    /**
     * The meta object literal for the '{@link fr.inria.atlanmod.json.impl.BooleanValueImpl <em>Boolean Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.inria.atlanmod.json.impl.BooleanValueImpl
     * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getBooleanValue()
     * @generated
     */
    EClass BOOLEAN_VALUE = eINSTANCE.getBooleanValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BOOLEAN_VALUE__VALUE = eINSTANCE.getBooleanValue_Value();

    /**
     * The meta object literal for the '{@link fr.inria.atlanmod.json.impl.NullValueImpl <em>Null Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.inria.atlanmod.json.impl.NullValueImpl
     * @see fr.inria.atlanmod.json.impl.JsonPackageImpl#getNullValue()
     * @generated
     */
    EClass NULL_VALUE = eINSTANCE.getNullValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NULL_VALUE__VALUE = eINSTANCE.getNullValue_Value();

  }

} //JsonPackage
