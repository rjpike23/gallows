package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.MapDescriptor;
import com.swkoan.gallows.config.RenderableLayerDescriptor;
import java.awt.Rectangle;
import java.util.List;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 *
 */
public class PojoMapDescriptor implements MapDescriptor {

    private Rectangle imageDim;
    private List<RenderableLayerDescriptor> layers;
    private CoordinateReferenceSystem crs;
    private Envelope boundingBox;
    private String backgroundColor;
    
    public PojoMapDescriptor(Rectangle imageDim, List<RenderableLayerDescriptor> layers,
            CoordinateReferenceSystem crs, Envelope boundingBox) {
        this.imageDim = imageDim;
        this.layers = layers;
        this.crs = crs;
        this.boundingBox = boundingBox;
    }

    @Override
    public Rectangle getImageDim() {
        return imageDim;
    }

    public void setImageDim(Rectangle imageDim) {
        this.imageDim = imageDim;
    }

    @Override
    public List<RenderableLayerDescriptor> getLayers() {
        return layers;
    }

    public void setLayers(List<RenderableLayerDescriptor> layers) {
        this.layers = layers;
    }
    
    @Override
    public CoordinateReferenceSystem getCrs() {
        return crs;
    }

    public void setCrs(CoordinateReferenceSystem crs) {
        this.crs = crs;
    }

    @Override
    public Envelope getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Envelope boundingBox) {
        this.boundingBox = boundingBox;
    }

    @Override
    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
