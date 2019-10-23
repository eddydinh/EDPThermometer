package model;


import model.exceptions.InsignificantFluctuationsException;


public class ThermometerBoiling extends Thermometer {
    public ThermometerBoiling(String freezingThreshold,
                              String boilingThreshold, double insignificantOffset) {
        super(freezingThreshold, boilingThreshold, insignificantOffset);
    }

    @Override
    public void setTemperature(double temperature) throws InsignificantFluctuationsException {

        if( isFahrenheit() && (getTemperature() == boilingThresholdF)
                || !isFahrenheit() && (getTemperature() == boilingThresholdC)
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
        isBoiling = false;
        if (isFahrenheit()){
            validateTempBoilingF(temperature);
        }else{
            validateTempBoilingC(temperature);
        }

    }

    private void validateTempBoilingF(double temperature) {

        if(getTemperature() < boilingThresholdF){
            if(temperature >= boilingThresholdF){
                isBoiling = true;

            }
        }
    }



    private void validateTempBoilingC(double temperature) {
        if(getTemperature() < boilingThresholdC){
            if(temperature >= boilingThresholdC){
                isBoiling = true;
            }
        }
    }
}
