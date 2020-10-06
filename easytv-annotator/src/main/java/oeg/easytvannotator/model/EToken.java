/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.model;

import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.kb.ExternalResource;
import java.util.ArrayList;
import java.util.List;
import oeg.easytvannotator.babelnet.BabelLangInterface;
import oeg.easytvannotator.babelnet.BabelNetSynset;

/**
 *
 * @author pcalleja
 */
public class EToken {
    
    
    public String Word;
    public String POS;
    public String Lemma;
    public String Stemm;
    public String Language;
    public String NE;
    
    
    public  List<BabelNetSynset> Synsets;
    
    //to remove
    public  List<BabelSynset> WordSynsets;
    public List<BabelNetSynset> LemmaSynsets;
    
    public  List<String> WordBabelblySemanticAnnotations;
    public List<String> LemmaBabelblySemanticAnnotations;
    
    public String Order;
    public String Start;
    public String End;
    
    public EToken(){}
    
    public EToken (String word,String POS, String Lemma,String Stemm, String Language){
    
        Word=word;
        this.POS=POS;
        this.Lemma=Lemma;
        this.Stemm=Stemm;
        this.Language=Language;
        this.NE="O";
        
        Synsets=new ArrayList();
        WordSynsets=new ArrayList();
        LemmaSynsets=new ArrayList();
        WordBabelblySemanticAnnotations=new ArrayList();
        LemmaBabelblySemanticAnnotations=new ArrayList();
    }

    public String createCSVLine() {
        String cell1= "\""+this.Word+"|"+this.getSynsetys(WordSynsets) +"\"";
        String cell2= "\""+this.Lemma+"|"+"NULL"+"\"";
        String cell3= "\""+this.Word+"|"+this.getBabelflyAnnotations(WordBabelblySemanticAnnotations)+"\"";
        String cell4= "\""+this.Lemma+"|"+this.getBabelflyAnnotations(LemmaBabelblySemanticAnnotations)+"\"";
        
        String Final= ";"+cell1+";"+cell2+";"+cell3+";"+cell4;
        return Final;
    }
    
    public String printResultsBabel() {
 
        String s="";
        System.out.println(this.Lemma);
        
       for(BabelNetSynset syn: LemmaSynsets){
          BabelSense sense= syn.getMainSense();
          
         
          ExternalResource rc=new ExternalResource() {};
          
          //  "http://babelnet.org/rdf/maestro_ES/s00046958n"
                  
            String id= syn.ID.replace("bn:", "");
            String ss="  http://babelnet.org/rdf/s"+id + "  --   "+    "http://babelnet.org/rdf/"+parseLemma(sense.getFullLemma())+"_"+sense.getLanguage().toString()+"/s"+id;
            System.out.println(ss);
        }
        if(s.length()>1){
        s= s.substring(0, s.length()-1);
        }
        
       
        return s;
    }
    
    public String parseLemma(String lemma){
    
    
        return lemma.replaceAll("Ã¡", "á").replaceAll("Ã©", "é").replaceAll("Ã­", "í").replaceAll("Ã³", "ó").replaceAll("Ãº", "ú");
    
    
    }
    
    
    public String getSynsetys( List<BabelSynset> List){
    
        //POS Babelpos= getBabelPOS(this.POS,this.Language);
        
        String s="";
        for(BabelSynset syn: List){
            s=s+syn.getID().toString()+ " "+ syn.getMainSense(BabelLangInterface.getLangType(Language) )+" "+"|";
        }
        if(s.length()>1){
        s= s.substring(0, s.length()-1);
        }
        return s;
    }
    
     public String getBabelflyAnnotations( List<String> List){
    
        String s="";
        for(String url: List){
            s=s+url+"|";
        }
         if(s.length()>1){
        s= s.substring(0, s.length()-1);
        }
        return s;
    
    }


    public void cleanSynsets() {
        
       
        List <BabelNetSynset> newSynsetList=new ArrayList();
        for(BabelNetSynset syn: this.LemmaSynsets){
        
            if(syn.MainSense==null){
                continue;
            }
            String Type= syn.Type.toLowerCase();
            if( (!Type.equals("named entity")) ){
                newSynsetList.add(syn);
            }
            
        }
        this.LemmaSynsets=newSynsetList;

        
    }
    
    public void copyVideoInforamtion(SignLanguageSegment seg) {
        
        this.Order=seg.getOrder();
        this.Start=seg.getStart();
        this.End=seg.getEnd();
    
    }
    
    
    
    
    
}
