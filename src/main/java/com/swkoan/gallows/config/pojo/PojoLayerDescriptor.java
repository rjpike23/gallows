package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.DataSourceDescriptor;
import com.swkoan.gallows.config.LayerDescriptor;
import java.util.List;

/**
 *
 */
public class PojoLayerDescriptor extends PojoFolderDescriptor implements LayerDescriptor {

    private DataSourceDescriptor dataSourceConfig;
    private String name;

    public PojoLayerDescriptor() {
    }

    public PojoLayerDescriptor(String name, String title, List<String> crs,
            BoundingBox exGeographicBoundingBox, List<BoundingBox> boundingBox) {
        super(title, crs, exGeographicBoundingBox, boundingBox);
        this.name = name;
    }

    public PojoLayerDescriptor(String name, String title) {
        super(title);
        this.name = name;
    }
    
    @Override
    public DataSourceDescriptor getDataSourceConfig() {
        return dataSourceConfig;
    }
    
    public void setDataSourceConfig(DataSourceDescriptor dsConfig) {
        this.dataSourceConfig = dsConfig;
    }
    
     @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
