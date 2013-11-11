package com.swkoan.gallows.config;

/**
 *
 */
public interface LayerConfig<T extends DataSourceConfig> extends FolderConfig {

    String getName();

    T getDataSourceConfig();
}
