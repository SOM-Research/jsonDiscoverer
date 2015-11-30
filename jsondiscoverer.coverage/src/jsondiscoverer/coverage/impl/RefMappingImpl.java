/**
 */
package jsondiscoverer.coverage.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import jsondiscoverer.coverage.CoveragePackage;
import jsondiscoverer.coverage.RefMapping;

/**
 * An implementation of the model object '<em><b>Ref Mapping</b></em>'.
 * <p>The following features are implemented:</p>
 * <ul>
 *   <li>{@link jsondiscoverer.coverage.impl.RefMappingImpl#getSource <em>Source</em>}</li>
 *   <li>{@link jsondiscoverer.coverage.impl.RefMappingImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RefMappingImpl extends CoverageMappingImpl implements RefMapping {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * 
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected EReference source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * 
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EReference target;

	/**
	 * @generated
	 */
	protected RefMappingImpl() {
		super();
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoveragePackage.Literals.REF_MAPPING;
	}

	/**
	 * @generated
	 */
	public EReference getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (EReference)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CoveragePackage.REF_MAPPING__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * @return The {@link EReference}
	 * @generated
	 */
	public EReference basicGetSource() {
		return source;
	}

	/**
	 * @generated
	 */
	public void setSource(EReference newSource) {
		EReference oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.REF_MAPPING__SOURCE, oldSource, source));
	}

	/**
	 * @generated
	 */
	public EReference getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (EReference)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CoveragePackage.REF_MAPPING__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * @return The {@link EReference}
	 * @generated
	 */
	public EReference basicGetTarget() {
		return target;
	}

	/**
	 * @generated
	 */
	public void setTarget(EReference newTarget) {
		EReference oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.REF_MAPPING__TARGET, oldTarget, target));
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoveragePackage.REF_MAPPING__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case CoveragePackage.REF_MAPPING__TARGET:
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
			case CoveragePackage.REF_MAPPING__SOURCE:
				setSource((EReference)newValue);
				return;
			case CoveragePackage.REF_MAPPING__TARGET:
				setTarget((EReference)newValue);
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
			case CoveragePackage.REF_MAPPING__SOURCE:
				setSource((EReference)null);
				return;
			case CoveragePackage.REF_MAPPING__TARGET:
				setTarget((EReference)null);
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
			case CoveragePackage.REF_MAPPING__SOURCE:
				return source != null;
			case CoveragePackage.REF_MAPPING__TARGET:
				return target != null;
		}
		return super.eIsSet(featureID);
	}

} //RefMappingImpl
