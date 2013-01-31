package com.swkoan.gallows.service.wms.ops;

import com.swkoan.gallows.data.MapDescription;
import com.swkoan.gallows.render.Renderer;
import com.swkoan.gallows.service.Operation;
import com.swkoan.gallows.service.RenderedImageStreamingOutput;
import com.swkoan.gallows.service.Request;
import com.swkoan.gallows.service.ResponseHandler;
import com.swkoan.gallows.service.wms.WMSCapabilityProvider;
import com.swkoan.gallows.service.wms.WMSConstants;
import com.swkoan.gallows.service.wms.WMSRequest;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import javax.imageio.ImageIO;
import javax.ws.rs.core.StreamingOutput;
import net.opengis.wms.Capability;
import net.opengis.wms.DCPType;
import net.opengis.wms.Get;
import net.opengis.wms.HTTP;
import net.opengis.wms.OnlineResource;
import net.opengis.wms.OperationType;
import net.opengis.wms.Post;

/**
 *
 */
public class BufferedImageGetMapOp implements Operation, WMSCapabilityProvider {

    private static Set<String> supportedMimeTypes;

    static {
        supportedMimeTypes = new TreeSet<String>();
        String[] iioMimeTypes = ImageIO.getWriterMIMETypes();
        supportedMimeTypes.addAll(Arrays.asList(iioMimeTypes));
    }
    private Renderer renderer;

    public BufferedImageGetMapOp(Renderer renderer) {
        this.renderer = renderer;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public boolean handles(Request request) {
        if (request instanceof WMSRequest) {
            WMSRequest wmsReq = (WMSRequest) request;
            return WMSConstants.GET_MAP_OP.equals(wmsReq.getRequest())
                    && supportedMimeTypes.contains(wmsReq.getFormat());
        } else {
            return false;
        }
    }

    @Override
    public void execute(Request request, ResponseHandler handler) {
        MapDescription mapDescription = null;
        BufferedImage image = new BufferedImage(
                (int) mapDescription.getImageDim().getWidth(),
                (int) mapDescription.getImageDim().getHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        renderer.render(mapDescription, ((Graphics2D) image.getGraphics()));
        StreamingOutput output = new RenderedImageStreamingOutput(image, ((WMSRequest) request).getFormat());
        handler.setResult(output);
    }

    @Override
    public void provide(Capability cap) {
        if (cap.getRequest() == null) {
            cap.setRequest(new net.opengis.wms.Request());
        }
        if (cap.getRequest().getGetMap() == null) {
            cap.getRequest().setGetMap(new OperationType());
            DCPType dcpType = new DCPType();
            HTTP http = new HTTP();
            Get get = new Get();
            OnlineResource or = new OnlineResource();
            or.setType("simple");
            or.setHref("http://request");
            get.setOnlineResource(or);
            http.setGet(get);
            Post post = new Post();
            post.setOnlineResource(or);
            http.setPost(post);
            dcpType.setHTTP(http);
            cap.getRequest().getGetMap().getDCPType().add(dcpType);
        }
        cap.getRequest().getGetMap().getFormat().addAll(supportedMimeTypes);
    }
}
