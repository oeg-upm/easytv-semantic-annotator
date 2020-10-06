/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.demos;

import com.babelscape.util.UniversalPOS;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.jlt.util.Language;
import java.io.IOException;
import oeg.easytvannotator.babelnet.BabelFyInterface;
import oeg.easytvannotator.babelnet.BabelNetInterface;
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.nlp.NLPInterface;

/**
 *
 * @author Pablo
 */
public class BabelComparatorTest {
    
    private NLPInterface Nlpinterface;

    private BabelNetInterface BabelInterface;

    //private BabelFyInterface  BabelFyInterface;
    public BabelComparatorTest() {

        Nlpinterface = new NLPInterface("");

        BabelInterface = new BabelNetInterface("", false);

        // BabelFyInterface = new BabelFyInterface();
    }

    public static void main(String[] args) throws IOException {

        String sentence;
        String lang;
        //sentence="Los ingenieros eléctricos trabajan duro";
        sentence = "El ingeniero eléctrico trabajar duro";

        lang = "es";

        BabelComparatorTest comp = new BabelComparatorTest();
        comp.annotate(sentence, lang);

    }

    public void annotate(String Sentence, String Lang) {

        ESentence Esentence = Nlpinterface.createESentence(Lang.toLowerCase(), Sentence);

        // Add tokens
        //Annotate 
        BabelInterface.callBabelNet(Esentence, Lang);

        Esentence.print();

        BabelFyInterface.processSimpleString(Sentence, Lang);
    }

    
}
