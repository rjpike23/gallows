package com.swkoan.gallows.gt.render;

import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.data.MapDescription;
import com.swkoan.gallows.gt.data.GeotoolsLayerFactory;
import com.swkoan.gallows.render.Renderer;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.map.MapViewport;
import org.geotools.renderer.lite.StreamingRenderer;

/**
 *
 */
public class GTStreamingRenderer implements Renderer {

    @Override
    public void render(MapDescription map, Graphics2D graphics) {
        MapViewport mapViewport = new MapViewport();
        mapViewport.setBounds(new ReferencedEnvelope(map.getBoundingBox()));
        mapViewport.setScreenArea(map.getImageDim());
        
        MapContent mapContent = new MapContent();
        mapContent.setViewport(mapViewport);
        
        GeotoolsLayerFactory layerFactory = new GeotoolsLayerFactory();
        List<Layer> gtLayers = new ArrayList<Layer>();
        for(LayerConfig layerConfig : map.getLayers()) {
            Layer layer = layerFactory.createLayer(layerConfig);
            if(layer != null) {
                gtLayers.add(layer);
            }
        }
        mapContent.addLayers(gtLayers);
        
        StreamingRenderer gtRenderer = new StreamingRenderer();
        gtRenderer.setMapContent(mapContent);
    }
}
