package com.swkoan.gallows.config;

/**
 *
 */
public interface GallowsConfig {
    
    void load();
    
    void save();
    
    ConfigStatus status();

    FolderConfig getRootLayerConfig();
    
    LayerConfig getLayerConfig(String name);
    
    DataSourceConfig getDataSourceConfigs();
}
