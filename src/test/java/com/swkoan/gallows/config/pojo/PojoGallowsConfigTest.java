/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.FolderConfig;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author rpike
 */
public class PojoGallowsConfigTest {
    
    public PojoGallowsConfigTest() {
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
        FolderConfig config = gc.getRootLayerConfig();
    }
}
