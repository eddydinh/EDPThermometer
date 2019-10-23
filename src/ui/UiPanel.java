package ui;

import model.*;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;

import model.exceptions.*;

public class UiPanel extends JPanel {
    private static final int CIRCLE_WIDTH = 100;

    private static final int OFFSET_CENTER_X = 300;
    private static final int RECT_WIDTH = CIRCLE_WIDTH / 2;
    private static final int RECT_LENGTH = 400;
    private static final int OFFSET_RECT_Y = 20;


    private Color thermometerColor = new Color(196, 43, 27);
    private Color thermometerColorBoiling = new Color(196, 43, 27);
    private Color thermometerColorFreezing = new Color(76, 168, 255);
    private RoomWithThermometer theRoom;
    private Thermometer theThermometer;
    private Thermometer theThermometerBoiling;
    private Thermometer theThermometerFreezing;


    private boolean flash = false;
    private int centerX;
    private int centerY;
    private String bottomMessage;
    private String statusMessage;
    private String boilingMessage;
    private String freezingMessage;
    private String boilingQuestion;
    private String freezingQuestion;
    private String offsetQuestion;

    private int previousTempArrayIndex = -1;

    public UiPanel(RoomWithThermometer theRoom) {
        setPreferredSize(new Dimension(theRoom.WIDTH, theRoom.LENGTH));
        setBackground(Color.WHITE);
        this.theRoom = theRoom;
        theThermometer = theRoom.getThermometer();
        theThermometerBoiling = theRoom.getThermometerBoiling();
        theThermometerFreezing = theRoom.getThermometerFreezing();
        centerX = theRoom.WIDTH / 2 - CIRCLE_WIDTH / 2;
        centerY = theRoom.LENGTH / 2 + CIRCLE_WIDTH / 2;
        bottomMessage = "PRESS ENTER TO SEE NEXT TEMPERATURE VALUE";
        statusMessage = "CURRENT BOILING THRESHOLD:" + theThermometer.getBoilingThresholdC() + " C / "
                + theThermometer.getBoilingThresholdF() + " F" +
                " | CURRENT FREEZING THRESHOLD: " + theThermometer.getFreezingThresholdC() + " C / "
                + theThermometer.getFreezingThresholdF() + " F" +
                " | IGNORE FLUCTUATIONS OF: +/- " + theRoom.INSIGNIFICANT_OFFSET + " C/F FROM BOILING/FREEZING THRESHOLD";
        boilingMessage = "This thermometer only labels boiling point when the temperature is in an increasing direction.";
        freezingMessage = "This thermometer only labels freezing point when the temperature is in a decreasing direction.";
//        boilingQuestion = "WHAT IS THE BOILING POINT? Enter value between " +
//                theThermometer.getMinTempC() + " and " + theThermometer.getMaxTempC() + " C OR "
//                + theThermometer.getMinTempF() + " and " + theThermometer.getMaxTempF() + " F. Example: 0 C, 500 C";

    }


    @Override
    protected void paintComponent(Graphics gameGraphics) {
        super.paintComponent(gameGraphics);
//
//        switch(theRoom.getState()){
//            case "freezing":
//                displayFreezingQuestion(gameGraphics);
//                break;
//            case "offset":
//                displayOffsetQuestion(gameGraphics);
//                break;
//            case "thermometers":
//                displayThermometers(gameGraphics);
//                break;
//            default:
//                displayBoilingQuestion(gameGraphics);
//                break;
//        }

        displayThermometers(gameGraphics);

        if (!bottomMessage.equals("NO MORE VALUE TO SEE!") && theRoom.getTempArrayIndex() != previousTempArrayIndex) {
            String tempString = theRoom.tempArray.get(theRoom.getTempArrayIndex());
            String[] temp = tempString.split(" ");
            if (temp[1].equals("F")) {
                theThermometer.setFahrenheit(true);
                theThermometerBoiling.setFahrenheit(true);
                theThermometerFreezing.setFahrenheit(true);
            } else {
                theThermometer.setFahrenheit(false);
                theThermometerBoiling.setFahrenheit(false);
                theThermometerFreezing.setFahrenheit(false);
            }
            double targetTemp = Double.parseDouble(temp[0]);
            try {
                theThermometer.setTemperature(targetTemp);
                theThermometerBoiling.setTemperature(targetTemp);
                theThermometerFreezing.setTemperature(targetTemp);
                bottomMessage = "PRESS ENTER TO SEE NEXT VALUE!";
            } catch (InsignificantFluctuationsException e) {
                bottomMessage = "IGNORED VALUE OF " + tempString + " DUE TO FLUCTUATION INSIGNIFICANCE. PRESS ENTER TO SEE NEXT VALUE";
            }
            previousTempArrayIndex = theRoom.getTempArrayIndex();
        }


    }

