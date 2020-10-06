/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.easytv.rdfy;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
//import be.ugent.rml.DataFetcher;
import be.ugent.rml.Executor;
import be.ugent.rml.records.RecordsFactory;
import be.ugent.rml.store.RDF4JStore;
import be.ugent.rml.store.QuadStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import virtuoso.jena.driver.*;
import org.rdfhdt.hdt.options.HDTOptions;

/**
 *
 * @author Pablo
 */
public class ExecuteMappings {
    
    
      public void mappingExe (String[] args) {

    	 //HDTOptions;
         
        String cwd = "./source/"; //path to default directory for local files
        String mappingFile = "./source/mappings/test.ttl"; //path to the mapping file that needs to be executed
        String propertiesFile = "./util/options.properties" ;
        
        
        try {
            InputStream mappingStream = new FileInputStream(mappingFile);
            Model model = Rio.parse(mappingStream, "", RDFFormat.TURTLE);
            RDF4JStore rmlStore = new RDF4JStore(model);
            

           // Executor executor = new Executor(rmlStore, new RecordsFactory(new DataFetcher(cwd, rmlStore)), null);
            
            Executor executor = new Executor(rmlStore, new RecordsFactory(cwd), null);
            
            QuadStore result = executor.execute(null);
                             
            
            String graph = result.toString().split(" ")[3].split("\n")[0];
            System.out.println("Graph: "+ graph);
            
            String datasetNTriple = result.toString().replaceAll(graph, " .").replaceAll("null", " .");
           

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
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
            
//            // load triples in VIRTUOSO
//            /*			STEP 1			*/
    		VirtGraph set = new VirtGraph (connection, user, pass);

    /*			STEP 2			*/
                    String str = "CLEAR GRAPH " + graph;
                    VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
                    vur.exec();                  

                    str = "INSERT INTO GRAPH "+ graph + " { " + datasetNTriple + " } " ;
                    vur = VirtuosoUpdateFactory.create(str, set);
                    vur.exec();                  

            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } 
}
