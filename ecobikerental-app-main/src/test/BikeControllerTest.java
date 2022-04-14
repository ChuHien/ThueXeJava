
package test;

import model.BikeModel;
import object.Bike;
import org.junit.Assert;
import view.*;
import controller.*;
import junit.framework.TestCase;
import org.junit.Test;


public class BikeControllerTest extends TestCase{
    private BikeController bikeController;
    public BikeControllerTest() { // creates a (simple) test fixture
        bikeController = new BikeController();
    }
    @Test
    public void testGetBikeById() {
        assertNull( bikeController.getBikeById(12));
        assertNotNull(bikeController.getBikeById(1));
    }
    @Test
    public void testGetBikeListByPlId(){
        assertNull( bikeController.getBikeListByPlId(12));
        assertNotNull( bikeController.getBikeListByPlId(1));

    }
    public void testCheckStatus(){
        assertTrue(bikeController.checkStatus(3));
        assertFalse(bikeController.checkStatus(1));
    }


}