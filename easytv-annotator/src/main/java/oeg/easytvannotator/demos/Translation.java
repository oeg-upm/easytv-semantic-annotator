/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.demos;

import com.babelscape.util.UniversalPOS;
import com.google.common.collect.Multimap;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelNetQuery;
import it.uniroma1.lcl.babelnet.BabelNetUtils;
import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetComparator;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.util.ScoredItem;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author pcalleja
 */
public class Translation {
    
    
    
    public static void main (String [] args) throws IOException{
    
        
        //testTranslations("car",Language.EN,Language.ES);
        testTra();
        
    }
    
    
    public static void testTra() throws IOException {
    	BabelNet bn = BabelNet.getInstance();
		BabelNetQuery query = new BabelNetQuery.Builder("book")
                        .POS(UniversalPOS.NOUN)
			.from(Language.EN)
                        //.sources(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN,BabelSenseSource.WIKIDATA))
			//.to(Arrays.asList(Language.EN))
                       
			.build();
                
        List<BabelSynset> synsets =  bn.getSynsets(query);
        
       Collections.sort(synsets, new BabelSynsetComparator("book"));
        
        //Collections.sort(synsets, new BabelSynsetComparator("book"));        
        for (BabelSynset synset : synsets) {
            System.out.println("Synset ID: " + synset.getID()+  "; TYPE: " + synset.getType()+  "  MAIN LEMMA: " + synset.getMainSense(Language.EN) + synset.getMainSense(Language.ES) );
        }

    }
    
    public static void test(){
      BabelNet bn = BabelNet.getInstance();
        BabelNetQuery query = new BabelNetQuery.Builder("car")
	.from(Language.EN)
	.build();
        List<BabelSynset> synsets = bn.getSynsets(query);
        // List<BabelSynset> synsets = bnInstance.getSynsets(word, lang, pos);
        Collections.sort(synsets, new BabelSynsetComparator("car"));
        for (BabelSynset synset : synsets) {
            System.out.print("  =>(" + synset.getID() + ") SOURCE: " + synset.getSenseSources() //get.getSynsetSource()
                    + "; TYPE: " + synset.getType()
                    + "; WN SYNSET: " + synset.getWordNetOffsets() + ";\n"
                    + "  MAIN LEMMA: " + synset.getMainSense(Language.EN)
                    + ";\n  IMAGES: " + synset.getImages()
                    + ";\n  CATEGORIES: " + synset.getCategories()
                    + ";\n  SENSES : { ");
            for (BabelSense sense : synset.getSenses(Language.EN)) {
                System.out.print(sense.toString() + " " + sense.getPronunciations() + " ");
            }
            System.out.println("}\n  -----");
        }
        
        
    
    }
    
    public static void testTranslations(String lemma, Language languageToSearch,Language... languagesToPrint) throws IOException
	{
               System.out.println(languagesToPrint.length);
		List<Language> allowedLanguages = Arrays.asList(languagesToPrint);
		Multimap<Language, ScoredItem<String>> translations =
			BabelNetUtils.getTranslations(languageToSearch, lemma);

                System.out.println(translations.size());
                
		System.out.println("TRANSLATIONS FOR " + lemma);
		for (Language language : translations.keySet())
		{
			if (allowedLanguages.contains(language))				
                    System.out.println("\t"+language+"=>"+translations.get(language));
		}
	}
    
}
