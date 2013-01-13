package com.swkoan.gallows.service.wms.ops;

import com.swkoan.gallows.service.Operation;
import com.swkoan.gallows.service.Request;
import com.swkoan.gallows.service.ResponseHandler;
import com.swkoan.gallows.service.wms.WMSCapabilityProvider;
import net.opengis.wms.Capability;

/**
 *
 */
public class GetMapOp implements Operation, WMSCapabilityProvider {

    @Override
    public boolean handles(Request request) {
        return true;
    }

    @Override
    public void execute(Request request, ResponseHandler handler) {
    }

    @Override
    public void provide(Capability cap) {
    }
    
}
