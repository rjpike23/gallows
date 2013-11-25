package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.FolderDescriptor;
import com.swkoan.gallows.config.StyleDescriptor;
import com.swkoan.gallows.security.SecurityDescriptor;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class PojoFolderDescriptor implements FolderDescriptor {

    private FolderDescriptor parent;
    private List<FolderDescriptor> children;
    private List<StyleDescriptor> styles;
    private Map<String, StyleDescriptor> styleIndex = new HashMap<String, StyleDescriptor>();
    private String title;
    private String layerAbstract;
    private List<String> keywordList;
    private List<String> crs;
    private BoundingBox exGeographicBoundingBox;
    private List<BoundingBox> boundingBox;
    //private List<Dimension> dimension;
    //private Attribution attribution;
    private List<URL> authorityURLs;
    //private List<Identifier> identifier;
    private List<URL> metadataURLs;
    private List<URL> dataURLs;
    private List<URL> featureListURLs;
    private Double minScaleDenominator;
    private Double maxScaleDenominator;
    private Boolean queryable;
    private BigInteger cascaded;
    private Boolean opaque;
    private Boolean noSubsets;
    private BigInteger fixedWidth;
    private BigInteger fixedHeight;

    public PojoFolderDescriptor() {
        children = new ArrayList<FolderDescriptor>();
        styles = new ArrayList<StyleDescriptor>();
        crs = new ArrayList<String>();
        boundingBox = new ArrayList<BoundingBox>();
    }

    public PojoFolderDescriptor(String title) {
        this();
        this.title = title;
    }

    public PojoFolderDescriptor(String title, List<String> crs, BoundingBox exGeographicBoundingBox, List<BoundingBox> boundingBox) {
        this();
        this.title = title;
        this.crs = crs;
        this.exGeographicBoundingBox = exGeographicBoundingBox;
        this.boundingBox = boundingBox;
    }

    @Override
    public FolderDescriptor getParent() {
        return parent;
    }

    public void setParent(FolderDescriptor parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }

    @Override
    public List<FolderDescriptor> getChildren() {
        return children;
    }

    public void setChildren(List<FolderDescriptor> children) {
        this.children = children;
    }

    // TODO: probably should return immutable list.
    @Override
    public List<StyleDescriptor> getStyles() {
        return styles;
    }

    public void setStyles(List<StyleDescriptor> styles) {
        this.styles = styles;
        styleIndex = new HashMap<String, StyleDescriptor>();
        for(StyleDescriptor style: styles) {
            styleIndex.put(style.getName(), style);
        }
    }

    @Override
    public StyleDescriptor getStyle(String name) {
        StyleDescriptor result = styleIndex.get(name);
        if(result == null && parent != null) {
            result = parent.getStyle(name);
        }
        return result;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<URL> getAuthorityURLs() {
        return authorityURLs;
    }

    public void setAuthorityURLs(List<URL> authorityURLs) {
        this.authorityURLs = authorityURLs;
    }

    @Override
    public List<Envelope> getBoundingBox() {
        List<Envelope> result = new ArrayList<Envelope>();
        for (BoundingBox bb : boundingBox) {
            result.add(bb.toEnvelope());
        }
        return result;
    }

    public void setBoundingBox(List<Envelope> boundingBox) {
        List<BoundingBox> bboxes = new ArrayList<BoundingBox>();
        for (Envelope envelope : boundingBox) {
            bboxes.add(new BoundingBox(envelope));
        }
        this.boundingBox = bboxes;
    }

    public BigInteger getCascaded() {
        return cascaded;
    }

    public void setCascaded(BigInteger cascaded) {
        this.cascaded = cascaded;
    }

    @Override
    public List<String> getCrs() {
        return crs;
    }

    public void setCrs(List<String> crs) {
        this.crs = crs;
    }

    public List<URL> getDataURLs() {
        return dataURLs;
    }

    public void setDataURLs(List<URL> dataURLs) {
        this.dataURLs = dataURLs;
    }

    @Override
    public Envelope getExGeographicBoundingBox() {
        return exGeographicBoundingBox==null? null : exGeographicBoundingBox.toEnvelope();
    }

    public void setExGeographicBoundingBox(Envelope exGeographicBoundingBox) {
        this.exGeographicBoundingBox = new BoundingBox(exGeographicBoundingBox);
    }

    public List<URL> getFeatureListURLs() {
        return featureListURLs;
    }

    public void setFeatureListURLs(List<URL> featureListURLs) {
        this.featureListURLs = featureListURLs;
    }

    public BigInteger getFixedHeight() {
        return fixedHeight;
    }

    public void setFixedHeight(BigInteger fixedHeight) {
        this.fixedHeight = fixedHeight;
    }

    public BigInteger getFixedWidth() {
        return fixedWidth;
    }

    public void setFixedWidth(BigInteger fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    public List<String> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(List<String> keywordList) {
        this.keywordList = keywordList;
    }

    public String getLayerAbstract() {
        return layerAbstract;
    }

    public void setLayerAbstract(String layerAbstract) {
        this.layerAbstract = layerAbstract;
    }

    public Double getMaxScaleDenominator() {
        return maxScaleDenominator;
    }

    public void setMaxScaleDenominator(Double maxScaleDenominator) {
        this.maxScaleDenominator = maxScaleDenominator;
    }

    public List<URL> getMetadataURLs() {
        return metadataURLs;
    }

    public void setMetadataURLs(List<URL> metadataURLs) {
        this.metadataURLs = metadataURLs;
    }

    public Double getMinScaleDenominator() {
        return minScaleDenominator;
    }

    public void setMinScaleDenominator(Double minScaleDenominator) {
        this.minScaleDenominator = minScaleDenominator;
    }

    public Boolean getNoSubsets() {
        return noSubsets;
    }

    public void setNoSubsets(Boolean noSubsets) {
        this.noSubsets = noSubsets;
    }

    public Boolean getOpaque() {
        return opaque;
    }

    public void setOpaque(Boolean opaque) {
        this.opaque = opaque;
    }

    public Boolean getQueryable() {
        return queryable;
    }

    public void setQueryable(Boolean queryable) {
        this.queryable = queryable;
    }

    @Override
    public SecurityDescriptor getSecurityDescriptor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
