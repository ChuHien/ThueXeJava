package model;

import javafx.collections.ObservableList;
import object.BikeType;
import object.Price;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BikeTypeModel {
    private static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/EcoBikeRental";  // your db name
        String user = "root";                                                         // your db username
        String password = "";                                                       // your db password
        return DriverManager.getConnection(url, user, password);
    }

    public void getTypesToTable(ObservableList<BikeType> data) throws SQLException {
        try {
            Class.forName ("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BikeType bikeType = null;
        try {
            con = this.getConnection();
            ps = con.prepareStatement("SELECT * FROM BikeType\n");
            rs = ps.executeQuery();

            while (rs.next()) {
                bikeType = new BikeType();
                bikeType.setId(rs.getInt("BikeTypeID"));
                bikeType.setName(rs.getString("Name"));

                data.add(bikeType);
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
    }

    public List<BikeType> getTypes() throws SQLException {
        List<BikeType> list = new ArrayList<BikeType>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BikeType bikeType = null;
        try {
            con = this.getConnection();
            ps = con.prepareStatement("SELECT * FROM BikeType\n");
            rs = ps.executeQuery();

            while (rs.next()) {
                bikeType = new BikeType();
                bikeType.setId(rs.getInt("BikeTypeID"));
                bikeType.setName(rs.getString("Name"));
                list.add(bikeType);
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
        return list;
    }

    public int insertBikeType(int biketypeid, String name) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            c = this.getConnection();
            ps = c.prepareStatement(
                    "INSERT INTO BikeType VALUES (?, ?)");
            ps.setString(2, name);
            ps.setInt(1, biketypeid);
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
