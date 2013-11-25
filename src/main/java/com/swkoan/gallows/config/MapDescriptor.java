package com.swkoan.gallows.config;

import java.awt.Rectangle;
import java.util.List;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 *
 */
public interface MapDescriptor extends Descriptor {

    Rectangle getImageDim();

    List<RenderableLayerDescriptor> getLayers();

    CoordinateReferenceSystem getCrs();

    Envelope getBoundingBox();

    String getBackgroundColor();
}
