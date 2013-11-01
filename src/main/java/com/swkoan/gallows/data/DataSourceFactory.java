
package com.swkoan.gallows.data;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.render.Renderer;

/**
 *
 */
public interface DataSourceFactory<T extends Object> {
    
    void setRenderer(Renderer renderer);
    
    T createDataSource(DataSourceConfig dsConfig);

    String getDataSourceConfigClassname();
}
