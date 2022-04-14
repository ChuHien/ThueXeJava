package controller;

import model.ParkingLotModel;
import object.Bike;
import object.ParkingLot;

import java.sql.SQLException;
import java.util.ArrayList;

public class ParkingLotController {
    private ParkingLotModel pModel;

    public ParkingLotController() {
        this.pModel = new ParkingLotModel();
    }

    public ArrayList<ParkingLot> getList() {
        ArrayList<ParkingLot> plList = null;
        try {
            plList = pModel.getList();
        } catch (SQLException ex){
            System.err.println("Error: " + ex.toString());
        }
        return  plList;
    }

    public ParkingLot getPlById(int plID) {
        ParkingLot pl = null;
        try {
            pl = pModel.getPLById(plID);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        return pl;
    }
}
