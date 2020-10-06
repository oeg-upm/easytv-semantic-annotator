/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.nlp;

import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EToken;

/**
 *
 * @author pcalleja
 */
public class NLPInterface {
    
    
    
    private String RootPath="";
    
    
    private SpanishIxaInterface SpanishLib;
    private ItalianIxaInterface ItalianLib;
    private StanfordInterface EnglishLib;
    private TreeTaggerInterface GreekLib;
    private TreeTaggerInterface CatalanLib;
    
    public NLPInterface(String RootPath){
    
        this.RootPath=RootPath;
        
        SpanishLib = new SpanishIxaInterface(RootPath);

        ItalianLib = new ItalianIxaInterface(RootPath);
        
        EnglishLib = new StanfordInterface();
        
        GreekLib = new TreeTaggerInterface(RootPath, "greek");
    
        CatalanLib = new TreeTaggerInterface(RootPath, "catalan");
        
        
    }
    
    
    
    public ESentence createESentence(String Language, String Sentence) {

        if (Language.equals("es")) {
            return SpanishLib.parseSentence(Sentence, Language);
        }
        if (Language.equals("en")) {
            return EnglishLib.parseSentence(Sentence, Language);
        }

        if (Language.equals("it")) {
            return ItalianLib.parseSentence(Sentence, Language);
        }

        if (Language.equals("ca")) {
            return CatalanLib.parseSentence(Sentence, Language);
        }

        if (Language.equals("el")) {
            return GreekLib.parseSentence(Sentence, Language);
        }
        return null;

    }
    
    

    public String getRootPath() {
        return RootPath;
    }

    public void setRootPath(String RootPath) {
        this.RootPath = RootPath;
    }

    public SpanishIxaInterface getSpanishLib() {
        return SpanishLib;
    }

    public void setSpanishLib(SpanishIxaInterface SpanishLib) {
        this.SpanishLib = SpanishLib;
    }

    public ItalianIxaInterface getItalianLib() {
        return ItalianLib;
    }

    public void setItalianLib(ItalianIxaInterface ItalianLib) {
        this.ItalianLib = ItalianLib;
    }

    public StanfordInterface getEnglishLib() {
        return EnglishLib;
    }

    public void setEnglishLib(StanfordInterface EnglishLib) {
        this.EnglishLib = EnglishLib;
    }

    public TreeTaggerInterface getGreekLib() {
        return GreekLib;
    }

    public void setGreekLib(TreeTaggerInterface GreekLib) {
        this.GreekLib = GreekLib;
    }

    
    
    
    
   
}
