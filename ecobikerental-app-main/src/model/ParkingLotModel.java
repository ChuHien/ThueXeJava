package model;

import object.Bike;
import object.ParkingLot;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ParkingLotModel {
    // TODO: cho connection ra ngoai dung chung (config)
    /**
     * To get the connection.
     * @return Connection
     * @throws Exception
     */
    private static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/ecobikerental";  // your db name
        String user = "root";                                                         // your db username
        String password = "";                                                       // your db password
        return DriverManager.getConnection(url, user, password);
    }
    /**
     * Get list parking lot
     * @throws SQLException
     */
    public ArrayList<ParkingLot> getList() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<ParkingLot> plList = new ArrayList<>();
//        System.out.println("hello");
        int cnt = 0;
        try {
            Class.forName ( "com.mysql.jdbc.Driver" );
            con = this.getConnection();
            ps = con.prepareStatement("SELECT ParkingLot.*, BikeNum FROM ParkingLot INNER JOIN (SELECT Bike.ParkingLotID,COUNT(Bike.BikeID) AS BikeNum FROM Bike GROUP BY Bike.ParkingLotID) AS BikeNum ON ParkingLot.ParkingLotID = BikeNum.ParkingLotID");
            rs = ps.executeQuery();
            while (rs.next()) {
                ParkingLot pl = new ParkingLot();
                pl.setId(rs.getInt("ParkingLotId"));
                pl.setName(rs.getString("Name"));
                pl.setAddress(rs.getString("Address"));
                pl.setArea(rs.getDouble("Area"));
                pl.setBikeNum(rs.getInt("BikeNum"));

                plList.add(pl);
                cnt++;
            }
            if (cnt > 0)
                return plList;
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
        return null;
    }

    public ParkingLot getPLById(int plID) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ParkingLot pl = null;
        int cnt = 0;
        try {
            con = this.getConnection();
            ps = con.prepareStatement(
                    "SELECT * FROM ParkingLot\n" +
                            "WHERE ParkingLotID = ?");
            ps.setInt(1, plID);
            rs = ps.executeQuery();
            pl = new ParkingLot();
            while (rs.next()) {
                pl.setId(rs.getInt("ParkingLotID"));
                pl.setName(rs.getString("Name"));
                pl.setAddress(rs.getString("Address"));
                pl.setArea(rs.getDouble("Area"));
                cnt++;
            }
            if (cnt > 0)
                return pl;
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
        return null;
    }

}
