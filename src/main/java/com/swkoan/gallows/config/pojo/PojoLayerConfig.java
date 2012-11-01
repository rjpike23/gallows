package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.LayerConfig;
import java.util.List;
import net.opengis.wms.Capability;
import net.opengis.wms.Layer;

/**
 *
 */
public class PojoLayerConfig implements LayerConfig {

    private LayerConfig parent;
    private List<LayerConfig> children;
    private String name;
    private String title;

    public PojoLayerConfig() {
    }

    public PojoLayerConfig(String name, String title) {
        this.name = name;
        this.title = title;
    }

    @Override
    public LayerConfig getParent() {
        return parent;
    }

    public void setParent(LayerConfig parent) {
        this.parent = parent;
    }

    @Override
    public List<LayerConfig> getChildren() {
        return children;
    }

    public void setChildren(List<LayerConfig> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void provideWMSCapabilitiesLayer(Layer layer) {
        layer.setName(name);
        layer.setTitle(title);
    }
}
