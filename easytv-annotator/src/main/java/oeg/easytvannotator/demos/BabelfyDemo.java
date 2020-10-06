/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.demos;

import it.uniroma1.lcl.babelfy.commons.BabelfyConstraints;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.MCS;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.ScoredCandidates;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.SemanticAnnotationResource;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation.Source;
import it.uniroma1.lcl.babelfy.commons.annotation.TokenOffsetFragment;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.jlt.util.Language;
import java.util.List;
import oeg.easytvannotator.babelnet.BabelLangInterface;

/**
 *
 * @author pcalleja
 */
public class BabelfyDemo {
    
       public static void main(String[] args) {

        String inputText = "calore";
        BabelfyConstraints constraints = new BabelfyConstraints();
        SemanticAnnotation a = new SemanticAnnotation(new TokenOffsetFragment(0, 0), 
                "bn:03083790n","http://dbpedia.org/resource/BabelNet", Source.BABELFY);
        constraints.addAnnotatedFragments(a);
        BabelfyParameters bp = new BabelfyParameters();
        bp.setAnnotationResource(SemanticAnnotationResource.BN);
        bp.setMCS(MCS.ON_WITH_STOPWORDS);
        bp.setScoredCandidates(ScoredCandidates.TOP);
        Babelfy bfy = new Babelfy(bp);
        List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(inputText, Language.IT, constraints);
        
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
       
       
       public static void example(String s, String lang){
       
         String inputText = s;
         Language Lang= BabelLangInterface.getLangType(lang);
        BabelfyConstraints constraints = new BabelfyConstraints();
        SemanticAnnotation a = new SemanticAnnotation(new TokenOffsetFragment(0, 0), 
                "bn:03083790n","http://dbpedia.org/resource/BabelNet", Source.OTHER);
        constraints.addAnnotatedFragments(a);
        BabelfyParameters bp = new BabelfyParameters();
        bp.setAnnotationResource(SemanticAnnotationResource.BN);
        bp.setMCS(MCS.ON_WITH_STOPWORDS);
        bp.setScoredCandidates(ScoredCandidates.ALL);
        Babelfy bfy = new Babelfy(bp);
        List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(inputText, Lang, constraints);
        
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
