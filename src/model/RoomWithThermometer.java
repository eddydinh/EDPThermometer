package model;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import model.exceptions.*;

public class RoomWithThermometer {
    public static final int WIDTH = 1500;
    public static final int LENGTH = 800;
    public static final String FREEZING_POINT = "15 F";
    public static final String BOILING_POINT = "212 F";
    public static final double INSIGNIFICANT_OFFSET = 0.5;
    public static final String DEFAULT_FILE_PATH = "src/temperature.txt";


    public List<String> tempArray;


    private int tempArrayIndex;


    Thermometer thermometer;
    Thermometer thermometerBoiling;
    Thermometer thermometerFreezing;



    public RoomWithThermometer(){
        try{
            tempArray = readToLines(DEFAULT_FILE_PATH);
        }catch (IOException exception){
            System.out.println("Can't read file");
        }

        initThemometers();



    }

    private void initThemometers() {

        thermometer = new Thermometer(FREEZING_POINT,
                BOILING_POINT,INSIGNIFICANT_OFFSET);
        thermometerBoiling = new Thermometer(FREEZING_POINT,
                BOILING_POINT,INSIGNIFICANT_OFFSET);
        thermometerFreezing = new Thermometer(FREEZING_POINT,
                BOILING_POINT,INSIGNIFICANT_OFFSET);

    }

    // Responds to key press codes
    // REQUIRES: key pressed code
    // MODIFIES: this
    // EFFECTS:
    public void keyPressed(int keyCode) throws NoMoreItemInTempArrayException {
        if(keyCode == KeyEvent.VK_ENTER){
            if(tempArrayIndex < tempArray.size() - 1){
                tempArrayIndex++;
            }else{
                throw new NoMoreItemInTempArrayException();
            }
        }

    }




    public List<String> readToLines(String filePath) throws IOException {

        return Files.readAllLines(Paths.get(filePath));

    }

    public Thermometer getThermometer() {
        return thermometer;
    }

    public Thermometer getThermometerFreezing() {
        return thermometerFreezing;
    }

    public Thermometer getThermometerBoiling() {
        return thermometerBoiling;
    }

    public int getTempArrayIndex() {
        return tempArrayIndex;
    }

}
