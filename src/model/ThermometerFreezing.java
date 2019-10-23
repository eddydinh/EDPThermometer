package model;


import model.exceptions.InsignificantFluctuationsException;


public class ThermometerFreezing extends Thermometer {

    public ThermometerFreezing(String freezingThreshold,
                               String boilingThreshold, double insignificantOffset) {
        super(freezingThreshold, boilingThreshold, insignificantOffset);
    }
   @Override
    public void setTemperature(double temperature) throws InsignificantFluctuationsException {

        if( isFahrenheit() && (getTemperature() == freezingThresholdF)
                || !isFahrenheit() && (getTemperature() == freezingThresholdC)
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
    @Override
    protected void validateTemp(double temperature) {
        isFreezing = false;
        if (isFahrenheit()){
            validateTempFreezingF(temperature);
        }else{
            validateTempFreezingC(temperature);
        }
    }

    private void validateTempFreezingC(double temperature) {
        if(getTemperature() > freezingThresholdC){
            if(temperature <= freezingThresholdC){

                isFreezing = true;
            }
        }
    }

    private void validateTempFreezingF(double temperature) {
        if(getTemperature() > freezingThresholdF){
            if(temperature <= freezingThresholdF){
                isFreezing = true;
            }
        }
    }


}
