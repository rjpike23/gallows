package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.FolderDescriptor;

/**
 * Helper class, used with Spring.
 */
public class LayerDescContainer {
    private FolderDescriptor layerConfig;

    public FolderDescriptor getRootLayerConfig() {
        return layerConfig;
    }

    public void setRootLayerConfig(FolderDescriptor layerConfig) {
        this.layerConfig = layerConfig;
    }
}
