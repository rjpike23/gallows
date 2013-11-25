package com.swkoan.gallows.gt.config.factory;

import com.swkoan.gallows.config.MapDescriptor;
import com.swkoan.gallows.config.RenderableLayerDescriptor;
import com.swkoan.gallows.config.factory.Factory;
import java.util.ArrayList;
import java.util.List;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.map.MapViewport;

/**
 *
 */
public class GTMapFactory implements Factory<MapContent, MapDescriptor> {

    private Factory<Layer, RenderableLayerDescriptor> layerFactory;

    public Factory<Layer, RenderableLayerDescriptor> getLayerFactory() {
        return layerFactory;
    }

    public void setLayerFactory(Factory<Layer, RenderableLayerDescriptor> layerFactory) {
        this.layerFactory = layerFactory;
    }

    @Override
    public Class<MapDescriptor> getDescriptorClass() {
        return MapDescriptor.class;
    }

    @Override
    public MapContent produce(MapDescriptor descriptor) {
        MapViewport mapViewport = new MapViewport();
        ReferencedEnvelope bbox = new ReferencedEnvelope(
                descriptor.getBoundingBox().getLowerCorner().getOrdinate(0),
                descriptor.getBoundingBox().getUpperCorner().getOrdinate(0),
                descriptor.getBoundingBox().getLowerCorner().getOrdinate(1),
                descriptor.getBoundingBox().getUpperCorner().getOrdinate(1),
                descriptor.getCrs());
        mapViewport.setBounds(bbox);
        mapViewport.setScreenArea(descriptor.getImageDim());

        MapContent mapContent = new MapContent();
        mapContent.setViewport(mapViewport);
        List<Layer> gtLayers = new ArrayList<Layer>();
        List<RenderableLayerDescriptor> layerDescs = descriptor.getLayers();
        for (RenderableLayerDescriptor desc : layerDescs) {
            Layer layer = layerFactory.produce(desc);
            if (layer != null) {
                gtLayers.add(layer);
            }
        }
        mapContent.addLayers(gtLayers);
        return mapContent;
    }
}
