/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.easytv.rdfy;

/**
 *
 * @author Pablo
 */
public class Queries {
    
    
    
    
    
    public static String getSearchTranslationQuery(String URLSource, String TargetLang){
 
        String q = "PREFIX etvonto: <https://w3id.org/def/easytv#>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX lemon: <http://lemon-model.net/lemon#>\n"
                + "\n"
                + "SELECT distinct ?vtarget  ?vtargeturl ?labelsle ?labelwle\n"
                + "\n"
                + "\n"
                + " where {\n"
                + "\n"
                + " ?levi etvonto:hasRealization <" + URLSource + "> .\n"
                + //http://easytv.linkeddata.es/resource/Video/nlp-easytv.oeg-upm.net-video-es-2
                "\n"
                + " ?translation a etvonto:Translation .\n"
                + "\n"
                + " ?translation etvonto:relates ?levi .\n"
                + " ?translation etvonto:relates ?letarget .\n"
                + "\n"
                + " ?letarget etvonto:hasRealization ?vtarget .\n"
                + "\n"
                + " ?vtarget lemon:language ?langtarget .\n"
                + " ?vtarget etvonto:url ?vtargeturl . \n"
                + "\n"
                + " ?targetsle a etvonto:SignedLinguisticExpression .\n"
                + " ?targetsle etvonto:hasRealization ?vtarget .\n"
                + " ?targetsle rdfs:label ?labelsle .\n"
                + "\n"
                + " ?targetwle a etvonto:WrittenLinguisticExpression .\n"
                + " ?targetwle etvonto:hasRealization ?vtarget .\n"
                + " ?targetwle rdfs:label ?labelwle .\n"
                + "\n"
                + "FILTER regex(?langtarget, \"" + TargetLang + "\", \"i\") \n"
                + "\n"
                + "\n"
                + "}";
        return q;

    }
    
    
    public static String getSearchAllTranslationsQuery(String URLSource){
 
        String q = "PREFIX etvonto: <https://w3id.org/def/easytv#>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX lemon: <http://lemon-model.net/lemon#>\n"
                + "\n"
                + "SELECT distinct ?vtarget  ?vtargeturl ?labelsle ?labelwle\n"
                + "\n"
                + "\n"
                + " where {\n"
                + "\n"
                + " ?levi etvonto:hasRealization <" + URLSource + "> .\n"
                + //http://easytv.linkeddata.es/resource/Video/nlp-easytv.oeg-upm.net-video-es-2
                "\n"
                + " ?translation a etvonto:Translation .\n"
                + "\n"
                + " ?translation etvonto:relates ?levi .\n"
                + " ?translation etvonto:relates ?letarget .\n"
                + "\n"
                + " ?letarget etvonto:hasRealization ?vtarget .\n"
                + "\n"
                + " ?vtarget lemon:language ?langtarget .\n"
                + " ?vtarget etvonto:url ?vtargeturl . \n"
                + "\n"
                + " ?targetsle a etvonto:SignedLinguisticExpression .\n"
                + " ?targetsle etvonto:hasRealization ?vtarget .\n"
                + " ?targetsle rdfs:label ?labelsle .\n"
                + "\n"
                + " ?targetwle a etvonto:WrittenLinguisticExpression .\n"
                + " ?targetwle etvonto:hasRealization ?vtarget .\n"
                + " ?targetwle rdfs:label ?labelwle .\n"
                + "\n"
               
                + "\n"
                + "\n"
                + "}";
        return q;

    }
    
    
    
