package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.FolderConfig;

/**
 * Helper class, used with Spring.
 */
public class LayerConfigContainer {
    private FolderConfig layerConfig;

    public FolderConfig getRootLayerConfig() {
        return layerConfig;
    }

    public void setRootLayerConfig(FolderConfig layerConfig) {
        this.layerConfig = layerConfig;
    }
}
