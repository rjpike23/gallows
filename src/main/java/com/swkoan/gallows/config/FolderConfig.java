package com.swkoan.gallows.config;

import com.swkoan.gallows.security.SecurityDescriptor;
import java.util.List;
import org.opengis.geometry.Envelope;

/**
 *
 */
public interface FolderConfig {

    SecurityDescriptor getSecurityDescriptor();
    
    FolderConfig getParent();

    String getTitle();

    List<FolderConfig> getChildren();

    List<Envelope> getBoundingBox();

    List<String> getCrs();

    Envelope getExGeographicBoundingBox();
}
