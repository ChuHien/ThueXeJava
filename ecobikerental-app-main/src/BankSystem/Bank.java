package BankSystem;

import object.Bike;
import object.BikeType;
import object.Price;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bank {
    private static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/BankSystem";  // your db name
        String user = "root";                                                         // your db username
        String password = "";                                                       // your db password
        return DriverManager.getConnection(url, user, password);
    }
    public boolean getCard(String cardID) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int cnt = 0;
        try {
            con = this.getConnection();
            ps = con.prepareStatement(
                    "SELECT * from CreditCard where cardID=?");
            ps.setString(1, cardID);
            rs = ps.executeQuery();
            while (rs.next()) {
                cnt++;
            }
            if (cnt > 0)
                return true;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    public float getAmount(String cardID) throws Exception {
        Connection con = null;
        try {
            Class.forName ("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        float amount = 0;
        int cnt = 0;
        try {
            con = this.getConnection();
            ps = con.prepareStatement(
                    "SELECT Amount from CreditCard where cardID=?");
            ps.setString(1, cardID);
            rs = ps.executeQuery();
            while (rs.next()) {
                amount = rs.getFloat("Amount");
                cnt++;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return amount;

    }

    public void subtractDeposit(float deposit, float amount, String cardID) {
        float subtractFee = amount -deposit;
        Connection c = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            c = this.getConnection();
            ps = c.prepareStatement(
                    "UPDATE CreditCard\n" +
                            "SET Amount = ?\n" +
                            "WHERE CardID = ?");
            ps.setFloat(1, subtractFee);
            ps.setString(2, cardID);
            cnt = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateAmount(String cardID, float addMoney){
        Connection c = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            c = this.getConnection();
            ps = c.prepareStatement(
                    "UPDATE CreditCard\n" +
                            "SET Amount = ?\n" +
                            "WHERE CardID = ?");
            ps.setFloat(1, addMoney);
            ps.setString(2, cardID);
            cnt = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
