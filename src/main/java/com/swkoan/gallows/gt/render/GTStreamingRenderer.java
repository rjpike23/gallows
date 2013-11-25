package com.swkoan.gallows.gt.render;

import com.swkoan.gallows.GallowsException;
import com.swkoan.gallows.config.MapDescriptor;
import com.swkoan.gallows.config.factory.Factory;
import com.swkoan.gallows.render.Renderer;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.renderer.lite.StreamingRenderer;

/**
 *
 */
public class GTStreamingRenderer implements Renderer {

    private static final Logger LOG = Logger.getLogger(GTStreamingRenderer.class.getName());
    private Factory<MapContent, MapDescriptor> mapFactory;

    public Factory<MapContent, MapDescriptor> getMapFactory() {
        return mapFactory;
    }

    public void setMapFactory(Factory<MapContent, MapDescriptor> mapFactory) {
        this.mapFactory = mapFactory;
    }

    @Override
    public void render(MapDescriptor map, Graphics2D graphics) {
        MapContent mapContent = mapFactory.produce(map);       
        try {
            // Render
            LOG.info("Calling GeoTools to render map.");
            StreamingRenderer gtRenderer = new StreamingRenderer();
            Map<Object, Object> rendererParams = new HashMap<Object, Object>();
            rendererParams.put(StreamingRenderer.ADVANCED_PROJECTION_HANDLING_KEY, true);
            gtRenderer.setRendererHints(rendererParams);
            gtRenderer.setMapContent(mapContent);
            gtRenderer.paint(graphics, mapContent.getViewport().getScreenArea(),
                    mapContent.getMaxBounds());
        }
        catch (Exception e) {
            LOG.log(Level.SEVERE, "Unexpected exception while rendering.", e);
            throw new GallowsException("Unexpected exception while rendering.", e);
        }
        finally {
            // Cleanup
            for (Layer layer : mapContent.layers()) {
                layer.getFeatureSource().getDataStore().dispose();
            }
            mapContent.dispose();
        }
    }

}
