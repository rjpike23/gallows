package com.swkoan.gallows.gt.config;

import com.swkoan.gallows.config.DataSourceDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class GTDataSourceDescriptor implements DataSourceDescriptor {
    Map<String, Object> params = new HashMap<String, Object>();

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    
}
