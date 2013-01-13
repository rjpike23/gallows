
package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.GallowsConfig;
import com.swkoan.gallows.config.LayerConfig;
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class PojoGallowsConfig implements GallowsConfig {
    private ConfigStatus currentStatus = new ConfigStatus();
    private LayerConfig layerConfig;

    @Override
    public void load() {
        currentStatus.loadSuccess();
        ClassPathXmlApplicationContext configCtx = new ClassPathXmlApplicationContext("/pojoConfigContext.xml");
        layerConfig = ((LayerConfigContainer) configCtx.getBean("layerList")).getRootLayerConfig();
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
    
}
