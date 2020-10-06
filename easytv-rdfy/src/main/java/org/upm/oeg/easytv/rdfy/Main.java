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
public class Main {
    	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
                
                
                Querier q= new Querier("C:\\Users\\Pablo\\Desktop\\EasyTV\\projects\\easytv-resources\\rdfy\\");
                q.sendQueryAll();
               // q.sendQueryGetVideoTranslation("http://easytv.linkeddata.es/resource/Video/nlp-easytv.oeg-upm.net-video-en-9", "es");
	}
}
