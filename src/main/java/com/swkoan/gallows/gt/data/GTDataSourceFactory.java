package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.GallowsException;
import com.swkoan.gallows.data.DataSourceFactory;
import com.swkoan.gallows.data.LayerFactory;
import java.io.IOException;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;

/**
 *
 */
public class GTDataSourceFactory implements DataSourceFactory<DataStore, GTDataSourceConfig> {

    @Override
    public void setLayerFactory(LayerFactory layerFactory) {
        layerFactory.registerDataSourceFactory(this);
    }

    @Override
    public DataStore createDataSource(GTDataSourceConfig dsConfig) {
        try {
            return DataStoreFinder.getDataStore(dsConfig.getParams());
        }
        catch(IOException e) {
            throw new GallowsException("Error instantiating GeoTools DataSource.", e);
        }
    }

    @Override
    public String getDataSourceConfigClassname() {
        return GTDataSourceConfig.class.getName();
    }
    
}
