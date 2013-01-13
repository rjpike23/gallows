package com.swkoan.gallows.service.wms;

import com.swkoan.gallows.config.LayerConfig;
import net.opengis.wms.BoundingBox;
import net.opengis.wms.EXGeographicBoundingBox;
import net.opengis.wms.Layer;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class WMSLayerCapabilityProvider {

    public Layer provide(LayerConfig layerConfig) {
        Layer layer = new Layer();

        layer.setName(layerConfig.getName());
        layer.setTitle(layerConfig.getTitle());
        for (String crs : layerConfig.getCrs()) {
            layer.getCRS().add(crs);
        }

        EXGeographicBoundingBox xgbb = new EXGeographicBoundingBox();
        DirectPosition upper = layerConfig.getExGeographicBoundingBox().getUpperCorner();
        DirectPosition lower = layerConfig.getExGeographicBoundingBox().getLowerCorner();
        xgbb.setEastBoundLongitude(upper.getOrdinate(0));
        xgbb.setNorthBoundLatitude(upper.getOrdinate(1));
        xgbb.setWestBoundLongitude(lower.getOrdinate(0));
        xgbb.setSouthBoundLatitude(lower.getOrdinate(1));
        layer.setEXGeographicBoundingBox(xgbb);

        for (Envelope env : layerConfig.getBoundingBox()) {
            BoundingBox bb = new BoundingBox();
            bb.setMaxx(upper.getOrdinate(0));
            bb.setMaxy(upper.getOrdinate(1));
            bb.setMinx(lower.getOrdinate(0));
            bb.setMiny(lower.getOrdinate(1));
            bb.setCRS(env.getCoordinateReferenceSystem().toWKT());
            layer.getBoundingBox().add(bb);
        }

        if (layerConfig.getChildren() != null) {
            for (LayerConfig child : layerConfig.getChildren()) {
                layer.getLayer().add(provide(child));
            }
        }
        return layer;
    }
}
