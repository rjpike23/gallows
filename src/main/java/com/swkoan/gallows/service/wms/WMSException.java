package com.swkoan.gallows.service.wms;

import java.io.PrintWriter;
import java.io.StringWriter;
import net.opengis.ogc.ServiceExceptionReport;
import net.opengis.ogc.ServiceExceptionType;

/**
 *
 */
public class WMSException extends RuntimeException {
    private String code;

    /**
     * Creates a new instance of
     * <code>WMSException</code> without detail message.
     */
    public WMSException() {
    }

    /**
     * Constructs an instance of
     * <code>WMSException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public WMSException(String msg, String code) {
        super(msg);
        this.code = code;
    }
    
    public WMSException(String msg, String code, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }
    
    public ServiceExceptionReport getWMSExceptionXML() {
        ServiceExceptionReport svcEx = new ServiceExceptionReport();
        svcEx.setVersion("1.3.0");
        ServiceExceptionType svcExType = new ServiceExceptionType();
        svcExType.setCode(code);
        svcExType.setValue(this.getMessage());
        StringWriter stackTrace = new StringWriter();
        PrintWriter pw = new PrintWriter(stackTrace);
        this.printStackTrace(pw);
        svcExType.setLocator(stackTrace.toString());
        svcEx.getServiceException().add(svcExType);
        return svcEx;
    }
}
