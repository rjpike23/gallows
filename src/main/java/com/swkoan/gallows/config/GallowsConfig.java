package com.swkoan.gallows.config;

/**
 *
 */
public interface GallowsConfig {
    
    void load();
    
    void save();
    
    ConfigStatus status();

    FolderDescriptor getRootLayerConfig();
    
    LayerDescriptor getLayerConfig(String name);
    
    DataSourceDescriptor getDataSourceConfigs();
}
