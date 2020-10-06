/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.nlp;

import oeg.easytvannotator.model.ESentence;

/**
 *
 * @author pcalleja
 */
public interface NLPApi {
    
    public abstract ESentence parseSentence(String Sentence, String Lang);
    
}
