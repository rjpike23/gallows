package com.swkoan.gallows.config;

import java.util.List;

/**
 *
 */
public interface GallowsConfig {
    
    void load();
    
    void save();
    
    ConfigStatus status();

    FolderConfig getRootLayerConfig();
    
    LayerConfig getLayerConfig(String name);
    
    List<StyleConfig> getStyleConfigs();
    
    StyleConfig getStyleConfig(String name);
    
    DataSourceConfig getDataSourceConfigs();
}
