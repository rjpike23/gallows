package com.swkoan.gallows.gt.config;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.FolderConfig;
import com.swkoan.gallows.config.pojo.PojoFolderConfig;
import com.swkoan.gallows.config.pojo.PojoLayerConfig;
import com.swkoan.gallows.data.LayerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.geotools.data.DataStore;
import org.geotools.map.Layer;
import org.geotools.referencing.CRS;

/**
 *
 */
public class GTJDBCLayerConfig extends PojoFolderConfig implements FolderConfig {
    DataSourceConfig dsConfig;
    LayerFactory<Layer, DataStore> layerFactory;

    public GTJDBCLayerConfig(DataSourceConfig dsConfig, LayerFactory<Layer, DataStore> layerFactory) {
        this.dsConfig = dsConfig;
        this.layerFactory = layerFactory;
        update();
    }

    @Override
    public List<String> getCrs() {
        List<String> crs = new ArrayList<String>();
        Set<String> authSet = CRS.getSupportedAuthorities(false);
        for(String auth: authSet) {
            Set<String> codes = CRS.getSupportedCodes(auth);
            crs.addAll(codes);
        }
        return crs;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dsConfig;
    }
    
    public void setDataSourceConfig(DataSourceConfig dsConfig) {
        this.dsConfig = dsConfig;

        update();
    }

    public LayerFactory<Layer, DataStore> getLayerFactory() {
        return layerFactory;
    }

    public void setLayerFactory(LayerFactory<Layer, DataStore> layerFactory) {
        this.layerFactory = layerFactory;
        update();
    }
    
    private void update() {
        List<FolderConfig> childLayers = new ArrayList<FolderConfig>();
        DataStore ds = layerFactory.createDataSource(dsConfig);
        try {
            String[] types = ds.getTypeNames();
            for(int i = 0; i < types.length; ++i) {
                PojoLayerConfig layerConfig = new PojoLayerConfig(types[i], types[i]);
                layerConfig.setDataSourceConfig(dsConfig);
                layerConfig.setExGeographicBoundingBox(ds.getFeatureSource(types[i]).getBounds());
                layerConfig.getBoundingBox().add(ds.getFeatureSource(types[i]).getBounds());
                childLayers.add(layerConfig);
            }
            ds.dispose();
            setChildren(childLayers);
        }
        catch(IOException e) {
            // TODO: What to do here?
        }
    }
    
}
