
package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.GallowsConfig;
import com.swkoan.gallows.config.LayerConfig;
import java.util.HashMap;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class PojoGallowsConfig implements GallowsConfig {
    private ConfigStatus currentStatus = new ConfigStatus();
    private LayerConfig layerConfig;
    private HashMap<String, LayerConfig> layerIndex = new HashMap<String, LayerConfig>();

    @Override
    public void load() {
        currentStatus.loadSuccess();
        ClassPathXmlApplicationContext configCtx = new ClassPathXmlApplicationContext("/pojoConfigContext.xml");
        layerConfig = ((LayerConfigContainer) configCtx.getBean("layerList")).getRootLayerConfig();
        addToIndex(layerConfig);
    }

    @Override
    public void save() {
        currentStatus.saveSuccess();
    }

    @Override
    public ConfigStatus status() {
        return currentStatus;
    }

    @Override
    public LayerConfig getRootLayerConfig() {
        if(currentStatus.getCurrentState()==ConfigStatus.States.INIT) {
            throw new IllegalStateException("Gallows configuration has not yet been loaded.");
        }
        return layerConfig;
    }

    @Override
    public DataSourceConfig getDataSourceConfigs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LayerConfig getLayerConfig(String name) {
        return layerIndex.get(name);
    }
    
    private void addToIndex(LayerConfig layer) {
        layerIndex.put(layer.getName(), layer);
        if(layer.getChildren() != null && !layer.getChildren().isEmpty()) {
            for(LayerConfig child: layer.getChildren()) {
                addToIndex(child);
            }
        }
    }
    
}
