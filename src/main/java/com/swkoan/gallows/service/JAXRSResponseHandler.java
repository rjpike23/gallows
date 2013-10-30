package com.swkoan.gallows.service;

import com.swkoan.gallows.service.wms.WMSCapabilityProvider;
import com.swkoan.gallows.service.wms.WMSException;
import com.swkoan.gallows.service.wms.WMSRequest;
import javax.ws.rs.core.Response;
import net.opengis.wms.Capability;

/**
 *
 */
public class JAXRSResponseHandler implements ResponseHandler, WMSCapabilityProvider {

    private Response.ResponseBuilder builder = Response.noContent();
    private String mimeType;
    private Object entity;

    public Response getJAXRSResponse() {
        builder.type(mimeType);
        builder.entity(entity);
        return builder.build();
    }

    @Override
    public void setResultMIMEType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public void setResult(Object o) {
        this.entity = o;
    }

    @Override
    public void unableToDispatchRequest() {
        builder.status(Response.Status.BAD_REQUEST);
    }

    @Override
    public void exceptionOnExecute(Exception e) {
        if(e instanceof WMSException) {
            entity = ((WMSException) e).getWMSExceptionXML();
            mimeType = "text/xml";
        }
        else {
            entity = e.getMessage();
            mimeType = "text/xml";
            builder.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void provide(WMSRequest request, Capability cap) {
        if (cap.getException() == null) {
            cap.setException(new net.opengis.wms.Exception());
        }
        cap.getException().getFormat().add("XML");
    }
}
