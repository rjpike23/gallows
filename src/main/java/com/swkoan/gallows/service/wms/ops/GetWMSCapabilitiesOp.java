package com.swkoan.gallows.service.wms.ops;

import com.swkoan.gallows.service.wms.WMSLayerCapabilityProvider;
import com.swkoan.gallows.config.ConfigStatus;
import com.swkoan.gallows.config.FolderConfig;
import com.swkoan.gallows.config.GallowsConfig;
import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.service.Operation;
import com.swkoan.gallows.service.Request;
import com.swkoan.gallows.service.ResponseHandler;
import com.swkoan.gallows.service.wms.WMSCapabilityProvider;
import com.swkoan.gallows.service.wms.WMSConstants;
import com.swkoan.gallows.service.wms.WMSRequest;
import java.util.Map;
import net.opengis.wms.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 */
public class GetWMSCapabilitiesOp implements Operation, ApplicationContextAware, WMSCapabilityProvider {

    private ApplicationContext springCtx;

    @Override
    public void setApplicationContext(ApplicationContext springCtx) throws BeansException {
        this.springCtx = springCtx;
    }

    @Override
    public boolean handles(Request request) {
        return (request instanceof WMSRequest)
                && WMSConstants.GET_CAPABILITIES_OP.equals(((WMSRequest) request).getRequest());
    }

    @Override
    public void execute(Request request, ResponseHandler handler) {
        WMSRequest wmsRequest = (WMSRequest) request;
        WMSCapabilities caps = new WMSCapabilities();
        caps.setVersion("1.3.0");
        caps.setService(getServiceMetadata(wmsRequest));
        caps.setCapability(getCapabilityMetadata(wmsRequest));
        handler.setResult(caps);
        handler.setResultMIMEType("text/xml");
    }

    private Service getServiceMetadata(WMSRequest request) {
        Service result = new Service();
        result.setName("WMS");
        result.setTitle("Gallows WMS Server");
        OnlineResource or = new OnlineResource();
        or.setHref(request.getUrl());
        result.setOnlineResource(or);
        return result;
    }

    private Capability getCapabilityMetadata(WMSRequest request) {
        Capability result = new Capability();

        // Collect all cap providers and have them provide:
        Map<String, WMSCapabilityProvider> capBeans =
                springCtx.getBeansOfType(WMSCapabilityProvider.class);
        for (WMSCapabilityProvider provider : capBeans.values()) {
            provider.provide(request, result);
        }

        // Layers:
        GallowsConfig gc = (GallowsConfig) springCtx.getBean("gallowsConfig");
        if(gc.status().getCurrentState() != ConfigStatus.States.LOADED) {
            gc.load();
        }
        FolderConfig layerCfg = gc.getRootLayerConfig();
        Layer layerMD = getLayerMetadata(layerCfg);
        result.setLayer(layerMD);
        return result;
    }

    private Layer getLayerMetadata(FolderConfig layerCfg) {
        WMSLayerCapabilityProvider provider = new WMSLayerCapabilityProvider();
        return provider.provide(layerCfg);
    }

    @Override
    public void provide(WMSRequest request, Capability cap) {
        if (cap.getRequest() == null) {
            cap.setRequest(new net.opengis.wms.Request());
        }
        // TODO: Check for existing operation type.
        OperationType opType = new OperationType();
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
        opType.getDCPType().add(dcpType);
        opType.getFormat().add("text/xml");
        cap.getRequest().setGetCapabilities(opType);
    }
}
