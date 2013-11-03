package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.LayerConfig;
import java.util.List;

/**
 *
 */
public class PojoLayerConfig extends PojoFolderConfig implements LayerConfig {

    private DataSourceConfig dataSourceConfig;
    private String name;

    public PojoLayerConfig() {
    }

    public PojoLayerConfig(String name, String title, List<String> crs,
            BoundingBox exGeographicBoundingBox, List<BoundingBox> boundingBox) {
        super(title, crs, exGeographicBoundingBox, boundingBox);
        this.name = name;
    }

    public PojoLayerConfig(String name, String title) {
        super(title);
        this.name = name;
    }
    
    @Override
    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }
    
    public void setDataSourceConfig(DataSourceConfig dsConfig) {
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
