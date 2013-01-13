package com.swkoan.gallows.config.pojo;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.Envelope2D;
import org.opengis.geometry.Envelope;

/**
 *
 */
public class BoundingBox {

    private double lowerLeftLatitude;
    private double lowerLeftLongitude;
    private double upperRightLatitude;
    private double upperRightLongitude;

    public BoundingBox() {
    }

    public BoundingBox(Envelope envelope) {
        this.fromEnvelope(envelope);
    }
    
    public BoundingBox(double lowerLeftLatitude, double lowerLeftLongitude,
            double upperRightLatitude, double upperRightLongitude) {
        this.lowerLeftLatitude = lowerLeftLatitude;
        this.lowerLeftLongitude = lowerLeftLongitude;
        this.upperRightLatitude = upperRightLatitude;
        this.upperRightLongitude = upperRightLongitude;
    }
    
    public double getLowerLeftLatitude() {
        return lowerLeftLatitude;
    }

    public void setLowerLeftLatitude(double lowerLeftLatitude) {
        this.lowerLeftLatitude = lowerLeftLatitude;
    }

    public double getLowerLeftLongitude() {
        return lowerLeftLongitude;
    }

    public void setLowerLeftLongitude(double lowerLeftLongitude) {
        this.lowerLeftLongitude = lowerLeftLongitude;
    }

    public double getUpperRightLatitude() {
        return upperRightLatitude;
    }

    public void setUpperRightLatitude(double upperRightLatitude) {
        this.upperRightLatitude = upperRightLatitude;
    }

    public double getUpperRightLongitude() {
        return upperRightLongitude;
    }

    public void setUpperRightLongitude(double upperRightLongitude) {
        this.upperRightLongitude = upperRightLongitude;
    }
    
    public void fromEnvelope(Envelope envelope) {
        this.setLowerLeftLatitude(envelope.getLowerCorner().getOrdinate(0));
        this.setLowerLeftLongitude(envelope.getLowerCorner().getOrdinate(1));
        this.setUpperRightLatitude(envelope.getUpperCorner().getOrdinate(0));
        this.setUpperRightLongitude(envelope.getUpperCorner().getOrdinate(1));
    }
    
    public Envelope toEnvelope() {
        DirectPosition2D lowerLeft = new DirectPosition2D(lowerLeftLatitude, lowerLeftLongitude);
        DirectPosition2D upperRight = new DirectPosition2D(upperRightLatitude, upperRightLongitude);
        Envelope envelope = new Envelope2D(lowerLeft, upperRight);
        return envelope;
    }
}
