package com.swkoan.gallows.service;

import javax.ws.rs.core.Response;

/**
 *
 */
public class JAXRSResponseHandler implements ResponseHandler {
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
}
