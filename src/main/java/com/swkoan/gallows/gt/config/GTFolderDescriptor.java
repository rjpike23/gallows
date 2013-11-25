package com.swkoan.gallows.gt.config;

import com.swkoan.gallows.config.DataSourceDescriptor;
import com.swkoan.gallows.config.FolderDescriptor;
import com.swkoan.gallows.config.factory.Factory;
import com.swkoan.gallows.config.pojo.PojoFolderDescriptor;
import com.swkoan.gallows.config.pojo.PojoLayerDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.geotools.data.DataStore;
import org.geotools.referencing.CRS;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class GTFolderDescriptor extends PojoFolderDescriptor implements FolderDescriptor {
    GTDataSourceDescriptor dsConfig;
    Factory<DataStore, DataSourceDescriptor> dsFactory;

    public GTFolderDescriptor(GTDataSourceDescriptor dsConfig, Factory<DataStore, DataSourceDescriptor> layerFactory) {
        this.dsConfig = dsConfig;
        this.dsFactory = layerFactory;
        update();
    }

    @Override
    public List<String> getCrs() {
        List<String> crs = new ArrayList<String>();
        Set<String> authSet = CRS.getSupportedAuthorities(false);
        for(String auth: authSet) {
            Set<String> codes = CRS.getSupportedCodes(auth);
            for(String code: codes) {
                crs.add(auth + ":" + code);
            }
        }
        return crs;
    }

    public DataSourceDescriptor getDataSourceConfig() {
        return dsConfig;
    }
    
    public void setDataSourceConfig(GTDataSourceDescriptor dsConfig) {
        this.dsConfig = dsConfig;

        update();
    }

    public Factory<DataStore, DataSourceDescriptor> getDsFactory() {
        return dsFactory;
    }

    public void setDsFactory(Factory<DataStore, DataSourceDescriptor> dsFactory) {
        this.dsFactory = dsFactory;
        update();
    }
    
    private void update() {
        List<FolderDescriptor> childLayers = new ArrayList<FolderDescriptor>();
        DataStore ds = dsFactory.produce(dsConfig);
        try {
            String[] types = ds.getTypeNames();
            for(int i = 0; i < types.length; ++i) {
                PojoLayerDescriptor layerConfig = new GTLayerDescriptor(types[i], types[i]);
                layerConfig.setParent(this);
                layerConfig.setDataSourceConfig(dsConfig);
                Envelope bounds = ds.getFeatureSource(types[i]).getBounds();
                layerConfig.setExGeographicBoundingBox(bounds);
                List<Envelope> bboxes = new ArrayList<Envelope>();
                bboxes.add(bounds);
                layerConfig.setBoundingBox(bboxes);
                childLayers.add(layerConfig);
            }
            setChildren(childLayers);
        }
        catch(IOException e) {
            // TODO: What to do here?
        }
        finally {
            ds.dispose();            
        }
    }
    
}
