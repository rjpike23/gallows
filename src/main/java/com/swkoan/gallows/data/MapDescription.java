package com.swkoan.gallows.data;

import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.config.StyleConfig;
import java.awt.Rectangle;
import java.util.List;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 *
 */
public class MapDescription {

    private Rectangle imageDim;
    private List<LayerConfig> layers;
    private List<StyleConfig> styles;
    private CoordinateReferenceSystem crs;
    private Envelope boundingBox;
    
    public MapDescription(Rectangle imageDim, List<LayerConfig> layers,
            List<StyleConfig> styles, CoordinateReferenceSystem crs, Envelope boundingBox) {
        this.imageDim = imageDim;
        this.layers = layers;
        this.styles = styles;
        this.crs = crs;
        this.boundingBox = boundingBox;
    }

    public Rectangle getImageDim() {
        return imageDim;
    }

    public void setImageDim(Rectangle imageDim) {
        this.imageDim = imageDim;
    }

    public List<LayerConfig> getLayers() {
        return layers;
    }

    public void setLayers(List<LayerConfig> layers) {
        this.layers = layers;
    }

    public List<StyleConfig> getStyles() {
        return styles;
    }

    public void setStyles(List<StyleConfig> styles) {
        this.styles = styles;
    }

    public CoordinateReferenceSystem getCrs() {
        return crs;
    }

    public void setCrs(CoordinateReferenceSystem crs) {
        this.crs = crs;
    }

    public Envelope getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Envelope boundingBox) {
        this.boundingBox = boundingBox;
    }

}
