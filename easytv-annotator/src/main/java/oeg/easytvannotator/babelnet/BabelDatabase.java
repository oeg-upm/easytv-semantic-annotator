/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.babelnet;

import it.uniroma1.lcl.babelnet.BabelSynset;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author pcalleja
 */
public class BabelDatabase {
    
    
    
    
    public static HashMap <String,List<BabelSynset>> BabelNetData=new HashMap();
    
    
    public static void addConcept(String word, String pos, String lang, List <BabelSynset> Synsets){
    
        String line=word.toLowerCase()+"-"+pos+"-"+lang;
        
        
        BabelNetData.put(line, Synsets);
    
    }
    
    
    public static List <BabelSynset>  getConcept(String word, String pos, String lang){
    
        String line=word.toLowerCase()+"-"+pos+"-"+lang;
        
        
        return BabelNetData.get(line);
      
        
    
    }
    
    
    
}
