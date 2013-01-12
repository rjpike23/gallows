package com.swkoan.gallows.config.pojo;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.LayerConfig;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class PojoLayerConfig implements LayerConfig {

    private LayerConfig parent;
    private List<LayerConfig> children;
    private String name;
    private String title;
    protected String layerAbstract;
    protected List<String> keywordList;
    protected List<String> crs;
    protected BoundingBox exGeographicBoundingBox;
    protected List<BoundingBox> boundingBox;
    //protected List<Dimension> dimension;
    //protected Attribution attribution;
    protected List<URL> authorityURLs;
    //protected List<Identifier> identifier;
    protected List<URL> metadataURLs;
    protected List<URL> dataURLs;
    protected List<URL> featureListURLs;
    protected Double minScaleDenominator;
    protected Double maxScaleDenominator;
    protected Boolean queryable;
    protected BigInteger cascaded;
    protected Boolean opaque;
    protected Boolean noSubsets;
    protected BigInteger fixedWidth;
    protected BigInteger fixedHeight;

    public PojoLayerConfig() {
    }

    public PojoLayerConfig(String name, String title, List<String> crs,
            BoundingBox exGeographicBoundingBox, List<BoundingBox> boundingBox) {
        this.name = name;
        this.title = title;
        this.crs = crs;
        this.exGeographicBoundingBox = exGeographicBoundingBox;
        this.boundingBox = boundingBox;
    }

    public PojoLayerConfig(String name, String title) {
        this.name = name;
        this.title = title;
    }
    
    @Override
    public DataSourceConfig getDataSourceConfig() {
        throw new UnsupportedOperationException("Not supported yet.");
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

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        for(Envelope envelope: boundingBox) {
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
        return exGeographicBoundingBox.toEnvelope();
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
}
