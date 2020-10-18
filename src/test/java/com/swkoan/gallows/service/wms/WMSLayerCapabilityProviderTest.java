package com.swkoan.gallows.service.wms;

import com.swkoan.gallows.config.LayerDescriptor;
import com.swkoan.gallows.config.pojo.BoundingBox;
import com.swkoan.gallows.config.pojo.PojoLayerDescriptor;
import java.util.Arrays;
import java.util.Collections;
import net.opengis.wms.v_1_3_0.EXGeographicBoundingBox;
import net.opengis.wms.v_1_3_0.Layer;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class WMSLayerCapabilityProviderTest {

    public WMSLayerCapabilityProviderTest() {
    }

    @Test
    public void testProvide() {
        BoundingBox testBox = new BoundingBox(1.0, 2.0, 3.0, 4.0);
        WMSLayerCapabilityProvider provider = new WMSLayerCapabilityProvider();
        LayerDescriptor lc = new PojoLayerDescriptor("Test.Layer", "Test Layer",
                Arrays.asList(new String[]{"CRS1"}),
                testBox,
                Collections.EMPTY_LIST);
        Layer layer = provider.provide(lc);
        assertEquals("Test.Layer", layer.getName());
        assertEquals("Test Layer", layer.getTitle());
        assertTrue(layer.getCRS().contains("CRS1"));
        assertEquals(1, layer.getCRS().size());
        EXGeographicBoundingBox geoBB = layer.getEXGeographicBoundingBox();
        assertEquals(1.0, geoBB.getSouthBoundLatitude(), 0);
        assertEquals(2.0, geoBB.getWestBoundLongitude(), 0);
        assertEquals(3.0, geoBB.getNorthBoundLatitude(), 0);
        assertEquals(4.0, geoBB.getEastBoundLongitude(), 0);
    }

    @Test
    public void testProvideNull() {
        try {
            WMSLayerCapabilityProvider provider = new WMSLayerCapabilityProvider();
            Layer layer = provider.provide(null);
            fail("Expected null pointer exception");
        } catch (NullPointerException e) {
        }
    }
}
