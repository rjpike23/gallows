package com.swkoan.gallows.service.wms;

import net.opengis.wms.Capability;

/**
 *
 */
public interface WMSCapabilityProvider {

    void provide(WMSRequest req, Capability cap);
}
