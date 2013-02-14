package com.swkoan.gallows.service;

import com.swkoan.gallows.service.wms.WMSCapabilityProvider;
import javax.ws.rs.core.Response;
import net.opengis.wms.Capability;

/**
 *
 */
public class JAXRSResponseHandler implements ResponseHandler, WMSCapabilityProvider {

    private Response.ResponseBuilder builder = Response.noContent();

    public Response getJAXRSResponse() {
        return builder.build();
    }

    @Override
    public void setResultMIMEType(String mimeType) {
        builder.type(mimeType);
    }

    @Override
    public void setResult(Object o) {
        builder.entity(o);
    }

    @Override
    public void unableToDispatchRequest() {
        builder.status(Response.Status.BAD_REQUEST);
    }

    @Override
    public void exceptionOnExecute(Exception e) {
        builder.status(Response.Status.INTERNAL_SERVER_ERROR);
    }

    @Override
    public void provide(Capability cap) {
        if (cap.getException() == null) {
            cap.setException(new net.opengis.wms.Exception());
        }
        cap.getException().getFormat().add("XML");
    }
}
