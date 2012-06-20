package com.swkoan.gallows.service;

import javax.ws.rs.core.Response;

/**
 *
 */
public class JAXRSResponseHandler implements ResponseHandler {
    private Response.ResponseBuilder builder;
    
    public Response getJAXRSResponse() {
        return builder.build();
    }
}
