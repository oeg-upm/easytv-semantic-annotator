/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.easytv.rdfy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pablo
 */
public class VideoCollection {
    
    
    
    private List <VideoTranslation> videos;
    
    public VideoCollection(){
    
        videos=new ArrayList();
    }

    public List<VideoTranslation> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoTranslation> videos) {
        this.videos = videos;
    }
    
    
    
}
