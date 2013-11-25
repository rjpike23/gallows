package com.swkoan.gallows.render;

import com.swkoan.gallows.config.MapDescriptor;
import java.awt.Graphics2D;

/**
 *
 */
public interface Renderer {

    void render(MapDescriptor map, Graphics2D graphics);
}
