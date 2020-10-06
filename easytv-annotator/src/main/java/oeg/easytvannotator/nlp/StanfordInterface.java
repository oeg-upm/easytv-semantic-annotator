/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;
import oeg.easytvannotator.model.ESentence;
import org.apache.log4j.Logger;

/**
 *
 * @author pcalleja
 */
public class StanfordInterface implements NLPApi {
    
      /* 
    public static void main (String [] args){
    
    
    parseSentence("Él tiene dos títulos universitarios  y un medallón" ,"ES");
        
    
    }*/
    
    
    static Logger logger = Logger.getLogger(StanfordInterface.class);
    
    
    @Override
    public ESentence parseSentence(String Sentence, String Lang) {

        logger.info("\n\n\n");
        logger.info("***** STANFORD ************");

        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");

        String text = Sentence;
        // spanish test
        //http://data.cervantesvirtual.com/blog/2017/07/17/libreria-corenlp-de-stanford-de-procesamiento-lenguage-natural-reconocimiento-entidades/

        
        
        if (Lang.toUpperCase().equals("ES")) {
            props.setProperty("tokenize.language", "ES");
            props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger");
            props.setProperty("ner.model", "edu/stanford/nlp/models/ner/spanish.ancora.distsim.s512.crf.ser.gz");

        }

        ESentence esentence = new ESentence();

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        

        StringBuffer tokenSentence = new StringBuffer();
        StringBuffer LemmaSentence = new StringBuffer();

        for (CoreMap sentence : sentences) {

            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos =  token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String lem =  token.get(CoreAnnotations.LemmaAnnotation.class);
                String stem = token.get(CoreAnnotations.StemAnnotation.class);
                logger.info(word + "  " + pos + "  " + lem + "  " + stem);

                esentence.addEToken(word, pos, lem, stem, Lang);
                tokenSentence.append(word + " ");
                LemmaSentence.append(lem + " ");
            }
        }

        esentence.OriginalText = Sentence;
        esentence.LematizedText = LemmaSentence.toString().trim();
        return esentence;

    }
    
}
