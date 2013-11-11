package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.DataSourceConfig;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
            dsf.createDataSource(null);
            fail("Expected NPE.");
        }
        catch(NullPointerException e) {
            // Expected.
        }
    }
    
    @Test
    public void testCreateDS() {
        GTDataSourceFactory dsf = new GTDataSourceFactory();
        GTDataSourceConfig dsCfg = new GTDataSourceConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dbtype", "postgis");
        params.put("host", "host");
        params.put("port", 1024);
        params.put("schema", "schema");
        params.put("database", "db");
        params.put("user", "user");
        params.put("password", "pw");
        dsCfg.setParams(params);
        assertNotNull(dsf.createDataSource(dsCfg));
    }

}
