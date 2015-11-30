/**
 */
package jsondiscoverer.coverage.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import jsondiscoverer.coverage.*;

/**
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * @see jsondiscoverer.coverage.CoveragePackage
 * @generated
 */
public class CoverageAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * 
	 * @generated
	 */
	protected static CoveragePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * 
	 * @generated
	 */
	public CoverageAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CoveragePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * 
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * 
	 * @generated
	 */
	protected CoverageSwitch<Adapter> modelSwitch =
		new CoverageSwitch<Adapter>() {
			@Override
			public Adapter caseCoverage(Coverage object) {
				return createCoverageAdapter();
			}
			@Override
			public Adapter caseCoverageMapping(CoverageMapping object) {
				return createCoverageMappingAdapter();
			}
			@Override
			public Adapter caseAttMapping(AttMapping object) {
				return createAttMappingAdapter();
			}
			@Override
			public Adapter caseRefMapping(RefMapping object) {
				return createRefMappingAdapter();
			}
			@Override
			public Adapter caseConceptMapping(ConceptMapping object) {
				return createConceptMappingAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * 
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link jsondiscoverer.coverage.Coverage <em>Coverage</em>}'.
	 * 
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * 
	 * @return the new adapter.
	 * @see jsondiscoverer.coverage.Coverage
	 * @generated
	 */
	public Adapter createCoverageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jsondiscoverer.coverage.CoverageMapping <em>Mapping</em>}'.
	 * 
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * 
	 * @return the new adapter.
	 * @see jsondiscoverer.coverage.CoverageMapping
	 * @generated
	 */
	public Adapter createCoverageMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jsondiscoverer.coverage.AttMapping <em>Att Mapping</em>}'.
	 * 
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * 
	 * @return the new adapter.
	 * @see jsondiscoverer.coverage.AttMapping
	 * @generated
	 */
	public Adapter createAttMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jsondiscoverer.coverage.RefMapping <em>Ref Mapping</em>}'.
	 * 
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * 
	 * @return the new adapter.
	 * @see jsondiscoverer.coverage.RefMapping
	 * @generated
	 */
	public Adapter createRefMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jsondiscoverer.coverage.ConceptMapping <em>Concept Mapping</em>}'.
	 * 
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * 
	 * @return the new adapter.
	 * @see jsondiscoverer.coverage.ConceptMapping
	 * @generated
	 */
	public Adapter createConceptMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * 
	 * This default implementation returns null.
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //CoverageAdapterFactory
