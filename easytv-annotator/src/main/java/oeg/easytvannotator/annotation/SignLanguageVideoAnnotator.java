/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.annotation;

import oeg.easytvannotator.babelnet.BabelFyInterface;
import oeg.easytvannotator.babelnet.BabelNetInterface;
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.SignLanguageVideo;
import oeg.easytvannotator.nlp.NLPInterface;
import org.apache.log4j.Logger;

/**
 *
 * @author Pablo
 */
public class SignLanguageVideoAnnotator {
    
    
   
    
    private NLPInterface Nlpinterface;
    
    private BabelNetInterface BabelInterface;
        
    private BabelFyInterface  BabelFyInterface;
    
    
    static Logger logger = Logger.getLogger(SignLanguageVideoAnnotator.class);
    
 
    
    
    public SignLanguageVideoAnnotator(String ResourcesPath, String ContextPath, boolean Serviceweb){
        
        Nlpinterface =new NLPInterface(ResourcesPath);

        BabelInterface= new BabelNetInterface(ContextPath,Serviceweb);
        
        BabelFyInterface = new BabelFyInterface();
        
        
    }
    

    
    public ESentence annotateSignLanguageVideo(SignLanguageVideo Video) {

        
        //SignLanguageVideo Video= Input.getVideo();
        Video.fillInfoOfWords();
        
        String Sentence = Video.getNls().trim();
        String Language=  Video.getLanguage();

        logger.info("Recieved: " + Sentence + ". Lang:" + Language);
     
        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
        
        // Add tokens
        Video.associateETokensToSegments(Esentence);
        
        //Annotate 
        BabelInterface.annotateVideo(Video);

        Video.associateSynsetsToSegments();
        
        
        // Associate Videos
        return Esentence;

    }
    
   

    public NLPInterface getNlpinterface() {
        return Nlpinterface;
    }

    public void setNlpinterface(NLPInterface Nlpinterface) {
        this.Nlpinterface = Nlpinterface;
    }
    
    
    
    public ESentence procesTestSentence(String Language, String Sentence) {

        Sentence = Sentence.trim();
        logger.info("Recieved: " + Sentence + "  Lang:" + Language);

        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
        
        logger.info("ESentence created: NºTokens " + Esentence.ListTokens.size());
        
        //BABELNET
        BabelInterface.callBabelNet(Esentence, Language);
        
        return Esentence;
    }
    
}
