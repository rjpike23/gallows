package com.swkoan.gallows.gt.config.factory;

import com.swkoan.gallows.GallowsException;
import com.swkoan.gallows.config.factory.Factory;
import com.swkoan.gallows.gt.config.GTDataSourceDescriptor;
import java.io.IOException;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;

/**
 *
 */
public class GTDataSourceFactory implements Factory<DataStore, GTDataSourceDescriptor> {

    @Override
    public Class<GTDataSourceDescriptor> getDescriptorClass() {
        return GTDataSourceDescriptor.class;
    }

    @Override
    public DataStore produce(GTDataSourceDescriptor descriptor) {
        try {
            return DataStoreFinder.getDataStore(descriptor.getParams());
        }
        catch (IOException e) {
            throw new GallowsException("Error instantiating GeoTools DataSource.", e);
        }
    }
}
