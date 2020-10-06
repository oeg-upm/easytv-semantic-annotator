/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.model;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import it.uniroma1.lcl.babelnet.BabelSynset;
import java.util.ArrayList;
import java.util.List;
import oeg.easytvannotator.babelnet.BabelNetSynset;

/**
 *
 * @author Pablo
 */
public class SignLanguageSegment {
    
    private String order;
    private String start;
    private String end;
    private String content;
    
    
    
    @JsonIgnore
    public String Word;
    @JsonIgnore
    public String POS;
    @JsonIgnore
    public String Lemma;
    
    @JsonIgnore
    public String Stemm;
    
    @JsonIgnore
    public String Language;
    
    @JsonIgnore
    public String NE;
    
    //public List<BabelSynset> WordSynsets;
    @JsonIgnore
    public List<BabelNetSynset> LemmaSynsets;
    @JsonIgnore
    public List<BabelNetSynset> Synsets;
    @JsonIgnore
    public List<String> WordBabelblySemanticAnnotations;
    
    @JsonIgnore
    public List<String> LemmaBabelblySemanticAnnotations;
    @JsonIgnore
    public List<EToken> ListTokens;
    
    
    public SignLanguageSegment(){
    
        Synsets=new ArrayList();
        LemmaSynsets=new ArrayList();
        ListTokens=new ArrayList();
        
    }
    
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String Word) {
        this.Word = Word;
    }

    public String getPOS() {
        return POS;
    }

    public void setPOS(String POS) {
        this.POS = POS;
    }

    public String getLemma() {
        return Lemma;
    }

    public void setLemma(String Lemma) {
        this.Lemma = Lemma;
    }

    public String getStemm() {
        return Stemm;
    }

    public void setStemm(String Stemm) {
        this.Stemm = Stemm;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public String getNE() {
        return NE;
    }

    public void setNE(String NE) {
        this.NE = NE;
    }

   
    @JsonIgnore
    public List<BabelNetSynset> getLemmaSynsets() {
        return LemmaSynsets;
    }
    
    public void setLemmaSynsets(List<BabelNetSynset> LemmaSynsets) {
        this.LemmaSynsets = LemmaSynsets;
    }

    @JsonIgnore
    public List<String> getWordBabelblySemanticAnnotations() {
        return WordBabelblySemanticAnnotations;
    }
    
    public void setWordBabelblySemanticAnnotations(List<String> WordBabelblySemanticAnnotations) {
        this.WordBabelblySemanticAnnotations = WordBabelblySemanticAnnotations;
    }

    @JsonIgnore
    public List<String> getLemmaBabelblySemanticAnnotations() {
        return LemmaBabelblySemanticAnnotations;
    }

    public void setLemmaBabelblySemanticAnnotations(List<String> LemmaBabelblySemanticAnnotations) {
        this.LemmaBabelblySemanticAnnotations = LemmaBabelblySemanticAnnotations;
    }

    @JsonIgnore
    public List<BabelNetSynset> getSynsets() {
        return Synsets;
    }

    public void setSynsets(List<BabelNetSynset> Synsets) {
        this.Synsets = Synsets;
    }
    
    @JsonIgnore
    public List<EToken> getListTokens() {
        return ListTokens;
    }

    public void setListTokens(List<EToken> ListTokens) {
        this.ListTokens = ListTokens;
    }

    
    public void copyTokenInformation(EToken tok) {
        
        content=tok.Word;
        this.Word=tok.Word;
        this.POS=tok.POS;
        this.Lemma=tok.Lemma;
        this.Stemm=tok.Stemm;
        this.Language=tok.Language;
        this.NE=tok.NE;
       // this.WordSynsets=tok.WordSynsets;
        this.LemmaSynsets=tok.LemmaSynsets;
        
    
    }

    public void generateLingProperties() {
        
        
        if( ListTokens.size()==1){
            this.Lemma=ListTokens.get(0).Lemma;
            this.POS=ListTokens.get(0).POS;
        }
        
        if (ListTokens.size()>1){
            
            String Lemm="";
            this.POS=ListTokens.get(0).POS;// JUST IN CASE
            boolean NP=false;
            boolean VP=false;
            for(EToken tok: ListTokens){
            
                Lemm=Lemm+" "+tok.Lemma;
                if(tok.POS.startsWith("N")){
                    NP=true;
                }
                if(tok.POS.startsWith("V")){
                    VP=true;
                }
            }
            this.Lemma=Lemm.trim();
            if(VP){
               this.POS="VP";
               return;
            }
            if(NP){
               this.POS="NP";
            }
        
        }
        
        
    }

}
