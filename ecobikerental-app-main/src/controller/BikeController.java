package controller;

import model.BikeModel;
import object.Bike;
import object.BikeType;
import object.Price;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BikeController {
    private BikeModel bModel;

    public BikeController() {
        this.bModel = new BikeModel();
    }

    public int insertBike(Bike bike) {
        int cnt = 0;
        try {
            cnt = bModel.insert(bike);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        return cnt;
    }

    public Bike getBikeById(int bikeID) {
        Bike bike = null;
        try {
            bike = bModel.getBikeById(bikeID);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        return bike;
    }
    
    public ArrayList<Bike> getBikeListByPlId(int ParkingLotID) {
        ArrayList<Bike> BikeList = null;
        try {
            BikeList = bModel.getBikeListByPlId(ParkingLotID);
        } catch (SQLException ex){
            System.err.println("Error: " + ex.toString());
        }
        return  BikeList;
    }

    public boolean checkStatus(int bikeID) {
        Bike bike = null;
        try {
            if ((bike = bModel.getBikeById(bikeID)) != null) {
                return bike.isStatus();
            }
            return false;
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        return false;
    }

    public void updateStatus(int bikeID, int plID, boolean status) {
        int cnt = 0;
        try {
            cnt = bModel.updateStatus(bikeID, plID, status);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
//        return cnt;
    }
}
