package object;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private int id;
    private int bikeID;
    private String cardID;
    private String date;
    private String unlockDate;
    private float deposit;
    private float rentingTime;                  // pause renting time -> // thoi gian thue
    private boolean status;                     // true -> da tra xe // false -> chua tra xe
    private String bikeName;
    private int paymentMethod;

    public int getPaymentMethod() { return paymentMethod; }

    public void setPaymentMethod(int paymentMethod) { this.paymentMethod = paymentMethod; }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getBikeID() {
        return bikeID;
    }

    public void setBikeID(int bikeID) {
        this.bikeID = bikeID;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(String unlockDate) {
        this.unlockDate = unlockDate;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public float getRentingTime() {
        return rentingTime;
    }

    public void setRentingTime(float rentingTime) {
        this.rentingTime = rentingTime;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public Transaction() {
    }

    public Transaction(int bikeID, String cardID, float deposit, int paymentMethod) {
        this.bikeID = bikeID;
        this.cardID = cardID;
        this.deposit = deposit;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        this.date = dateFormat.format(date);
        this.rentingTime = 0;
        this.unlockDate = dateFormat.format(date);
        this.paymentMethod = paymentMethod;
    }
}
