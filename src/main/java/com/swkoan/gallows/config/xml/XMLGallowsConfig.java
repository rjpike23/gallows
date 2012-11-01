package com.swkoan.gallows.config.xml;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.GallowsConfig;
import com.swkoan.gallows.config.LayerConfig;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class XMLGallowsConfig implements GallowsConfig {
    private ConfigStatus currentStatus = new ConfigStatus();
    private LayerConfig layerConfig;

    @Override
    public LayerConfig getLayerConfig() {
        if(currentStatus.getCurrentState()==ConfigStatus.States.INIT) {
            throw new IllegalStateException("Gallows configuration has not yet been loaded.");
        }
        return layerConfig;
    }

    @Override
    public void load() {
        
    }

    @Override
    public void save() {

    }

    @Override
    public ConfigStatus status() {
        return null;
    }
    
}
