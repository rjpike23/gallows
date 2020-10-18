package com.swkoan.gallows.service;

import com.swkoan.gallows.service.wms.WMSException;
import javax.ws.rs.core.Response;
import net.opengis.wms.v_1_3_0.exceptions.ServiceExceptionReport;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class JAXRSResponseHandlerTest {
    
    public JAXRSResponseHandlerTest() {
    }
    
    @Test
    public void testExceptionOnExecute() {
        JAXRSResponseHandler handler = new JAXRSResponseHandler();
        handler.exceptionOnExecute(new WMSException("Message", "Code"));
        Response response = handler.getJAXRSResponse();
        assertTrue(response.getEntity() instanceof ServiceExceptionReport);
    }
}
