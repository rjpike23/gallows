package com.swkoan.gallows.gt.config;

import com.swkoan.gallows.config.StyleConfig;

/**
 *
 */
public class GTStyleConfig implements StyleConfig {

    private String name;
    private String sldUri;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSldUri() {
        return sldUri;
    }

    public void setSldUri(String sldUri) {
        this.sldUri = sldUri;
    }
}
