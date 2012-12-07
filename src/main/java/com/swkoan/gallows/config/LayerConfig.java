package com.swkoan.gallows.config;

import java.util.List;
import org.opengis.geometry.Envelope;

/**
 *
 */
public interface LayerConfig {
    LayerConfig getParent();
    
    List<LayerConfig> getChildren();

    String getName();
    
    String getTitle();

    List<Envelope> getBoundingBox();

    List<String> getCrs();

    Envelope getExGeographicBoundingBox();
}
