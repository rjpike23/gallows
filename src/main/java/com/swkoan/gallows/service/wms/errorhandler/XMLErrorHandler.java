package com.swkoan.gallows.service.wms.errorhandler;

import com.swkoan.gallows.service.wms.ErrorHandler;
import com.swkoan.gallows.service.wms.WMSCapabilityProvider;
import net.opengis.wms.Capability;

/**
 *
 */
public class XMLErrorHandler implements ErrorHandler, WMSCapabilityProvider {

    @Override
    public void provide(Capability cap) {
        if(cap.getException() == null) {
            cap.setException(new net.opengis.wms.Exception());
        }
        cap.getException().getFormat().add("XML");
    }
    
}
