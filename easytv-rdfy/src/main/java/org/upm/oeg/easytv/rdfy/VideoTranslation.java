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
public class VideoTranslation {
    
    
      private String   video ;
      private String   wle ;
      private String   sle;
      private String   videoURL; 
    
    
     public  VideoTranslation(String a, String b, String c, String d) {

        this.video=a;
        this.wle=b;
        this.sle=c;
        this.videoURL=d;
    }
     
     

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getWle() {
        return wle;
    }

    public void setWle(String wle) {
        this.wle = wle;
    }

    public String getSle() {
        return sle;
    }

    public void setSle(String sle) {
        this.sle = sle;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
    
    
}
