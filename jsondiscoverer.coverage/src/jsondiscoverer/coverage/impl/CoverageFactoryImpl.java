/**
 */
package jsondiscoverer.coverage.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import jsondiscoverer.coverage.*;

/**
 * An implementation of the model <b>Factory</b>.
 * @generated
 */
public class CoverageFactoryImpl extends EFactoryImpl implements CoverageFactory {
	/**
	 * Creates the default factory implementation.
	 * 
	 * @return The {@link CoverageFactory}
	 * @generated
	 */
	public static CoverageFactory init() {
		try {
			CoverageFactory theCoverageFactory = (CoverageFactory)EPackage.Registry.INSTANCE.getEFactory(CoveragePackage.eNS_URI);
			if (theCoverageFactory != null) {
				return theCoverageFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CoverageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * 
	 * @generated
	 */
	public CoverageFactoryImpl() {
		super();
	}

	/**
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CoveragePackage.COVERAGE: return createCoverage();
			case CoveragePackage.ATT_MAPPING: return createAttMapping();
			case CoveragePackage.REF_MAPPING: return createRefMapping();
			case CoveragePackage.CONCEPT_MAPPING: return createConceptMapping();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * @generated
	 */
	public Coverage createCoverage() {
		CoverageImpl coverage = new CoverageImpl();
		return coverage;
	}

	/**
	 * @generated
	 */
	public AttMapping createAttMapping() {
		AttMappingImpl attMapping = new AttMappingImpl();
		return attMapping;
	}

	/**
	 * @generated
	 */
	public RefMapping createRefMapping() {
		RefMappingImpl refMapping = new RefMappingImpl();
		return refMapping;
	}

	/**
	 * @generated
	 */
	public ConceptMapping createConceptMapping() {
		ConceptMappingImpl conceptMapping = new ConceptMappingImpl();
		return conceptMapping;
	}

	/**
	 * @generated
	 */
	public CoveragePackage getCoveragePackage() {
		return (CoveragePackage)getEPackage();
	}

	/**
	 * @deprecated
	 * @return The {@link CoveragePackage}
	 * @generated
	 */
	@Deprecated
	public static CoveragePackage getPackage() {
		return CoveragePackage.eINSTANCE;
	}

} //CoverageFactoryImpl
