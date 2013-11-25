package com.swkoan.gallows.gt.config;

import com.swkoan.gallows.config.LayerDescriptor;
import com.swkoan.gallows.config.pojo.PojoLayerDescriptor;
import java.util.ArrayList;
import java.util.List;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class GTLayerDescriptor extends PojoLayerDescriptor implements LayerDescriptor {
    List<Envelope> bboxes = new ArrayList<Envelope>();

    public GTLayerDescriptor() {
    }

    public GTLayerDescriptor(String name, String title) {
        super(name, title);
    }

    @Override
    public List<Envelope> getBoundingBox() {
        return bboxes;
    }

    @Override
    public void setBoundingBox(List<Envelope> boundingBox) {
        bboxes = boundingBox;
    }
    
    
}
