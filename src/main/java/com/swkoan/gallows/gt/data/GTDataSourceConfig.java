package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.DataSourceConfig;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class GTDataSourceConfig implements DataSourceConfig {
    Map<String, Object> params = new HashMap<String, Object>();

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    
}
