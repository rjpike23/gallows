package com.swkoan.gallows.service.wms.ops;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.GallowsConfig;
import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.data.MapDescription;
import com.swkoan.gallows.render.Renderer;
import com.swkoan.gallows.service.Operation;
import com.swkoan.gallows.service.RenderedImageStreamingOutput;
import com.swkoan.gallows.service.Request;
import com.swkoan.gallows.service.ResponseHandler;
import com.swkoan.gallows.service.wms.WMSCapabilityProvider;
import com.swkoan.gallows.service.wms.WMSConstants;
import com.swkoan.gallows.service.wms.WMSException;
import com.swkoan.gallows.service.wms.WMSRequest;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.ws.rs.core.StreamingOutput;
import net.opengis.wms.Capability;
import net.opengis.wms.DCPType;
import net.opengis.wms.Get;
import net.opengis.wms.HTTP;
import net.opengis.wms.OnlineResource;
import net.opengis.wms.OperationType;
import net.opengis.wms.Post;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 */
public class BufferedImageGetMapOp implements Operation, WMSCapabilityProvider, ApplicationContextAware {

    private static final Logger LOG = Logger.getLogger(BufferedImageGetMapOp.class.getName());
    private static Set<String> supportedMimeTypes;

    static {
        supportedMimeTypes = new TreeSet<String>();
        String[] iioMimeTypes = ImageIO.getWriterMIMETypes();
        supportedMimeTypes.addAll(Arrays.asList(iioMimeTypes));
    }
    private Renderer renderer;
    private ApplicationContext springCtx;

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
        }
        else {
            return false;
        }
    }

    @Override
    public void execute(Request request, ResponseHandler handler) {
        try {
            WMSRequest wmsRequest = (WMSRequest) request;
            String crsParam = wmsRequest.getCrs();
            if (crsParam == null) {
                throw new WMSException("CRS parameter is required", "InvalidParameters");
            }
            if (wmsRequest.getWidth() == null || wmsRequest.getHeight() == null) {
                throw new WMSException("HEIGHT and WIDTH parameters are required.", "InvalidParameters");
            }
            if (wmsRequest.getLayerNames() == null) {
                throw new WMSException("LAYERS parameter is required", "InvalidParameters");
            }
            Rectangle mapSize = new Rectangle(wmsRequest.getWidth(), wmsRequest.getHeight());

            GallowsConfig gc = (GallowsConfig) springCtx.getBean("gallowsConfig");
            if (gc.status().getCurrentState() != ConfigStatus.States.LOADED) {
                gc.load();
            }
            List<LayerConfig> layerConfigs = new ArrayList<LayerConfig>();
            for (String layerName : wmsRequest.getLayerNames()) {
                LayerConfig lc = gc.getLayerConfig(layerName);
                if (lc != null) {
                    layerConfigs.add(lc);
                }
                else {
                    throw new WMSException(layerName, "LayerNotDefined");
                }
            }
            // TODO: using geotools CRS class, this code layer should not have
            // dependencies on non-standards based libraries.
            CoordinateReferenceSystem crs = CRS.decode(crsParam, false);
            
            LOG.info("WMS GetMap request validated, rendering...");
            MapDescription mapDescription = new MapDescription(
                    mapSize, layerConfigs, crs, wmsRequest.getBbox());
            BufferedImage image = new BufferedImage(
                    (int) mapDescription.getImageDim().getWidth(),
                    (int) mapDescription.getImageDim().getHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            renderer.render(mapDescription, ((Graphics2D) image.getGraphics()));

            StreamingOutput output = new RenderedImageStreamingOutput(image, wmsRequest.getFormat());
            handler.setResult(output);
            handler.setResultMIMEType(wmsRequest.getFormat());
            LOG.info("WMS GetMap request completed.");
        }
        catch (FactoryException e) {
            throw new WMSException("The service does not support the specified CRS.", "InvalidCRS", e);
        }
        catch (WMSException e) {
            throw e;
        }
        catch (Exception e) {
            LOG.log(Level.SEVERE, "Unexpected exception!", e);
            throw new WMSException("Unexpected error", null, e);
        }
    }

    @Override
    public void provide(WMSRequest request, Capability cap) {
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
            or.setHref(request.getUrl());
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

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.springCtx = ac;
    }
}
