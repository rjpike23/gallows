package com.swkoan.gallows.data;

import com.swkoan.gallows.config.LayerConfig;
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
    private CoordinateReferenceSystem crs;
    private Envelope boundingBox;
    
    public MapDescription(Rectangle imageDim, List<LayerConfig> layers, CoordinateReferenceSystem crs, Envelope boundingBox) {
        this.imageDim = imageDim;
        this.layers = layers;
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
