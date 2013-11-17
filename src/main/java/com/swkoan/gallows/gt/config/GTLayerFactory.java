package com.swkoan.gallows.gt.config;

import com.swkoan.gallows.config.GallowsException;
import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.config.StyleConfig;
import com.swkoan.gallows.data.DataSourceFactory;
import com.swkoan.gallows.data.LayerFactory;
import com.swkoan.gallows.data.StyleFactory;
import com.swkoan.gallows.gt.data.GTDataSourceConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;

/**
 *
 */
public class GTLayerFactory implements LayerFactory<Layer, DataStore, GTDataSourceConfig> {

    private Map<String, DataSourceFactory<DataStore, GTDataSourceConfig>> dsFactories =
            new HashMap<String, DataSourceFactory<DataStore, GTDataSourceConfig>>();
    private StyleFactory<Style, StyleConfig> styleFactory;

    public GTLayerFactory() {
        System.getProperties().setProperty("org.geotools.referencing.forceXY", "true");
    }

    @Override
    public void registerDataSourceFactory(DataSourceFactory factory) {
        dsFactories.put(factory.getDataSourceConfigClassname(), factory);
    }
    
    public void setStyleFactory(StyleFactory factory) {
        styleFactory = factory;
    }

    @Override
    public DataStore createDataSource(GTDataSourceConfig dsCfg) {
        DataStore store = null;
        DataSourceFactory<DataStore, GTDataSourceConfig> dsFactory = dsFactories.get(dsCfg.getClass().getName());
        if (dsFactory != null) {
            store = dsFactory.createDataSource(dsCfg);
        }
        return store;
    }

    @Override
    public Layer createLayer(LayerConfig<GTDataSourceConfig> layerConfig, StyleConfig styleConfig) {
        try {
            GTDataSourceConfig dsCfg = layerConfig.getDataSourceConfig();
            DataStore store = createDataSource(dsCfg);
            if (store != null) {
                FeatureSource source = store.getFeatureSource(layerConfig.getName());
                Style style = null;
                if (styleConfig == null) {
                    style = SLD.createSimpleStyle(source.getSchema());
                }
                else {
                    style = styleFactory.createStyle(styleConfig);
                }
                Layer layer = new FeatureLayer(source, style);
                return layer;
            }
            else {
                throw new GallowsException("Could not create data source for layer "
                        + layerConfig.getName());
            }
        }
        catch (IOException e) {
            throw new GallowsException("Error getting GeoTools feature source for layer "
                    + layerConfig.getName(), e);
        }
    }
}
