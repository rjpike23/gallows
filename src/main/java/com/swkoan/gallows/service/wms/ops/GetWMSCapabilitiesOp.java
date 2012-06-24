package com.swkoan.gallows.service.wms.ops;

import com.swkoan.gallows.config.GallowsConfig;
import com.swkoan.gallows.service.Operation;
import com.swkoan.gallows.service.Request;
import com.swkoan.gallows.service.ResponseHandler;
import com.swkoan.gallows.service.wms.ErrorHandler;
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
    
    private ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.ac = ac;
    }

    @Override
    public boolean handles(Request request) {
        return (request instanceof WMSRequest) &&
                WMSConstants.GET_CAPABILITIES_OP.equals(((WMSRequest) request).getRequest());
    }

    @Override
    public void execute(Request request, ResponseHandler handler) {
        WMSRequest wmsRequest = (WMSRequest) request;
        WMSCapabilities caps = new WMSCapabilities();
        caps.setService(getServiceMetadata(wmsRequest));
        caps.setCapability(getCapabilityMetadata());
        handler.setResult(caps);
        handler.setResultMIMEType(wmsRequest.getFormat());
    }
    
    private Service getServiceMetadata(WMSRequest request) {
        Service result = new Service();
        result.setName("WMS");
        result.setTitle("Gallows WMS Server");
        OnlineResource or = new OnlineResource();
        or.setHref(request.getURL());
        result.setOnlineResource(or);
        return result;
    }
    
    private Capability getCapabilityMetadata() {
        Capability result = new Capability();
        
        // Request types:
        Map<String, WMSCapabilityProvider> capBeans = 
                ac.getBeansOfType(WMSCapabilityProvider.class);
        for(WMSCapabilityProvider provider: capBeans.values()) {
            provider.provide(result);
        }
        
        // Layers:
        return result;
    }

    @Override
    public void provide(Capability cap) {
        if(cap.getRequest() == null) {
            cap.setRequest(new net.opengis.wms.Request());
        }
        OperationType opType = new OperationType();
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
        opType.getDCPType().add(dcpType);
        opType.getFormat().add("text/xml");
        cap.getRequest().setGetCapabilities(opType);
    }
    
}
