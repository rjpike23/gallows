package com.swkoan.gallows.service;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 */
public class RenderedImageStreamingOutput implements StreamingOutput  {
    
    private RenderedImage image;
    private String outputMimeType;

    public RenderedImageStreamingOutput(RenderedImage image, String outputMimeType) {
        this.image = image;
        this.outputMimeType = outputMimeType;
    }

    @Override
    public void write(OutputStream out) throws IOException, WebApplicationException {
        ImageOutputStream os = new MemoryCacheImageOutputStream(out);
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType(outputMimeType);
        if(writers.hasNext()) {
            // Just use the first ImageWriter that is returned.
            ImageWriter writer = writers.next();
            writer.setOutput(os);
            writer.write(image);
        }
        else {
            // TODO: Not supported format.
        }
    }
    
}
