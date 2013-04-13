package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.LayerConfig;
import java.io.IOException;
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
            DataSourceConfig dsCfg = layerConfig.getDataSourceConfig();
            GeotoolsDataSourceVisitor visitor = new GeotoolsDataSourceVisitor();
            dsCfg.accept(visitor);
            Map<String, Object> params = visitor.getParams();
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
