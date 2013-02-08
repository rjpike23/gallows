package com.swkoan.gallows.config;

import com.swkoan.gallows.gt.data.jdbc.PostgisDSConfig;

/**
 *
 */
public interface DataSourceVisitor {

    void visit(PostgisDSConfig dsConfig);
}
