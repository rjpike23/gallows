package com.swkoan.gallows.config;

import java.util.List;
import net.opengis.wms.Layer;

/**
 *
 */
public interface LayerConfig {
    LayerConfig getParent();
    
    List<LayerConfig> getChildren();
    
    void provideWMSCapabilitiesLayer(Layer layer);
}
