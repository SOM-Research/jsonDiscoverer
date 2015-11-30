/**
 */
package jsondiscoverer.coverage.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import jsondiscoverer.coverage.AttMapping;
import jsondiscoverer.coverage.CoveragePackage;

/**
 * An implementation of the model object '<em><b>Att Mapping</b></em>'.
 * <p>The following features are implemented:</p>
 * <ul>
 *   <li>{@link jsondiscoverer.coverage.impl.AttMappingImpl#getSource <em>Source</em>}</li>
 *   <li>{@link jsondiscoverer.coverage.impl.AttMappingImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttMappingImpl extends CoverageMappingImpl implements AttMapping {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * 
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected EAttribute source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * 
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EAttribute target;

	/**
	 * @see AttMapping
	 * @generated
	 */
	protected AttMappingImpl() {
		super();
	}

	/**
	 * @see CoveragePackage.Literals#ATT_MAPPING
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoveragePackage.Literals.ATT_MAPPING;
	}

	/**
	 * @see AttMapping#getSource()
	 * @generated
	 */
	public EAttribute getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (EAttribute)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CoveragePackage.ATT_MAPPING__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * @return The {@link EAttribute}
	 * @generated
	 */
	public EAttribute basicGetSource() {
		return source;
	}

	/**
	 * @generated
	 */
	public void setSource(EAttribute newSource) {
		EAttribute oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.ATT_MAPPING__SOURCE, oldSource, source));
	}

	/**
	 * @generated
	 */
	public EAttribute getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (EAttribute)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CoveragePackage.ATT_MAPPING__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * @return The {@link EAttribute}
	 * @generated
	 */
	public EAttribute basicGetTarget() {
		return target;
	}

	/**
	 * @generated
	 */
	public void setTarget(EAttribute newTarget) {
		EAttribute oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.ATT_MAPPING__TARGET, oldTarget, target));
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoveragePackage.ATT_MAPPING__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case CoveragePackage.ATT_MAPPING__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CoveragePackage.ATT_MAPPING__SOURCE:
				setSource((EAttribute)newValue);
				return;
			case CoveragePackage.ATT_MAPPING__TARGET:
				setTarget((EAttribute)newValue);
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
			case CoveragePackage.ATT_MAPPING__SOURCE:
				setSource((EAttribute)null);
				return;
			case CoveragePackage.ATT_MAPPING__TARGET:
				setTarget((EAttribute)null);
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
			case CoveragePackage.ATT_MAPPING__SOURCE:
				return source != null;
			case CoveragePackage.ATT_MAPPING__TARGET:
				return target != null;
		}
		return super.eIsSet(featureID);
	}

} //AttMappingImpl
