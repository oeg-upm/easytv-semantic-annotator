/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.easytv.rdfy;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
//import be.ugent.rml.DataFetcher;
import be.ugent.rml.Executor;
import be.ugent.rml.records.RecordsFactory;
import be.ugent.rml.store.QuadStore;
import be.ugent.rml.store.RDF4JStore;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Timestamp;
import org.apache.log4j.Logger;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;




public class Mapper {
    
    
    private String ContextPath;

    private String cwd ;
    private String mappingFile;
    private String propertiesFile;
    
    private String Graph;

    private String logDir;
 
    static Logger logger = Logger.getLogger(Mapper.class);
    
    
    
    public Mapper(String ContextPath) throws Exception {

        this.ContextPath = ContextPath;
        logDir = ContextPath + "logs"; //path to default directory for local files
        cwd = ContextPath + "source/"; //path to default directory for local files
        mappingFile = ContextPath + "source/mappings/mapperFile.ttl"; //path to the mapping file that needs to be executed
        propertiesFile = ContextPath + "util/options.properties";
        
        
        logger.info("Files used for configuration");
        logger.info("Cwd:"+cwd);
        logger.info("MappingFile:"+mappingFile);
        logger.info("PropertiesFile:"+propertiesFile);
    }

    public void generateMappings(String Name) throws Exception{

        try {

            String dataset = generateNTriples();
            
            //LOG
            createLogFile(Name + ".rdf", this.logDir+"/rdf", dataset);
            
            logger.info("Graph:"+Graph);
            insertMappings(dataset, Graph);
            
        } catch (Exception e) {
            logger.error("Error generating mappings:"+e);
            throw new Exception("Error generating mappings:"+e);
        }

    }

    
    
    
    public String generateNTriples() throws Exception {

        InputStream mappingStream = new FileInputStream(mappingFile);
        
       
        Model model = Rio.parse(mappingStream, "", RDFFormat.TURTLE);
        RDF4JStore rmlStore = new RDF4JStore(model);

        //Executor executor = new Executor(rmlStore, new RecordsFactory(new DataFetcher(cwd, rmlStore)), null);
        Executor executor = new Executor(rmlStore, new RecordsFactory(cwd), null);
            
        QuadStore result = executor.execute(null);

        Graph = result.toString().split(" ")[3].split("\n")[0];
       
        String datasetNTriple = result.toString().replaceAll(Graph, " .").replaceAll("null", " .");
        return datasetNTriple;
    }
    
    

    public void insertMappings(String datasetNTriple, String graph) throws Exception {

        String user = "";
        String pass = "";
        String connection = "";

        try {

            Properties properties = new Properties();

            properties.load(new FileInputStream(new File(propertiesFile)));
            user = properties.getProperty("user");
            pass = properties.getProperty("pass");
            connection = properties.getProperty("connection");

        } catch (Exception e) {
           logger.error("No properties file for mapper:"+e);
           throw new Exception("No properties file for mapper:"+e);
        }

        // load triples in VIRTUOSO
        VirtGraph set = new VirtGraph(connection, user, pass);

       // graph = graph.substring(1).replaceAll(">","");
        
        logger.info("Clearing Graph :"+graph);
        String str = "CLEAR GRAPH " + graph;
        VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();

        
        logger.info("Inserting Graph :"+graph);
        str = "INSERT INTO GRAPH " + graph + " { " + datasetNTriple + " } ";
        vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();
        
       
    }

    public void createJsonFile(String Name, String json) throws Exception {
   
        // JSON File  
        logger.info("Recieved :"+json);
        MapperFile.createFile(ContextPath+"source/json/videoSL.json", json);
        createLogFile(Name + ".json", this.logDir+"/jsonnlp", json);
        
        // File 2
        String path= ContextPath + "source/json/videoSL.json";
        String text=  MapperFile.generateTTLFileContent(path);
        
        File f= new File (mappingFile);
        if(!f.exists()){
            logger.info("Mapping file does not exist. Creating new one");
            MapperFile.createFile(mappingFile,text);
        }
        
    
    
    }

    
    
    
    public static File createLogFile(String FileName, String Dir, String Text) {


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = timestamp.toString().replaceAll(":", "\\.");

         
         
        File file = null;
        try {

            file = new File(Dir + File.separator + time+"-"+FileName);

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF8"));

            out.append(Text);

            out.flush();
            out.close();

        } catch (IOException e) {
           logger.error(e);

        }

        return file;

    }
   
    
    
