package com.swkoan.gallows.config.xml;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.DataSourceDescriptor;
import com.swkoan.gallows.config.GallowsConfig;
import com.swkoan.gallows.config.LayerDescriptor;

/**
 *
 */
public class XMLGallowsConfig implements GallowsConfig {
    private ConfigStatus currentStatus = new ConfigStatus();
    private LayerDescriptor layerConfig;

    @Override
    public LayerDescriptor getRootLayerConfig() {
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

    @Override
    public DataSourceDescriptor getDataSourceConfigs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LayerDescriptor getLayerConfig(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
