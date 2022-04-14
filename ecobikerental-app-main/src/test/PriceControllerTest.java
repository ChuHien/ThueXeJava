package test;
import BankSystem.Bank;
import model.BikeModel;
import object.Bike;
import org.junit.Assert;
import view.*;
import controller.*;
import junit.framework.TestCase;
import org.junit.Test;
public class PriceControllerTest extends TestCase {
    private PriceController priceController;
    public PriceControllerTest() { // creates a (simple) test fixture
        priceController = new PriceController();
    }
    @Test
    public void testGetPrices() throws Exception {
        assertNotNull(priceController.getPrices());
    }
}

