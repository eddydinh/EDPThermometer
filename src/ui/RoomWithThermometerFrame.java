package ui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.RoomWithThermometer;

import model.exceptions.*;

public class RoomWithThermometerFrame extends JFrame {
    private static final int INTERVAL = 20;
    private static final int INTERVAL_FLASH = 500;
    private UiPanel uiPanel;
    private RoomWithThermometer theRoom;

    RoomWithThermometerFrame() {
        super("WELCOME TO THE ROOM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        theRoom = new RoomWithThermometer();
        uiPanel = new UiPanel(theRoom);
        uiPanel.setLayout(null);
        add(uiPanel);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
        addTimerFlash();


    }

    // Set up timer
    // MODIFIES: none
    // EFFECTS: initializes a timer that updates game each
    // INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                uiPanel.repaint();
            }
        });
        t.start();
    }

    //Set up timer for flash
    //MODIFIES: UiPanel.flash
    //EFFECTS: invert UiPanel's flash boolean variable, make cursor flash
    //INTERVAL milliseconds
    private void addTimerFlash() {
        Timer t = new Timer(INTERVAL_FLASH, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                uiPanel.updateFlash();
            }
        });
        t.start();
    }

    // Centres frame on desktop
    // MODIFIES: this
    // EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            try {
                theRoom.keyPressed(e.getKeyCode());
            }catch(NoMoreItemInTempArrayException exception){
                uiPanel.setBottomMessage("NO MORE VALUE TO SEE!");
            }

        }
    }

    /*
     * Play the game
     */
    public static void main(String[] args) {
        new RoomWithThermometerFrame();
    }
}
