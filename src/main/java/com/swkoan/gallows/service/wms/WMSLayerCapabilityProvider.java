package com.swkoan.gallows.service.wms;

import com.swkoan.gallows.config.FolderConfig;
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

    public Layer provide(FolderConfig layerConfig) {
        Layer layer = new Layer();

        if (layerConfig instanceof LayerConfig) {
            layer.setName(((LayerConfig) layerConfig).getName());
        }
        layer.setTitle(layerConfig.getTitle());
        for (String crs : layerConfig.getCrs()) {
            layer.getCRS().add(crs);
        }

        if (layerConfig.getExGeographicBoundingBox() != null) {
            EXGeographicBoundingBox xgbb = new EXGeographicBoundingBox();
            DirectPosition upper = layerConfig.getExGeographicBoundingBox().getUpperCorner();
            DirectPosition lower = layerConfig.getExGeographicBoundingBox().getLowerCorner();
            xgbb.setEastBoundLongitude(upper.getOrdinate(0));
            xgbb.setNorthBoundLatitude(upper.getOrdinate(1));
            xgbb.setWestBoundLongitude(lower.getOrdinate(0));
            xgbb.setSouthBoundLatitude(lower.getOrdinate(1));
            layer.setEXGeographicBoundingBox(xgbb);
        }
        
        for (Envelope env : layerConfig.getBoundingBox()) {
            BoundingBox bb = new BoundingBox();
            bb.setMaxx(env.getUpperCorner().getOrdinate(0));
            bb.setMaxy(env.getUpperCorner().getOrdinate(1));
            bb.setMinx(env.getLowerCorner().getOrdinate(0));
            bb.setMiny(env.getLowerCorner().getOrdinate(1));
            bb.setCRS(env.getCoordinateReferenceSystem().toWKT());
            layer.getBoundingBox().add(bb);
        }

        if (layerConfig.getChildren() != null) {
            for (FolderConfig child : layerConfig.getChildren()) {
                layer.getLayer().add(provide(child));
            }
        }
        return layer;
    }
}
