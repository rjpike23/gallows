package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.GallowsException;
import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.data.DataSourceFactory;
import com.swkoan.gallows.data.LayerFactory;
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
public class GTLayerFactory implements LayerFactory<Layer, DataStore> {

    private Map<String, DataSourceFactory<DataStore>> dsFactories =
            new HashMap<String, DataSourceFactory<DataStore>>();

    public GTLayerFactory() {
        System.getProperties().setProperty("org.geotools.referencing.forceXY", "true");
    }

    @Override
    public void registerDataSourceFactory(DataSourceFactory factory) {
        dsFactories.put(factory.getDataSourceConfigClassname(), factory);
    }

    @Override
    public DataStore createDataSource(DataSourceConfig dsCfg) {
        DataStore store = null;
        DataSourceFactory<DataStore> dsFactory = dsFactories.get(dsCfg.getClass().getName());
        if (dsFactory != null) {
            store = dsFactory.createDataSource(dsCfg);
        }
        return store;
    }

    @Override
    public Layer createLayer(LayerConfig layerConfig) {
        try {
            DataSourceConfig dsCfg = layerConfig.getDataSourceConfig();
            DataStore store = createDataSource(dsCfg);
            if (store != null) {
                FeatureSource source = store.getFeatureSource(layerConfig.getName());
                Style style = SLD.createSimpleStyle(source.getSchema());
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
