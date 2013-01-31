package com.swkoan.gallows.service;

/**
 *
 */
public interface ResponseHandler {

    void setResultMIMEType(String mimeType);

    void setResult(Object o);

    void unableToDispatchRequest();

    void exceptionOnExecute(Exception e);
}
