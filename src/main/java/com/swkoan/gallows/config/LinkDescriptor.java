package com.swkoan.gallows.config;

import com.swkoan.gallows.security.SecurityDescriptor;
import java.util.List;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class LinkDescriptor implements LayerDescriptor {
    String name;
    String title;
    LayerDescriptor target;

    @Override
    public FolderDescriptor getParent() {
        return target.getParent();
    }

    @Override
    public List<FolderDescriptor> getChildren() {
        return target.getChildren();
    }

    @Override
    public List<StyleDescriptor> getStyles() {
        return target.getStyles();
    }

    @Override
    public StyleDescriptor getStyle(String name) {
        return target.getStyle(name);
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
    public DataSourceDescriptor getDataSourceConfig() {
        return target.getDataSourceConfig();
    }

    @Override
    public SecurityDescriptor getSecurityDescriptor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
