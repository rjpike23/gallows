package com.swkoan.gallows.service;

import com.swkoan.gallows.service.wms.WMSException;

/**
 *
 */
public interface ResponseHandler {

    void setResultMIMEType(String mimeType);

    void setResult(Object o);

    void unableToDispatchRequest();

    void exceptionOnExecute(Exception e);
}
