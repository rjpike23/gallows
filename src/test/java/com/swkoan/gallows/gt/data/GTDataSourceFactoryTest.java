package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.gt.config.GTDataSourceDescriptor;
import com.swkoan.gallows.gt.config.factory.GTDataSourceFactory;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class GTDataSourceFactoryTest {
    
    public GTDataSourceFactoryTest() {
    }

    @Test
    public void testCreateDSNull() {
        try {
            GTDataSourceFactory dsf = new GTDataSourceFactory();
            dsf.produce(null);
            fail("Expected NPE.");
        }
        catch(NullPointerException e) {
            // Expected.
        }
    }
    
    @Test
    public void testCreateDS() {
        GTDataSourceFactory dsf = new GTDataSourceFactory();
        GTDataSourceDescriptor dsCfg = new GTDataSourceDescriptor();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dbtype", "postgis");
        params.put("host", "host");
        params.put("port", 1024);
        params.put("schema", "schema");
        params.put("database", "db");
        params.put("user", "user");
        params.put("password", "pw");
        dsCfg.setParams(params);
        assertNotNull(dsf.produce(dsCfg));
    }

}
