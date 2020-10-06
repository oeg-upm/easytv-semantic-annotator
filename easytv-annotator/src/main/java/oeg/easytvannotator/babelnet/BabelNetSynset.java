/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.babelnet;

import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.jlt.util.Language;
import org.apache.log4j.Logger;

/**
 *
 * @author Pablo
 */
public class BabelNetSynset implements Comparable<BabelNetSynset>  {
    
    static Logger logger = Logger.getLogger(BabelNetSynset.class);
    public boolean isKey;
    public String ID;
    public String Type;
    
    //public String MainSense;
    
    public String OriginalWord;
    public String SimpleLemma;
    public String Language;
    
    public BabelSynset syn;
    
    public BabelSense MainSense;
    
    public String signLanguageRepresentation="";
    
    public BabelNetSynset(BabelSynset synset,String OriginalWord,Language lang){
    
        syn=synset;
        this.Language=lang.toString();
        this.OriginalWord = OriginalWord;
        isKey= synset.isKeyConcept();
        ID   = synset.getID().toString();
        Type = synset.getType().toString();
       
        
        try {
            MainSense=  synset.getMainSense(lang).get();
        }catch(Exception e){
        
            logger.info("No main sense. Synset:"+ID+", isKey"+ID+", Language:"+lang+", OriginalWorld:"+OriginalWord);
           
        }
        
         try {
            MainSense=  synset.getSenses(lang).get(0);
        }catch(Exception e){
        
            logger.info("No senses. Synset:"+ID+", isKey"+ID+", Language:"+lang+", OriginalWorld:"+OriginalWord);
            logger.info("Synset Discarded "+ID);
            MainSense=null;
        }
        
         if(MainSense!=null){
            SimpleLemma = parseLemma(MainSense.getSimpleLemma());
        
         }
        
    
    }
    
    public BabelNetSynset(String type, boolean Key,String OriginalWord,String lemma,Language lang){
    
        this.Language=lang.toString();
        this.OriginalWord = OriginalWord;
        isKey= Key;
        Type = type;
        SimpleLemma = lemma;
        
        
        
      
        // NUMERO DE SENSES synset.
         
           //System.out.println("Synset ID: " + synset.getID()+  "; TYPE: " + synset.getType()+  "  MAIN SENSE ES: " + synset.getMainSense(lang) + synset.getMainSense(Language.ES) +"  MAIN SENSE EN:"+synset.getMainSense(Language.EN) );
        
    
    
    }
    
    
    public BabelSense getMainSense(){
    
         return  MainSense;
    
    }
    
    public String parseLemma(String lemma){

        return lemma.replaceAll("Ã¡", "á").replaceAll("Ã©", "é").replaceAll("Ã­", "í").replaceAll("Ã³", "ó").replaceAll("Ãº", "ú");

    }

    @Override
    public int compareTo(BabelNetSynset Babel) {
        
        // Concepts better
        if((Babel.Type.toLowerCase().equals("concept")) && !(this.Type.toLowerCase().equals("concept"))){
            return 1;
        }
        
        if(!(Babel.Type.toLowerCase().equals("concept")) && (this.Type.toLowerCase().equals("concept"))){
            return -1;
        }
        
        
        // Similarity
        boolean similarity1 = Babel.SimpleLemma.toLowerCase().equals(Babel.OriginalWord.toLowerCase());
        boolean similarity2 = this.SimpleLemma.toLowerCase().equals(this.OriginalWord.toLowerCase());
        
        
        if((similarity1) && !(similarity2)){
            return 1;
        }
        
        if(!(similarity1) && (similarity2)){
            return -1;
        }
        
        
        // keys better
        
        if((Babel.isKey) && !(this.isKey)){
            return 1;
        }
        
        if(!(Babel.isKey) && (this.isKey)){
            return -1;
        }
        
        return 0;
       
    }

    public String getInfo() {
       return this.OriginalWord +" "+this.SimpleLemma +" "+this.Language +"  "+this.Type +" "+this.isKey  ;
    }
    
}
