package com.swkoan.gallows.config;

import com.swkoan.gallows.security.SecurityDescriptor;
import java.util.List;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class LinkLayerConfig implements LayerConfig {
    String name;
    String title;
    LayerConfig target;

    @Override
    public FolderConfig getParent() {
        return target.getParent();
    }

    @Override
    public List<FolderConfig> getChildren() {
        return target.getChildren();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public List<Envelope> getBoundingBox() {
        return target.getBoundingBox();
    }

    @Override
    public List<String> getCrs() {
        return target.getCrs();
    }

    @Override
    public Envelope getExGeographicBoundingBox() {
        return target.getExGeographicBoundingBox();
    }

    @Override
    public DataSourceConfig getDataSourceConfig() {
        return target.getDataSourceConfig();
    }

    @Override
    public SecurityDescriptor getSecurityDescriptor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
