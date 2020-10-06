/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.demos;

import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.nlp.NLPInterface;
import org.apache.log4j.Logger;

/**
 *
 * @author Pablo
 */
public class NLPInterfaceDemo {
    
     static Logger logger = Logger.getLogger(NLPInterfaceDemo.class);
     static NLPInterface Nlpinterface;
     
    
    public static void main (String[] args){
    
    
     Nlpinterface =new NLPInterface("");
    
     ESentence sen= NLPInterfaceDemo.procesSentence("ES", "Los ingenieros eléctricos trabajan rápido");
     sen.print();
     
    
    }
     
    public static ESentence procesSentence(String Language, String Sentence) {
        
        

        Sentence = Sentence.trim();
        logger.info("Recieved: " + Sentence + ". Lang: " + Language);

        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
        
        logger.info("ESentence created: NºTokens " + Esentence.ListTokens.size());
        System.out.println("ESentence created: NºTokens " + Esentence.ListTokens.size());
       
        
        return Esentence;
    }
    
}
