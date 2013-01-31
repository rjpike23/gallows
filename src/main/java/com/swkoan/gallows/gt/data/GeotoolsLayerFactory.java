package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.LayerConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;

/**
 *
 */
public class GeotoolsLayerFactory {

    public Layer createLayer(LayerConfig layerConfig) {
        try {
            layerConfig.getDataSourceConfig();
            Map<String, Object> params = new HashMap<String, Object>();
            DataStore store = DataStoreFinder.getDataStore(params);
            FeatureSource source = store.getFeatureSource(layerConfig.getName());
            Style style = SLD.createSimpleStyle(source.getSchema());
            Layer layer = new FeatureLayer(source, style);
            return layer;
        }
        catch(IOException e) {
            //TODO: what to do here?
            return null;
        }
    }
}
