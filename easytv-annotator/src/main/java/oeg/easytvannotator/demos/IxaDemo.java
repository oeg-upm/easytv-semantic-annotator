/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.demos;

import com.google.common.io.Files;
import eus.ixa.ixa.pipe.ml.utils.Flags;
import eus.ixa.ixa.pipe.pos.Annotate;
import eus.ixa.ixa.pipe.pos.CLI;
import ixa.kaflib.Entity;
import ixa.kaflib.KAFDocument;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import ixa.kaflib.Term;
import ixa.kaflib.Terminal;
import ixa.kaflib.Tree;
import ixa.kaflib.TreeNode;
import ixa.kaflib.WF;
import ixa.kaflib.NonTerminal;
import ixa.kaflib.Statement;
/**
 *
 * @author pcalleja
 */
public class IxaDemo {
    
    String PosModel;
    String lemmatizerModel;
    String NERModel;
    String ParseModel;
    String language;
    String kafVersion;

    
    
    eus.ixa.ixa.pipe.nerc.Annotate neAnnotator;
    eus.ixa.ixa.pipe.pos.Annotate posAnnotator;
    eus.ixa.ixa.pipe.parse.Annotate ParseAnnotator;
    private Properties annotatePosProperties;
    private Properties annotateNEProperties;
    private Properties annotateParseProperties;


    
    public static void main (String [] args) throws IOException{
    
       IxaDemo exec= new IxaDemo();

       //String sentence=readFile("../CORPORA/Plain/Wikipedia/es/Angus Young.txt");
       //String sentence = "El chocolate negro es muy amargo. El chocolate blanco es dulce.";
       String sentence="he visto esta película tres veces";
       exec.initProperties();
       exec.findEntities(sentence);
       
       
    
    }

    
     public static int recursivo(TreeNode root, int nivel) {
        if (!root.isTerminal()) {
            NonTerminal nt = (NonTerminal) root;
            for (int i = 0; i < nivel; i++) {
                System.out.print("-");
            }
            System.out.println("" + nt.getId() + " " + nt.getLabel());
            List<TreeNode> children7 = root.getChildren();
            for (TreeNode child7 : children7) {
                recursivo(child7, nivel + 1);
            }
        } else {
            Terminal term = (Terminal) root;
            for (int i = 0; i < nivel; i++) {
                System.out.print("-");
            }
            System.out.println(term.getId() + " " + term.getStr());
            //    return;
        }
        return nivel;
    }

