package test;
import BankSystem.Bank;
import model.BikeModel;
import object.Bike;
import org.junit.Assert;
import view.*;
import controller.*;
import junit.framework.TestCase;
import org.junit.Test;
public class BikeTypeControllerTest extends TestCase {
    private BikeTypeController bikeTypeController;
    public BikeTypeControllerTest() { // creates a (simple) test fixture
        bikeTypeController = new BikeTypeController();
    }
    @Test
    public void testGetTypes() throws Exception {
        assertNotNull(bikeTypeController.getTypes());
    }
}
