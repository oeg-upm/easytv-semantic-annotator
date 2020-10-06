/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.nlp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import oeg.easytvannotator.model.ESentence;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author pcalleja
 */
public class TreeTaggerInterface implements NLPApi{
    
   static Logger logger = Logger.getLogger(TreeTaggerInterface.class);
    
    File TreeTaggerDir;
    
    String Path;
    
    String batFile;
    
    
    public TreeTaggerInterface(String path, String Language){
        Path=path;
        
        batFile=Language;
        
        System.out.println("Path:: "+Path);
        //catalan
    
    }
    
    public void process(String Path,String Text){
    
         TreeTaggerDir= new File(Path+"TreeTagger");
         
        try {
            createTreeTaggerInput( Text );
            
            
            //SystemUtils.IS_OS_LINUX  SystemUtils.IS_OS_WINDOWS
            if(SystemUtils.IS_OS_WINDOWS){
                logger.info("TreeTagger Under Windows");
                System.out.println("TreeTagger Under Windows");
                sendCommandWindows() ;
            }
            
            
            if(SystemUtils.IS_OS_LINUX){
                
                logger.info("TreeTagger Under Linux");
                System.out.println("TreeTagger Under Linux");
                sendCommandLinux() ;
            
            }
            
            
            readOutput(Text,"el");
            
            
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        } catch (IOException | InterruptedException ex) {
            logger.error(ex);
        }
        
    }
    
    
    public void createTreeTaggerInput( String Text ) throws UnsupportedEncodingException, FileNotFoundException, IOException{
    
        
        File file = new File(TreeTaggerDir.getAbsolutePath()+"/result/Input.txt");
        logger.info("Creating input File :"+file.getAbsolutePath());
        Writer Writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));
         Writer.append(Text);
         Writer.flush();
         Writer.close();
         
         
    }
    
    
    public void sendCommandWindows() throws IOException, InterruptedException{
      
        String bat= TreeTaggerDir.getAbsolutePath()+"/bin/tag-"+ batFile +".bat";
        String Input= "\""+TreeTaggerDir.getAbsolutePath()+"/result/Input.txt\"";
        String Output= "\""+ TreeTaggerDir.getAbsolutePath()+"/result/Output.txt\"";
        
        logger.info("Executing command:   \""+bat+"\" "+Input+" "+Output);
        Process pro = Runtime.getRuntime().exec("  \""+bat+"\" "+Input+" "+Output,null,new File(TreeTaggerDir.getAbsolutePath()));

        
       
        BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
        String line;
        while ((line = in.readLine()) != null) {
          
            //System.out.println(line);
            logger.info("TreeTagger output:"+line);
        }
        pro.waitFor();
       

        in.close();


    
    }
    
    
    
    public void sendCommandLinux() throws IOException, InterruptedException{
      
        String bat= TreeTaggerDir.getAbsolutePath()+"/cmd/tree-tagger-"+ batFile+"-file";
        String Input= TreeTaggerDir.getAbsolutePath()+"/result/Input.txt";
        String Output=  TreeTaggerDir.getAbsolutePath()+"/result/Output.txt";
        
        logger.info("Executing command:   \""+bat+"\" "+Input+" "+Output);
        System.out.println("Executing command:   \""+bat+"\" "+Input+" "+Output);
        //Process pro = Runtime.getRuntime().exec("   \""+bat+"\" "+Input+" "+Output);
        //Process pro = Runtime.getRuntime().exec("ls -la", null,new File("./src"));
       
        Process pro = Runtime.getRuntime().exec(bat+" "+Input+" "+Output, null,new File(TreeTaggerDir.getAbsolutePath()));
        
        BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
        String line;
        while ((line = in.readLine()) != null) {
          
             logger.info("TreeTagger output:"+line);
             System.out.println("TreeTagger output:"+line);
        }
        pro.waitFor();
        

        in.close();

        logger.info("TreeTagger finished");
    
    }
     
  
   
    public ESentence  readOutput(String Text,String Lang) throws UnsupportedEncodingException, FileNotFoundException, IOException{
    
    
       
       File file = new File(TreeTaggerDir.getAbsolutePath() + "/result/Output.txt");
        logger.info("Reading Output File :"+file.getAbsolutePath());
        BufferedReader Buffer = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), "UTF8"));
    
        ESentence esentence=new ESentence();
        StringBuffer LemmaSentence=new StringBuffer();
        
        String line;
        while ((line = Buffer.readLine()) != null) {
          
            String [] fields= line.split("\t");
            String word=fields[0];
            String pos=fields[1];
            String lem=fields[2];
            if(lem.equals("<unknown>")){lem=word;}
            logger.info("Retrieved from Treetagger:"+word +"  "+pos+"  "+lem+"  ");
        
          
            esentence.addEToken(word, pos, lem, "",Lang);
            LemmaSentence.append(lem+" ");
            System.out.println(line);
            
        }
        esentence.OriginalText=Text;
        esentence.LematizedText= LemmaSentence.toString().trim();
        Buffer.close();
        
        
        logger.info("Reading output file finished");
        System.out.println("Reading output file finished");
        
        return esentence;

    }
    
    

    @Override
    public ESentence parseSentence(String Sentence, String Language) {
        
        
         TreeTaggerDir= new File(Path+"TreeTagger");
         
        try {
            createTreeTaggerInput( Sentence );
            
            
            //SystemUtils.IS_OS_LINUX  SystemUtils.IS_OS_WINDOWS
            if(SystemUtils.IS_OS_WINDOWS){
                 logger.info("TreeTagger Under Windows");
                sendCommandWindows() ;
            }else{
                
                  logger.info("TreeTagger Under Linux");
                sendCommandLinux() ;
            }
            
            
            return readOutput(Sentence,Language);
            
            
      } catch (FileNotFoundException ex) {
            logger.error(ex);
        } catch (IOException | InterruptedException ex) {
            logger.error(ex);
        }
        
        return new ESentence();
        
    }
    
       

    
}
