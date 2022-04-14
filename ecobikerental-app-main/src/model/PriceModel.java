package model;

import javafx.collections.ObservableList;
import object.Price;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PriceModel {
    private static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/EcoBikeRental";  // your db name
        String user = "root";                                                         // your db username
        String password = "";                                                       // your db password
        return DriverManager.getConnection(url, user, password);
    }

    public void getPricesToTable(ObservableList<Price> data) throws SQLException {
        try {
            Class.forName ("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Price price = null;
        try {
            con = this.getConnection();
            ps = con.prepareStatement("SELECT * FROM PriceList\n");
            rs = ps.executeQuery();

            while (rs.next()) {
                price = new Price();
                price.setId(rs.getInt("PriceID"));
                price.setName(rs.getString("Name"));
                price.setPrice((double) rs.getFloat("Price"));
                data.add(price);
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

    public List<Price> getPrices() throws SQLException {
        List<Price> list = new ArrayList<Price>();
        Connection con = null;
        try {
            Class.forName ("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        Price price = null;
        try {
            con = this.getConnection();
            ps = con.prepareStatement("SELECT * FROM PriceList\n");
            rs = ps.executeQuery();

            while (rs.next()) {
                price = new Price();
                price.setId(rs.getInt("PriceID"));
                price.setName(rs.getString("Name"));
                price.setPrice((double) rs.getFloat("Price"));
                list.add(price);
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

    public int updatePrice(int id, float price) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            c = this.getConnection();
            ps = c.prepareStatement(
                    "UPDATE PriceList\n" +
                            "SET price = ?\n" +
                            "WHERE PriceId = ?");
            ps.setInt(2, id);
            ps.setFloat(1, price);
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