    private void displayFreezingQuestion(Graphics gameGraphics) {
        int contentWidth = drawQuestion(freezingQuestion,gameGraphics);
        drawFlashingCursor(gameGraphics,centerX,centerY,contentWidth,Color.BLACK);
    }

    private int drawQuestion(String question,Graphics gameGraphics) {
        return drawMessage(question, gameGraphics, 25, 0,centerY,Color.BLACK);
    }

    private void displayOffsetQuestion(Graphics gameGraphics) {
        drawQuestion(offsetQuestion,gameGraphics);

    }

    private void displayBoilingQuestion(Graphics gameGraphics) {
        drawQuestion(boilingQuestion,gameGraphics);
    }


    // MODIFIES: gameGraphics
    // EFFECTS:  draws GAME_OVER_MESSAGE strings, and replay instructions onto gameGraphics
    private void displayThermometers(Graphics gameGraphics) {
        drawThermometer(gameGraphics);
        drawThermometerBoiling(gameGraphics);
        drawThermometerFreezing(gameGraphics);
        drawMessage(bottomMessage, gameGraphics, 20, 0, theRoom.LENGTH - 100, Color.BLACK);
        drawMessage(statusMessage, gameGraphics, 15, 0, theRoom.LENGTH - 50, Color.BLACK);
    }

    private void drawThermometerBoiling(Graphics gameGraphics) {
        drawRect(gameGraphics, centerX + OFFSET_CENTER_X + RECT_WIDTH + CIRCLE_WIDTH / 4,
                centerY + OFFSET_RECT_Y, RECT_WIDTH, RECT_LENGTH, null, null);

        drawCircle(gameGraphics, thermometerColorBoiling, centerX + OFFSET_CENTER_X,
                centerY, CIRCLE_WIDTH);

        //Inner rect
        drawRect(gameGraphics, centerX + OFFSET_CENTER_X + RECT_WIDTH + 1 + CIRCLE_WIDTH / 4,
                centerY + OFFSET_RECT_Y + 5, RECT_WIDTH + 1,
                computeRectLength(theThermometerBoiling), thermometerColorBoiling, theThermometerBoiling);


        drawMessage(boilingMessage, gameGraphics, 12, centerX + OFFSET_CENTER_X + RECT_WIDTH + 1 + CIRCLE_WIDTH / 4 + 250,
                centerY + OFFSET_RECT_Y + CIRCLE_WIDTH + 30
                , thermometerColorBoiling
        );
    }

    private void drawThermometerFreezing(Graphics gameGraphics) {

        drawRect(gameGraphics, centerX - OFFSET_CENTER_X + RECT_WIDTH + CIRCLE_WIDTH / 4,
                centerY + OFFSET_RECT_Y, RECT_WIDTH, RECT_LENGTH, null, null);

        drawCircle(gameGraphics, thermometerColorFreezing, centerX - OFFSET_CENTER_X,
                centerY, CIRCLE_WIDTH);

        //Inner rect
        drawRect(gameGraphics, centerX - OFFSET_CENTER_X + RECT_WIDTH + 1 + CIRCLE_WIDTH / 4,
                centerY + OFFSET_RECT_Y + 5, RECT_WIDTH + 1,
                computeRectLength(theThermometerFreezing), thermometerColorFreezing, theThermometerFreezing);

        drawMessage(freezingMessage, gameGraphics, 12, centerX - OFFSET_CENTER_X + RECT_WIDTH + 1 + CIRCLE_WIDTH / 4 + 250,
                centerY + OFFSET_RECT_Y + CIRCLE_WIDTH + 30
                , thermometerColorFreezing);
    }

    private void drawThermometer(Graphics gameGraphics) {

        drawRect(gameGraphics, centerX + RECT_WIDTH + CIRCLE_WIDTH / 4,
                centerY + OFFSET_RECT_Y, RECT_WIDTH, RECT_LENGTH, null, null);

        drawCircle(gameGraphics, thermometerColor, centerX, centerY, CIRCLE_WIDTH);


        //Inner rect
        drawRect(gameGraphics, centerX + RECT_WIDTH + 1 + CIRCLE_WIDTH / 4,
                centerY + OFFSET_RECT_Y + 5, RECT_WIDTH + 1,
                computeRectLength(theThermometer), thermometerColor, theThermometer);


    }

    private int computeRectLength(Thermometer thermometer) {
        double temp = thermometer.getTemperature();
        double maxTemp;
        if (thermometer.isFahrenheit()) {
            maxTemp = thermometer.getMaxTempF();
        } else {
            maxTemp = thermometer.getMaxTempC();
        }

        return (int) ((RECT_LENGTH / 2) + (RECT_LENGTH / 2 * (temp / maxTemp)));

    }


