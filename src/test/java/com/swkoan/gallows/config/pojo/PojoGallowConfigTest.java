/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.LayerConfig;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author rpike
 */
public class PojoGallowConfigTest {
    
    public PojoGallowConfigTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Test
    public void testLoad() {
        PojoGallowsConfig gc = new PojoGallowsConfig();
        assertTrue(gc.status().getCurrentState() == ConfigStatus.States.INIT);
        try {
            gc.getRootLayerConfig();
            fail("IllegalStateException should be thrown.");
        }
        catch(IllegalStateException e) {
            // Success.
        }
        gc.load();
        assertTrue(gc.status().getCurrentState() == ConfigStatus.States.LOADED);
    }
    
    @Test
    public void testLayerConfigs() {
        PojoGallowsConfig gc = new PojoGallowsConfig();
        gc.load();
        LayerConfig config = gc.getRootLayerConfig();
    }
}
