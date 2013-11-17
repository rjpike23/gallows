package com.swkoan.gallows.data;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.config.StyleConfig;

/**
 *
 */
public interface LayerFactory<T extends Object, U extends Object, V extends DataSourceConfig> {

    void registerDataSourceFactory(DataSourceFactory<U, V> factory);
    
    U createDataSource(V dsConfig);

    T createLayer(LayerConfig<V> layerConfig, StyleConfig styleConfig);
}
