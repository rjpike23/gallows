package com.swkoan.gallows.gt.config.factory;

import com.swkoan.gallows.GallowsException;
import com.swkoan.gallows.config.factory.Factory;
import com.swkoan.gallows.gt.config.GTStyleDescriptor;
import java.net.URL;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.styling.SLDParser;
import org.geotools.styling.Style;

/**
 *
 */
public class GTStyleFactory implements Factory<Style, GTStyleDescriptor> {

    @Override
    public Class<GTStyleDescriptor> getDescriptorClass() {
        return GTStyleDescriptor.class;
    }

    @Override
    public Style produce(GTStyleDescriptor styleConfig) {
        try {
            org.geotools.styling.StyleFactory styleFactory =
                    CommonFactoryFinder.getStyleFactory();
            SLDParser stylereader = new SLDParser(styleFactory, new URL(styleConfig.getSldUri()));
            Style[] style = stylereader.readXML();
            return style[0];
        }
        catch (Exception e) {
            throw new GallowsException("Message!", e);
        }
    }
}
