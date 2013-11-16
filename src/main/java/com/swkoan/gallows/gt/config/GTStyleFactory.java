package com.swkoan.gallows.gt.config;

import com.swkoan.gallows.config.GallowsException;
import com.swkoan.gallows.data.StyleFactory;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.styling.SLDParser;
import org.geotools.styling.Style;

/**
 *
 */
public class GTStyleFactory implements StyleFactory<Style, GTStyleConfig> {

    @Override
    public Style createStyle(GTStyleConfig styleConfig) {
        try {
            org.geotools.styling.StyleFactory styleFactory =
                    CommonFactoryFinder.getStyleFactory();
            SLDParser stylereader = new SLDParser(styleFactory, styleConfig.getSldUri());
            Style[] style = stylereader.readXML();
            return style[0];
        }
        catch (Exception e) {
            throw new GallowsException("Message!", e);
        }
    }
}
