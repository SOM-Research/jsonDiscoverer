/**
 */
package jsondiscoverer.coverage.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import jsondiscoverer.coverage.ConceptMapping;
import jsondiscoverer.coverage.CoveragePackage;

/**
 * An implementation of the model object '<em><b>Concept Mapping</b></em>'.
 * <p>The following features are implemented:</p>
 * <ul>
 *   <li>{@link jsondiscoverer.coverage.impl.ConceptMappingImpl#getSource <em>Source</em>}</li>
 *   <li>{@link jsondiscoverer.coverage.impl.ConceptMappingImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConceptMappingImpl extends CoverageMappingImpl implements ConceptMapping {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * 
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected EClass source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * 
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EClass target;

	/**
	 * @generated
	 */
	protected ConceptMappingImpl() {
		super();
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoveragePackage.Literals.CONCEPT_MAPPING;
	}

	/**
	 * @generated
	 */
	public EClass getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (EClass)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CoveragePackage.CONCEPT_MAPPING__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * @return the {@link EClass}
	 * @generated
	 */
	public EClass basicGetSource() {
		return source;
	}

	/**
	 * @generated
	 */
	public void setSource(EClass newSource) {
		EClass oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.CONCEPT_MAPPING__SOURCE, oldSource, source));
	}

	/**
	 * @generated
	 */
	public EClass getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (EClass)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CoveragePackage.CONCEPT_MAPPING__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * @return The {@link EClass}
	 * @generated
	 */
	public EClass basicGetTarget() {
		return target;
	}

	/**
	 * @generated
	 */
	public void setTarget(EClass newTarget) {
		EClass oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.CONCEPT_MAPPING__TARGET, oldTarget, target));
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoveragePackage.CONCEPT_MAPPING__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case CoveragePackage.CONCEPT_MAPPING__TARGET:
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
			case CoveragePackage.CONCEPT_MAPPING__SOURCE:
				setSource((EClass)newValue);
				return;
			case CoveragePackage.CONCEPT_MAPPING__TARGET:
				setTarget((EClass)newValue);
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
			case CoveragePackage.CONCEPT_MAPPING__SOURCE:
				setSource((EClass)null);
				return;
			case CoveragePackage.CONCEPT_MAPPING__TARGET:
				setTarget((EClass)null);
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
			case CoveragePackage.CONCEPT_MAPPING__SOURCE:
				return source != null;
			case CoveragePackage.CONCEPT_MAPPING__TARGET:
				return target != null;
		}
		return super.eIsSet(featureID);
	}

} //ConceptMappingImpl
