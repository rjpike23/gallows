package com.swkoan.gallows.service.wms.ops;

import com.swkoan.gallows.service.Operation;
import com.swkoan.gallows.service.ResponseHandler;
import com.swkoan.gallows.service.wms.WMSRequest;
import net.opengis.wms.WMSCapabilities;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class GetWMSCapabilitiesOpTest {
    ApplicationContext appContext;
    
    public GetWMSCapabilitiesOpTest() {
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
        Operation op = (Operation) appContext.getBean("wmsGetCapabilitiesOp");
        
        WMSRequest request = new WMSRequest(null, null, "GetCapabilities", "1.3.0", "text/xml", null);
        assertTrue(op.handles(request));
        
        request = new WMSRequest(null, null, "NoMatch", "1.3.0", "text/xml", null);
        assertFalse(op.handles(request));
    }
    
    @Test
    public void testExecute() {
        Operation op = (Operation) appContext.getBean("wmsGetCapabilitiesOp");
        
        WMSRequest request = new WMSRequest("http://testvalue.com/", null, "GetCapabilities", "1.3.0", "text/xml", null);
        ResponseHandler handler = mock(ResponseHandler.class);
        op.execute(request, handler);
        
        verify(handler).setResultMIMEType("text/xml");
        verify(handler).setResult(any(WMSCapabilities.class));
    }

    @Test
    public void testDefaults() {
        Operation op = (Operation) appContext.getBean("wmsGetCapabilitiesOp");
        
        WMSRequest request = new WMSRequest("http://testvalue.com/", null, "GetCapabilities", null, null, null);
        ResponseHandler handler = mock(ResponseHandler.class);
        op.execute(request, handler);
        
        verify(handler).setResultMIMEType("text/xml");
        verify(handler).setResult(any(WMSCapabilities.class));
    }
}
