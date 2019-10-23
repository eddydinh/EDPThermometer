package test;

import model.RoomWithThermometer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class testRoom {
    RoomWithThermometer theRoom;

    @BeforeEach
    void setUp(){
        theRoom = new RoomWithThermometer();
    }

  @Test
    void testReadLines(){
      List<String> tempArray = null;
      try{
          tempArray = theRoom.readToLines(theRoom.DEFAULT_FILE_PATH);
          assertEquals(tempArray, theRoom.tempArray);

      }catch(IOException e){
          fail("IOEXCEPTION CAUGHT");
      }
  }



}
