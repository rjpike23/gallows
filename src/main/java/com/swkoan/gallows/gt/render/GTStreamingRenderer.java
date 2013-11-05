package com.swkoan.gallows.gt.render;

import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.data.LayerFactory;
import com.swkoan.gallows.data.MapDescription;
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
    private LayerFactory<Layer, DataStore> layerFactory;

    @Override
    public void render(MapDescription map, Graphics2D graphics) {
        // Setup data for Geotools
        MapViewport mapViewport = new MapViewport();
        ReferencedEnvelope bbox = new ReferencedEnvelope(
                map.getBoundingBox().getLowerCorner().getOrdinate(0),
                map.getBoundingBox().getUpperCorner().getOrdinate(0),
                map.getBoundingBox().getLowerCorner().getOrdinate(1),
                map.getBoundingBox().getUpperCorner().getOrdinate(1),
                map.getCrs());
        mapViewport.setBounds(bbox);
        //mapViewport.setCoordinateReferenceSystem(map.getCrs());
        mapViewport.setScreenArea(map.getImageDim());
        MapContent mapContent = new MapContent();
        mapContent.setViewport(mapViewport);
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
        Map<Object, Object> rendererParams = new HashMap<Object, Object>();
        rendererParams.put(StreamingRenderer.ADVANCED_PROJECTION_HANDLING_KEY, true);
        gtRenderer.setRendererHints(rendererParams);
        gtRenderer.setMapContent(mapContent);
        try {
            gtRenderer.paint(graphics, map.getImageDim(), bbox);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        // Cleanup
        for(Layer layer: gtLayers) {
            layer.getFeatureSource().getDataStore().dispose();
        }
        mapContent.dispose();
    }

    @Override
    public void setLayerFactory(LayerFactory layerFactory) {
        this.layerFactory = layerFactory;
    }
}
