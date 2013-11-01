package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.data.DataSourceFactory;
import java.io.IOException;
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
public class GTLayerFactory {
    private Map<String, DataSourceFactory<DataStore>> dsFactories;

    public GTLayerFactory(Map<String, DataSourceFactory<DataStore>> dsFactories) {
        this.dsFactories = dsFactories;
    }

    public Layer createLayer(LayerConfig layerConfig) {
        try {
            DataSourceConfig dsCfg = layerConfig.getDataSourceConfig();
            DataSourceFactory<DataStore> dsFactory = dsFactories.get(dsCfg.getClass().getName());
            if(dsFactory != null) {
                DataStore store = dsFactory.createDataSource(dsCfg);
                FeatureSource source = store.getFeatureSource(layerConfig.getName());
                Style style = SLD.createSimpleStyle(source.getSchema());
                Layer layer = new FeatureLayer(source, style);
                return layer;
            }
            else {
                //TODO: what to do here?
                return null;
            }
        }
        catch(IOException e) {
            //TODO: what to do here?
            return null;
        }
    }
}
