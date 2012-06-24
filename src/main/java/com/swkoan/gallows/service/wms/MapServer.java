package com.swkoan.gallows.service.wms;

import com.swkoan.gallows.service.JAXRSResponseHandler;
import com.swkoan.gallows.service.OperationDispatcher;
import com.swkoan.gallows.service.Request;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 */
@Path("WMS")
public class MapServer {

    @Context
    private UriInfo context;
    @Context
    private SecurityContext secContext;
    private OperationDispatcher dispatcher;

    /**
     * Creates a new instance of MapServer
     */
    public MapServer(OperationDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * Retrieves representation of an instance of
     * com.swkoan.gallows.servlet.MapServer
     */
    @GET
    @Produces("application/xml")
    public Response getRequest(
            @QueryParam(WMSConstants.REQUEST_PARAM) String request,
            @QueryParam(WMSConstants.VERSION_PARAM) String version,
            @QueryParam(WMSConstants.FORMAT_PARAM) String format) {
        Request wmsRequest = new WMSRequest(context.getAbsolutePath().toString(),
                secContext.getUserPrincipal(), request, version, format);
        JAXRSResponseHandler responseHandler = new JAXRSResponseHandler();
        dispatcher.dispatch(wmsRequest, responseHandler);
        return responseHandler.getJAXRSResponse();
    }
}
