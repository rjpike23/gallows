package com.swkoan.gallows.config;

import java.util.List;

/**
 *
 */
public interface GallowsConfig {
    
    void load();
    
    void save();
    
    ConfigStatus status();

    LayerConfig getRootLayerConfig();
    
    DataSourceConfig getDataSourceConfigs();
}
