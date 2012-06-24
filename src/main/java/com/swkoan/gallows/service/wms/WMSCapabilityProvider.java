package com.swkoan.gallows.service.wms;

import net.opengis.wms.Capability;

/**
 *
 */
public interface WMSCapabilityProvider {

    void provide(Capability cap);
}
