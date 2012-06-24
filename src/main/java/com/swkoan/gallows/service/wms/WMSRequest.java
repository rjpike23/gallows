package com.swkoan.gallows.service.wms;

import com.swkoan.gallows.service.Request;
import java.security.Principal;

/**
 *
 */
public class WMSRequest implements Request {
    private String url;
    private Principal user;
    private String request;
    private String version;
    private String format;

    public WMSRequest(String url, Principal user, String request, String version, String format) {
        this.url = url;
        this.user = user;
        this.request = request;
        this.version = version;
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String getRequest() {
        return request;
    }

    public Principal getUser() {
        return user;
    }

    public String getVersion() {
        return version;
    }

    public String getURL() {
        return url;
    }
    
}
