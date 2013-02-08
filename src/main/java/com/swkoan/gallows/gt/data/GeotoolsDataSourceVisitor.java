package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.DataSourceVisitor;
import com.swkoan.gallows.gt.data.jdbc.PostgisDSConfig;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class GeotoolsDataSourceVisitor implements DataSourceVisitor {

    public static final String DB_TYPE = "dbtype";
    public static final String HOST = "host";
    public static final String PORT = "port";
    public static final String SCHEMA = "schema";
    public static final String DATABASE = "database";
    public static final String USER = "user";
    public static final String PASSWORD = "passwd";
    private Map<String, Object> params = new HashMap<String, Object>();
    
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public void visit(PostgisDSConfig dsConfig) {
        params.put(DB_TYPE, "postgis");
        params.put(HOST, dsConfig.getHost());
        params.put(PORT, dsConfig.getPort());
        params.put(SCHEMA, dsConfig.getSchema());
        params.put(DATABASE, dsConfig.getDatabase());
        params.put(USER, dsConfig.getUser());
        params.put(PASSWORD, dsConfig.getPassword());
    }
}
