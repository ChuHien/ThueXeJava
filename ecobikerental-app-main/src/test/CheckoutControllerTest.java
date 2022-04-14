package test;
import model.BikeModel;
import object.Bike;
import org.junit.Assert;
import view.*;
import controller.*;
import junit.framework.TestCase;
import org.junit.Test;
import view.User.ReturnBikeScreen.CheckoutController;
import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

public class CheckoutControllerTest extends TestCase{
    private CheckoutController checkoutController;
    public CheckoutControllerTest(){
        checkoutController = new CheckoutController();
    }
    @Test
    public void testFeeCalculate(){
        assertEquals(checkoutController.feeCalculate(30,0),10000.0);
        assertNotEquals(checkoutController.feeCalculate(10,0),10000.0);
        assertEquals(checkoutController.feeCalculate(64,0),19000.0);
    }
}
