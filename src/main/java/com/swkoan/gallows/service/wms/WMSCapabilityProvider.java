package com.swkoan.gallows.service.wms;

import net.opengis.wms.v_1_3_0.Capability;

/**
 *
 */
public interface WMSCapabilityProvider {

    void provide(WMSRequest req, Capability cap);
}