    public void readtree(KAFDocument kaf){
    
        
        
        List<Tree> constituents = kaf.getConstituents();
        for (Tree arbol : constituents) {
            String id = arbol.getGroupID();
            String tipo = arbol.getType();
            String k = arbol.toString();
            System.out.println(id + " " + tipo + " " + k);
            TreeNode root = arbol.getRoot();

            recursivo(root, 0);
//            System.exit(0);

            if (!root.isTerminal()) {
                NonTerminal nt = (NonTerminal) root;
                String label = nt.getLabel();
                System.out.println("-     " + nt.getId() + " " + label);
            }
            List<TreeNode> children = root.getChildren();
            for (TreeNode child : children) {
                String edge = child.getEdgeId();
                String id2 = child.getId();
                if (!child.isTerminal()) {
                    List<TreeNode> children2 = child.getChildren();
                    for (TreeNode child2 : children2) {
                        String id3 = child2.getId();
                        if (!child2.isTerminal()) {
                            NonTerminal nt = (NonTerminal) child2;
                            System.out.println("--    " + nt.getId() + " " + nt.getLabel());
                            List<TreeNode> children3 = nt.getChildren();
                            for (TreeNode child3 : children3) {
                                if (!child3.isTerminal()) {
                                    NonTerminal nt2 = (NonTerminal) child3;
                                    System.out.println("---   " + nt2.getId() + " " + nt2.getLabel());
                                    List<TreeNode> children4 = nt2.getChildren();
                                    for (TreeNode child4 : children4) {
                                        if (!child4.isTerminal()) {
                                            NonTerminal n7 = (NonTerminal) child4;
                                            System.out.println("----  " + n7.getId() + " " + n7.getLabel());
                                            List<TreeNode> tn7 = n7.getChildren();
                                            for (TreeNode child5 : tn7) {
                                                if (!child5.isTerminal()) {
                                                    NonTerminal tn9 = (NonTerminal) child5;
                                                    System.out.println("----- " + tn9.getId() + " " + tn9.getLabel());
                                                    List<TreeNode> children10 = tn9.getChildren();
                                                    for (TreeNode child6 : children10) {
                                                        if (!child6.isTerminal()) {
                                                            NonTerminal ntx = (NonTerminal) child6;
                                                            System.out.println("------" + ntx.getId() + " " + ntx.getLabel());
                                                            List<TreeNode> children7 = child6.getChildren();
                                                            for (TreeNode child7 : children7) {
                                                                if (!child7.isTerminal()) {
                                                                    NonTerminal nty = (NonTerminal) child7;
                                                                    System.out.println("------" + ntx.getId() + " " + ntx.getLabel());
                                                                } else {
                                                                    Terminal term = (Terminal) child7;
                                                                    System.out.println(term.getId() + " " + term.getStr());

                                                                }

                                                            }
                                                        } else {
                                                            Terminal term = (Terminal) child6;
                                                            System.out.println(term.getId() + " " + term.getStr());
                                                        }
                                                    }
                                                } else {
                                                    Terminal term = (Terminal) child5;
                                                    System.out.println(term.getId() + " " + term.getStr());
                                                }
                                            }
                                        } else {
                                            Terminal term = (Terminal) child4;
                                            System.out.println(term.getId() + " " + term.getStr());
                                        }
                                    }

                                } else {
                                    Terminal term = (Terminal) child3;
                                    System.out.println(term.getId() + " " + term.getStr());

                                }
                            }

                        } else {
                            Terminal term = (Terminal) child2;
                            System.out.println(term.getId() + " " + term.getStr());
                        }
                    }
                } else {
                    Terminal term = (Terminal) child;
                    System.out.println(term.getId() + " " + term.getStr());
                }
            }
        }
        List<Statement> statements = kaf.getStatements();
        final List<List<WF>> sentences = kaf.getSentences();

        for (List<WF> sentence : sentences) {

            // get array of token forms from a list of WF objects
            final String[] tokens = new String[sentence.size()];
            for (int i = 0; i < sentence.size(); i++) {
                WF fragmento = sentence.get(i);
                String idu = fragmento.getId();
                //   System.out.println("IDIDID: " + idu);
                tokens[i] = fragmento.getForm();
                //    System.out.println(tokens[i]);
            }
            /*
            // Constituent Parsing: OpenNLP
            final String sent = getSentenceFromTokens(tokens);
            final Parse[] parsedSentence = parserConstituent.parse(sent, 1);
            for (Parse p : parsedSentence) {
                Parse[] parseList = p.getChildren();
                for (Parse pp : parseList) {
                    System.out.println("Hijo: " + pp);
                    Parse[] hijos = pp.getChildren();
                    for (Parse hijo : hijos) {
                        System.out.println("Nieto: " + hijo);
                        Parse[] nietos = hijo.getChildren();
                        for (Parse nieto : nietos) {
                            System.out.println("BisNieto: " + nieto);
                        }

                    }

                    pp.show();
                    pp.showCodeTree();
                    //       System.out.println(pp);
                }
            }*/

        }
    
    }

    

    private void findEntities(String text) throws IOException{
        
        KAFDocument kaf;

        // CREATES DE DOCUMENT
        InputStream is = new ByteArrayInputStream(text.getBytes());
        BufferedReader breader = new BufferedReader(new InputStreamReader(is));
        kaf = new KAFDocument(language, kafVersion);

        String version = CLI.class.getPackage().getImplementationVersion();
        String commit = CLI.class.getPackage().getSpecificationVersion();

        eus.ixa.ixa.pipe.tok.Annotate tokAnnotator = new eus.ixa.ixa.pipe.tok.Annotate(breader, annotatePosProperties);

        // Tokenize
        tokAnnotator.tokenizeToKAF(kaf);

        // PosTag
        KAFDocument.LinguisticProcessor newLp = kaf.addLinguisticProcessor("terms", "ixa-pipe-pos-" + Files.getNameWithoutExtension(PosModel), version + "-" + commit);
        newLp.setBeginTimestamp();
        posAnnotator.annotatePOSToKAF(kaf);
        newLp.setEndTimestamp();

        // NER
        KAFDocument.LinguisticProcessor newLp2 = kaf.addLinguisticProcessor("entities", "ixa-pipe-nerc-" + Files.getNameWithoutExtension(NERModel), version + "-" + commit);
        newLp2.setBeginTimestamp();
        neAnnotator.annotateNEs(kaf);
        newLp2.setEndTimestamp();
        
        
        // PARSE
        KAFDocument.LinguisticProcessor newLp3 = kaf.addLinguisticProcessor("constituency", "ixa-pipe-parse-" + Files.getNameWithoutExtension(ParseModel), version + "-" + commit);
        newLp3.setBeginTimestamp();
        ParseAnnotator.parseToKAF(kaf);
        newLp3.setEndTimestamp();

        
        // Results
    
        for (Term term : kaf.getTerms()) {

            System.out.println(term.getLemma()+" "+term.getForm()+"  "+term.getPos()+"  "+term.getStr());
          

        }
        
        for (Entity ent : kaf.getEntities()) {

            System.out.println(ent.getStr() + "\t" + ent.getType() + "\t");

        }

        readtree(kaf);
        kaf.save("example.txt");
        breader.close();
        

    }
    
 

