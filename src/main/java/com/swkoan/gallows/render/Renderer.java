package com.swkoan.gallows.render;

import com.swkoan.gallows.data.DataSourceFactory;
import com.swkoan.gallows.data.MapDescription;
import java.awt.Graphics2D;
import org.geotools.data.DataStore;

/**
 *
 */
public interface Renderer {

    void registerDataSourceFactory(DataSourceFactory<DataStore> factory);

    void render(MapDescription map, Graphics2D graphics);
}
