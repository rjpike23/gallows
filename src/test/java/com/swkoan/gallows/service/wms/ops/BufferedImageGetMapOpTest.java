package com.swkoan.gallows.service.wms.ops;

import com.swkoan.gallows.data.MapDescription;
import com.swkoan.gallows.render.Renderer;
import com.swkoan.gallows.service.Operation;
import com.swkoan.gallows.service.ResponseHandler;
import com.swkoan.gallows.service.wms.WMSConstants;
import com.swkoan.gallows.service.wms.WMSException;
import com.swkoan.gallows.service.wms.WMSRequest;
import com.swkoan.gallows.testutil.MVHashMap;
import java.awt.Graphics2D;
import javax.ws.rs.core.MultivaluedMap;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class BufferedImageGetMapOpTest {

    ApplicationContext appContext;

    public BufferedImageGetMapOpTest() {
    }

    @Before
    public void setupAppContext() {
        appContext = new ClassPathXmlApplicationContext("testAppContext.xml");
    }

    @After
    public void destroyAppContext() {
        appContext = null;
    }

    @Test
    public void testHandles() {
        WMSRequest wmsRequest = new WMSRequest(
                "http://test.com/", null, WMSConstants.GET_MAP_OP, "1.3.0",
                "image/png", null);
        assertTrue(new BufferedImageGetMapOp(null).handles(wmsRequest));

        wmsRequest = new WMSRequest(
                "http://test.com/", null, "No-op", "1.3.0",
                "image/png", null);
        assertFalse(new BufferedImageGetMapOp(null).handles(wmsRequest));

        wmsRequest = new WMSRequest(
                "http://test.com/", null, WMSConstants.GET_MAP_OP, "1.3.0",
                "no/image", null);
        assertFalse(new BufferedImageGetMapOp(null).handles(wmsRequest));

        try {
            wmsRequest = new WMSRequest(null, null, null, null, null, null);
            assertFalse(new BufferedImageGetMapOp(null).handles(wmsRequest));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testExecute() {
        BufferedImageGetMapOp op = (BufferedImageGetMapOp) appContext.getBean("wmsBufferedImageGetMapOp");

        MultivaluedMap<String, String> params = new MVHashMap<String, String>();
        params.add(WMSConstants.HEIGHT_PARAM, "300");
        params.add(WMSConstants.WIDTH_PARAM, "350");
        params.add(WMSConstants.CRS_PARAM, "epsg:4326");
        params.add(WMSConstants.LAYERS_PARAM, "Layer1");
        params.add(WMSConstants.STYLES_PARAM, "");
        WMSRequest request = new WMSRequest("http://testvalue.com/", null,
                WMSConstants.GET_MAP_OP, "1.3.0", "image/png", params);

        ResponseHandler handler = mock(ResponseHandler.class);
        Renderer renderer = mock(Renderer.class);
        op.setRenderer(renderer);

        op.execute(request, handler);

        verify(renderer).render(any(MapDescription.class), any(Graphics2D.class));
        verify(handler).setResultMIMEType("image/png");
    }

    @Test
    public void testInvalidHeightWidth() {
        MultivaluedMap<String, String> params = new MVHashMap<String, String>();
        params.add(WMSConstants.CRS_PARAM, "epsg:4326");
        params.add(WMSConstants.LAYERS_PARAM, "Layer1");
        params.add(WMSConstants.STYLES_PARAM, "");
        callExecuteWithInvalidParams(params, "InvalidParameters");
        
        params = new MVHashMap<String, String>();
        params.add(WMSConstants.HEIGHT_PARAM, "300");
        params.add(WMSConstants.CRS_PARAM, "epsg:4326");
        params.add(WMSConstants.LAYERS_PARAM, "Layer1");
        params.add(WMSConstants.STYLES_PARAM, "");
        callExecuteWithInvalidParams(params, "InvalidParameters");
        
        params = new MVHashMap<String, String>();
        params.add(WMSConstants.WIDTH_PARAM, "350");
        params.add(WMSConstants.CRS_PARAM, "epsg:4326");
        params.add(WMSConstants.LAYERS_PARAM, "Layer1");
        params.add(WMSConstants.STYLES_PARAM, "");
        callExecuteWithInvalidParams(params, "InvalidParameters");
    }
    
    @Test
    public void testInvalidSRS() {
        MultivaluedMap<String, String> params = new MVHashMap<String, String>();
        params.add(WMSConstants.HEIGHT_PARAM, "300");
        params.add(WMSConstants.WIDTH_PARAM, "350");
        params.add(WMSConstants.LAYERS_PARAM, "Layer1");
        params.add(WMSConstants.STYLES_PARAM, "");
        callExecuteWithInvalidParams(params, "InvalidParameters");
        
        params = new MVHashMap<String, String>();
        params.add(WMSConstants.HEIGHT_PARAM, "300");
        params.add(WMSConstants.WIDTH_PARAM, "350");
        params.add(WMSConstants.CRS_PARAM, "bleh");
        params.add(WMSConstants.LAYERS_PARAM, "Layer1");
        params.add(WMSConstants.STYLES_PARAM, "");
        callExecuteWithInvalidParams(params, "InvalidCRS");
    }

    @Test
    public void testInvalidLayer() {
        MultivaluedMap<String, String> params = new MVHashMap<String, String>();
        params.add(WMSConstants.HEIGHT_PARAM, "300");
        params.add(WMSConstants.WIDTH_PARAM, "350");
        params.add(WMSConstants.CRS_PARAM, "epsg:4326");
        params.add(WMSConstants.STYLES_PARAM, "");
        callExecuteWithInvalidParams(params, "InvalidParameters");
        
        params = new MVHashMap<String, String>();
        params.add(WMSConstants.HEIGHT_PARAM, "300");
        params.add(WMSConstants.WIDTH_PARAM, "350");
        params.add(WMSConstants.CRS_PARAM, "epsg:4326");
        params.add(WMSConstants.LAYERS_PARAM, "bleh");
        params.add(WMSConstants.STYLES_PARAM, "");
        callExecuteWithInvalidParams(params, "LayerNotDefined");
    }

    private void callExecuteWithInvalidParams(MultivaluedMap<String, String> params, String errorCode) {
        WMSRequest request = new WMSRequest("http://testvalue.com/", null,
                WMSConstants.GET_MAP_OP, "1.3.0", "image/png", params);
        BufferedImageGetMapOp op = (BufferedImageGetMapOp) appContext.getBean("wmsBufferedImageGetMapOp");
        ResponseHandler handler = mock(ResponseHandler.class);
        try {
            op.execute(request, handler);
            fail("Expected an " + errorCode + "exception.");
        } catch (WMSException e) {
            assertEquals(errorCode, e.getCode());
        }
    }
}
