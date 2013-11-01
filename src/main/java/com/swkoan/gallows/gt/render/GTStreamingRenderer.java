package com.swkoan.gallows.gt.render;

import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.data.DataSourceFactory;
import com.swkoan.gallows.data.MapDescription;
import com.swkoan.gallows.gt.data.GTLayerFactory;
import com.swkoan.gallows.render.Renderer;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.geotools.data.DataStore;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.map.MapViewport;
import org.geotools.renderer.lite.StreamingRenderer;

/**
 *
 */
public class GTStreamingRenderer implements Renderer {
    private Map<String, DataSourceFactory<DataStore>> dsFactories =
            new HashMap<String, DataSourceFactory<DataStore>>();
    
    @Override
    public void registerDataSourceFactory(DataSourceFactory<DataStore> factory) {
        dsFactories.put(factory.getDataSourceConfigClassname(), factory);
    }

    @Override
    public void render(MapDescription map, Graphics2D graphics) {
        // Setup data for Geotools
        MapViewport mapViewport = new MapViewport();
        ReferencedEnvelope bbox = new ReferencedEnvelope(map.getBoundingBox());
        mapViewport.setBounds(bbox);
        mapViewport.setScreenArea(map.getImageDim());
        MapContent mapContent = new MapContent();
        mapContent.setViewport(mapViewport);
        GTLayerFactory layerFactory = new GTLayerFactory(dsFactories);
        List<Layer> gtLayers = new ArrayList<Layer>();
        for(LayerConfig layerConfig : map.getLayers()) {
            Layer layer = layerFactory.createLayer(layerConfig);
            if(layer != null) {
                gtLayers.add(layer);
            }
        }
        mapContent.addLayers(gtLayers);
        
        // Render
        StreamingRenderer gtRenderer = new StreamingRenderer();
        gtRenderer.setMapContent(mapContent);
        gtRenderer.paint(graphics, map.getImageDim(), bbox);
        
        // Cleanup
        for(Layer layer: gtLayers) {
            layer.getFeatureSource().getDataStore().dispose();
        }
        mapContent.dispose();
    }
}
