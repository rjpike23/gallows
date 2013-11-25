package com.swkoan.gallows.config;

/**
 *
 */
public interface LayerDescriptor<T extends DataSourceDescriptor> extends FolderDescriptor {

    String getName();

    T getDataSourceConfig();
}
