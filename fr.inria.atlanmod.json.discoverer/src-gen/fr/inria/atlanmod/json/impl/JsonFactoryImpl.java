/**
 */
package fr.inria.atlanmod.json.impl;

import fr.inria.atlanmod.json.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JsonFactoryImpl extends EFactoryImpl implements JsonFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static JsonFactory init()
  {
    try
    {
      JsonFactory theJsonFactory = (JsonFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.inria.fr/atlanmod/Json"); 
      if (theJsonFactory != null)
      {
        return theJsonFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new JsonFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case JsonPackage.MODEL: return createModel();
      case JsonPackage.JSON_OBJECT: return createJsonObject();
      case JsonPackage.PAIR: return createPair();
      case JsonPackage.VALUE: return createValue();
      case JsonPackage.STRING_VALUE: return createStringValue();
      case JsonPackage.NUMBER_VALUE: return createNumberValue();
      case JsonPackage.JSON_OBJECT_VALUE: return createJsonObjectValue();
      case JsonPackage.ARRAY_VALUE: return createArrayValue();
      case JsonPackage.BOOLEAN_VALUE: return createBooleanValue();
      case JsonPackage.NULL_VALUE: return createNullValue();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Model createModel()
  {
    ModelImpl model = new ModelImpl();
    return model;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonObject createJsonObject()
  {
    JsonObjectImpl jsonObject = new JsonObjectImpl();
    return jsonObject;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Pair createPair()
  {
    PairImpl pair = new PairImpl();
    return pair;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Value createValue()
  {
    ValueImpl value = new ValueImpl();
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StringValue createStringValue()
  {
    StringValueImpl stringValue = new StringValueImpl();
    return stringValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NumberValue createNumberValue()
  {
    NumberValueImpl numberValue = new NumberValueImpl();
    return numberValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonObjectValue createJsonObjectValue()
  {
    JsonObjectValueImpl jsonObjectValue = new JsonObjectValueImpl();
    return jsonObjectValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ArrayValue createArrayValue()
  {
    ArrayValueImpl arrayValue = new ArrayValueImpl();
    return arrayValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BooleanValue createBooleanValue()
  {
    BooleanValueImpl booleanValue = new BooleanValueImpl();
    return booleanValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NullValue createNullValue()
  {
    NullValueImpl nullValue = new NullValueImpl();
    return nullValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonPackage getJsonPackage()
  {
    return (JsonPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static JsonPackage getPackage()
  {
    return JsonPackage.eINSTANCE;
  }

} //JsonFactoryImpl
