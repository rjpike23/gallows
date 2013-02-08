package com.swkoan.gallows.gt.data.jdbc;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.DataSourceVisitor;

/**
 *
 */
public class PostgisDSConfig implements DataSourceConfig {

    private String host;
    private Integer port;
    private String schema;
    private String database;
    private String user;
    private String password;
    private boolean looseBBox = true;
    private boolean preparedStatements = false;

    public PostgisDSConfig() {
    }

    public PostgisDSConfig(String host, Integer port, String schema, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.schema = schema;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    @Override
    public void accept(DataSourceVisitor visitor) {
        visitor.visit(this);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLooseBBox() {
        return looseBBox;
    }

    public void setLooseBBox(boolean looseBBox) {
        this.looseBBox = looseBBox;
    }

    public boolean isPreparedStatements() {
        return preparedStatements;
    }

    public void setPreparedStatements(boolean preparedStatements) {
        this.preparedStatements = preparedStatements;
    }
}
