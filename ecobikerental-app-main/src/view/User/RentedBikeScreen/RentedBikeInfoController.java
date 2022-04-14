package view.User.RentedBikeScreen;

import controller.BikeController;
import controller.PriceController;
import controller.TransactionController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import object.Bike;
import object.Price;
import object.Transaction;
import view.AlertBox;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class RentedBikeInfoController implements Initializable {
    Bike bike;
    Stage curState;

    @FXML private TextField bikeID;
    @FXML private Text nameText;
    @FXML private Text batteryText;
    @FXML private Text typeText;
    @FXML private Text timeText;
    @FXML private Text priceText;
    @FXML private Button lockButton;
    @FXML private Button unlockButton;

//    private Date unLockDate;
    private double rentalTime;
    private double rentalFee;
    private boolean lockStatus;

    public RentedBikeInfoController() {

    }

    public void initData(Bike bike, Transaction transaction, Stage curState) {
        this.bike = bike;
        this.curState = curState;
        nameText.setText(bike.getName());
        batteryText.setText(bike.getBattery());
        typeText.setText(bike.getType());
        this.rentalTime = transaction.getRentingTime() + timeCalculate(transaction.getUnlockDate());
        timeText.setText(this.rentalTime + " phút");
        this.rentalFee = feeCalculate(this.rentalTime, transaction.getPaymentMethod());
        priceText.setText(this.rentalFee + " đồng");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.lockStatus = false;
        this.lockButton.setVisible(true);
        this.unlockButton.setVisible(false);
    }

    @FXML
    void lockBike(ActionEvent event) {
        // luu time, ko luu date
        this.lockStatus = true;
        this.lockButton.setVisible(false);
        this.unlockButton.setVisible(true);

        TransactionController transactionController = new TransactionController();

        Transaction tr = transactionController.getRentalTransaction(this.bike.getId());

        this.rentalTime = tr.getRentingTime() + timeCalculate(tr.getUnlockDate());

        // update db
        transactionController.updateRentalTime(tr.getId(), tr.getUnlockDate(), this.rentalTime);

        // show ~ setState
        System.out.println(tr.getUnlockDate() + " - " + this.rentalTime );
        this.timeText.setText(this.rentalTime + " phút");
        this.priceText.setText(this.rentalFee + " đồng");
    }

    @FXML
    void unlockBike(ActionEvent event) {
        // luu date, ko luu time
        this.lockStatus = false;
        this.lockButton.setVisible(true);
        this.unlockButton.setVisible(false);

        TransactionController transactionController = new TransactionController();

        Transaction tr = transactionController.getRentalTransaction(this.bike.getId());

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        String unlockDate = dateFormat.format(date);

        //update db
        transactionController.updateRentalTime(tr.getId(), unlockDate, this.rentalTime);

        // show ~ setState
        System.out.println(unlockDate + " - " + this.rentalTime );
        // time + fee ko thay doi
    }

    @FXML
    void close(ActionEvent event) {
        if (!this.lockStatus) {
            curState.close();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText(null);
            a.setTitle("Xe đang khóa!");
            a.setContentText("Hãy mở khóa xe trước khi thoát!");
            a.show();
        }
    }
    private double timeCalculate(String date) {
        try {
            Date dateBegin = new SimpleDateFormat("dd-M-yyyy HH:mm:ss").parse(date);
            long begin = dateBegin.getTime();
            long end = new Date().getTime();
            return (double) Math.ceil((float) (end - begin) / (1000 * 60) * 10) / 10;         // tinh theo phut
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
        }
        return 0;
    }

    private double feeCalculate(double minutes, int paymentMethod) {
        double first30minute = 10000;
        double per15minute = 3000;
        double perDay = 200000;
        double per1Hour = 10000;
        double latePer15minute = 2000;

        PriceController priceController = new PriceController();
        List<Price> priceList = priceController.getPrices();
        if (priceList.get(0) != null) {
            first30minute = priceList.get(0).getPrice();
        }
        if (priceList.get(1) != null) {
            per15minute = priceList.get(1).getPrice();
        }
        if (priceList.get(2) != null) {
            perDay = priceList.get(2).getPrice();
        }
        if (priceList.get(3) != null) {
            per1Hour = priceList.get(3).getPrice();
        }
        if (priceList.get(4) != null) {
            latePer15minute = priceList.get(4).getPrice();
        }

        switch (paymentMethod) {
            case 0:
                if (minutes <= 10.0) return 0;
                if (minutes <= 30.0) return first30minute;
                double fee = first30minute;
                minutes-= 30;
                while (minutes > 0) {
                    fee+= per15minute;
                    minutes-= 15;
                }
                return fee;
            case 1:
                if (minutes < 12*60) {
                    return (double) (perDay - (12-Math.ceil(minutes/60)) * per1Hour);
                } else if (minutes <= 24*60) {
                    return perDay;
                } else {
                    return perDay + latePer15minute*Math.ceil((minutes - 24*60)/15);
                }
            default:
                return 0;
        }
    }

    @FXML private void goToRentedBike(MouseEvent event) throws IOException {
        TransactionController transactionController = new TransactionController();
        BikeController bikeController = new BikeController();
        String text = bikeID.getText();
        bikeID.clear();
        if(!isNumeric(text) || text.equals("")) {
            AlertBox.display("Bạn chưa nhập mã xe hoặc mã xe không hợp lệ");
        } else {
            int id = Integer.parseInt(text);
            Bike bike = bikeController.getBikeById(id);
            if(bike != null) {
                Transaction trans = transactionController.getRentalTransaction(id);
                if(trans != null) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("RentedBikeInfo.fxml"));
                    Parent parent = loader.load();

                    Scene scene = new Scene(parent);
                    //This line gets the Stage information
                    Stage window = new Stage();

                    // Specifies the modality for new window.
                    window.initModality(Modality.WINDOW_MODAL);

                    // Specifies the owner Window (parent) for new window
                    Stage curState = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.initOwner(curState);

                    //access the controller and call a method
                    RentedBikeInfoController controller = loader.getController();
                    controller.initData(bike,trans,window);
                    window.setScene(scene);
                    window.show();
                } else {
                    AlertBox.display("Xe hiện chưa được thuê");
                }
            } else {
                AlertBox.display("Mã xe không tồn tại");
            }
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}