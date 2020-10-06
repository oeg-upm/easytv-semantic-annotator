/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.demos;

import it.uniroma1.lcl.babelnet.BabelSynset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oeg.easytvannotator.babelnet.BabelNetSynset;
import it.uniroma1.lcl.jlt.util.Language;

/**
 *
 * @author Pablo
 */
public class SorterDemo {
    
    
    public static void main (String [] args){
    
    
        
         List <BabelNetSynset> lis= new ArrayList();
        
      // String type, boolean Key,String OriginalWord,String lemma,Language lang){
            
            lis.add(new BabelNetSynset("concept",true,"mirar","mirar",Language.ES));
            lis.add(new BabelNetSynset("otro",true,"mirar","mirar",Language.ES));    
            lis.add(new BabelNetSynset("concept",false,"mirar","mirar",Language.ES));
            lis.add(new BabelNetSynset("concept",true,"mirar","mirar",Language.ES));
             lis.add(new BabelNetSynset("concept",false,"ver","mirar",Language.ES));
            lis.add(new BabelNetSynset("concept",true,"ver","mirar",Language.ES));
            lis.add(new BabelNetSynset("concept",true,"observar","mirar",Language.ES));
            lis.add(new BabelNetSynset("concept",false,"mirar","mirar",Language.ES));
            lis.add(new BabelNetSynset("ssss",true,"ver","mirar",Language.ES));
        
        Collections.sort(lis);
        
        for(BabelNetSynset bal: lis){
        
            System.out.println(bal.getInfo());
        }
    
    }
}