      public String queryTranslation(String UrlID1, String UrlID2, String Nls1, String Nls2, String Sls1, String Sls2){
        
        
    String qu= "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> a  <https://w3id.org/def/easytv#Translation> .\n" +
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#expertSuggestion> \"true\"^^xsd:boolean .\n" +
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#confidence> \"100.0\"^^xsd:float .\n" +
           
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#relates> <http://easytv.linkeddata.es/resource/SignedLinguisticExpression/"+Sls1+""+UrlID1+"> .\n" +
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#relates> <http://easytv.linkeddata.es/resource/SignedLinguisticExpression/"+Sls2+""+UrlID2+"> .\n" +
            
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#relates> <http://easytv.linkeddata.es/resource/SignedLinguisticExpression/"+Nls1+""+UrlID1+"> .\n" +
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#relates> <http://easytv.linkeddata.es/resource/SignedLinguisticExpression/"+Nls2+""+UrlID2+"> .";

           return qu; 
            
            
        }
      
      
      
      public String querySetTranslationbyUser(String UrlID1, String UrlID2, String Nls1, String Nls2, String Sls1, String Sls2, String confidence){
        
        
    String qu= "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> a  <https://w3id.org/def/easytv#Translation> .\n" +
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#expertSuggestion> \"false\"^^xsd:boolean .\n" +
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#confidence> \""+confidence+"\"^^xsd:float .\n" +
           
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#relates> <http://easytv.linkeddata.es/resource/SignedLinguisticExpression/"+Sls1+""+UrlID1+"> .\n" +
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#relates> <http://easytv.linkeddata.es/resource/SignedLinguisticExpression/"+Sls2+""+UrlID2+"> .\n" +
            
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#relates> <http://easytv.linkeddata.es/resource/SignedLinguisticExpression/"+Nls1+""+UrlID1+"> .\n" +
            "<http://easytv.linkeddata.es/resource/Translation/"+UrlID1+"-"+UrlID2+"> <https://w3id.org/def/easytv#relates> <http://easytv.linkeddata.es/resource/SignedLinguisticExpression/"+Nls2+""+UrlID2+"> .";

           return qu; 
            
            
        }
      
      public void createTranslation(String UrlID1, String UrlID2, String Nls1, String Nls2, String Sls1, String Sls2) throws Exception{
      
          
          String q= queryTranslation( UrlID1,  UrlID2,  Nls1,  Nls2,  Sls1,  Sls2);
          
          
           String user = "";
        String pass = "";
        String connection = "";


            Properties properties = new Properties();

            properties.load(new FileInputStream(new File(propertiesFile)));
            user = properties.getProperty("user");
            pass = properties.getProperty("pass");
            connection = properties.getProperty("connection");

      

        // load triples in VIRTUOSO
        VirtGraph set = new VirtGraph(connection, user, pass);

       // graph = graph.substring(1).replaceAll(">","");
        
       

        String graph= "<http://easytv.linkeddata.es/graph/translations>";
        logger.info("Inserting Graph :"+graph);
        String str = "INSERT INTO GRAPH " + graph + " { " + q + " } ";
        
            logger.info(q);
            
            logger.info("\n\n");
            
            logger.info(str);
        
        VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();
          
       
      }

      
       public void createTranslationByUser(String UrlID1, String UrlID2, String Nls1, String Nls2, String Sls1, String Sls2, String Confidence) throws Exception{
      
          
          String q= querySetTranslationbyUser( UrlID1,  UrlID2,  Nls1,  Nls2,  Sls1,  Sls2,Confidence);
          
          
           String user = "";
        String pass = "";
        String connection = "";


            Properties properties = new Properties();

            properties.load(new FileInputStream(new File(propertiesFile)));
            user = properties.getProperty("user");
            pass = properties.getProperty("pass");
            connection = properties.getProperty("connection");

      

        // load triples in VIRTUOSO
        VirtGraph set = new VirtGraph(connection, user, pass);

       // graph = graph.substring(1).replaceAll(">","");
        
       

        String graph= "<http://easytv.linkeddata.es/graph/translations>";
        logger.info("Translation by user");
        logger.info("Inserting Graph :"+graph);
        String str = "INSERT INTO GRAPH " + graph + " { " + q + " } ";
        
            logger.info(q);
            
            logger.info("\n\n");
            
            logger.info(str);
        
        VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();
          
       
      }

    
}
