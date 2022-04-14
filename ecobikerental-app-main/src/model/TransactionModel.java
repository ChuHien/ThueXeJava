package model;

import object.Transaction;
import java.sql.*;
import java.util.ArrayList;

public class TransactionModel {
    // TODO: cho connection ra ngoai dung chung (config)
    /**
     * To get the connection.
     * @return Connection
     * @throws Exception
     */
    private static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/EcoBikeRental";  // your db name
        String user = "root";                                                         // your db username
        String password = "";                                                       // your db password
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Insert new transaction into the database
     * @param transaction
     * @throws SQLException
     */
    public int insert(Transaction transaction) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            conn = this.getConnection();
            ps = conn.prepareStatement(
                    "INSERT INTO TransactionInfo(BikeID, CardID, Date, UnlockDate, Deposit, RentingTime, Status, PaymentMethod)\n" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            int i = 0;
            ps.setInt(++i, transaction.getBikeID());
            ps.setString(++i, transaction.getCardID());
            ps.setString(++i, transaction.getDate());
            ps.setString(++i, transaction.getUnlockDate());
            ps.setFloat(++i, transaction.getDeposit());
            ps.setFloat(++i, transaction.getRentingTime());
            ps.setBoolean(++i, transaction.isStatus());
            ps.setInt(++i, transaction.getPaymentMethod());
            cnt = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return cnt;
    }

    // TODO: hoan thanh not
    /**
     * Get transaction info by bike id
     * @param bikeID
     * @param status
     * @throws SQLException
     */
    public Transaction getByBikeID(int bikeID, boolean status) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Transaction tr = null;
        int cnt = 0;
        try {
            conn = this.getConnection();
            ps = conn.prepareStatement(
                    "SELECT TransactionInfo.*, Bike.Name AS BikeName  FROM TransactionInfo\n" +
                            "INNER JOIN Bike ON Bike.BikeID = TransactionInfo.BikeID\n" +
                            "WHERE Bike.BikeID = ? AND TransactionInfo.Status = ?");
            ps.setInt(1, bikeID);
            ps.setBoolean(2, status);
            rs = ps.executeQuery();
            tr = new Transaction();
            while (rs.next()) {
                tr.setId(rs.getInt("TransactionID"));
                tr.setBikeID(rs.getInt("BikeID"));
                tr.setCardID(rs.getString("CardID"));
                tr.setDate(rs.getString("Date"));
                tr.setStatus(rs.getBoolean("Status"));
                tr.setDeposit(rs.getFloat("Deposit"));
                tr.setUnlockDate(rs.getString("UnlockDate"));
                tr.setRentingTime(rs.getFloat("RentingTime"));
                tr.setBikeName(rs.getString("BikeName"));
                tr.setPaymentMethod(rs.getInt("PaymentMethod"));
                cnt++;
            }
            if (cnt > 0)
                return tr;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    public ArrayList<Transaction> getbyCardID(String CardID) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Transaction> TransList = new ArrayList<>();
        Transaction tr = null;
        int cnt = 0;
        try {
            conn = this.getConnection();
            ps = conn.prepareStatement(
                    "SELECT TransactionInfo.* FROM TransactionInfo\n" +
                            "WHERE TransactionInfo.CardID LIKE ?");
            ps.setString(1, CardID);
            rs = ps.executeQuery();
            while (rs.next()) {
                tr = new Transaction();
                tr.setId(rs.getInt("TransactionID"));
                tr.setBikeID(rs.getInt("BikeID"));
                tr.setCardID(rs.getString("CardID"));
                tr.setDate(rs.getString("Date"));
                tr.setUnlockDate(rs.getString("UnlockDate"));
                tr.setStatus(rs.getBoolean("Status"));
                tr.setDeposit(rs.getFloat("Deposit"));
                tr.setRentingTime(rs.getFloat("RentingTime"));
                tr.setPaymentMethod(rs.getInt("PaymentMethod"));
                cnt++;
                TransList.add(tr);
            }
            if (cnt > 0)
                return TransList;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    // TODO: nên dùng chung 1 hàm update duy nhất
    public int update(int trID, boolean status) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            conn = this.getConnection();
            ps = conn.prepareStatement(
                    "UPDATE TransactionInfo\n" +
                            "SET Status = ?\n" +
                            "WHERE TransactionID = ?");
            ps.setInt(2, trID);
            ps.setBoolean(1, status);
            cnt = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return cnt;
    }

    public int updateRentingTime(int trID, String unlockDate, double rentingTime) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            conn = this.getConnection();
            ps = conn.prepareStatement(
                    "UPDATE TransactionInfo\n" +
                            "SET UnlockDate = ?, RentingTime = ?\n" +
                            "WHERE TransactionID = ?");
            ps.setInt(3, trID);
            ps.setString(1, unlockDate);
            ps.setFloat(2, (float) rentingTime);
            cnt = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return cnt;
    }
}
