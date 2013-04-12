package com.swkoan.gallows.service.wms;

import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.config.pojo.BoundingBox;
import com.swkoan.gallows.config.pojo.PojoLayerConfig;
import java.util.Arrays;
import java.util.Collections;
import net.opengis.wms.Layer;
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
        WMSLayerCapabilityProvider provider = new WMSLayerCapabilityProvider();
        LayerConfig lc = new PojoLayerConfig("Test.Layer", "Test Layer",
                Arrays.asList(new String[]{"CRS1"}),
                new BoundingBox(0, 0, 0, 0),
                Collections.EMPTY_LIST);
        Layer layer = provider.provide(lc);
    }

    @Test
    public void testProvideNull() {
        try {
            WMSLayerCapabilityProvider provider = new WMSLayerCapabilityProvider();
            Layer layer = provider.provide(null);
            fail("Expected null pointer exception");
        }
        catch (NullPointerException e) {
        }
    }
}
