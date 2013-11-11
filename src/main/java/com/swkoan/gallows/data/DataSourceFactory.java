package com.swkoan.gallows.data;

import com.swkoan.gallows.config.DataSourceConfig;

/**
 *
 */
public interface DataSourceFactory<T extends Object, U extends DataSourceConfig> {
    
    void setLayerFactory(LayerFactory layerFactory);
    
    T createDataSource(U dsConfig);

    String getDataSourceConfigClassname();
}
