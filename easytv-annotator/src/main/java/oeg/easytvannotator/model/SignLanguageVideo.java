/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.model;

import java.util.List;

/**
 *
 * @author Pablo
 */
public class SignLanguageVideo {
    
    private String url;
    private String nls;
    private String sls;
    private String duration;
    private String language;
    private List <SignLanguageSegment> segments;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNls() {
        return nls;
    }

    public void setNls(String nls) {
        this.nls = nls;
    }

    public String getSls() {
        return sls;
    }

    public void setSls(String sls) {
        this.sls = sls;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<SignLanguageSegment> getSegments() {
        return segments;
    }

    public void setSegments(List<SignLanguageSegment> segments) {
        this.segments = segments;
    }

    public void fillInfoOfWords(){
    
        for(SignLanguageSegment seg : this.getSegments()){
        
           
            seg.setWord(seg.getContent());
            seg.setLanguage(this.getLanguage());
        
        
        }
    
    }
    
    
    public void associateETokensToSegments(ESentence Sentence){
    
       
    
       
        for(SignLanguageSegment seg: getSegments()){
                
            String text= seg.getContent().toLowerCase();
            
                for(EToken tok: Sentence.ListTokens){
            
                    if(text.contains(tok.Word.toLowerCase())){
                        seg.ListTokens.add(tok);
                        
                    }
                    
                }
              
                
            }
    
    
    
    }
    
    
    public void associateSynsetsToSegments(){
    
         for(SignLanguageSegment seg: getSegments()){

                for(EToken tok: seg.ListTokens){
            
                    seg.getSynsets().addAll(tok.Synsets);
                    
                    seg.generateLingProperties();
                }
              
                
            }
    
        
    
    }

    

   
    
    
}


