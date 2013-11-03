package com.swkoan.gallows.config;

/**
 *
 */
public interface LayerConfig extends FolderConfig {

    String getName();

    DataSourceConfig getDataSourceConfig();
}