    //Draw rect component of main game screen
    //MODIFIES: gameGraphics
    //EFFECTS: draws all the rectangular component of main game screen
    private void drawCircle(Graphics gameGraphics, Color circleColor,
                            int circleX, int circleY, int circleWidth) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(circleColor);
        gameGraphics.fillOval(
                circleX, circleY,
                circleWidth, circleWidth);
        gameGraphics.setColor(savedColor);


    }

    //Draw rect component of main game screen
    //MODIFIES: gameGraphics
    //EFFECTS: draws all the rectangular component of main game screen
    private void drawRect(Graphics gameGraphics,
                          int rectX, int rectY, int rectWidth, int rectLength, Color rectColor, Thermometer thermometer) {
        if (rectLength < 25) {
            rectLength = 25;
        }
        Graphics2D g2d = (Graphics2D) gameGraphics;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(180), rectX, rectY);
        if (rectColor == null) {
            gameGraphics.drawRect(
                    rectX, rectY,
                    rectWidth, rectLength);
            g2d.setTransform(old);


        } else {
            Color savedColor = gameGraphics.getColor();
            gameGraphics.setColor(rectColor);
            gameGraphics.fillRect(
                    rectX, rectY,
                    rectWidth, rectLength);
            gameGraphics.setColor(savedColor);

            drawInputLevelLine(gameGraphics, rectX, rectY + rectLength, RECT_WIDTH + 30);
            g2d.setTransform(old);

            String message;
            if (thermometer.isBoiling()) {
                message = "(Boiling) " + String.valueOf(thermometer.getTemperature()) + thermometer.getDegreeType();
                if (thermometer instanceof Thermometer) {
                    thermometerColor = thermometerColorBoiling;
                }
            } else if (thermometer.isFreezing()) {
                message = "(Freezing) " + String.valueOf(thermometer.getTemperature()) + thermometer.getDegreeType();
                if (thermometer instanceof Thermometer) {
                    thermometerColor = thermometerColorFreezing;
                }
            } else {
                message = String.valueOf(thermometer.getTemperature()) + thermometer.getDegreeType();
            }
            drawMessage(message, gameGraphics,
                    15, rectX - RECT_WIDTH - 30,
                    rectY - rectLength, Color.BLACK);


        }


    }

    private void drawInputLevelLine(Graphics gameGraphics, int lineX, int lineY, int lineWidth) {
        gameGraphics.drawLine(lineX, lineY, lineX + lineWidth, lineY);
    }


    //Draws message
    //MODIFIES: gameGraphics
    //EFFECTS: draws messages onto game Graphics
    private int drawMessage(
            String message,
            Graphics gameGraphics,
            int fontSize,
            int messageX,
            int messageY,
            Color textColor) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(textColor);
        gameGraphics.setFont(new Font("Arial", fontSize, fontSize));
        FontMetrics fontMetrics = gameGraphics.getFontMetrics();
        int stringWidth = displayString(message, gameGraphics, fontMetrics, messageX, messageY);
        gameGraphics.setColor(savedColor);
        return stringWidth;


    }


    // MODIFIES: gameGraphics
    // EFFECTS: Draw content for password boxes on login screen
    private void drawFlashingCursor(Graphics gameGraphics,
                                    int logInBoxX,
                                    int logInBoxY,
                                    int contentWidth,
                                    Color color) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(color);
        gameGraphics.setFont(new Font("Arial", 25, 25));
        String str = "|";
        if (flash) {
            str = "";
        }
        gameGraphics.drawString(str,
                logInBoxX + contentWidth,
                logInBoxY - 8);
        gameGraphics.setColor(savedColor);

    }

    // Centres a string on the screen
    // MODIFIES: gameGraphics
    // EFFECTS:  display the string str horizontally onto g at vertical position y
    private int displayString(String string, Graphics gameGraphics, FontMetrics fontMetrics, int stringX, int stringY) {
        int stringWidth = fontMetrics.stringWidth(string);
        if (stringX == 0) {
            gameGraphics.drawString(string, (theRoom.WIDTH - stringWidth) / 2, stringY);
        } else {
            gameGraphics.drawString(string, stringX - stringWidth, stringY);
        }
        return stringWidth;
    }

    //MODIFIES: flash
    //EFFECTS: invert flash boolean variable
    public void updateFlash() {
        flash = !flash;
    }


    public void setBottomMessage(String bottomMessage) {
        this.bottomMessage = bottomMessage;
    }


}
