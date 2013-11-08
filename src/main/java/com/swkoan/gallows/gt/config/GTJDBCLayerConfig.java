package com.swkoan.gallows.gt.config;

import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.config.pojo.PojoLayerConfig;
import java.util.ArrayList;
import java.util.List;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class GTJDBCLayerConfig extends PojoLayerConfig implements LayerConfig {
    List<Envelope> bboxes = new ArrayList<Envelope>();

    public GTJDBCLayerConfig() {
    }

    public GTJDBCLayerConfig(String name, String title) {
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
