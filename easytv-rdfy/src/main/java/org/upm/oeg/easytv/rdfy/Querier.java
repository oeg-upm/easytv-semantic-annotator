/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.easytv.rdfy;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Resource;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;

/**
 *
 * @author Pablo
 */
public class Querier {
    
        private String user = "";
        private String pass = "";
        private String connection = "";
        
        
    private static Logger logger = Logger.getLogger(Querier.class);

    
    public Querier(String ContextPath) throws Exception{
     
        String propertiesFile = ContextPath + "/util/options.properties";

        Properties properties = new Properties();

        properties.load(new FileInputStream(new File(propertiesFile)));
       
        this.user = properties.getProperty("user");
        this.pass = properties.getProperty("pass");
        this.connection = properties.getProperty("connection");

        

    
    
    }
    
        public void access() {

        VirtGraph graph = new VirtGraph(this.connection, this.user, this.pass);
        
        
       
        graph.clear();

       

        Query sparql = QueryFactory.create("SELECT ?s ?p ?o WHERE { ?s ?p ?o }");

        
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, graph);

        ResultSet results = vqe.execSelect();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            RDFNode graph_name = result.get("graph");
            RDFNode s = result.get("s");
            RDFNode p = result.get("p");
            RDFNode o = result.get("o");
            System.out.println(graph_name + " { " + s + " " + p + " " + o + " . }");
        }

        System.out.println("graph.getCount() = " + graph.getCount());

        graph.clear();

    }
        
        
        public void sendQuery(String query) {

        VirtGraph graph = new VirtGraph(this.connection, this.user, this.pass);
        /*  */
        graph.clear();

       

        Query sparql = QueryFactory.create(query);

        
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, graph);

        ResultSet results = vqe.execSelect();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            RDFNode graph_name = result.get("graph");
            RDFNode s = result.get("s");
            RDFNode p = result.get("p");
            RDFNode o = result.get("o");
            System.out.println(graph_name + " { " + s + " " + p + " " + o + " . }");
        }

        System.out.println("graph.getCount() = " + graph.getCount());

        graph.clear();

    }
        
        
        public VideoCollection sendQueryAll() {

    
        QueryExecution qe = null;

        Query query = QueryFactory.create(Queries.getQueryAll(), Syntax.syntaxARQ);
        qe = QueryExecutionFactory.sparqlService("http://easytv.linkeddata.es/sparql", query);

//			System.out.println(query);
        //poner timeout
//			qe.setTimeout(60000); //1min
        ResultSet results = qe.execSelect();
        VideoCollection col= new VideoCollection();

      
        
        while (results.hasNext()) {

            //?video ?wle ?sle ?videoURL
            QuerySolution qs = results.next();
            String vtargeturl = qs.getLiteral("videoURL").toString();
            String vtarget = qs.getLiteral("video").toString();

            String labelsle = qs.getLiteral("wle").toString();
            String labelwle = qs.getLiteral("sle").toString();

            System.out.println(vtarget + "  " + vtargeturl + "  " + labelsle + "  " + labelwle);
            System.out.println();
            VideoTranslation v = new VideoTranslation(vtarget, labelwle, labelsle, vtargeturl );
            col.getVideos().add(v);
            
            //Resource id = qs.getResource("term");
            //String termId = id.getLocalName();
            //String ontoNs = id.getNameSpace();
        }

       
        
        return col;
    }
        
        
        
          public VideoCollection sendQueryAllOfLang(String TargetLang) {

    
        QueryExecution qe = null;

        Query query = QueryFactory.create(Queries.getQueryAllVideosOfLanguage(TargetLang), Syntax.syntaxARQ);
        qe = QueryExecutionFactory.sparqlService("http://easytv.linkeddata.es/sparql", query);

//			System.out.println(query);
        //poner timeout
//			qe.setTimeout(60000); //1min
        ResultSet results = qe.execSelect();
        VideoCollection col= new VideoCollection();

        
        while (results.hasNext()) {

            //?video ?wle ?sle ?videoURL
            QuerySolution qs = results.next();
            String vtargeturl = qs.getLiteral("videoURL").toString();
            String vtarget = qs.getLiteral("video").toString();

            String labelsle = qs.getLiteral("wle").toString();
            String labelwle = qs.getLiteral("sle").toString();

            System.out.println(vtarget + "  " + vtargeturl + "  " + labelsle + "  " + labelwle);
            System.out.println();
            VideoTranslation v = new VideoTranslation(vtarget, labelwle, labelsle, vtargeturl );
            col.getVideos().add(v);
            
            //Resource id = qs.getResource("term");
            //String termId = id.getLocalName();
            //String ontoNs = id.getNameSpace();
        }

       
        
        return col;
    }
        
        
        
     public VideoTranslation sendQueryGetVideoTranslation(String VideoID, String TargetLang) {

        Queries q = new Queries();

        QueryExecution qe = null;

        Query query = QueryFactory.create(Queries.getSearchTranslationQuery(VideoID, TargetLang), Syntax.syntaxARQ);
        qe = QueryExecutionFactory.sparqlService("http://easytv.linkeddata.es/sparql", query);

//			System.out.println(query);
        //poner timeout
//			qe.setTimeout(60000); //1min
        ResultSet results = qe.execSelect();

        int i = 0;

       VideoTranslation v=  new VideoTranslation("", "", "", "");
        while (results.hasNext()) {

            QuerySolution qs = results.next();
            String vtarget = qs.getResource("vtarget").toString();
            String vtargeturl = qs.getLiteral("vtargeturl").toString();

            String labelsle = qs.getLiteral("labelsle").toString();
            String labelwle = qs.getLiteral("labelwle").toString();

            System.out.println(vtarget + "  " + vtargeturl + "  " + labelsle + "  " + labelwle);
            System.out.println();
            v = new VideoTranslation(vtarget, labelwle, labelsle, vtargeturl );
            i++;
            break;
            //Resource id = qs.getResource("term");
            //String termId = id.getLocalName();
            //String ontoNs = id.getNameSpace();
        }
        
        if(i==0){
            System.out.println("NO RESULTS IN FRIST SEARCH. FINDING POTENTIAL TRANSLATIONS");
            return sendQueryGetPotentialVideoTranslation( VideoID,  TargetLang);
        }

        return v;
           
    }
     
     
     public List<VideoTranslation> sendQueryGetAllVideoTranslations(String VideoID) {

        Queries q = new Queries();

        QueryExecution qe = null;

        Query query = QueryFactory.create(Queries.getSearchAllTranslationsQuery(VideoID), Syntax.syntaxARQ);
        qe = QueryExecutionFactory.sparqlService("http://easytv.linkeddata.es/sparql", query);

//			System.out.println(query);
        //poner timeout
//			qe.setTimeout(60000); //1min
        ResultSet results = qe.execSelect();

        int i = 0;

       
       List <VideoTranslation> list=new ArrayList();
        while (results.hasNext()) {

            QuerySolution qs = results.next();
            String vtarget = qs.getResource("vtarget").toString();
            String vtargeturl = qs.getLiteral("vtargeturl").toString();

            String labelsle = qs.getLiteral("labelsle").toString();
            String labelwle = qs.getLiteral("labelwle").toString();

            System.out.println(vtarget + "  " + vtargeturl + "  " + labelsle + "  " + labelwle);
            System.out.println();
            VideoTranslation v;//=  new VideoTranslation("", "", "", "");
            v = new VideoTranslation(vtarget, labelwle, labelsle, vtargeturl );
            list.add(v);
            i++;
            
        
            
        }
        
       
       

        return list;
           
    }
     
     
     
     
     public VideoTranslation sendQueryGetPotentialVideoTranslation(String VideoID, String TargetLang) {

      

        QueryExecution qe = null;

        Query query = QueryFactory.create(Queries.getSearchPotentialTranslationQuery(VideoID, TargetLang), Syntax.syntaxARQ);
        qe = QueryExecutionFactory.sparqlService("http://easytv.linkeddata.es/sparql", query);


        ResultSet results = qe.execSelect();

        int i = 0;

       VideoTranslation v=  new VideoTranslation("", "", "", "");
        while (results.hasNext()) {

            QuerySolution qs = results.next();
            String vtarget = qs.getResource("vtarget").toString();
            String vtargeturl = qs.getLiteral("vtargeturl").toString();

            String labelsle = qs.getLiteral("labelsle").toString();
            String labelwle = qs.getLiteral("labelwle").toString();

            System.out.println(vtarget + "  " + vtargeturl + "  " + labelsle + "  " + labelwle);
            System.out.println();
            v = new VideoTranslation(vtarget, labelwle, labelsle, vtargeturl );
            break;
            //Resource id = qs.getResource("term");
            //String termId = id.getLocalName();
            //String ontoNs = id.getNameSpace();
        }

        return v;
           
    }

    public boolean deleteGraph(String graphURI) {

        try{
        VirtGraph graph = new VirtGraph(this.connection, this.user, this.pass);

        logger.info("Cleaning Graph:"+graphURI);
        String str = "CLEAR GRAPH <" + graphURI + ">";

        VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, graph);

        vur.exec();
        
        }catch(Exception e){
            
            logger.error("Graph Cleaning Failed.",e);
            System.out.println(e);
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
            
        }
        
        logger.info("Graph Cleaned");
        
        return true;

    }
    
    
    public List <String> getAllGraphs(){
        
        List <String> listRes=new ArrayList();
    
        String queryString = "PREFIX etvonto: <https://w3id.org/def/easytv#>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "SELECT distinct ?g\n"
                + " where {\n"
                + " GRAPH ?g{\n"
                + "?a ?p ?o .\n"
                + "}\n"
                + "} order by ?g";

          QueryExecution qe = null;

        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        qe = QueryExecutionFactory.sparqlService("http://easytv.linkeddata.es/sparql", query);


        ResultSet results = qe.execSelect();

       

        while (results.hasNext()) {

            QuerySolution qs = results.next();
            String res= qs.getLiteral("g").toString();
            listRes.add(res);
            logger.info("Retrieved:"+res);
            
        }

        
        
        return listRes;
        
        
    }
    
    
    
      public boolean deleteGraphFromVideoURL(String VideoURL) throws Exception{
    
        
          String graph = getGraphFromVideoURL(VideoURL);
          if(graph==null){
              throw new Exception ("Graph has not been found for video:"+VideoURL);
          }
          
          boolean result= deleteGraph(graph);
          
          return result;
    
        
        
    }
  
    public String getGraphFromVideoURL(String VideoURL) {
        
        
         String queryString = "PREFIX etvonto: <https://w3id.org/def/easytv#>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "SELECT distinct ?g\n"
                + " where {\n"
                + " GRAPH ?g{\n"
                + "<" + VideoURL + "> ?s ?p .\n"
                + "}\n"
                + "} order by ?g";

        QueryExecution qe = null;

        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        qe = QueryExecutionFactory.sparqlService("http://easytv.linkeddata.es/sparql", query);


        ResultSet results = qe.execSelect();
        
        String res=null;

    
        while (results.hasNext()) {

            QuerySolution qs = results.next();
            res = qs.getLiteral("g").toString();
            logger.info("Retrieved:"+res);
            break;
           
        }


       return res;

    }


    
    
    public List<VideoTranslation> getVideosFromKeyConcept(String key, String lang) {

        Queries q = new Queries();

        QueryExecution qe = null;

        Query query = QueryFactory.create(Queries.getAllVideosOfaKeyConcept(key, lang), Syntax.syntaxARQ);
        qe = QueryExecutionFactory.sparqlService("http://easytv.linkeddata.es/sparql", query);

//			System.out.println(query);
        //poner timeout
//			qe.setTimeout(60000); //1min
        ResultSet results = qe.execSelect();

        int i = 0;
         List <VideoTranslation> list=new ArrayList();
        while (results.hasNext()) {

            QuerySolution qs = results.next();
            String vtarget = qs.getResource("vtarget").toString();
            String vtargeturl = qs.getLiteral("vtargeturl").toString();

            String labelsle = qs.getLiteral("labelsle").toString();
            String labelwle = qs.getLiteral("labelwle").toString();

            System.out.println(vtarget + "  " + vtargeturl + "  " + labelsle + "  " + labelwle);
            System.out.println();
            VideoTranslation v = new VideoTranslation(vtarget, labelwle, labelsle, vtargeturl );
            i++;
            list.add(v);
           
           
        }
        
      
       

        return list;
           
    }
    
    //"SELECT distinct ?vtarget  ?vtargeturl ?labelsle ?labelwle\n" +
   
        
   
   
       

}
