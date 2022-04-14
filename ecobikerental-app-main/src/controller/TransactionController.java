package controller;

import model.TransactionModel;
import object.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionController {
    TransactionModel tModel = null;

    public TransactionController() {
        this.tModel = new TransactionModel();
    }

    public Transaction getRentalTransaction(int bikeID) {
        Transaction tr = null;
        try {
            tr = tModel.getByBikeID(bikeID, false);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        return tr;
    }

    public void updateStatus(int trID, boolean status) {
        int cnt = 0;
        try {
            cnt = tModel.update(trID, status);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
//        return cnt;
    }

    public void updateRentalTime(int trID, String unlockDate, double rentingTime) {
        int cnt = 0;
        try {
            cnt = tModel.updateRentingTime(trID, unlockDate, rentingTime);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
//        return cnt;
    }

    public void insert(Transaction trans) {
        int cnt = 0;
        try {
            cnt = tModel.insert(trans);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    public boolean checkCardID(String CardID) {
        ArrayList<Transaction> transList = null;
        try {
            transList = tModel.getbyCardID(CardID);
            if(transList == null) {
                return true;
            }
            for(Transaction trans : transList) {
                if(!trans.isStatus()) {
                    return false;
                }
            }
        } catch (SQLException ex) {
        System.err.println("Error: " + ex.toString());
        }
        return true;
    }

}
