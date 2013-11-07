package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.FolderConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class PojoGallowsConfigTest {
    
    ApplicationContext appCtx;
    public PojoGallowsConfigTest() {
    }

    @Before
    public void setupAppContext() {
        appCtx = new ClassPathXmlApplicationContext("testAppContext.xml");
    }

    @After
    public void destroyAppContext() {
        appCtx = null;
    }

    @Test
    public void testLoad() {
        PojoGallowsConfig gc = (PojoGallowsConfig) appCtx.getBean("gallowsConfig");
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
        FolderConfig config = gc.getRootLayerConfig();
        assertNotNull(config);
    }
}