    public static String getSearchPotentialTranslationQuery(String URLSource, String TargetLang) {

        String q = "PREFIX etvonto: <https://w3id.org/def/easytv#>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX lemon: <http://lemon-model.net/lemon#>\n"
                + "\n"
                + "SELECT distinct ?videoTarget ?labelleviT\n"
                + "\n"
                + " where {\n"
                + "\n"
                + " ?levi etvonto:hasRealization <" + URLSource + "> .\n"
                + "\n"
                + " ?levi etvonto:hasComponent ?itemo .\n"
                + " ?itemo etvonto:hasForm ?formo .\n"
                + " ?lexicalentryo lemon:canonicalForm ?formo .\n"
                + " ?lexicalentryo lemon:sense ?lexicalsenseo .\n"
                + " ?lexicalsenseo lemon:reference ?synseto .\n"
                + " \n"
                + "  ?lexicalsenset lemon:reference ?synseto .\n"
                + "  ?lexicalentryt lemon:sense ?lexicalsenset .\n"
                + "  ?lexicalentryt lemon:canonicalForm ?formt .\n"
                + "  ?itemt etvonto:hasForm ?formt .\n"
                + "  ?leviT etvonto:hasComponent ?itemt .\n"
                + "  ?leviT etvonto:hasRealization ?videoTarget .\n"
                + "\n"
                + "  ?videoTarget etvonto:url ?vtargeturl . \n"
                + "  ?videoTarget lemon:language ?langtarget . \n"
                + "\n"
                + "  ?leviT rdfs:label ?labelleviT .\n"
                + "\n"
                + "\n"
                + "FILTER regex(?langtarget, \"" + TargetLang + "\", \"i\") \n"
                + "\n"
                + "\n"
                + "}";
        return q;

    }
    
    
    
         
     public static String getQueryAll(){
        
        String qu = "PREFIX etvonto: <https://w3id.org/def/easytv#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "\n" +
            "\n" +
            "SELECT distinct ?video ?wle ?sle ?videoURL where {\n" +
            "\n" +
            " ?video a etvonto:Video . \n" +
            " ?video etvonto:url ?videoURL . \n" +
            "\n" +
            " ?wleURI etvonto:hasRealization ?video .\n" +
            " ?wleURI a etvonto:WrittenLinguisticExpression .\n" +
            " ?wleURI rdfs:label ?wle .\n" +
            "\n" +
            " ?sleURI etvonto:hasRealization ?video .\n" +
            " ?sleURI a etvonto:SignedLinguisticExpression .\n" +
            " ?sleURI rdfs:label ?sle .\n" +
            "\n" +
            "}\n" +
            "\n" +
            "ORDER BY (?wle)";

        return qu;

    }
     
     public static String getQueryAllVideosOfLanguage(String TargetLang){


        String s = "PREFIX etvonto: <https://w3id.org/def/easytv#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX lemon: <http://lemon-model.net/lemon#>\n" +
            "\n" +
            "\n" +
            "SELECT distinct ?video ?wle ?sle ?videoURL ?langvideo where {\n" +
            "\n" +
            " ?video a etvonto:Video . \n" +
            " ?video etvonto:url ?videoURL . \n" +
            " ?video lemon:language ?langvideo .\n" +
            "\n" +
            " ?wleURI etvonto:hasRealization ?video .\n" +
            " ?wleURI a etvonto:WrittenLinguisticExpression .\n" +
            " ?wleURI rdfs:label ?wle .\n" +
            "\n" +
            " ?sleURI etvonto:hasRealization ?video .\n" +
            " ?sleURI a etvonto:SignedLinguisticExpression .\n" +
            " ?sleURI rdfs:label ?sle .\n" +
            "\n" +
            "FILTER regex(?langvideo, \"" + TargetLang + "\", \"i\") \n" +
            "\n" +
            "\n" +
            "}\n" +
            "\n" +
            "ORDER BY (?wle)";

        return s;

     }
     
     
     
     public static String getAllVideosOfaKeyConcept(String key, String lang){
     
         String s="PREFIX etvonto: <https://w3id.org/def/easytv#>\n" +
"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
"PREFIX lemon: <http://lemon-model.net/lemon#>\n" +
"\n" +
"SELECT distinct ?vtarget  ?vtargeturl ?labelsle ?labelwle\n" +
"\n" +
"\n" +
" where {\n" +
"\n" +
"\n" +
" ?video a etvonto:Video . \n" +
" ?video etvonto:url ?videoURL . \n" +
" ?video lemon:language ?langvideo .\n" +
"\n" +
"\n" +
" ?wleURI etvonto:hasRealization ?video .\n" +
" ?wleURI rdfs:label ?wle .\n" +
"\n" +
"\n" +
" ?translation a etvonto:Translation .\n" +
"\n" +
" ?translation etvonto:relates ?wleURI .\n" +
" ?translation etvonto:relates ?letarget .\n" +
"\n" +
" ?letarget etvonto:hasRealization ?vtarget .\n" +
"\n" +
" ?vtarget lemon:language ?langtarget .\n" +
" ?vtarget etvonto:url ?vtargeturl . \n" +
"\n" +
" ?targetsle a etvonto:SignedLinguisticExpression .\n" +
" ?targetsle etvonto:hasRealization ?vtarget .\n" +
" ?targetsle rdfs:label ?labelsle .\n" +
"\n" +
" ?targetwle a etvonto:WrittenLinguisticExpression .\n" +
" ?targetwle etvonto:hasRealization ?vtarget .\n" +
" ?targetwle rdfs:label ?labelwle .\n" +
"\n" +
"\n" +
"FILTER regex(?wle, \""+key+"\", \"i\") \n" +
"FILTER regex(?langvideo, \""+lang+"\", \"i\")\n" +
"\n" +
"\n" +
"}";
    return s; 
     
     }
        
             
    
        
        

}
