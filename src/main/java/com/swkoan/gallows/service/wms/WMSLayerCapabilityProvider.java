package com.swkoan.gallows.service.wms;

import com.swkoan.gallows.config.FolderDescriptor;
import com.swkoan.gallows.config.LayerDescriptor;
import com.swkoan.gallows.config.StyleDescriptor;
import java.util.Set;
import net.opengis.wms.v_1_3_0.BoundingBox;
import net.opengis.wms.v_1_3_0.EXGeographicBoundingBox;
import net.opengis.wms.v_1_3_0.Layer;
import net.opengis.wms.v_1_3_0.Style;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.ReferenceIdentifier;

/**
 *
 */
public class WMSLayerCapabilityProvider {

    public Layer provide(FolderDescriptor layerConfig) {
        Layer layer = new Layer();

        if (layerConfig instanceof LayerDescriptor) {
            layer.setName(((LayerDescriptor) layerConfig).getName());
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
            if(env.getCoordinateReferenceSystem() != null) {
                Set<ReferenceIdentifier> names = env.getCoordinateReferenceSystem().getIdentifiers();
                for(ReferenceIdentifier name: names) {
                    bb.setCRS(name.toString());
                    break;
                }
            }
            layer.getBoundingBox().add(bb);
        }
        
        for(StyleDescriptor styleCfg: layerConfig.getStyles()) {
            Style style = new Style();
            style.setName(styleCfg.getName());
            style.setTitle(styleCfg.getTitle());
            layer.getStyle().add(style);
        }

        if (layerConfig.getChildren() != null) {
            for (FolderDescriptor child : layerConfig.getChildren()) {
                layer.getLayer().add(provide(child));
            }
        }
        return layer;
    }
}
