/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytv.annotator.webservice.comm.output;

import java.util.ArrayList;
import java.util.List;
import oeg.easytvannotator.model.SignLanguageSegment;
import oeg.easytvannotator.model.SignLanguageVideo;

/**
 *
 * @author Pablo
 */
public class EResultVideoAnnotation {
    
    public List<EResultSegment> Segments;
    
    public String Nls;
    public String Sls;
    public String Url;
    public String UrlID;
    public String Language;
    
    
    

     public EResultVideoAnnotation(){
        this.Nls="Failed";
     }
     
    public EResultVideoAnnotation(SignLanguageVideo video){
    
        this.Nls=video.getNls();
        this.Sls=video.getSls();
        this.Url=video.getUrl();
        this.Language= video.getLanguage();
        
        String newUrl = this.Url.replace("http://", "");
        UrlID = newUrl.replace(".mp4", "");
        UrlID = UrlID.replace("/", "-");
        UrlID = UrlID.replace(":", "-");
        Segments=new ArrayList();
        
        int counter=1;
        for (SignLanguageSegment seg : video.getSegments()) {

           String next;
           next= String.valueOf(counter+1);
           
           
           if(counter+1 > video.getSegments().size()){
               next=null;
           }
            
           Segments.add(new EResultSegment(seg,next ,Url,Nls,Sls));
           counter++;
        }

        
    }
    
}
