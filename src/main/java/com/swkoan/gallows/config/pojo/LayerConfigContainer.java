package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.LayerConfig;

/**
 * Helper class, used with Spring.
 */
public class LayerConfigContainer {
    private LayerConfig layerConfig;

    public LayerConfig getRootLayerConfig() {
        return layerConfig;
    }

    public void setRootLayerConfig(LayerConfig layerConfig) {
        this.layerConfig = layerConfig;
    }
}
