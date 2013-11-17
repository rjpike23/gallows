package com.swkoan.gallows.gt.config;

import com.swkoan.gallows.config.StyleConfig;

/**
 *
 */
public class GTStyleConfig implements StyleConfig {

    private String name;
    private String title;
    private String sldUri;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getSldUri() {
        return sldUri;
    }

    public void setSldUri(String sldUri) {
        this.sldUri = sldUri;
    }

}
