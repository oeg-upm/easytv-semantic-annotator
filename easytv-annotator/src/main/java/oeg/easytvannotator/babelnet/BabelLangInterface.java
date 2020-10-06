/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.babelnet;

import it.uniroma1.lcl.jlt.util.Language;

/**
 *
 * @author pcalleja
 */
public class BabelLangInterface {
    
    
    
    public static Language getLangType(String Lang) {

        Lang = Lang.toUpperCase();
        if (Lang.equals("EN")) {

            return Language.EN;
        }

        if (Lang.equals("ES")) {

            return Language.ES;
        }

        if (Lang.equals("IT")) {

            return Language.IT;
        }
        if (Lang.equals("EL")) {

            return Language.EL;
        }

        if (Lang.equals("CA")) {
            return Language.CA;
        }

        if (Lang.equals("CAT")) {
            return Language.CA;
        }
        return Language.EN;

    }
    
    
}
