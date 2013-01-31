package com.swkoan.gallows.render;

import com.swkoan.gallows.data.MapDescription;
import java.awt.Graphics2D;

/**
 *
 */
public interface Renderer {

    void render(MapDescription map, Graphics2D graphics);
}
