package com.swkoan.gallows.gt.render;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class GTStreamingRendererTest {
    
    public GTStreamingRendererTest() {
    }

    // Only one lame test to avoid going down the rabbit hole on GT rendering process.
    @Test
    public void testRenderNull() {
        try {
            GTStreamingRenderer rend = new GTStreamingRenderer();
            rend.render(null, null);
            fail("Expected NPE.");
        }
        catch(NullPointerException e) {
            // expected.
        }
    }
}
