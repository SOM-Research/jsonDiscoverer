package fr.inria.atlanmod.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import fr.inria.atlanmod.services.JsonGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalJsonParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_INT", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'{'", "'}'", "'['", "']'", "':'", "','", "'true'", "'false'", "'null'"
    };
    public static final int T__19=19;
    public static final int RULE_ID=6;
    public static final int RULE_STRING=4;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_ANY_OTHER=10;
    public static final int RULE_INT=5;
    public static final int RULE_WS=9;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;

    // delegates
    // delegators


        public InternalJsonParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalJsonParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalJsonParser.tokenNames; }
    public String getGrammarFileName() { return "../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g"; }



     	private JsonGrammarAccess grammarAccess;
     	
        public InternalJsonParser(TokenStream input, JsonGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Model";	
       	}
       	
       	@Override
       	protected JsonGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleModel"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:67:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:68:2: (iv_ruleModel= ruleModel EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:69:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_ruleModel_in_entryRuleModel75);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModel85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:76:1: ruleModel returns [EObject current=null] : ( ( (lv_objects_0_0= ruleJsonObject ) ) | ( ruleLSQUARE ( (lv_objects_2_0= ruleJsonObject ) ) ( ruleCOMMA ( (lv_objects_4_0= ruleJsonObject ) ) )* ruleRSQUARE ) ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_objects_0_0 = null;

        EObject lv_objects_2_0 = null;

        EObject lv_objects_4_0 = null;


         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:79:28: ( ( ( (lv_objects_0_0= ruleJsonObject ) ) | ( ruleLSQUARE ( (lv_objects_2_0= ruleJsonObject ) ) ( ruleCOMMA ( (lv_objects_4_0= ruleJsonObject ) ) )* ruleRSQUARE ) ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:80:1: ( ( (lv_objects_0_0= ruleJsonObject ) ) | ( ruleLSQUARE ( (lv_objects_2_0= ruleJsonObject ) ) ( ruleCOMMA ( (lv_objects_4_0= ruleJsonObject ) ) )* ruleRSQUARE ) )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:80:1: ( ( (lv_objects_0_0= ruleJsonObject ) ) | ( ruleLSQUARE ( (lv_objects_2_0= ruleJsonObject ) ) ( ruleCOMMA ( (lv_objects_4_0= ruleJsonObject ) ) )* ruleRSQUARE ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==11) ) {
                alt2=1;
            }
            else if ( (LA2_0==13) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:80:2: ( (lv_objects_0_0= ruleJsonObject ) )
                    {
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:80:2: ( (lv_objects_0_0= ruleJsonObject ) )
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:81:1: (lv_objects_0_0= ruleJsonObject )
                    {
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:81:1: (lv_objects_0_0= ruleJsonObject )
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:82:3: lv_objects_0_0= ruleJsonObject
                    {
                     
                    	        newCompositeNode(grammarAccess.getModelAccess().getObjectsJsonObjectParserRuleCall_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleJsonObject_in_ruleModel131);
                    lv_objects_0_0=ruleJsonObject();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getModelRule());
                    	        }
                           		add(
                           			current, 
                           			"objects",
                            		lv_objects_0_0, 
                            		"JsonObject");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:99:6: ( ruleLSQUARE ( (lv_objects_2_0= ruleJsonObject ) ) ( ruleCOMMA ( (lv_objects_4_0= ruleJsonObject ) ) )* ruleRSQUARE )
                    {
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:99:6: ( ruleLSQUARE ( (lv_objects_2_0= ruleJsonObject ) ) ( ruleCOMMA ( (lv_objects_4_0= ruleJsonObject ) ) )* ruleRSQUARE )
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:100:5: ruleLSQUARE ( (lv_objects_2_0= ruleJsonObject ) ) ( ruleCOMMA ( (lv_objects_4_0= ruleJsonObject ) ) )* ruleRSQUARE
                    {
                     
                            newCompositeNode(grammarAccess.getModelAccess().getLSQUAREParserRuleCall_1_0()); 
                        
                    pushFollow(FOLLOW_ruleLSQUARE_in_ruleModel154);
                    ruleLSQUARE();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:107:1: ( (lv_objects_2_0= ruleJsonObject ) )
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:108:1: (lv_objects_2_0= ruleJsonObject )
                    {
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:108:1: (lv_objects_2_0= ruleJsonObject )
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:109:3: lv_objects_2_0= ruleJsonObject
                    {
                     
                    	        newCompositeNode(grammarAccess.getModelAccess().getObjectsJsonObjectParserRuleCall_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleJsonObject_in_ruleModel174);
                    lv_objects_2_0=ruleJsonObject();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getModelRule());
                    	        }
                           		add(
                           			current, 
                           			"objects",
                            		lv_objects_2_0, 
                            		"JsonObject");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:125:2: ( ruleCOMMA ( (lv_objects_4_0= ruleJsonObject ) ) )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==16) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:126:5: ruleCOMMA ( (lv_objects_4_0= ruleJsonObject ) )
                    	    {
                    	     
                    	            newCompositeNode(grammarAccess.getModelAccess().getCOMMAParserRuleCall_1_2_0()); 
                    	        
                    	    pushFollow(FOLLOW_ruleCOMMA_in_ruleModel191);
                    	    ruleCOMMA();

                    	    state._fsp--;

                    	     
                    	            afterParserOrEnumRuleCall();
                    	        
                    	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:133:1: ( (lv_objects_4_0= ruleJsonObject ) )
                    	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:134:1: (lv_objects_4_0= ruleJsonObject )
                    	    {
                    	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:134:1: (lv_objects_4_0= ruleJsonObject )
                    	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:135:3: lv_objects_4_0= ruleJsonObject
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getModelAccess().getObjectsJsonObjectParserRuleCall_1_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleJsonObject_in_ruleModel211);
                    	    lv_objects_4_0=ruleJsonObject();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getModelRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"objects",
                    	            		lv_objects_4_0, 
                    	            		"JsonObject");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                     
                            newCompositeNode(grammarAccess.getModelAccess().getRSQUAREParserRuleCall_1_3()); 
                        
                    pushFollow(FOLLOW_ruleRSQUARE_in_ruleModel229);
                    ruleRSQUARE();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleJsonObject"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:167:1: entryRuleJsonObject returns [EObject current=null] : iv_ruleJsonObject= ruleJsonObject EOF ;
    public final EObject entryRuleJsonObject() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonObject = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:168:2: (iv_ruleJsonObject= ruleJsonObject EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:169:2: iv_ruleJsonObject= ruleJsonObject EOF
            {
             newCompositeNode(grammarAccess.getJsonObjectRule()); 
            pushFollow(FOLLOW_ruleJsonObject_in_entryRuleJsonObject265);
            iv_ruleJsonObject=ruleJsonObject();

            state._fsp--;

             current =iv_ruleJsonObject; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonObject275); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonObject"


    // $ANTLR start "ruleJsonObject"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:176:1: ruleJsonObject returns [EObject current=null] : ( ruleLCURLY ( (lv_pairs_1_0= rulePair ) ) ( ruleCOMMA ( (lv_pairs_3_0= rulePair ) ) )* ruleRCURLY ) ;
    public final EObject ruleJsonObject() throws RecognitionException {
        EObject current = null;

        EObject lv_pairs_1_0 = null;

        EObject lv_pairs_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:179:28: ( ( ruleLCURLY ( (lv_pairs_1_0= rulePair ) ) ( ruleCOMMA ( (lv_pairs_3_0= rulePair ) ) )* ruleRCURLY ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:180:1: ( ruleLCURLY ( (lv_pairs_1_0= rulePair ) ) ( ruleCOMMA ( (lv_pairs_3_0= rulePair ) ) )* ruleRCURLY )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:180:1: ( ruleLCURLY ( (lv_pairs_1_0= rulePair ) ) ( ruleCOMMA ( (lv_pairs_3_0= rulePair ) ) )* ruleRCURLY )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:181:5: ruleLCURLY ( (lv_pairs_1_0= rulePair ) ) ( ruleCOMMA ( (lv_pairs_3_0= rulePair ) ) )* ruleRCURLY
            {
             
                    newCompositeNode(grammarAccess.getJsonObjectAccess().getLCURLYParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleLCURLY_in_ruleJsonObject316);
            ruleLCURLY();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:188:1: ( (lv_pairs_1_0= rulePair ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:189:1: (lv_pairs_1_0= rulePair )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:189:1: (lv_pairs_1_0= rulePair )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:190:3: lv_pairs_1_0= rulePair
            {
             
            	        newCompositeNode(grammarAccess.getJsonObjectAccess().getPairsPairParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_rulePair_in_ruleJsonObject336);
            lv_pairs_1_0=rulePair();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getJsonObjectRule());
            	        }
                   		add(
                   			current, 
                   			"pairs",
                    		lv_pairs_1_0, 
                    		"Pair");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:206:2: ( ruleCOMMA ( (lv_pairs_3_0= rulePair ) ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==16) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:207:5: ruleCOMMA ( (lv_pairs_3_0= rulePair ) )
            	    {
            	     
            	            newCompositeNode(grammarAccess.getJsonObjectAccess().getCOMMAParserRuleCall_2_0()); 
            	        
            	    pushFollow(FOLLOW_ruleCOMMA_in_ruleJsonObject353);
            	    ruleCOMMA();

            	    state._fsp--;

            	     
            	            afterParserOrEnumRuleCall();
            	        
            	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:214:1: ( (lv_pairs_3_0= rulePair ) )
            	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:215:1: (lv_pairs_3_0= rulePair )
            	    {
            	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:215:1: (lv_pairs_3_0= rulePair )
            	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:216:3: lv_pairs_3_0= rulePair
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getJsonObjectAccess().getPairsPairParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePair_in_ruleJsonObject373);
            	    lv_pairs_3_0=rulePair();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getJsonObjectRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"pairs",
            	            		lv_pairs_3_0, 
            	            		"Pair");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

             
                    newCompositeNode(grammarAccess.getJsonObjectAccess().getRCURLYParserRuleCall_3()); 
                
            pushFollow(FOLLOW_ruleRCURLY_in_ruleJsonObject391);
            ruleRCURLY();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonObject"


    // $ANTLR start "entryRulePair"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:248:1: entryRulePair returns [EObject current=null] : iv_rulePair= rulePair EOF ;
    public final EObject entryRulePair() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePair = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:249:2: (iv_rulePair= rulePair EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:250:2: iv_rulePair= rulePair EOF
            {
             newCompositeNode(grammarAccess.getPairRule()); 
            pushFollow(FOLLOW_rulePair_in_entryRulePair426);
            iv_rulePair=rulePair();

            state._fsp--;

             current =iv_rulePair; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePair436); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePair"


    // $ANTLR start "rulePair"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:257:1: rulePair returns [EObject current=null] : ( ( (lv_string_0_0= RULE_STRING ) ) ruleCOLON ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject rulePair() throws RecognitionException {
        EObject current = null;

        Token lv_string_0_0=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:260:28: ( ( ( (lv_string_0_0= RULE_STRING ) ) ruleCOLON ( (lv_value_2_0= ruleValue ) ) ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:261:1: ( ( (lv_string_0_0= RULE_STRING ) ) ruleCOLON ( (lv_value_2_0= ruleValue ) ) )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:261:1: ( ( (lv_string_0_0= RULE_STRING ) ) ruleCOLON ( (lv_value_2_0= ruleValue ) ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:261:2: ( (lv_string_0_0= RULE_STRING ) ) ruleCOLON ( (lv_value_2_0= ruleValue ) )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:261:2: ( (lv_string_0_0= RULE_STRING ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:262:1: (lv_string_0_0= RULE_STRING )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:262:1: (lv_string_0_0= RULE_STRING )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:263:3: lv_string_0_0= RULE_STRING
            {
            lv_string_0_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rulePair478); 

            			newLeafNode(lv_string_0_0, grammarAccess.getPairAccess().getStringSTRINGTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPairRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"string",
                    		lv_string_0_0, 
                    		"STRING");
            	    

            }


            }

             
                    newCompositeNode(grammarAccess.getPairAccess().getCOLONParserRuleCall_1()); 
                
            pushFollow(FOLLOW_ruleCOLON_in_rulePair499);
            ruleCOLON();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:287:1: ( (lv_value_2_0= ruleValue ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:288:1: (lv_value_2_0= ruleValue )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:288:1: (lv_value_2_0= ruleValue )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:289:3: lv_value_2_0= ruleValue
            {
             
            	        newCompositeNode(grammarAccess.getPairAccess().getValueValueParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleValue_in_rulePair519);
            lv_value_2_0=ruleValue();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePair"


    // $ANTLR start "entryRuleValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:313:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:314:2: (iv_ruleValue= ruleValue EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:315:2: iv_ruleValue= ruleValue EOF
            {
             newCompositeNode(grammarAccess.getValueRule()); 
            pushFollow(FOLLOW_ruleValue_in_entryRuleValue555);
            iv_ruleValue=ruleValue();

            state._fsp--;

             current =iv_ruleValue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleValue565); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:322:1: ruleValue returns [EObject current=null] : (this_StringValue_0= ruleStringValue | this_NumberValue_1= ruleNumberValue | this_JsonObjectValue_2= ruleJsonObjectValue | this_ArrayValue_3= ruleArrayValue | this_BooleanValue_4= ruleBooleanValue | this_NullValue_5= ruleNullValue ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        EObject this_StringValue_0 = null;

        EObject this_NumberValue_1 = null;

        EObject this_JsonObjectValue_2 = null;

        EObject this_ArrayValue_3 = null;

        EObject this_BooleanValue_4 = null;

        EObject this_NullValue_5 = null;


         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:325:28: ( (this_StringValue_0= ruleStringValue | this_NumberValue_1= ruleNumberValue | this_JsonObjectValue_2= ruleJsonObjectValue | this_ArrayValue_3= ruleArrayValue | this_BooleanValue_4= ruleBooleanValue | this_NullValue_5= ruleNullValue ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:326:1: (this_StringValue_0= ruleStringValue | this_NumberValue_1= ruleNumberValue | this_JsonObjectValue_2= ruleJsonObjectValue | this_ArrayValue_3= ruleArrayValue | this_BooleanValue_4= ruleBooleanValue | this_NullValue_5= ruleNullValue )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:326:1: (this_StringValue_0= ruleStringValue | this_NumberValue_1= ruleNumberValue | this_JsonObjectValue_2= ruleJsonObjectValue | this_ArrayValue_3= ruleArrayValue | this_BooleanValue_4= ruleBooleanValue | this_NullValue_5= ruleNullValue )
            int alt4=6;
            switch ( input.LA(1) ) {
            case RULE_STRING:
                {
                alt4=1;
                }
                break;
            case RULE_INT:
                {
                alt4=2;
                }
                break;
            case 11:
                {
                alt4=3;
                }
                break;
            case 13:
                {
                alt4=4;
                }
                break;
            case 17:
            case 18:
                {
                alt4=5;
                }
                break;
            case 19:
                {
                alt4=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:327:5: this_StringValue_0= ruleStringValue
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getStringValueParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleStringValue_in_ruleValue612);
                    this_StringValue_0=ruleStringValue();

                    state._fsp--;

                     
                            current = this_StringValue_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:337:5: this_NumberValue_1= ruleNumberValue
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getNumberValueParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleNumberValue_in_ruleValue639);
                    this_NumberValue_1=ruleNumberValue();

                    state._fsp--;

                     
                            current = this_NumberValue_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:347:5: this_JsonObjectValue_2= ruleJsonObjectValue
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getJsonObjectValueParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleJsonObjectValue_in_ruleValue666);
                    this_JsonObjectValue_2=ruleJsonObjectValue();

                    state._fsp--;

                     
                            current = this_JsonObjectValue_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:357:5: this_ArrayValue_3= ruleArrayValue
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getArrayValueParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleArrayValue_in_ruleValue693);
                    this_ArrayValue_3=ruleArrayValue();

                    state._fsp--;

                     
                            current = this_ArrayValue_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:367:5: this_BooleanValue_4= ruleBooleanValue
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getBooleanValueParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_ruleBooleanValue_in_ruleValue720);
                    this_BooleanValue_4=ruleBooleanValue();

                    state._fsp--;

                     
                            current = this_BooleanValue_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:377:5: this_NullValue_5= ruleNullValue
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getNullValueParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleNullValue_in_ruleValue747);
                    this_NullValue_5=ruleNullValue();

                    state._fsp--;

                     
                            current = this_NullValue_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleStringValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:393:1: entryRuleStringValue returns [EObject current=null] : iv_ruleStringValue= ruleStringValue EOF ;
    public final EObject entryRuleStringValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringValue = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:394:2: (iv_ruleStringValue= ruleStringValue EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:395:2: iv_ruleStringValue= ruleStringValue EOF
            {
             newCompositeNode(grammarAccess.getStringValueRule()); 
            pushFollow(FOLLOW_ruleStringValue_in_entryRuleStringValue782);
            iv_ruleStringValue=ruleStringValue();

            state._fsp--;

             current =iv_ruleStringValue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleStringValue792); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStringValue"


    // $ANTLR start "ruleStringValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:402:1: ruleStringValue returns [EObject current=null] : ( (lv_value_0_0= RULE_STRING ) ) ;
    public final EObject ruleStringValue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:405:28: ( ( (lv_value_0_0= RULE_STRING ) ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:406:1: ( (lv_value_0_0= RULE_STRING ) )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:406:1: ( (lv_value_0_0= RULE_STRING ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:407:1: (lv_value_0_0= RULE_STRING )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:407:1: (lv_value_0_0= RULE_STRING )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:408:3: lv_value_0_0= RULE_STRING
            {
            lv_value_0_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleStringValue833); 

            			newLeafNode(lv_value_0_0, grammarAccess.getStringValueAccess().getValueSTRINGTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getStringValueRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"STRING");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStringValue"


    // $ANTLR start "entryRuleNumberValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:432:1: entryRuleNumberValue returns [EObject current=null] : iv_ruleNumberValue= ruleNumberValue EOF ;
    public final EObject entryRuleNumberValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumberValue = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:433:2: (iv_ruleNumberValue= ruleNumberValue EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:434:2: iv_ruleNumberValue= ruleNumberValue EOF
            {
             newCompositeNode(grammarAccess.getNumberValueRule()); 
            pushFollow(FOLLOW_ruleNumberValue_in_entryRuleNumberValue873);
            iv_ruleNumberValue=ruleNumberValue();

            state._fsp--;

             current =iv_ruleNumberValue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNumberValue883); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNumberValue"


    // $ANTLR start "ruleNumberValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:441:1: ruleNumberValue returns [EObject current=null] : ( (lv_value_0_0= RULE_INT ) ) ;
    public final EObject ruleNumberValue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:444:28: ( ( (lv_value_0_0= RULE_INT ) ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:445:1: ( (lv_value_0_0= RULE_INT ) )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:445:1: ( (lv_value_0_0= RULE_INT ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:446:1: (lv_value_0_0= RULE_INT )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:446:1: (lv_value_0_0= RULE_INT )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:447:3: lv_value_0_0= RULE_INT
            {
            lv_value_0_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleNumberValue924); 

            			newLeafNode(lv_value_0_0, grammarAccess.getNumberValueAccess().getValueINTTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getNumberValueRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"INT");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNumberValue"


    // $ANTLR start "entryRuleJsonObjectValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:471:1: entryRuleJsonObjectValue returns [EObject current=null] : iv_ruleJsonObjectValue= ruleJsonObjectValue EOF ;
    public final EObject entryRuleJsonObjectValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonObjectValue = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:472:2: (iv_ruleJsonObjectValue= ruleJsonObjectValue EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:473:2: iv_ruleJsonObjectValue= ruleJsonObjectValue EOF
            {
             newCompositeNode(grammarAccess.getJsonObjectValueRule()); 
            pushFollow(FOLLOW_ruleJsonObjectValue_in_entryRuleJsonObjectValue964);
            iv_ruleJsonObjectValue=ruleJsonObjectValue();

            state._fsp--;

             current =iv_ruleJsonObjectValue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonObjectValue974); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonObjectValue"


    // $ANTLR start "ruleJsonObjectValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:480:1: ruleJsonObjectValue returns [EObject current=null] : ( (lv_value_0_0= ruleJsonObject ) ) ;
    public final EObject ruleJsonObjectValue() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:483:28: ( ( (lv_value_0_0= ruleJsonObject ) ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:484:1: ( (lv_value_0_0= ruleJsonObject ) )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:484:1: ( (lv_value_0_0= ruleJsonObject ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:485:1: (lv_value_0_0= ruleJsonObject )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:485:1: (lv_value_0_0= ruleJsonObject )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:486:3: lv_value_0_0= ruleJsonObject
            {
             
            	        newCompositeNode(grammarAccess.getJsonObjectValueAccess().getValueJsonObjectParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleJsonObject_in_ruleJsonObjectValue1019);
            lv_value_0_0=ruleJsonObject();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getJsonObjectValueRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"JsonObject");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonObjectValue"


    // $ANTLR start "entryRuleArrayValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:510:1: entryRuleArrayValue returns [EObject current=null] : iv_ruleArrayValue= ruleArrayValue EOF ;
    public final EObject entryRuleArrayValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayValue = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:511:2: (iv_ruleArrayValue= ruleArrayValue EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:512:2: iv_ruleArrayValue= ruleArrayValue EOF
            {
             newCompositeNode(grammarAccess.getArrayValueRule()); 
            pushFollow(FOLLOW_ruleArrayValue_in_entryRuleArrayValue1054);
            iv_ruleArrayValue=ruleArrayValue();

            state._fsp--;

             current =iv_ruleArrayValue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleArrayValue1064); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArrayValue"


    // $ANTLR start "ruleArrayValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:519:1: ruleArrayValue returns [EObject current=null] : ( ruleLSQUARE ( (lv_value_1_0= ruleValue ) ) ( ruleCOMMA ( (lv_value_3_0= ruleValue ) ) )* ruleRSQUARE ) ;
    public final EObject ruleArrayValue() throws RecognitionException {
        EObject current = null;

        EObject lv_value_1_0 = null;

        EObject lv_value_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:522:28: ( ( ruleLSQUARE ( (lv_value_1_0= ruleValue ) ) ( ruleCOMMA ( (lv_value_3_0= ruleValue ) ) )* ruleRSQUARE ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:523:1: ( ruleLSQUARE ( (lv_value_1_0= ruleValue ) ) ( ruleCOMMA ( (lv_value_3_0= ruleValue ) ) )* ruleRSQUARE )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:523:1: ( ruleLSQUARE ( (lv_value_1_0= ruleValue ) ) ( ruleCOMMA ( (lv_value_3_0= ruleValue ) ) )* ruleRSQUARE )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:524:5: ruleLSQUARE ( (lv_value_1_0= ruleValue ) ) ( ruleCOMMA ( (lv_value_3_0= ruleValue ) ) )* ruleRSQUARE
            {
             
                    newCompositeNode(grammarAccess.getArrayValueAccess().getLSQUAREParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleLSQUARE_in_ruleArrayValue1105);
            ruleLSQUARE();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:531:1: ( (lv_value_1_0= ruleValue ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:532:1: (lv_value_1_0= ruleValue )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:532:1: (lv_value_1_0= ruleValue )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:533:3: lv_value_1_0= ruleValue
            {
             
            	        newCompositeNode(grammarAccess.getArrayValueAccess().getValueValueParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleValue_in_ruleArrayValue1125);
            lv_value_1_0=ruleValue();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getArrayValueRule());
            	        }
                   		add(
                   			current, 
                   			"value",
                    		lv_value_1_0, 
                    		"Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:549:2: ( ruleCOMMA ( (lv_value_3_0= ruleValue ) ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==16) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:550:5: ruleCOMMA ( (lv_value_3_0= ruleValue ) )
            	    {
            	     
            	            newCompositeNode(grammarAccess.getArrayValueAccess().getCOMMAParserRuleCall_2_0()); 
            	        
            	    pushFollow(FOLLOW_ruleCOMMA_in_ruleArrayValue1142);
            	    ruleCOMMA();

            	    state._fsp--;

            	     
            	            afterParserOrEnumRuleCall();
            	        
            	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:557:1: ( (lv_value_3_0= ruleValue ) )
            	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:558:1: (lv_value_3_0= ruleValue )
            	    {
            	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:558:1: (lv_value_3_0= ruleValue )
            	    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:559:3: lv_value_3_0= ruleValue
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getArrayValueAccess().getValueValueParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleValue_in_ruleArrayValue1162);
            	    lv_value_3_0=ruleValue();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getArrayValueRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"value",
            	            		lv_value_3_0, 
            	            		"Value");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

             
                    newCompositeNode(grammarAccess.getArrayValueAccess().getRSQUAREParserRuleCall_3()); 
                
            pushFollow(FOLLOW_ruleRSQUARE_in_ruleArrayValue1180);
            ruleRSQUARE();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArrayValue"


    // $ANTLR start "entryRuleBooleanValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:591:1: entryRuleBooleanValue returns [EObject current=null] : iv_ruleBooleanValue= ruleBooleanValue EOF ;
    public final EObject entryRuleBooleanValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBooleanValue = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:592:2: (iv_ruleBooleanValue= ruleBooleanValue EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:593:2: iv_ruleBooleanValue= ruleBooleanValue EOF
            {
             newCompositeNode(grammarAccess.getBooleanValueRule()); 
            pushFollow(FOLLOW_ruleBooleanValue_in_entryRuleBooleanValue1215);
            iv_ruleBooleanValue=ruleBooleanValue();

            state._fsp--;

             current =iv_ruleBooleanValue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBooleanValue1225); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBooleanValue"


    // $ANTLR start "ruleBooleanValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:600:1: ruleBooleanValue returns [EObject current=null] : ( (lv_value_0_0= ruleBOOL ) ) ;
    public final EObject ruleBooleanValue() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:603:28: ( ( (lv_value_0_0= ruleBOOL ) ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:604:1: ( (lv_value_0_0= ruleBOOL ) )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:604:1: ( (lv_value_0_0= ruleBOOL ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:605:1: (lv_value_0_0= ruleBOOL )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:605:1: (lv_value_0_0= ruleBOOL )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:606:3: lv_value_0_0= ruleBOOL
            {
             
            	        newCompositeNode(grammarAccess.getBooleanValueAccess().getValueBOOLParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleBOOL_in_ruleBooleanValue1270);
            lv_value_0_0=ruleBOOL();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getBooleanValueRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"BOOL");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBooleanValue"


    // $ANTLR start "entryRuleNullValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:630:1: entryRuleNullValue returns [EObject current=null] : iv_ruleNullValue= ruleNullValue EOF ;
    public final EObject entryRuleNullValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNullValue = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:631:2: (iv_ruleNullValue= ruleNullValue EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:632:2: iv_ruleNullValue= ruleNullValue EOF
            {
             newCompositeNode(grammarAccess.getNullValueRule()); 
            pushFollow(FOLLOW_ruleNullValue_in_entryRuleNullValue1305);
            iv_ruleNullValue=ruleNullValue();

            state._fsp--;

             current =iv_ruleNullValue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNullValue1315); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNullValue"


    // $ANTLR start "ruleNullValue"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:639:1: ruleNullValue returns [EObject current=null] : ( (lv_value_0_0= ruleNULL ) ) ;
    public final EObject ruleNullValue() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:642:28: ( ( (lv_value_0_0= ruleNULL ) ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:643:1: ( (lv_value_0_0= ruleNULL ) )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:643:1: ( (lv_value_0_0= ruleNULL ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:644:1: (lv_value_0_0= ruleNULL )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:644:1: (lv_value_0_0= ruleNULL )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:645:3: lv_value_0_0= ruleNULL
            {
             
            	        newCompositeNode(grammarAccess.getNullValueAccess().getValueNULLParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleNULL_in_ruleNullValue1360);
            lv_value_0_0=ruleNULL();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getNullValueRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"NULL");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNullValue"


    // $ANTLR start "entryRuleLCURLY"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:669:1: entryRuleLCURLY returns [String current=null] : iv_ruleLCURLY= ruleLCURLY EOF ;
    public final String entryRuleLCURLY() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLCURLY = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:670:2: (iv_ruleLCURLY= ruleLCURLY EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:671:2: iv_ruleLCURLY= ruleLCURLY EOF
            {
             newCompositeNode(grammarAccess.getLCURLYRule()); 
            pushFollow(FOLLOW_ruleLCURLY_in_entryRuleLCURLY1396);
            iv_ruleLCURLY=ruleLCURLY();

            state._fsp--;

             current =iv_ruleLCURLY.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLCURLY1407); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLCURLY"


    // $ANTLR start "ruleLCURLY"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:678:1: ruleLCURLY returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= '{' ;
    public final AntlrDatatypeRuleToken ruleLCURLY() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:681:28: (kw= '{' )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:683:2: kw= '{'
            {
            kw=(Token)match(input,11,FOLLOW_11_in_ruleLCURLY1444); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getLCURLYAccess().getLeftCurlyBracketKeyword()); 
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLCURLY"


    // $ANTLR start "entryRuleRCURLY"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:696:1: entryRuleRCURLY returns [String current=null] : iv_ruleRCURLY= ruleRCURLY EOF ;
    public final String entryRuleRCURLY() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleRCURLY = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:697:2: (iv_ruleRCURLY= ruleRCURLY EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:698:2: iv_ruleRCURLY= ruleRCURLY EOF
            {
             newCompositeNode(grammarAccess.getRCURLYRule()); 
            pushFollow(FOLLOW_ruleRCURLY_in_entryRuleRCURLY1484);
            iv_ruleRCURLY=ruleRCURLY();

            state._fsp--;

             current =iv_ruleRCURLY.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRCURLY1495); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRCURLY"


    // $ANTLR start "ruleRCURLY"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:705:1: ruleRCURLY returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= '}' ;
    public final AntlrDatatypeRuleToken ruleRCURLY() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:708:28: (kw= '}' )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:710:2: kw= '}'
            {
            kw=(Token)match(input,12,FOLLOW_12_in_ruleRCURLY1532); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getRCURLYAccess().getRightCurlyBracketKeyword()); 
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRCURLY"


    // $ANTLR start "entryRuleLSQUARE"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:723:1: entryRuleLSQUARE returns [String current=null] : iv_ruleLSQUARE= ruleLSQUARE EOF ;
    public final String entryRuleLSQUARE() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLSQUARE = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:724:2: (iv_ruleLSQUARE= ruleLSQUARE EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:725:2: iv_ruleLSQUARE= ruleLSQUARE EOF
            {
             newCompositeNode(grammarAccess.getLSQUARERule()); 
            pushFollow(FOLLOW_ruleLSQUARE_in_entryRuleLSQUARE1572);
            iv_ruleLSQUARE=ruleLSQUARE();

            state._fsp--;

             current =iv_ruleLSQUARE.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLSQUARE1583); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLSQUARE"


    // $ANTLR start "ruleLSQUARE"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:732:1: ruleLSQUARE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= '[' ;
    public final AntlrDatatypeRuleToken ruleLSQUARE() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:735:28: (kw= '[' )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:737:2: kw= '['
            {
            kw=(Token)match(input,13,FOLLOW_13_in_ruleLSQUARE1620); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getLSQUAREAccess().getLeftSquareBracketKeyword()); 
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLSQUARE"


    // $ANTLR start "entryRuleRSQUARE"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:750:1: entryRuleRSQUARE returns [String current=null] : iv_ruleRSQUARE= ruleRSQUARE EOF ;
    public final String entryRuleRSQUARE() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleRSQUARE = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:751:2: (iv_ruleRSQUARE= ruleRSQUARE EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:752:2: iv_ruleRSQUARE= ruleRSQUARE EOF
            {
             newCompositeNode(grammarAccess.getRSQUARERule()); 
            pushFollow(FOLLOW_ruleRSQUARE_in_entryRuleRSQUARE1660);
            iv_ruleRSQUARE=ruleRSQUARE();

            state._fsp--;

             current =iv_ruleRSQUARE.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRSQUARE1671); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRSQUARE"


    // $ANTLR start "ruleRSQUARE"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:759:1: ruleRSQUARE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= ']' ;
    public final AntlrDatatypeRuleToken ruleRSQUARE() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:762:28: (kw= ']' )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:764:2: kw= ']'
            {
            kw=(Token)match(input,14,FOLLOW_14_in_ruleRSQUARE1708); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getRSQUAREAccess().getRightSquareBracketKeyword()); 
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRSQUARE"


    // $ANTLR start "entryRuleCOLON"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:777:1: entryRuleCOLON returns [String current=null] : iv_ruleCOLON= ruleCOLON EOF ;
    public final String entryRuleCOLON() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCOLON = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:778:2: (iv_ruleCOLON= ruleCOLON EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:779:2: iv_ruleCOLON= ruleCOLON EOF
            {
             newCompositeNode(grammarAccess.getCOLONRule()); 
            pushFollow(FOLLOW_ruleCOLON_in_entryRuleCOLON1748);
            iv_ruleCOLON=ruleCOLON();

            state._fsp--;

             current =iv_ruleCOLON.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCOLON1759); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCOLON"


    // $ANTLR start "ruleCOLON"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:786:1: ruleCOLON returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= ':' ;
    public final AntlrDatatypeRuleToken ruleCOLON() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:789:28: (kw= ':' )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:791:2: kw= ':'
            {
            kw=(Token)match(input,15,FOLLOW_15_in_ruleCOLON1796); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getCOLONAccess().getColonKeyword()); 
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCOLON"


    // $ANTLR start "entryRuleCOMMA"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:804:1: entryRuleCOMMA returns [String current=null] : iv_ruleCOMMA= ruleCOMMA EOF ;
    public final String entryRuleCOMMA() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCOMMA = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:805:2: (iv_ruleCOMMA= ruleCOMMA EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:806:2: iv_ruleCOMMA= ruleCOMMA EOF
            {
             newCompositeNode(grammarAccess.getCOMMARule()); 
            pushFollow(FOLLOW_ruleCOMMA_in_entryRuleCOMMA1836);
            iv_ruleCOMMA=ruleCOMMA();

            state._fsp--;

             current =iv_ruleCOMMA.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCOMMA1847); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCOMMA"


    // $ANTLR start "ruleCOMMA"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:813:1: ruleCOMMA returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= ',' ;
    public final AntlrDatatypeRuleToken ruleCOMMA() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:816:28: (kw= ',' )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:818:2: kw= ','
            {
            kw=(Token)match(input,16,FOLLOW_16_in_ruleCOMMA1884); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getCOMMAAccess().getCommaKeyword()); 
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCOMMA"


    // $ANTLR start "entryRuleBOOL"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:831:1: entryRuleBOOL returns [String current=null] : iv_ruleBOOL= ruleBOOL EOF ;
    public final String entryRuleBOOL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOL = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:832:2: (iv_ruleBOOL= ruleBOOL EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:833:2: iv_ruleBOOL= ruleBOOL EOF
            {
             newCompositeNode(grammarAccess.getBOOLRule()); 
            pushFollow(FOLLOW_ruleBOOL_in_entryRuleBOOL1924);
            iv_ruleBOOL=ruleBOOL();

            state._fsp--;

             current =iv_ruleBOOL.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBOOL1935); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBOOL"


    // $ANTLR start "ruleBOOL"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:840:1: ruleBOOL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBOOL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:843:28: ( (kw= 'true' | kw= 'false' ) )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:844:1: (kw= 'true' | kw= 'false' )
            {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:844:1: (kw= 'true' | kw= 'false' )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==17) ) {
                alt6=1;
            }
            else if ( (LA6_0==18) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:845:2: kw= 'true'
                    {
                    kw=(Token)match(input,17,FOLLOW_17_in_ruleBOOL1973); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getBOOLAccess().getTrueKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:852:2: kw= 'false'
                    {
                    kw=(Token)match(input,18,FOLLOW_18_in_ruleBOOL1992); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getBOOLAccess().getFalseKeyword_1()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBOOL"


    // $ANTLR start "entryRuleNULL"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:865:1: entryRuleNULL returns [String current=null] : iv_ruleNULL= ruleNULL EOF ;
    public final String entryRuleNULL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNULL = null;


        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:866:2: (iv_ruleNULL= ruleNULL EOF )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:867:2: iv_ruleNULL= ruleNULL EOF
            {
             newCompositeNode(grammarAccess.getNULLRule()); 
            pushFollow(FOLLOW_ruleNULL_in_entryRuleNULL2033);
            iv_ruleNULL=ruleNULL();

            state._fsp--;

             current =iv_ruleNULL.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNULL2044); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNULL"


    // $ANTLR start "ruleNULL"
    // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:874:1: ruleNULL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'null' ;
    public final AntlrDatatypeRuleToken ruleNULL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:877:28: (kw= 'null' )
            // ../fr.inria.atlanmod.json.discoverer/src-gen/fr/inria/atlanmod/parser/antlr/internal/InternalJson.g:879:2: kw= 'null'
            {
            kw=(Token)match(input,19,FOLLOW_19_in_ruleNULL2081); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getNULLAccess().getNullKeyword()); 
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNULL"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleModel_in_entryRuleModel75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModel85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonObject_in_ruleModel131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLSQUARE_in_ruleModel154 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ruleJsonObject_in_ruleModel174 = new BitSet(new long[]{0x0000000000014000L});
    public static final BitSet FOLLOW_ruleCOMMA_in_ruleModel191 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ruleJsonObject_in_ruleModel211 = new BitSet(new long[]{0x0000000000014000L});
    public static final BitSet FOLLOW_ruleRSQUARE_in_ruleModel229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonObject_in_entryRuleJsonObject265 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonObject275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLCURLY_in_ruleJsonObject316 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulePair_in_ruleJsonObject336 = new BitSet(new long[]{0x0000000000011000L});
    public static final BitSet FOLLOW_ruleCOMMA_in_ruleJsonObject353 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulePair_in_ruleJsonObject373 = new BitSet(new long[]{0x0000000000011000L});
    public static final BitSet FOLLOW_ruleRCURLY_in_ruleJsonObject391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePair_in_entryRulePair426 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePair436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rulePair478 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ruleCOLON_in_rulePair499 = new BitSet(new long[]{0x00000000000E2830L});
    public static final BitSet FOLLOW_ruleValue_in_rulePair519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValue_in_entryRuleValue555 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleValue565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStringValue_in_ruleValue612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNumberValue_in_ruleValue639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonObjectValue_in_ruleValue666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayValue_in_ruleValue693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBooleanValue_in_ruleValue720 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNullValue_in_ruleValue747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStringValue_in_entryRuleStringValue782 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStringValue792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleStringValue833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNumberValue_in_entryRuleNumberValue873 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNumberValue883 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleNumberValue924 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonObjectValue_in_entryRuleJsonObjectValue964 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonObjectValue974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonObject_in_ruleJsonObjectValue1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayValue_in_entryRuleArrayValue1054 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArrayValue1064 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLSQUARE_in_ruleArrayValue1105 = new BitSet(new long[]{0x00000000000E2830L});
    public static final BitSet FOLLOW_ruleValue_in_ruleArrayValue1125 = new BitSet(new long[]{0x0000000000014000L});
    public static final BitSet FOLLOW_ruleCOMMA_in_ruleArrayValue1142 = new BitSet(new long[]{0x00000000000E2830L});
    public static final BitSet FOLLOW_ruleValue_in_ruleArrayValue1162 = new BitSet(new long[]{0x0000000000014000L});
    public static final BitSet FOLLOW_ruleRSQUARE_in_ruleArrayValue1180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBooleanValue_in_entryRuleBooleanValue1215 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBooleanValue1225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBOOL_in_ruleBooleanValue1270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNullValue_in_entryRuleNullValue1305 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNullValue1315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNULL_in_ruleNullValue1360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLCURLY_in_entryRuleLCURLY1396 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLCURLY1407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleLCURLY1444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRCURLY_in_entryRuleRCURLY1484 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRCURLY1495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_ruleRCURLY1532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLSQUARE_in_entryRuleLSQUARE1572 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLSQUARE1583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_ruleLSQUARE1620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRSQUARE_in_entryRuleRSQUARE1660 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRSQUARE1671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleRSQUARE1708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCOLON_in_entryRuleCOLON1748 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCOLON1759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleCOLON1796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCOMMA_in_entryRuleCOMMA1836 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCOMMA1847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleCOMMA1884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBOOL_in_entryRuleBOOL1924 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBOOL1935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleBOOL1973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleBOOL1992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNULL_in_entryRuleNULL2033 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNULL2044 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleNULL2081 = new BitSet(new long[]{0x0000000000000002L});

}