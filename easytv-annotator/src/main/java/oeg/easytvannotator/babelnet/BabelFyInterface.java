/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.babelnet;

import it.uniroma1.lcl.babelfy.commons.BabelfyConstraints;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters;
import it.uniroma1.lcl.babelfy.commons.BabelfyToken;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelfy.commons.annotation.TokenOffsetFragment;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.jlt.util.Language;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EToken;

/**
 *
 * @author pcalleja
 */
public class BabelFyInterface {
    
     
   
    public static void callBabelFly(ESentence ESentence, String Language) {
      
        // BABELFLY original
         BabelFyInterface.callBabelFlyOriginalWords(ESentence, Language);
        // BABELFLY Lemma
        BabelFyInterface.callBabelFlyLemmatized(ESentence, Language);
        
        
    }

  
     public static void callBabelFlyLemmatized(ESentence sentence, String lang) {

        
         Language langu= BabelLangInterface.getLangType(lang);
         
         List<BabelfyToken> tokenizedInput = new ArrayList<>();
         for (EToken tok : sentence.ListTokens)
            tokenizedInput.add(new BabelfyToken(tok.Lemma, langu));
         
        
         
        BabelfyConstraints constraints = new BabelfyConstraints();
        SemanticAnnotation a = new SemanticAnnotation(new TokenOffsetFragment(0, 0), "bn:03083790n",
                "http://dbpedia.org/resource/BabelNet", SemanticAnnotation.Source.OTHER);
        constraints.addAnnotatedFragments(a);
        BabelfyParameters bp = new BabelfyParameters();
        bp.setAnnotationResource(BabelfyParameters.SemanticAnnotationResource.BN);
        bp.setMCS(BabelfyParameters.MCS.ON_WITH_STOPWORDS);
        bp.setScoredCandidates(BabelfyParameters.ScoredCandidates.TOP); ///.ALL
        Babelfy bfy = new Babelfy(bp);
        List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(tokenizedInput, langu, constraints);

        Collections.sort(bfyAnnotations);
        
        for (SemanticAnnotation annotation : bfyAnnotations) {

            int init= annotation.getTokenOffsetFragment().getStart();
            sentence.ListTokens.get(init).LemmaBabelblySemanticAnnotations.add( annotation.getBabelSynsetID());
            
        }
       

    }
     
     
      public static void callBabelFlyOriginalWords(ESentence sentence, String lang) {

        
         Language langu= BabelLangInterface.getLangType(lang);
         
         List<BabelfyToken> tokenizedInput = new ArrayList<>();
         for (EToken tok : sentence.ListTokens)
            tokenizedInput.add(new BabelfyToken(tok.Word, langu));
         
        
         
        BabelfyConstraints constraints = new BabelfyConstraints();
        SemanticAnnotation a = new SemanticAnnotation(new TokenOffsetFragment(0, 0), "bn:03083790n",
                "http://dbpedia.org/resource/BabelNet", SemanticAnnotation.Source.OTHER);
        constraints.addAnnotatedFragments(a);
        BabelfyParameters bp = new BabelfyParameters();
        bp.setAnnotationResource(BabelfyParameters.SemanticAnnotationResource.BN);
        bp.setMCS(BabelfyParameters.MCS.ON_WITH_STOPWORDS);
        bp.setScoredCandidates(BabelfyParameters.ScoredCandidates.TOP); ///.ALL
        Babelfy bfy = new Babelfy(bp);
        List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(tokenizedInput, langu, constraints);

        Collections.sort(bfyAnnotations);
        
        for (SemanticAnnotation annotation : bfyAnnotations) {
 
            int init= annotation.getTokenOffsetFragment().getStart();

            sentence.ListTokens.get(init).WordBabelblySemanticAnnotations.add( annotation.getBabelSynsetID());
            
        }
       

        
    }
      
      
       public static void processSimpleString(String s, String lang){
       
        String inputText = s;
        Language Lang= BabelLangInterface.getLangType(lang);
        
        BabelfyParameters bp = new BabelfyParameters();
        
        bp.setAnnotationResource(BabelfyParameters.SemanticAnnotationResource.BN);
        bp.setMCS(BabelfyParameters.MCS.ON_WITH_STOPWORDS);
        //bp.setMCS(BabelfyParameters.MCS.ON);
        bp.setScoredCandidates(BabelfyParameters.ScoredCandidates.TOP);
        bp.setMatchingType(BabelfyParameters.MatchingType.PARTIAL_MATCHING);
        bp.setPoStaggingOptions(BabelfyParameters.PosTaggingOptions.STANDARD);
        bp.setAnnotationType(BabelfyParameters.SemanticAnnotationType.ALL);
        
        Babelfy bfy = new Babelfy(bp);
        List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(inputText, Lang);
        
        //bfyAnnotations is the result of Babelfy.babelfy() call
        for (SemanticAnnotation annotation : bfyAnnotations) {
            //splitting the input text using the CharOffsetFragment start and end anchors
            String frag = inputText.substring(annotation.getCharOffsetFragment().getStart(),
                    annotation.getCharOffsetFragment().getEnd() + 1);
            System.out.println(frag + "\t" + annotation.getBabelSynsetID());
            System.out.println("\t" + annotation.getBabelNetURL());
            System.out.println("\t" + annotation.getDBpediaURL());
            System.out.println("\t" + annotation.getSource());
        }
       
       
       
       }

    
}
