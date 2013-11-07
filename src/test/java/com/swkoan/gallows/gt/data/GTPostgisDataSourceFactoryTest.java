package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.gt.data.jdbc.PostgisDSConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class GTPostgisDataSourceFactoryTest {
    
    public GTPostgisDataSourceFactoryTest() {
    }

    @Test
    public void testCreateDSNull() {
        try {
            GTPostgisDataSourceFactory dsf = new GTPostgisDataSourceFactory();
            dsf.createDataSource(null);
            fail("Expected NPE.");
        }
        catch(NullPointerException e) {
            // Expected.
        }
    }
    
    @Test
    public void testCreateDS() {
        GTPostgisDataSourceFactory dsf = new GTPostgisDataSourceFactory();
        DataSourceConfig dsCfg = new PostgisDSConfig("host", 1024, "schema", "db", "user", "pw");
        assertNotNull(dsf.createDataSource(dsCfg));
    }

}
