/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.babelnet;

import com.babelscape.util.UniversalPOS;

/**
 *
 * @author pcalleja
 */
public class BabelPosInterface {
      
    public static UniversalPOS getBabelPOS(String pos, String Lang){
     
        Lang=Lang.toUpperCase();
        
        if (pos.startsWith("V")) {
            
            if(pos.startsWith("VA")){
                return null;
            }
            
            return UniversalPOS.VERB;
        }

        if (pos.startsWith("N")) {
            return UniversalPOS.NOUN;
        }
        
       

        if (Lang.equals("EN")) {

            if (pos.equals("JJ")) {
                return UniversalPOS.ADJ;

            }

            if (pos.equals("IN")) {
                return UniversalPOS.VERB;

            }
            if (pos.equals("DT")) {
                //return UniversalPOS.DET;

            }

        }

        if (Lang.equals("ES")) {
            
            if (pos.startsWith("A")) {
                return UniversalPOS.ADJ;

            }

            if (pos.startsWith("R")) {
                return UniversalPOS.ADV;

            }
            
            if (pos.startsWith("D")) {
                //return UniversalPOS.DET;

            }

            if (pos.startsWith("T")) {
                //return UniversalPOS.ADP;

            }
            
            if (pos.startsWith("PP")) {
                //return UniversalPOS.PRON;

            }
            
        }
        
        
        if (Lang.equals("EL")) {
            
            if (pos.startsWith("A")) {
                return UniversalPOS.ADJ;

            }

            if (pos.startsWith("R")) {
                return UniversalPOS.ADV;

            }
            
            if (pos.startsWith("D")) {
                //return UniversalPOS.DET;

            }

            if (pos.startsWith("T")) {
                //return UniversalPOS.ADP;

            }
            
            if (pos.startsWith("PP")) {
                //return UniversalPOS.PRON;

            }
            
           
            
            
        }
        
        
        if (Lang.equals("CA")) {
            
            if (pos.startsWith("ADJ")) {
                return UniversalPOS.ADJ;

            }

            if (pos.startsWith("VERB")) {
                return UniversalPOS.VERB;

            }
            
            if (pos.startsWith("NOUN")) {
                return UniversalPOS.NOUN;

            }

            
            
           
            
            
        }

        return null;

    }
}
