package com.swkoan.gallows.config;

/**
 *
 */
public interface RenderableLayerDescriptor extends Descriptor {

    LayerDescriptor<DataSourceDescriptor> getLayer();

    StyleDescriptor getStyle();
}
