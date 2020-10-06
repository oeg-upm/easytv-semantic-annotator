/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.easytv.rdfy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Timestamp;

/**
 *
 * @author Pablo
 */
public class MapperFile {
    
    
     public static String generateTTLFileContent(String Path) {

         String completePath = Path;

         
         String val="@prefix rr: <http://www.w3.org/ns/r2rml#>.\n" +
"@prefix rml: <http://semweb.mmlab.be/ns/rml#>.\n" +
"@prefix ql: <http://semweb.mmlab.be/ns/ql#>.\n" +
"@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.\n" +
"@prefix lemon: <http://lemon-model.net/lemon#>.\n" +
"@prefix etvonto: <https://w3id.org/def/easytv#>.\n" +
"@prefix etvdata: <http://easytv.linkeddata.es/resource/>.\n" +
"@prefix skos: <http://www.w3.org/2004/02/skos/core#>.\n" +
"@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.\n" +
"@base <http://easytv.linkeddata.es/resource/mapping>.\n" +
"\n" +
"\n" +
"<#mappingVideo>\n" +
"  	rml:logicalSource [\n" +
"    	rml:source \""+completePath+"\";\n" +  
"    	rml:referenceFormulation ql:JSONPath;\n" +
"    	rml:iterator \"$\"\n" +
"  	];\n" +
"\n" +
"\n" +
"  	rr:subjectMap [\n" +
"    	rr:template \"http://easytv.linkeddata.es/resource/Video/{UrlID}\";\n" +
"    	rr:class etvonto:Video ;\n" +
"    	rr:graphMap [ rr:template \"http://easytv.linkeddata.es/graph/{UrlID}\" ]\n" +
"  	];\n" +
"\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate lemon:language;\n" +
"    rr:objectMap [\n" +
"      rml:reference \"Language\" \n" +
"    ]\n" +
"  ];\n" +
"\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate etvonto:url;\n" +
"    rr:objectMap [\n" +
"      rml:reference \"Url\" \n" +
"    ]\n" +
"  ]\n" +
" .\n" +
"\n" +
"\n" +
"<#mappingSignedLinguisticExpression>\n" +
"  	rml:logicalSource [\n" +
"    	rml:source \""+completePath+"\";\n" +
"    	rml:referenceFormulation ql:JSONPath;\n" +
"    	rml:iterator \"$\"\n" +
"  	];\n" +
"\n" +
"\n" +
"  	rr:subjectMap [\n" +
"    	rr:template \"http://easytv.linkeddata.es/resource/SignedLinguisticExpression/{Sls}{UrlID}\";\n" +
"    	rr:class etvonto:SignedLinguisticExpression ;\n" +
"    	rr:graphMap [ rr:template \"http://easytv.linkeddata.es/graph/{UrlID}\" ]\n" +
"\n" +
"  	];\n" +
"\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate etvonto:hasRealization;\n" +
"    rr:objectMap [\n" +
"      rr:parentTriplesMap <#mappingVideo>; #check\n" +
"    ]\n" +
"  ];\n" +
"\n" +
"  \n" +
"  rr:predicateObjectMap [\n" +
"   rr:predicate rdfs:label;\n" +
"   rr:objectMap [\n" +
"     rml:reference \"Sls\"\n" +
"   ]\n" +
" ];\n" +
" \n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate etvonto:decomposition;\n" +
"    rr:objectMap [\n" +
"      rr:template \"http://easytv.linkeddata.es/resource/LinguisticExpressionItem/{Sls}{UrlID}1\";\n" +
"      rr:termType rr:IRI\n" +
"    ]\n" +
"  ]\n" +
"\n" +
" .\n" +
"\n" +
"	<#mappingWrittenLinguisticExpression>\n" +
"  	rml:logicalSource [\n" +
"    	rml:source \""+completePath+"\";\n" +
"    	rml:referenceFormulation ql:JSONPath;\n" +
"    	rml:iterator \"$\"\n" +
"  	];\n" +
"\n" +
"  	rr:subjectMap [\n" +
"    	rr:template \"http://easytv.linkeddata.es/resource/WrittenLinguisticExpression/{Nls}{UrlID}\";\n" +
"    	rr:class etvonto:WrittenLinguisticExpression ;\n" +
"		rr:graphMap [ rr:template \"http://easytv.linkeddata.es/graph/{UrlID}\" ]\n" +
"\n" +
"  	];\n" +
"\n" +
"	\n" +
"	rr:predicateObjectMap [\n" +
"   rr:predicate rdfs:label;\n" +
"   rr:objectMap [\n" +
"     rml:reference \"Nls\"\n" +
"	]\n" +
"	];\n" +
"	\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate etvonto:hasRealization;\n" +
"    rr:objectMap [\n" +
"      rr:parentTriplesMap <#mappingVideo>; #check\n" +
"    ]\n" +
"  ]\n" +
" .\n" +
"\n" +
"\n" +
"<#mappingSegment>\n" +
"  	rml:logicalSource [\n" +
"    	rml:source \""+completePath+"\";\n" +
"    	rml:referenceFormulation ql:JSONPath;\n" +
"    	rml:iterator \"$.Segments[*]\"\n" +
"  	];\n" +
"\n" +
"\n" +
"  	rr:subjectMap [\n" +
"    	rr:template \"http://easytv.linkeddata.es/resource/LinguisticExpressionItem/{Sls}{UrlID}{Order}\";\n" +
"    	rr:class etvonto:LinguisticExpressionItem \n" +
"  	];\n" +
"\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate etvonto:nextItem;\n" +
"    rr:objectMap [\n" +
"      rr:template \"http://easytv.linkeddata.es/resource/LinguisticExpressionItem/{Sls}{UrlID}{NextOrder}\";\n" +
"      rr:termType rr:IRI\n" +
"    ]\n" +
"  ];\n" +
"\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate etvonto:hasForm;\n" +
"    rr:objectMap [\n" +
"      rr:template \"http://easytv.linkeddata.es/resource/LinguisticExpressionItem/{Sls}{UrlID}{Order}{Word}\";\n" +
"      rr:termType rr:IRI\n" +
"    ]\n" +
"  ]\n" +
"\n" +
" .\n" +
"\n" +
"<#mappingForm>\n" +
"  	rml:logicalSource [\n" +
"    	rml:source \""+completePath+"\";\n" +
"    	rml:referenceFormulation ql:JSONPath;\n" +
"    	rml:iterator \"$.Segments[*]\"\n" +
"  	];\n" +
"\n" +
"\n" +
"  	rr:subjectMap [\n" +
"    	rr:template \"http://easytv.linkeddata.es/resource/Form/{Sls}{UrlID}{Order}{Word}\";\n" +
"    	rr:class lemon:Form \n" +
"  	];\n" +
"\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate lemon:writtenRep;\n" +
"    rr:objectMap [\n" +
"      rml:reference \"Word\";\n" +
"    ]\n" +
"  ]\n" +
"\n" +
" .\n" +
"\n" +
"<#mappingLexicalEntry>\n" +
"  	rml:logicalSource [\n" +
"    	rml:source \""+completePath+"\";\n" +
"    	rml:referenceFormulation ql:JSONPath;\n" +
"    	rml:iterator \"$.Segments[*]\"\n" +
"  	];\n" +
"\n" +
"\n" +
"  	rr:subjectMap [\n" +
"    	rr:template \"http://easytv.linkeddata.es/resource/LexicalEntry/{Sls}{UrlID}{Order}{Lemma}\";\n" +
"    	rr:class lemon:LexicalEntry \n" +
"  	];\n" +
"\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate lemon:language;\n" +
"    rr:objectMap [\n" +
"      rml:reference \"Language\";\n" +
"    ]\n" +
"  ];\n" +
"\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate lemon:canonicalForm;\n" +
"    rr:objectMap [\n" +
"      rr:template \"http://easytv.linkeddata.es/resource/Form/{Sls}{UrlID}{Order}{Word}\";\n" +
"      rr:termType rr:IRI\n" +
"    ]\n" +
"  ];\n" +
"\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate lemon:sense;\n" +
"    rr:objectMap [\n" +
"      rml:reference \"Synsets[*].Sense\" ;        \n" +
"      rr:termType rr:IRI\n" +
"    ]\n" +
"  ]\n" +
"\n" +
" .\n" +
"\n" +
"\n" +
"\n" +
" <#mappingSense>\n" +
"  	rml:logicalSource [\n" +
"    	rml:source \""+completePath+"\";\n" +
"    	rml:referenceFormulation ql:JSONPath;\n" +
"    	rml:iterator \"$.Segments[*].Synsets[*]\"\n" +
"  	];\n" +
"\n" +
"\n" +
"  	rr:subjectMap [\n" +
"    	rml:reference \"Sense\";\n" +
"    	rr:class lemon:LexicalSense \n" +
"  	];\n" +
"\n" +
"  rr:predicateObjectMap [\n" +
"    rr:predicate lemon:reference;\n" +
"    rr:objectMap [\n" +
"        rml:reference \"Url\";\n" +
"        rr:termType rr:IRI\n" +
"    ]\n" +
"  ]\n" +
"\n" +
" .\n" +
"\n" +
" <#mappingSynset>\n" +
"  	rml:logicalSource [\n" +
"    	rml:source \""+completePath+"\";\n" +
"    	rml:referenceFormulation ql:JSONPath;\n" +
"    	rml:iterator \"$.Segments[*].Synsets[*]\"\n" +
"  	];\n" +
"\n" +
"\n" +
"  	rr:subjectMap [\n" +
"    	rml:reference \"Url\";\n" +
"    	rr:class skos:Concept \n" +
"  	]\n" +
"\n" +
" .";
         
         
         
         return val ;
        
       
    }
     
     
     
      public static void createFile(String PathToFile, String Content) throws Exception{
    
          File fileDir = new File(PathToFile);

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileDir), "UTF8"));

        out.append(Content);

        out.flush();
        out.close();


    
    
    }
    
    
    
     
    
}
