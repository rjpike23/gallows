package com.swkoan.gallows.gt.config.factory;

import com.swkoan.gallows.GallowsException;
import com.swkoan.gallows.config.LayerDescriptor;
import com.swkoan.gallows.config.RenderableLayerDescriptor;
import com.swkoan.gallows.config.factory.Factory;
import com.swkoan.gallows.gt.config.GTDataSourceDescriptor;
import com.swkoan.gallows.gt.config.GTStyleDescriptor;
import java.io.IOException;
import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;

/**
 *
 */
public class GTLayerFactory implements Factory<Layer, RenderableLayerDescriptor> {

    private Factory<DataStore, GTDataSourceDescriptor> dsFactory;
    private Factory<Style, GTStyleDescriptor> styleFactory;

    public GTLayerFactory() {
        System.getProperties().setProperty("org.geotools.referencing.forceXY", "true");
    }

    public Factory<DataStore, GTDataSourceDescriptor> getDsFactory() {
        return dsFactory;
    }

    public void setDsFactory(Factory<DataStore, GTDataSourceDescriptor> dsFactory) {
        this.dsFactory = dsFactory;
    }

    public Factory<Style, GTStyleDescriptor> getStyleFactory() {
        return styleFactory;
    }

    public void setStyleFactory(Factory<Style, GTStyleDescriptor> styleFactory) {
        this.styleFactory = styleFactory;
    }

    @Override
    public Class<RenderableLayerDescriptor> getDescriptorClass() {
        return RenderableLayerDescriptor.class;
    }

    @Override
    public Layer produce(RenderableLayerDescriptor descriptor) {
        LayerDescriptor layerDesc = descriptor.getLayer();
        GTStyleDescriptor styleDesc = (GTStyleDescriptor) descriptor.getStyle();
        try {
            GTDataSourceDescriptor dsDesc = (GTDataSourceDescriptor) layerDesc.getDataSourceConfig();
            DataStore store = dsFactory.produce(dsDesc);
            if (store != null) {
                FeatureSource source = store.getFeatureSource(layerDesc.getName());
                Style style;
                if (styleDesc == null) {
                    style = SLD.createSimpleStyle(source.getSchema());
                }
                else {
                    style = styleFactory.produce(styleDesc);
                }
                Layer layer = new FeatureLayer(source, style);
                return layer;
            }
            else {
                throw new GallowsException("Could not create data source for layer "
                        + layerDesc.getName());
            }
        }
        catch (IOException e) {
            throw new GallowsException("Error getting GeoTools feature source for layer "
                    + layerDesc.getName(), e);
        }
    }
}
