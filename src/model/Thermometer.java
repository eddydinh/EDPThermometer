package model;

import java.util.ArrayList;
import java.util.List;
import model.exceptions.*;
public class Thermometer {


    protected double temperature;
    protected double freezingThresholdC;



    protected double boilingThresholdC;
    protected double freezingThresholdF;
    protected double boilingThresholdF;
    protected double insignificantOffset;
    protected boolean isFahrenheit;


    protected double minTempC = -500.0;
    protected double maxTempC = 500.0;
    protected double minTempF = -868;
    protected double maxTempF = 932;
    protected boolean isBoiling;
    protected boolean isFreezing;


    public Thermometer(String freezingThreshold,
                       String boilingThreshold,
                       double insignificantOffset) {
        initThresholds(freezingThreshold,boilingThreshold);
        this.insignificantOffset = insignificantOffset;


    }

    private void initThresholds(String freezingThreshold, String boilingThreshold) {
        String[] tempString = freezingThreshold.split(" ");

        if(tempString[1].equals("C")){
            this.freezingThresholdC = Double.valueOf(tempString[0]);
            this.freezingThresholdF = convertCToF(freezingThresholdC);
        }else{
            this.freezingThresholdF = Double.valueOf(tempString[0]);
            this.freezingThresholdC= convertFToC(freezingThresholdF);
        }

        tempString = boilingThreshold.split(" ");
        if(tempString[1].equals("C")){
            this.boilingThresholdC = Double.valueOf(tempString[0]);
            this.boilingThresholdF = convertCToF(boilingThresholdC);
        }else{
            this.boilingThresholdF = Double.valueOf(tempString[0]);
            this.boilingThresholdC= convertFToC(boilingThresholdF);
        }
    }

    private double convertCToF(double degreeC) {
        return (degreeC * 9/5) + 32;
    }

    private double convertFToC(double degreeF){
        return (degreeF  - 32) * 5/9;
    }
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) throws InsignificantFluctuationsException {

        if( isFahrenheit() && (getTemperature() == boilingThresholdF || getTemperature() == freezingThresholdF)
             || !isFahrenheit() && (getTemperature() == boilingThresholdC || getTemperature() == freezingThresholdC)
        ){
            if(Math.abs(temperature-getTemperature()) > insignificantOffset){
                validateTemp(temperature);
                this.temperature = temperature;

            }else{
                throw new InsignificantFluctuationsException ();
            }
        }else{
            validateTemp(temperature);
            this.temperature = temperature;
        }


    }

    //Assume boiling point is always > freezing point

    protected void validateTemp(double temperature) {

       if(isFahrenheit()){

           validateTempF(temperature);
       }else{

           validateTempC(temperature);
       }
    }

    private void validateTempF(double temperature) {

        if(temperature >= getBoilingThresholdF()){
            isBoiling = true;
            isFreezing = false;

        }else if (temperature <= getFreezingThresholdF()){
            isBoiling = false;
            isFreezing = true;
        }else{
            isBoiling = false;
            isFreezing = false;
        }
    }

    private void validateTempC(double temperature) {

        if(temperature >= getBoilingThresholdC()){
            isBoiling = true;
            isFreezing = false;

        }else if (temperature <= getFreezingThresholdC()){
            isBoiling = false;
            isFreezing = true;
        }else{
            isBoiling = false;
            isFreezing = false;
        }
    }


    public boolean isFahrenheit() {
        return isFahrenheit;
    }

    public void setFahrenheit(boolean fahrenheit) {
        isFahrenheit = fahrenheit;
    }

    public String getDegreeType() {
        if (isFahrenheit){
            return " F";
        }
        else{
            return  " C";
        }
    }



    public double getMaxTempC() {
        return maxTempC;
    }

    public double getMaxTempF() {
        return maxTempF;
    }


    public boolean isBoiling() {

        return isBoiling;
    }

    public boolean isFreezing() {
        return isFreezing;
    }

    public double getFreezingThresholdC() {
        return freezingThresholdC;
    }

    public double getBoilingThresholdC() {
        return boilingThresholdC;
    }

    public double getFreezingThresholdF() {
        return freezingThresholdF;
    }

    public double getBoilingThresholdF() {
        return boilingThresholdF;
    }
}
