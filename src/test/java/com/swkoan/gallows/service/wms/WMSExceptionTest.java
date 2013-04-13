package com.swkoan.gallows.service.wms;

import net.opengis.ogc.ServiceExceptionReport;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class WMSExceptionTest {
    
    public WMSExceptionTest() {
    }
    
    @Test
    public void testGetWMSExceptionXML() {
        WMSException e = new WMSException("Message", "Code");
        ServiceExceptionReport result = e.getWMSExceptionXML();
        assertNotNull(result);
        assertNotNull(result.getServiceException());
        assertFalse(result.getServiceException().isEmpty());
        assertEquals("Message", result.getServiceException().get(0).getValue());
        assertEquals("Code", result.getServiceException().get(0).getCode());
    }
}
