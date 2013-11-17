package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.FolderConfig;
import com.swkoan.gallows.config.GallowsConfig;
import com.swkoan.gallows.config.LayerConfig;
import java.util.HashMap;
import java.util.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class PojoGallowsConfig implements GallowsConfig, ApplicationContextAware {

    private static final Logger LOG = Logger.getLogger(PojoGallowsConfig.class.getName());
    private String suppCtxFile = null;
    private ConfigStatus currentStatus = new ConfigStatus();
    private FolderConfig layerConfig;
    private HashMap<String, LayerConfig> layerIndex = new HashMap<String, LayerConfig>();
    private ApplicationContext appCtx;

    @Override
    public void load() {
        LOG.info("Loading POJO GallowsConfig from Spring context.");
        currentStatus.loadSuccess();
        ApplicationContext configCtx = appCtx;
        if (suppCtxFile != null) {
            configCtx = new ClassPathXmlApplicationContext(suppCtxFile);
        }
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

    public String getSuppCtxFile() {
        return suppCtxFile;
    }

    public void setSuppCtxFile(String suppCtxFile) {
        this.suppCtxFile = suppCtxFile;
    }

    @Override
    public FolderConfig getRootLayerConfig() {
        if (currentStatus.getCurrentState() == ConfigStatus.States.INIT) {
            throw new IllegalStateException("Gallows configuration has not yet been loaded.");
        }
        return layerConfig;
    }

    @Override
    public DataSourceConfig getDataSourceConfigs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LayerConfig getLayerConfig(String name) {
        return layerIndex.get(name);
    }

    private void addToIndex(FolderConfig layer) {
        if (layer instanceof LayerConfig) {
            layerIndex.put(((LayerConfig) layer).getName(), (LayerConfig) layer);
        }
        if (layer.getChildren() != null && !layer.getChildren().isEmpty()) {
            for (FolderConfig child : layer.getChildren()) {
                addToIndex(child);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.appCtx = ac;
    }

}
