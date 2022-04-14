package controller;

import model.PriceModel;
import object.Price;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PriceController {
    private PriceModel pModel;
    public PriceController() { this.pModel = new PriceModel(); }

    public List<Price> getPrices() {
        List<Price> list = new ArrayList<Price>();
        try {
            list = pModel.getPrices();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        return list;
    }

    public void updatePrice(int id, float price) {
        int cnt = 0;
        try {
            cnt = pModel.updatePrice(id, price);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
//        return cnt;
    }
}
