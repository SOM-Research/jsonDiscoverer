/**
 */
package jsondiscoverer.coverage.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import jsondiscoverer.coverage.Coverage;
import jsondiscoverer.coverage.CoverageMapping;
import jsondiscoverer.coverage.CoveragePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Coverage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link jsondiscoverer.coverage.impl.CoverageImpl#getMappings <em>Mappings</em>}</li>
 *   <li>{@link jsondiscoverer.coverage.impl.CoverageImpl#getInput <em>Input</em>}</li>
 *   <li>{@link jsondiscoverer.coverage.impl.CoverageImpl#getService <em>Service</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CoverageImpl extends EObjectImpl implements Coverage {
	/**
	 * The cached value of the '{@link #getMappings() <em>Mappings</em>}' containment reference list.
	 * 
	 * @see #getMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<CoverageMapping> mappings;

	/**
	 * The cached value of the '{@link #getInput() <em>Input</em>}' reference.
	 * 
	 * @see #getInput()
	 * @generated
	 * @ordered
	 */
	protected EAttribute input;

	/**
	 * The default value of the '{@link #getService() <em>Service</em>}' attribute.
	 * 
	 * @see #getService()
	 * @generated
	 * @ordered
	 */
	protected static final String SERVICE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getService() <em>Service</em>}' attribute.
	 * 
	 * @see #getService()
	 * @generated
	 * @ordered
	 */
	protected String service = SERVICE_EDEFAULT;

	/**
	 * @generated
	 */
	protected CoverageImpl() {
		super();
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoveragePackage.Literals.COVERAGE;
	}

	/**
	 * @generated
	 */
	public EList<CoverageMapping> getMappings() {
		if (mappings == null) {
			mappings = new EObjectContainmentEList<CoverageMapping>(CoverageMapping.class, this, CoveragePackage.COVERAGE__MAPPINGS);
		}
		return mappings;
	}

	/**
	 * @generated
	 */
	public EAttribute getInput() {
		if (input != null && input.eIsProxy()) {
			InternalEObject oldInput = (InternalEObject)input;
			input = (EAttribute)eResolveProxy(oldInput);
			if (input != oldInput) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CoveragePackage.COVERAGE__INPUT, oldInput, input));
			}
		}
		return input;
	}

	/**
	 * @return The {@link EAttribute}
	 * @generated
	 */
	public EAttribute basicGetInput() {
		return input;
	}

	/**
	 * @generated
	 */
	public void setInput(EAttribute newInput) {
		EAttribute oldInput = input;
		input = newInput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE__INPUT, oldInput, input));
	}

	/**
	 * @generated
	 */
	public String getService() {
		return service;
	}

	/**
	 * @generated
	 */
	public void setService(String newService) {
		String oldService = service;
		service = newService;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE__SERVICE, oldService, service));
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CoveragePackage.COVERAGE__MAPPINGS:
				return ((InternalEList<?>)getMappings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoveragePackage.COVERAGE__MAPPINGS:
				return getMappings();
			case CoveragePackage.COVERAGE__INPUT:
				if (resolve) return getInput();
				return basicGetInput();
			case CoveragePackage.COVERAGE__SERVICE:
				return getService();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CoveragePackage.COVERAGE__MAPPINGS:
				getMappings().clear();
				getMappings().addAll((Collection<? extends CoverageMapping>)newValue);
				return;
			case CoveragePackage.COVERAGE__INPUT:
				setInput((EAttribute)newValue);
				return;
			case CoveragePackage.COVERAGE__SERVICE:
				setService((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CoveragePackage.COVERAGE__MAPPINGS:
				getMappings().clear();
				return;
			case CoveragePackage.COVERAGE__INPUT:
				setInput((EAttribute)null);
				return;
			case CoveragePackage.COVERAGE__SERVICE:
				setService(SERVICE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CoveragePackage.COVERAGE__MAPPINGS:
				return mappings != null && !mappings.isEmpty();
			case CoveragePackage.COVERAGE__INPUT:
				return input != null;
			case CoveragePackage.COVERAGE__SERVICE:
				return SERVICE_EDEFAULT == null ? service != null : !SERVICE_EDEFAULT.equals(service);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (service: ");
		result.append(service);
		result.append(')');
		return result.toString();
	}

} //CoverageImpl
