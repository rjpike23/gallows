package com.swkoan.gallows.service.wms.ops;

import com.swkoan.gallows.service.Operation;
import com.swkoan.gallows.service.wms.WMSRequest;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class GetCapabilitiesTest {
    
    public GetCapabilitiesTest() {
    }

    @Test
    public void testHandles() {
        Operation op = new GetWMSCapabilitiesOp();
        
        WMSRequest request = new WMSRequest(null, null, "GetCapabilities", "1.3.0", "text/xml");
        assertTrue(op.handles(request));
        
        request = new WMSRequest(null, null, "NoMatch", "1.3.0", "text/xml");
        assertFalse(op.handles(request));
    }
}
