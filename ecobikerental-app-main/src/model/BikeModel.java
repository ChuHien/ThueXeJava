package model;

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
public class BikeModel {
    // TODO: cho connection ra ngoai dung chung (config)
    /**
     * To get the connection.
     * @return Connection
     * @throws Exception
     */
    private static Connection getConnection() throws Exception {
//        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/EcoBikeRental";  // your db name
        String user = "root";                                                         // your db username
        String password = "";                                                       // your db password
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * To insert the record into the database
     * @param bike
     * @throws SQLException
     */
    public int insert(Bike bike) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        int cnt = 0;
//        try {
//            c = this.getConnection();
//            ps = c.prepareStatement("insert into employees values(?, ?, ?, ?)");
//            int i = 0;
//            ps.setInt(++i, emp.getId());
//            ps.setNString(++i, emp.getFirstName());
//            ps.setNString(++i, emp.getLastName());
//            ps.setInt(++i, emp.getAge());
//            cnt = ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (ps != null) {
//                ps.close();
//            }
//            if (c != null) {
//                c.close();
//            }
//        }
        return cnt;
    }

    /**
     * To display the data from the database
     * @param bikeID
     * @throws SQLException
     */
    public Bike getBikeById(int bikeID) throws SQLException {
        Connection con = null;
        try {
            Class.forName ("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        Bike bike = null;
        int cnt = 0;
        try {
            con = this.getConnection();
            if(con != null) System.out.println("oke");
            ps = con.prepareStatement(
                    "SELECT Bike.*, ParkingLot.Name AS PLName, BikeType.Name AS TypeName, PriceList.Price FROM Bike\n" +
                    "INNER JOIN ParkingLot ON ParkingLot.ParkingLotID = Bike.ParkingLotID\n" +
                    "INNER JOIN PriceList ON PriceList.PriceID = Bike.PriceID\n" +
                    "INNER JOIN BikeType ON Bike.BikeTypeID = BikeType.BikeTypeID\n" +
                    "WHERE BikeID = ?");
            ps.setInt(1, bikeID);
            rs = ps.executeQuery();
            bike = new Bike();
            while (rs.next()) {
                bike.setPlName(rs.getString("PLName"));
                bike.setPlID(rs.getInt("ParkingLotID"));
                bike.setId(rs.getInt("BikeID"));
                bike.setName(rs.getString("Name"));
                bike.setType(rs.getString("TypeName"));
                bike.setBattery(rs.getString("BatteryStatus"));
                bike.setPrice(((double) rs.getFloat("Price")));
                bike.setStatus(rs.getBoolean("Status"));
                cnt++;
            }
            if (cnt > 0)
                return bike;
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

    public ArrayList<Bike> getBikeListByPlId(int ParkingLotID) throws SQLException {
        Connection con = null;
        try {
            Class.forName ("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Bike> BikeList = new ArrayList<>();
        Bike bike = null;
        int cnt = 0;
        try {
            con = this.getConnection();
            ps = con.prepareStatement(
                    "SELECT Bike.*, ParkingLot.Name AS PLName, BikeType.Name AS TypeName, PriceList.Price FROM Bike\n" +
                            "INNER JOIN ParkingLot ON ParkingLot.ParkingLotID = Bike.ParkingLotID\n" +
                            "INNER JOIN PriceList ON PriceList.PriceID = Bike.PriceID\n" +
                            "INNER JOIN BikeType ON Bike.BikeTypeID = BikeType.BikeTypeID\n" +
                            "WHERE Bike.ParkingLotID = ?");
            ps.setInt(1,ParkingLotID);
            rs = ps.executeQuery();
            while (rs.next()) {
                bike = new Bike();
                bike.setPlName(rs.getString("PLName"));
                bike.setPlID(rs.getInt("ParkingLotID"));
                bike.setId(rs.getInt("BikeID"));
                bike.setName(rs.getString("Name"));
                bike.setType(rs.getString("TypeName"));
                bike.setBattery(rs.getString("BatteryStatus"));
                bike.setPrice(((double) rs.getFloat("Price")));
                bike.setStatus(rs.getBoolean("Status"));
                cnt++;
                BikeList.add(bike);
            }
            if (cnt > 0)
                return BikeList;
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

    // TODO: nên dùng chung 1 hàm update duy nhất
    public int updateStatus(int bikeID, int plID, boolean status) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            c = this.getConnection();
            ps = c.prepareStatement(
                    "UPDATE Bike\n" +
                    "SET Status = ?, ParkingLotID = ?\n" +
                    "WHERE BikeID = ?");
            ps.setInt(3, bikeID);
            ps.setBoolean(1, status);
            ps.setInt(2, plID);
            cnt = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (c != null) {
                c.close();
            }
        }
        return cnt;
    }
}