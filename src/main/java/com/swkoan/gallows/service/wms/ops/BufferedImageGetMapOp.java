package com.swkoan.gallows.service.wms.ops;

import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.GallowsConfig;
import com.swkoan.gallows.config.LayerDescriptor;
import com.swkoan.gallows.config.RenderableLayerDescriptor;
import com.swkoan.gallows.config.StyleDescriptor;
import com.swkoan.gallows.config.pojo.PojoMapDescriptor;
import com.swkoan.gallows.config.pojo.PojoRenderableLayerDescriptor;
import com.swkoan.gallows.render.Renderer;
import com.swkoan.gallows.service.Operation;
import com.swkoan.gallows.service.RenderedImageStreamingOutput;
import com.swkoan.gallows.service.Request;
import com.swkoan.gallows.service.ResponseHandler;
import com.swkoan.gallows.service.wms.WMSCapabilityProvider;
import com.swkoan.gallows.service.wms.WMSConstants;
import com.swkoan.gallows.service.wms.WMSException;
import com.swkoan.gallows.service.wms.WMSRequest;
import java.awt.Color;
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
import net.opengis.wms.v_1_3_0.Capability;
import net.opengis.wms.v_1_3_0.DCPType;
import net.opengis.wms.v_1_3_0.Get;
import net.opengis.wms.v_1_3_0.HTTP;
import net.opengis.wms.v_1_3_0.OnlineResource;
import net.opengis.wms.v_1_3_0.OperationType;
import net.opengis.wms.v_1_3_0.Post;
import org.geotools.referencing.CRS;
import org.hisrc.w3c.xlink.v_1_0.TypeType;
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

            List<RenderableLayerDescriptor> layerConfigs = new ArrayList<RenderableLayerDescriptor>();
            List<String> layerNames = wmsRequest.getLayerNames();
            List<String> styleNames = wmsRequest.getStyleNames();
            for (int i = 0; i < layerNames.size(); ++i) {
                String layerName = layerNames.get(i);
                LayerDescriptor ld = gc.getLayerConfig(layerName);
                if (ld != null) {
                    StyleDescriptor style = null;
                    if(styleNames != null && styleNames.size() > i) {
                        String styleName = styleNames.get(i);
                        if(! "".equals(styleName.trim())) {
                            style = ld.getStyle(styleName);
                            if(style == null) {
                                throw new WMSException(styleName, "StyleNotDefined");
                            }
                        }
                    }
                    RenderableLayerDescriptor rld = new PojoRenderableLayerDescriptor(ld, style);
                    layerConfigs.add(rld);
                }
                else {
                    throw new WMSException(layerName, "LayerNotDefined");
                }
            }

            // TODO: using geotools CRS class, this code layer should not have
            // dependencies on non-standards based libraries.
            CoordinateReferenceSystem crs = CRS.decode(crsParam, false);

            LOG.info("WMS GetMap request validated, rendering...");
            PojoMapDescriptor mapDescription = new PojoMapDescriptor(
                    mapSize, layerConfigs, crs, wmsRequest.getBbox());
            BufferedImage image = new BufferedImage(
                    (int) mapDescription.getImageDim().getWidth(),
                    (int) mapDescription.getImageDim().getHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics = ((Graphics2D) image.getGraphics());
            if(wmsRequest.getBackgroundColor() != null) {
                int argb = Integer.decode(wmsRequest.getBackgroundColor());
                Color color = new Color(argb, false);
                graphics.setColor(color);
                graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
            }
            renderer.render(mapDescription, graphics);

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
            cap.setRequest(new net.opengis.wms.v_1_3_0.Request());
        }
        if (cap.getRequest().getGetMap() == null) {
            cap.getRequest().setGetMap(new OperationType());
            DCPType dcpType = new DCPType();
            HTTP http = new HTTP();
            Get get = new Get();
            OnlineResource or = new OnlineResource();
            or.setTYPE(TypeType.SIMPLE);
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
