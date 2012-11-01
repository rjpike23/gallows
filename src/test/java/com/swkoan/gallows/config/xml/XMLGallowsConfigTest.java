package com.swkoan.gallows.config.xml;

import com.swkoan.gallows.config.GallowsConfig;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 */
public class XMLGallowsConfigTest {
    
    public XMLGallowsConfigTest() {
    }
    
    @Test
    public void testIllegalLoadState() {
        GallowsConfig gc = new XMLGallowsConfig();
        try {
            gc.getLayerConfig();
            fail("Expected an IllegalStateException");
        }
        catch(IllegalStateException e) {
            // Success!
        }
    }
}
