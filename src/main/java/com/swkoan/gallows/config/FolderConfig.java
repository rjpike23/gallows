package com.swkoan.gallows.config;

import java.util.List;
import org.opengis.geometry.Envelope;

/**
 *
 */
public interface FolderConfig {

    FolderConfig getParent();

    String getTitle();

    List<FolderConfig> getChildren();

    List<Envelope> getBoundingBox();

    List<String> getCrs();

    Envelope getExGeographicBoundingBox();
}
