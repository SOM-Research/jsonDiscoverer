package fr.inria.atlanmod.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import fr.inria.atlanmod.json.ArrayValue;
import fr.inria.atlanmod.json.BooleanValue;
import fr.inria.atlanmod.json.JsonObject;
import fr.inria.atlanmod.json.JsonObjectValue;
import fr.inria.atlanmod.json.JsonPackage;
import fr.inria.atlanmod.json.Model;
import fr.inria.atlanmod.json.NullValue;
import fr.inria.atlanmod.json.NumberValue;
import fr.inria.atlanmod.json.Pair;
import fr.inria.atlanmod.json.StringValue;
import fr.inria.atlanmod.services.JsonGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class JsonSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private JsonGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == JsonPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case JsonPackage.ARRAY_VALUE:
				if(context == grammarAccess.getArrayValueRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_ArrayValue(context, (ArrayValue) semanticObject); 
					return; 
				}
				else break;
			case JsonPackage.BOOLEAN_VALUE:
				if(context == grammarAccess.getBooleanValueRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_BooleanValue(context, (BooleanValue) semanticObject); 
					return; 
				}
				else break;
			case JsonPackage.JSON_OBJECT:
				if(context == grammarAccess.getJsonObjectRule()) {
					sequence_JsonObject(context, (JsonObject) semanticObject); 
					return; 
				}
				else break;
			case JsonPackage.JSON_OBJECT_VALUE:
				if(context == grammarAccess.getJsonObjectValueRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_JsonObjectValue(context, (JsonObjectValue) semanticObject); 
					return; 
				}
				else break;
			case JsonPackage.MODEL:
				if(context == grammarAccess.getModelRule()) {
					sequence_Model(context, (Model) semanticObject); 
					return; 
				}
				else break;
			case JsonPackage.NULL_VALUE:
				if(context == grammarAccess.getNullValueRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_NullValue(context, (NullValue) semanticObject); 
					return; 
				}
				else break;
			case JsonPackage.NUMBER_VALUE:
				if(context == grammarAccess.getNumberValueRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_NumberValue(context, (NumberValue) semanticObject); 
					return; 
				}
				else break;
			case JsonPackage.PAIR:
				if(context == grammarAccess.getPairRule()) {
					sequence_Pair(context, (Pair) semanticObject); 
					return; 
				}
				else break;
			case JsonPackage.STRING_VALUE:
				if(context == grammarAccess.getStringValueRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_StringValue(context, (StringValue) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (value+=Value value+=Value*)
	 */
	protected void sequence_ArrayValue(EObject context, ArrayValue semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=BOOL
	 */
	protected void sequence_BooleanValue(EObject context, BooleanValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JsonPackage.Literals.BOOLEAN_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JsonPackage.Literals.BOOLEAN_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBooleanValueAccess().getValueBOOLParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=JsonObject
	 */
	protected void sequence_JsonObjectValue(EObject context, JsonObjectValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JsonPackage.Literals.JSON_OBJECT_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JsonPackage.Literals.JSON_OBJECT_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJsonObjectValueAccess().getValueJsonObjectParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (pairs+=Pair pairs+=Pair*)
	 */
	protected void sequence_JsonObject(EObject context, JsonObject semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (objects+=JsonObject | (objects+=JsonObject objects+=JsonObject*))
	 */
	protected void sequence_Model(EObject context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=NULL
	 */
	protected void sequence_NullValue(EObject context, NullValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JsonPackage.Literals.NULL_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JsonPackage.Literals.NULL_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNullValueAccess().getValueNULLParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=INT
	 */
	protected void sequence_NumberValue(EObject context, NumberValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JsonPackage.Literals.NUMBER_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JsonPackage.Literals.NUMBER_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNumberValueAccess().getValueINTTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (string=STRING value=Value)
	 */
	protected void sequence_Pair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JsonPackage.Literals.PAIR__STRING) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JsonPackage.Literals.PAIR__STRING));
			if(transientValues.isValueTransient(semanticObject, JsonPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JsonPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPairAccess().getStringSTRINGTerminalRuleCall_0_0(), semanticObject.getString());
		feeder.accept(grammarAccess.getPairAccess().getValueValueParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=STRING
	 */
	protected void sequence_StringValue(EObject context, StringValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JsonPackage.Literals.STRING_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JsonPackage.Literals.STRING_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getStringValueAccess().getValueSTRINGTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
}
