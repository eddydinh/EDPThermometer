package model;


import java.util.List;

public class ThermometerFreezing extends Thermometer {

    public ThermometerFreezing(String freezingThreshold,
                               String boilingThreshold, double insignificantOffset) {
        super(freezingThreshold, boilingThreshold, insignificantOffset);
    }
}
