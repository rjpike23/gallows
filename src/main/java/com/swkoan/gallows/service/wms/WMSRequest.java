package com.swkoan.gallows.service.wms;

import com.swkoan.gallows.service.Request;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.geotools.geometry.Envelope2D;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class WMSRequest implements Request {

    private String url;
    private Principal user;
    private String request;
    private String version;
    private String format;
    private MultivaluedMap<String, String> parameterMap;

    public WMSRequest(String url, Principal user, String request, String version, String format, MultivaluedMap<String, String> parameterMap) {
        this.url = url;
        this.user = user;
        this.request = request;
        this.version = version;
        this.format = format;
        this.parameterMap = parameterMap;
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

    public String getUrl() {
        return url;
    }

    public List<String> getLayerNames() {
        return getParamValueList(WMSConstants.LAYERS_PARAM);
    }

    public List<String> getStyleNames() {
        return getParamValueList(WMSConstants.STYLES_PARAM);
    }

    public String getCrs() {
        return getParamValue(WMSConstants.CRS_PARAM);
    }

    public Envelope getBbox() {
        String paramVal = getParamValue(WMSConstants.BBOX_PARAM);
        Envelope2D bbox = null;
        if (paramVal != null) {
            String[] strings = paramVal.split(",");
            if (strings.length == 4) {
                double[] dvals = new double[4];
                for (int i = 0; i < 4; ++i) {
                    dvals[i] = Double.parseDouble(strings[i]);
                }
                bbox = new Envelope2D();
                bbox.setFrameFromDiagonal(dvals[0], dvals[1], dvals[2], dvals[3]);
            }
        }
        return bbox;
    }

    public Integer getWidth() {
        return getParamValueInteger(WMSConstants.WIDTH_PARAM);
    }

    public Integer getHeight() {
        return getParamValueInteger(WMSConstants.HEIGHT_PARAM);
    }

    public Boolean getTransparent() {
        String paramValue = getParamValue(WMSConstants.TRANSPARENT_PARAM);
        if(paramValue != null) {
            return Boolean.parseBoolean(paramValue);
        }
        else {
            return Boolean.FALSE;
        }
    }

    public String getBackgroundColor() {
        return getParamValue(WMSConstants.BGCOLOR_PARAM);
    }

    public String getExceptionFormat() {
        return getParamValue(WMSConstants.EXCEPTIONS_PARAM);
    }

    private List<String> getParamValueList(String key) {
        List<String> result = null;
        if (parameterMap != null) {
            List<String> paramList = parameterMap.get(key);
            if (paramList != null) {
                result = new ArrayList<String>();
                for (String s : paramList) {
                    result.addAll(Arrays.asList(s.split(",")));
                }
            }
        }
        return result;
    }

    private String getParamValue(String key) {
        String result = null;
        if (parameterMap != null) {
            result = parameterMap.getFirst(key);
        }
        return result;
    }

    private Integer getParamValueInteger(String key) {
        String val = getParamValue(key);
        if(val != null) {
            return Integer.parseInt(val);
        }
        else {
            return null;
        }
    }
    
    @Override
    public String toString() {
        return "WMSRequest: " + request + " v=" + version + ". Layers: " + this.getParamValue(WMSConstants.LAYERS_PARAM);
    }
}
