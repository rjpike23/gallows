package com.swkoan.gallows.data;

import com.swkoan.gallows.config.DataSourceConfig;

/**
 *
 */
public interface DataSourceFactory<T extends Object> {
    
    void setLayerFactory(LayerFactory layerFactory);
    
    T createDataSource(DataSourceConfig dsConfig);

    String getDataSourceConfigClassname();
}
