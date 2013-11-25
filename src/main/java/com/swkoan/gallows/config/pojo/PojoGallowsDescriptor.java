package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.DataSourceDescriptor;
import com.swkoan.gallows.config.FolderDescriptor;
import com.swkoan.gallows.config.GallowsConfig;
import com.swkoan.gallows.config.LayerDescriptor;
import java.util.HashMap;
import java.util.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class PojoGallowsDescriptor implements GallowsConfig, ApplicationContextAware {

    private static final Logger LOG = Logger.getLogger(PojoGallowsDescriptor.class.getName());
    private String suppCtxFile = null;
    private ConfigStatus currentStatus = new ConfigStatus();
    private FolderDescriptor layerConfig;
    private HashMap<String, LayerDescriptor> layerIndex = new HashMap<String, LayerDescriptor>();
    private ApplicationContext appCtx;

    @Override
    public void load() {
        LOG.info("Loading POJO GallowsConfig from Spring context.");
        currentStatus.loadSuccess();
        ApplicationContext configCtx = appCtx;
        if (suppCtxFile != null) {
            configCtx = new ClassPathXmlApplicationContext(suppCtxFile);
        }
        layerConfig = ((LayerDescContainer) configCtx.getBean("layerList")).getRootLayerConfig();
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
    public FolderDescriptor getRootLayerConfig() {
        if (currentStatus.getCurrentState() == ConfigStatus.States.INIT) {
            throw new IllegalStateException("Gallows configuration has not yet been loaded.");
        }
        return layerConfig;
    }

    @Override
    public DataSourceDescriptor getDataSourceConfigs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LayerDescriptor getLayerConfig(String name) {
        return layerIndex.get(name);
    }

    private void addToIndex(FolderDescriptor layer) {
        if (layer instanceof LayerDescriptor) {
            layerIndex.put(((LayerDescriptor) layer).getName(), (LayerDescriptor) layer);
        }
        if (layer.getChildren() != null && !layer.getChildren().isEmpty()) {
            for (FolderDescriptor child : layer.getChildren()) {
                addToIndex(child);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.appCtx = ac;
    }

}
