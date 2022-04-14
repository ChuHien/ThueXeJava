package controller;

import model.BikeTypeModel;
import object.BikeType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BikeTypeController {
    private BikeTypeModel bikeTypeModel;
    public BikeTypeController() { this.bikeTypeModel = new BikeTypeModel(); }

    public List<BikeType> getTypes() {
        List<BikeType> list = new ArrayList<BikeType>();
        try {
            list = bikeTypeModel.getTypes();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        return list;
    }

    public void insertBikeType(int id, String name) {
        int cnt = 0;
        try {
            cnt = bikeTypeModel.insertBikeType(id,name);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
//        return cnt;
    }
}
