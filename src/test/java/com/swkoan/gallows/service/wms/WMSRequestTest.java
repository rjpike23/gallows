package com.swkoan.gallows.service.wms;

import com.swkoan.gallows.testutil.MVHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class WMSRequestTest {
    
    public WMSRequestTest() {
    }

    @Test
    public void testGetBBox() {
        MultivaluedMap<String, String> params = new MVHashMap<String, String>();
        params.add(WMSConstants.BBOX_PARAM, "0,10,100,150");
        WMSRequest req = new WMSRequest(null, null, null, null, null, params);
        Envelope env = req.getBbox();
        assertEquals(0, env.getLowerCorner().getOrdinate(0), 0);
        assertEquals(10, env.getLowerCorner().getOrdinate(1), 0);
        assertEquals(100, env.getUpperCorner().getOrdinate(0), 0);
        assertEquals(150, env.getUpperCorner().getOrdinate(1), 0);
    }
}