    private void initProperties() {
       
        String multiwords;
        String dictag;
        String noseg;

        String normalize; //Set normalization method according to corpus; the default option does not escape "brackets or forward slashes.
        String untokenizable; //Print untokenizable characters.
        String hardParagraph; //Do not segment paragraphs. Ever. 
        
        
        PosModel              = new File("resources/models/morph-models-1.5.0/es/es-pos-perceptron-autodict01-ancora-2.0.bin").getAbsolutePath();
        lemmatizerModel    = new File("resources/models/morph-models-1.5.0/es/es-lemma-perceptron-ancora-2.0.bin").getAbsolutePath();
        language           = "es";
        kafVersion         = "1.5.0";
        
        multiwords         = "false"; //true
        dictag             = new File("resources/models/tag").getAbsolutePath();
        normalize          = "true";
        untokenizable      = "false"; // false
        hardParagraph      = "false";
        noseg              = "false";


        this.annotatePosProperties = new Properties();

        annotatePosProperties.setProperty("normalize", normalize);
        annotatePosProperties.setProperty("untokenizable", untokenizable);
        annotatePosProperties.setProperty("hardParagraph", hardParagraph);
        annotatePosProperties.setProperty("noseg",noseg);
        annotatePosProperties.setProperty("model", PosModel);
        annotatePosProperties.setProperty("lemmatizerModel", lemmatizerModel);
        annotatePosProperties.setProperty("language", language);
        annotatePosProperties.setProperty("multiwords", multiwords);
        annotatePosProperties.setProperty("dictTag", dictag);
        annotatePosProperties.setProperty("dictPath", dictag);
        annotatePosProperties.setProperty("ruleBasedOption", dictag);

        try {
            this.posAnnotator    = new Annotate(annotatePosProperties);
        } catch (IOException e) {
            
        }
        
        ////////
        
        
        NERModel = new File("resources/models/morph-models-1.5.0/es/es-4-class-clusters-dictlbj-ancora.bin").getAbsolutePath();
        language = "es";
        dictag = new File("resources/models/tag").getAbsolutePath();

        

         this.annotateNEProperties = new Properties();
        annotateNEProperties.setProperty("model", NERModel);
        annotateNEProperties.setProperty("language", language);
        annotateNEProperties.setProperty("ruleBasedOption", Flags.DEFAULT_LEXER);
        annotateNEProperties.setProperty("dictTag", Flags.DEFAULT_DICT_OPTION);
        annotateNEProperties.setProperty("dictPath", Flags.DEFAULT_DICT_PATH);
        annotateNEProperties.setProperty("clearFeatures", Flags.DEFAULT_FEATURE_FLAG);
        
    
        
       try {
            this.neAnnotator    = new eus.ixa.ixa.pipe.nerc.Annotate(annotateNEProperties);
        } catch (IOException e) {
          //throw new RuntimeException("Error init",e);
        }
        
        
       
       
       // PARSER
        ParseModel = new File("resources/models/parse-models/es-parser-chunking.bin").getAbsolutePath();
        this.annotateParseProperties = new Properties();
        annotateParseProperties.setProperty("model", ParseModel);
        annotateParseProperties.setProperty("language", language);
        annotateParseProperties.setProperty("headFinder", eus.ixa.ixa.pipe.parse.Flags.DEFAULT_HEADFINDER);
       
        
    
        
       try {
            this.ParseAnnotator    = new eus.ixa.ixa.pipe.parse.Annotate(annotateParseProperties);
        } catch (Exception e) {
           System.out.println("Error in parser");
        }
        
    }

    
       
    
    public static String readFile(String FilePath) throws IOException{
    
        BufferedReader br = null;
        File fr = new File(FilePath);

  
        br =   new BufferedReader(new InputStreamReader(new FileInputStream(fr), "UTF8"));

        String Line;
        StringBuffer buffer=new StringBuffer();

        while ((Line = br.readLine()) != null) {
           
            buffer.append(Line +"\n");
        }

        br.close();
        
        System.out.println(buffer.toString());
        
        return buffer.toString();


    }
    
}
