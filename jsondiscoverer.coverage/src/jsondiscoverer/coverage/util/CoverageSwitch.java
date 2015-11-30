/**
 */
package jsondiscoverer.coverage.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import jsondiscoverer.coverage.*;

/**
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * 
 * @see jsondiscoverer.coverage.CoveragePackage
 * @generated
 */
public class CoverageSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * 
	 * @generated
	 */
	protected static CoveragePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * 
	 * @generated
	 */
	public CoverageSwitch() {
		if (modelPackage == null) {
			modelPackage = CoveragePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * 
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case CoveragePackage.COVERAGE: {
				Coverage coverage = (Coverage)theEObject;
				T result = caseCoverage(coverage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CoveragePackage.COVERAGE_MAPPING: {
				CoverageMapping coverageMapping = (CoverageMapping)theEObject;
				T result = caseCoverageMapping(coverageMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CoveragePackage.ATT_MAPPING: {
				AttMapping attMapping = (AttMapping)theEObject;
				T result = caseAttMapping(attMapping);
				if (result == null) result = caseCoverageMapping(attMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CoveragePackage.REF_MAPPING: {
				RefMapping refMapping = (RefMapping)theEObject;
				T result = caseRefMapping(refMapping);
				if (result == null) result = caseCoverageMapping(refMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CoveragePackage.CONCEPT_MAPPING: {
				ConceptMapping conceptMapping = (ConceptMapping)theEObject;
				T result = caseConceptMapping(conceptMapping);
				if (result == null) result = caseCoverageMapping(conceptMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Coverage</em>'.
	 * 
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Coverage</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCoverage(Coverage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mapping</em>'.
	 * 
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCoverageMapping(CoverageMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Att Mapping</em>'.
	 * 
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Att Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttMapping(AttMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ref Mapping</em>'.
	 * 
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ref Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRefMapping(RefMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Concept Mapping</em>'.
	 * 
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Concept Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConceptMapping(ConceptMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * 
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //CoverageSwitch
