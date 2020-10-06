/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytv.annotator.webservice.comm.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.uniroma1.lcl.babelnet.BabelSense;
import java.util.ArrayList;
import java.util.List;
import oeg.easytvannotator.babelnet.BabelNetSynset;
import oeg.easytvannotator.model.SignLanguageSegment;

/**
 *
 * @author Pablo
 */
public class EResultSegment {
    
    public String Word;
    public String POS;
    public String Lemma;
    public String Language;
    public String Order;
    public String NextOrder;
    public String Start;
    public String End;
    
    public List<ESynset> Synsets;    
    public String Url;
    public String UrlID;
    public String Nls;
    public String Sls;
    
    @JsonIgnore
    private int maxSyns=3;

    public EResultSegment(SignLanguageSegment seg, String NextOrder, String Url, String Nls, String Sls){
    
        this.Word=seg.Word;
        this.POS=seg.POS;
        this.Lemma=seg.Lemma;
        this.Language=seg.Language;
        this.Order=seg.getOrder();
        this.Start=seg.getStart();
        this.End=seg.getEnd();
        this.Synsets=new ArrayList();
        this.NextOrder=NextOrder;
        int counter=0;
        
        this.Url= Url;
        this.Nls=Nls;
        this.Sls=Sls;
        
        String newUrl = this.Url.replace("http://", "");
        UrlID = newUrl.replace(".mp4", "");
        UrlID = UrlID.replace("/", "-");
        
        for (BabelNetSynset syn : seg.Synsets) {
            BabelSense sense = syn.MainSense;

            if (counter==maxSyns){break;}
            //  "http://babelnet.org/rdf/maestro_ES/s00046958n"
            String id = syn.ID.replace("bn:", "");

            String url = "http://babelnet.org/rdf/s" + id;
            String sens = "http://babelnet.org/rdf/" + parseLemma(sense.getFullLemma()) + "_" + sense.getLanguage().toString() + "/s" + id;

            ESynset syns = new ESynset(url, sens);
            Synsets.add(syns);
            counter++;
        }


    }

    
     public String parseLemma(String lemma){
        return lemma.replaceAll("Ã¡", "á").replaceAll("Ã©", "é").replaceAll("Ã­", "í").replaceAll("Ã³", "ó").replaceAll("Ãº", "ú");
    }
    
}
