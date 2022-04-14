package test;
import BankSystem.Bank;
import model.BikeModel;
import object.Bike;
import org.junit.Assert;
import view.*;
import controller.*;
import junit.framework.TestCase;
import org.junit.Test;
public class BankTest extends TestCase {
    private Bank bank;
    public BankTest() { // creates a (simple) test fixture
        bank = new Bank();
    }
    @Test
    public void testGetAmount() throws Exception {
        assertEquals(bank.getAmount("333333"),(float) 1000000.0);
    }
}
