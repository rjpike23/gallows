package com.swkoan.gallows.data;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.LayerConfig;

/**
 *
 */
public interface LayerFactory<T extends Object, U extends Object> {

    void registerDataSourceFactory(DataSourceFactory factory);
    
    U createDataSource(DataSourceConfig dsConfig);

    T createLayer(LayerConfig layerConfig);
}
