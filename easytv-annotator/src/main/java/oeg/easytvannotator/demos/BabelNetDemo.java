/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.demos;

import com.babelscape.util.POS;
import com.babelscape.util.UniversalPOS;
import com.google.common.collect.Multimap;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelNetQuery;
import it.uniroma1.lcl.babelnet.BabelNetUtils;
import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetComparator;
import it.uniroma1.lcl.babelnet.data.BabelSenseSource;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.util.ScoredItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import oeg.easytvannotator.babelnet.BabelNetInterface;
import oeg.easytvannotator.babelnet.BabelNetSynset;

/**
 *
 * @author pcalleja
 */
public class BabelNetDemo {
    
    public static BabelNet bnInstance;
    
    
     
    public static void main(String [] args) throws IOException{
    
 
     
     callBabelNetWordPOS("Engineering", Language.EN ,UniversalPOS.NOUN);
     
     //exampletranslation();
     //callBabelNetWord("anoche", Language.ES);
    }
    
    
    
     public static void exampletranslation() throws IOException {
    	BabelNet bn = BabelNet.getInstance();
		BabelNetQuery query = new BabelNetQuery.Builder("película")
                        .POS(UniversalPOS.NOUN)
			.from(Language.ES)
			.to(Arrays.asList(Language.ES))
			.build();
        for (BabelSynset synset : bn.getSynsets(query)) {
            System.out.println("Synset ID: " + synset.getID());
            
            System.out.println(synset.getMainSense(Language.ES));
            BabelSense s= synset.getMainSense(Language.ES).get();
            if(s!=null){
            System.out.println(s.getFullLemma()+"  "+s.getSimpleLemma());
            }
//  String a= s.getSimpleLemma();
            
        }

    }
    public static List<BabelSynset> callBabelNetWordPOS(String word, Language lang, UniversalPOS pos)  {

        System.out.println("Babelnet Call: Word-"+word+" Lang-"+lang+" POS-"+pos);
        
        
       
        List<BabelSynset> synsets =new ArrayList();
         
        try {
            if (bnInstance == null) {
                bnInstance = BabelNet.getInstance();
            }
            
            //,BabelSenseSource.WN,BabelSenseSource.WIKIDATA,BabelSenseSource.MCR_ES)

        BabelNetQuery query = new BabelNetQuery.Builder(word)
	.POS(pos)
	.from(lang)
        
                
        //.sources(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN,BabelSenseSource.WIKIDATA))
        //        .to(Arrays.asList(Language.EN))
        .build();
                
       synsets =  bnInstance.getSynsets(query);
       
       //transform(synsets,word,lang);
       
       System.out.println("Results:"+synsets.size());
       
       System.out.println("-------------------------");
        
       
       
       for (BabelSynset synset : synsets) {
           //System.out.println(synset.isKeyConcept());
           System.out.println("Key:"+synset.isKeyConcept()+";  Synset ID: " + synset.getID()+  "; TYPE: " + synset.getType()+  "  MAIN SENSE ES: " + synset.getSenses().size() );
           
           System.out.println(synset.getMainSense().get().getFullLemma());
           
        //System.out.println("Synset ID: " + synset.getID()+  "; TYPE: " + synset.getType()+  "  MAIN SENSE ES: " + synset.getMainSense(lang) + synset.getMainSense(Language.ES) +"  MAIN SENSE EN:"+synset.getMainSense(Language.EN) );
        
       }
       
       
       System.out.println("-------------------------");
       Collections.sort(synsets, new BabelSynsetComparator(word));
       for (BabelSynset synset : synsets) {
        System.out.println("Key:"+synset.isKeyConcept()+";  Synset ID: " + synset.getID()+  "; TYPE: " + synset.getType()+  "  MAIN SENSE ES: " + synset.getMainSense(lang) );
               
       
       }

         
           
        } catch (Exception ex) {
            Logger.getLogger(BabelNetInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        if(synsets.size()>4){
            return synsets.subList(0, 4);
        }
        
        return synsets;
    }
     
    
    public static void transform(List<BabelSynset>list, String word, Language lang){
    
        List <BabelNetSynset> lis= new ArrayList();
        
        for(BabelSynset sin: list){
            
            lis.add(new BabelNetSynset(sin,word,lang));
            
        
        }
        
        Collections.sort(lis);
        
        for(BabelNetSynset sin:lis){
        
                System.out.println(sin.ID +"  "+sin.SimpleLemma+sin.isKey);
        }
        
    
    }
    
    
    public static List<BabelSynset> callBabelNetWord(String word, Language lang)  {

        System.out.println("Babelnet Call: Word-"+word+" Lang-"+lang);
        
        
       
        List<BabelSynset> synsets =new ArrayList();
         
        try {
            if (bnInstance == null) {
                bnInstance = BabelNet.getInstance();
            }
            
            //,BabelSenseSource.WN,BabelSenseSource.WIKIDATA,BabelSenseSource.MCR_ES)

        BabelNetQuery query = new BabelNetQuery.Builder(word)
	//.POS(pos)
	.from(lang)
                
        //.sources(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN,BabelSenseSource.WIKIDATA))
                .to(Arrays.asList(Language.EN))
        .build();
                
       synsets =  bnInstance.getSynsets(query);
       
       System.out.println("Results:"+synsets.size());
       Collections.sort(synsets, new BabelSynsetComparator(word));
       for (BabelSynset synset : synsets) {
           System.out.println(synset.isKeyConcept());
         //  System.out.println(synset.);
           System.out.println(synset.getPOS());
            System.out.println("Synset ID: " +" "+synset.toString() +" "+ synset.getID()+  "; TYPE: " + synset.getType()+  "  MAIN LEMMA: " + synset.getMainSense(lang) + synset.getMainSense(Language.ES) +" "+synset.getMainSense(Language.EN) );
        }
            
           
        } catch (Exception ex) {
            Logger.getLogger(BabelNetInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        if(synsets.size()>4){
            return synsets.subList(0, 4);
        }
        
        return synsets;
    }

     
     
    public static void testQuery() throws IOException {
    	BabelNet bn = BabelNet.getInstance();
		BabelNetQuery query = new BabelNetQuery.Builder("ser")
                        .POS(UniversalPOS.VERB)
			.from(Language.ES)
                        //.sources(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN,BabelSenseSource.WIKIDATA))
			//.to(Arrays.asList(Language.EN))
                       
			.build();
                
        List<BabelSynset> synsets =  bn.getSynsets(query);
        
       Collections.sort(synsets, new BabelSynsetComparator("ser"));
        
        //Collections.sort(synsets, new BabelSynsetComparator("book"));        
        for (BabelSynset synset : synsets) {
            System.out.println("Synset ID: " + synset.getID()+  "; TYPE: " + synset.getType()+  "  MAIN LEMMA: " + synset.getMainSense(Language.EN) + synset.getMainSense(Language.ES) );
        }

    }
    
    public static void testTra() throws IOException {
    	BabelNet bn = BabelNet.getInstance();
		BabelNetQuery query = new BabelNetQuery.Builder("book")
                        .POS(UniversalPOS.NOUN)
			.from(Language.EN)
                        //.sources(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN,BabelSenseSource.WIKIDATA))
			//.to(Arrays.asList(Language.EN))
                       
			.build();
                
        List<BabelSynset> synsets =  bn.getSynsets(query);
        
       Collections.sort(synsets, new BabelSynsetComparator("book"));
        
        //Collections.sort(synsets, new BabelSynsetComparator("book"));        
        for (BabelSynset synset : synsets) {
            System.out.println("Synset ID: " + synset.getID()+  "; TYPE: " + synset.getType()+  "  MAIN LEMMA: " + synset.getMainSense(Language.EN) + synset.getMainSense(Language.ES) );
        }

    }
    
   
    
    public static void test(){
      BabelNet bn = BabelNet.getInstance();
        BabelNetQuery query = new BabelNetQuery.Builder("car")
	.from(Language.EN)
	.build();
        List<BabelSynset> synsets = bn.getSynsets(query);
        // List<BabelSynset> synsets = bnInstance.getSynsets(word, lang, pos);
        Collections.sort(synsets, new BabelSynsetComparator("car"));
        for (BabelSynset synset : synsets) {
            System.out.print("  =>(" + synset.getID() + ") SOURCE: " + synset.getSenseSources() //get.getSynsetSource()
                    + "; TYPE: " + synset.getType()
                    + "; WN SYNSET: " + synset.getWordNetOffsets() + ";\n"
                    + "  MAIN LEMMA: " + synset.getMainSense(Language.EN)
                    + ";\n  IMAGES: " + synset.getImages()
                    + ";\n  CATEGORIES: " + synset.getCategories()
                    + ";\n  SENSES : { ");
            for (BabelSense sense : synset.getSenses(Language.EN)) {
                System.out.print(sense.toString() + " " + sense.getPronunciations() + " ");
            }
            System.out.println("}\n  -----");
        }
        
        
    
    }
    
    
    
      
    
    
    public static void callBabelNetWithStanfordParser(String Sentence, String Lang)  {
        
        System.out.println("\n\n\n");
         System.out.println("***** STANFORD ************");
        
        
        
    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");

    
     String text =Sentence;
    // spanish test
    //http://data.cervantesvirtual.com/blog/2017/07/17/libreria-corenlp-de-stanford-de-procesamiento-lenguage-natural-reconocimiento-entidades/
    
    getLangType( Lang);
    if(Lang.toUpperCase().equals("ES")){
    props.setProperty("tokenize.language", "ES");
    props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger");
    props.setProperty("ner.model", "edu/stanford/nlp/models/ner/spanish.ancora.distsim.s512.crf.ser.gz");
    
    } 
    
 
  
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    // create an empty Annotation just with the given text
    Annotation document = new Annotation(text);

    // run all Annotators on this text
    pipeline.annotate(document);

    // these are all the sentences in this document
    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
    List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

    for(CoreMap sentence: sentences) {
      // traversing the words in the current sentence
      // a CoreLabel is a CoreMap with additional token-specific methods
      for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
        // this is the text of the token
        String word = token.get(CoreAnnotations.TextAnnotation.class);
        // this is the POS tag of the token
        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
        // this is the NER label of the token
        String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
        
        System.out.println("word: " + word + " pos: " + pos + " ne:" + ne);
      }

      // this is the parse tree of the current sentence
      Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
      System.out.println("parse tree:\n" + tree);

      // this is the Stanford dependency graph of the current sentence
      SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
      System.out.println("dependency graph:\n" + dependencies);
    }

  
    
    System.out.println("\n\n\n");
    System.out.println("***** BABELNET ************");
    
    for(CoreMap sentence: sentences) {
    
      for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
      
        String word = token.get(CoreAnnotations.TextAnnotation.class);
       
        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

        
        if(pos.toUpperCase().startsWith("V")){
        callGraphicBabelNetWordPOS( word, Langua,UniversalPOS.VERB);
        }
        
        if(pos.toUpperCase().startsWith("N")){
        callGraphicBabelNetWordPOS( word, Langua,UniversalPOS.NOUN);
       
        
        }
        
     
      }

     
    }
    
    
  }
    
     private static Language Langua;
     
    public static Language getLangType(String Lang){
     
         Lang= Lang.toUpperCase();
         if (Lang.equals("EN")){
         Langua= Language.EN;
         return Langua;
         }
     
         if (Lang.equals("ES")){
         Langua= Language.ES;
         return Langua;
         }
         
         if (Lang.equals("IT")){
         Langua= Language.IT;
         return Langua;
         }
          
          Langua=Language.EN;
          return Langua;
     
     }
    
    public static void callGraphicBabelNetWordPOS(String word, Language lang, POS pos)  {

        if (bnInstance == null) {
            bnInstance = BabelNet.getInstance();
            System.out.println("entro");
        }
        System.out.println("SYNSETS WITH the word: \"" + word + "\""+ lang+"  "+ "("+pos+")");
        
        BabelNetQuery query = new BabelNetQuery.Builder(word)
	.POS(pos)
	.from(lang)
        .sources(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN,BabelSenseSource.WIKIDATA))
			//.to(Arrays.asList(Language.EN))
                       
			.build();
                
        List<BabelSynset> synsets =  bnInstance.getSynsets(query);
        
       Collections.sort(synsets, new BabelSynsetComparator(word));

        
       // List<BabelSynset> synsets = bnInstance.getSynsets(word, lang, pos);
     
        for (BabelSynset synset : synsets) {
            System.out.print("  =>(" + synset.getID() + ") SOURCE: " + synset.getSenseSources() //get.getSynsetSource()
                    + "; TYPE: " + synset.getType()
                    + "; WN SYNSET: " + synset.getWordNetOffsets() + ";\n"
                    + "  MAIN LEMMA: " + synset.getMainSense(lang)
                    + ";\n  IMAGES: " + synset.getImages()
                    + ";\n  CATEGORIES: " + synset.getCategories()
                    + ";\n  SENSES : { ");
            for (BabelSense sense : synset.getSenses(lang)) {
                System.out.print(sense.toString() + " " + sense.getPronunciations() + " ");
            }
            System.out.println("}\n  -----");
        }
    }
    
    public static void callGraphicBabelNetWord(String word, Language lang)  {

        if (bnInstance == null) {
            bnInstance = BabelNet.getInstance();
        }
        System.out.println("SYNSETS WITH the word: \"" + word + "\""+ lang+"  "+ "(NOPOS)");
        BabelNetQuery query = new BabelNetQuery.Builder(word)
	
	.from(lang)
        .sources(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN,BabelSenseSource.WIKIDATA))
			//.to(Arrays.asList(Language.EN))
                       
			.build();
                
        List<BabelSynset> synsets =  bnInstance.getSynsets(query);
           Collections.sort(synsets, new BabelSynsetComparator(word));
        for (BabelSynset synset : synsets) {
            System.out.print("  =>(" + synset.getID() + ") SOURCE: " + synset.getSenseSources()
                    + "; TYPE: " + synset.getType() //getSynsetType()
                    + "; WN SYNSET: " + synset.getWordNetOffsets() + ";\n"
                    + "  MAIN LEMMA: " + synset.getMainSense(lang)
                    + ";\n  IMAGES: " + synset.getImages()
                    + ";\n  CATEGORIES: " + synset.getCategories()
                    + ";\n  SENSES : { ");
            for (BabelSense sense : synset.getSenses(lang)) {
                System.out.print(sense.toString() + " " + sense.getPronunciations() + " ");
            }
            System.out.println("}\n  -----");
        }
    }
    
    public static void testTranslations(String lemma, Language languageToSearch,Language... languagesToPrint) throws IOException
	{
               System.out.println(languagesToPrint.length);
		List<Language> allowedLanguages = Arrays.asList(languagesToPrint);
		Multimap<Language, ScoredItem<String>> translations =
			BabelNetUtils.getTranslations(languageToSearch, lemma);

                System.out.println(translations.size());
                
		System.out.println("TRANSLATIONS FOR " + lemma);
		for (Language language : translations.keySet())
		{
			if (allowedLanguages.contains(language))				
                    System.out.println("\t"+language+"=>"+translations.get(language));
		}
	}
    
    
    
}
