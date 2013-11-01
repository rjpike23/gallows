package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.data.DataSourceFactory;
import com.swkoan.gallows.gt.data.jdbc.PostgisDSConfig;
import com.swkoan.gallows.render.Renderer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;

/**
 * Creates GeoTools data sources based on parameters from the Gallows Config
 * objects.
 */
public class GTPostgisDataSourceFactory implements DataSourceFactory<DataStore> {

    public static final String DB_TYPE = "dbtype";
    public static final String HOST = "host";
    public static final String PORT = "port";
    public static final String SCHEMA = "schema";
    public static final String DATABASE = "database";
    public static final String USER = "user";
    public static final String PASSWORD = "passwd";

    @Override
    public DataStore createDataSource(DataSourceConfig dsConfig) {
        try {
            PostgisDSConfig pgDsConfig = (PostgisDSConfig) dsConfig;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(DB_TYPE, "postgis");
            params.put(HOST, pgDsConfig.getHost());
            params.put(PORT, pgDsConfig.getPort());
            params.put(SCHEMA, pgDsConfig.getSchema());
            params.put(DATABASE, pgDsConfig.getDatabase());
            params.put(USER, pgDsConfig.getUser());
            params.put(PASSWORD, pgDsConfig.getPassword());
            return DataStoreFinder.getDataStore(params);
        } 
        catch (IOException e) {
            // TODO: what to do here?
            return null;
        }
    }

    @Override
    public String getDataSourceConfigClassname() {
        return PostgisDSConfig.class.getName();
    }

    @Override
    public void setRenderer(Renderer renderer) {
        renderer.registerDataSourceFactory(this);
    }
}
