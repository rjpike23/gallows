package com.swkoan.gallows.config;

import com.swkoan.gallows.security.SecurityDescriptor;
import java.util.List;
import org.opengis.geometry.Envelope;

/**
 *
 */
public interface FolderDescriptor extends Descriptor {

    SecurityDescriptor getSecurityDescriptor();
    
    FolderDescriptor getParent();

    String getTitle();

    List<FolderDescriptor> getChildren();
    
    List<StyleDescriptor> getStyles();
    
    StyleDescriptor getStyle(String name);

    List<Envelope> getBoundingBox();

    List<String> getCrs();

    Envelope getExGeographicBoundingBox();
}
