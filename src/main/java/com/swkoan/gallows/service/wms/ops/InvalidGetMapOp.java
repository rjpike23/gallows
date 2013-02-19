package com.swkoan.gallows.service.wms.ops;

import com.swkoan.gallows.service.Operation;
import com.swkoan.gallows.service.Request;
import com.swkoan.gallows.service.ResponseHandler;
import com.swkoan.gallows.service.wms.WMSConstants;
import com.swkoan.gallows.service.wms.WMSException;
import com.swkoan.gallows.service.wms.WMSRequest;

/**
 * This is a meta-operation that validates a GetMap request, and returns a WMS-specific
 * error to the client.
 */
public class InvalidGetMapOp implements Operation {

    @Override
    public boolean handles(Request request) {
        if(request instanceof WMSRequest) {
            WMSRequest wmsRequest = (WMSRequest) request;
            return WMSConstants.GET_MAP_OP.equals(wmsRequest.getRequest());
        }
        else {
            return false;
        }
    }

    @Override
    public void execute(Request request, ResponseHandler handler) {
        throw new WMSException("", "InvalidFormat");
    }
    
}
