package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.DataSourceDescriptor;
import com.swkoan.gallows.config.LayerDescriptor;
import com.swkoan.gallows.config.RenderableLayerDescriptor;
import com.swkoan.gallows.config.StyleDescriptor;

/**
 *
 */
public class PojoRenderableLayerDescriptor implements RenderableLayerDescriptor {
    
    LayerDescriptor<DataSourceDescriptor> layerDesc;
    StyleDescriptor styleDesc;

    public PojoRenderableLayerDescriptor(LayerDescriptor<DataSourceDescriptor> layerDesc, StyleDescriptor styleDesc) {
        this.layerDesc = layerDesc;
        this.styleDesc = styleDesc;
    }

    @Override
    public LayerDescriptor<DataSourceDescriptor> getLayer() {
        return layerDesc;
    }

    @Override
    public StyleDescriptor getStyle() {
        return styleDesc;
    }
    
}
