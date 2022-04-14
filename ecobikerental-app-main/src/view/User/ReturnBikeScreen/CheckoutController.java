package view.User.ReturnBikeScreen;

import controller.BikeController;
import controller.PriceController;
import controller.TransactionController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import BankSystem.Bank;
import object.Bike;
import object.Price;
import object.Transaction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CheckoutController {
    @FXML
    private Label customerLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label depositLabel;

    @FXML
    private Label timeLabel2;

    @FXML
    private Label errorMessage;

    @FXML
    private Label bikeLabel;

    @FXML
    private Label paymentMethod;

    @FXML
    private Label timeLabel;

    @FXML
    private TextField cardIDField;

    TransactionController transactionController = new TransactionController();
    private Transaction tr = null;
    private int parkingLotID;
    private int bikeID;
    private double rentalTime = 0;
    private double rentalFee = 0;
    Bank banksystem=new Bank();

    public void initData( int parkingLotID, int bikeID) {
        this.bikeID = bikeID;
        this.parkingLotID = parkingLotID;
        this.tr = transactionController.getRentalTransaction(bikeID);

        if (tr == null) {
            errorMessage.setText("Conflict Error!");
            errorMessage.setVisible(true);
        } else {
            customerLabel.setText(this.tr.getCardID());
            bikeLabel.setText("ID" + this.tr.getBikeID() + " - " + this.tr.getBikeName());
            timeLabel.setText(this.tr.getDate());
            this.rentalTime = this.tr.getRentingTime() + timeCalculate(tr.getUnlockDate());
            timeLabel2.setText(this.rentalTime + " phút");
            depositLabel.setText(Float.toString(this.tr.getDeposit()) + " đồng");
            this.rentalFee = feeCalculate(this.rentalTime, this.tr.getPaymentMethod());
            amountLabel.setText(this.rentalFee + " đồng");
            switch (this.tr.getPaymentMethod()) {
                case 0: paymentMethod.setText("Theo giờ"); break;
                case 1: paymentMethod.setText("Theo ngày"); break;
                default: paymentMethod.setText("null"); break;
            }
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

    public double feeCalculate(double minutes, int paymentMethod) {
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

    @FXML
    void checkout(ActionEvent event) throws  Exception {
        String cardID = cardIDField.getText();
        cardIDField.clear();
        boolean result = banksystem.getCard(cardID);

        if(result==true) {
              float amount = banksystem.getAmount(cardID);
              if(amount +tr.getDeposit() > this.rentalFee) {
              float addmoney = (float) (tr.getDeposit() - this.rentalFee)+amount;
              banksystem.updateAmount(cardID, addmoney);
                // update bike's status
                BikeController bikeController = new BikeController();
                System.out.println(this.parkingLotID);
                bikeController.updateStatus(this.bikeID, this.parkingLotID,false);

                // update transaction
                transactionController.updateStatus(this.tr.getId(), true);
                errorMessage.setVisible(false);
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Thanh toán thành công!");
                a.setHeaderText(null);
                a.setHeight(100);
                a.setContentText("Thanh toán thành công. Quay về màn hình chính");
                a.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                a.show();
                try {
                    Thread.sleep(1000);
                    this.backToUserScreen(event);
                } catch (Exception ex) {
                    System.err.println("Error: " + ex.toString());
                }
                return;
            } else {
                errorMessage.setText("Không đủ tiền để thực hiện giao dịch");
                errorMessage.setVisible(true);
                return;
            }
        } else {
//            System.err.println(result.getMessage());
            errorMessage.setText("Thẻ không tồn tại hoặc mã thẻ không chính xác");
            errorMessage.setVisible(true);
            return;
        }
    }

    @FXML
    public void backToUserScreen(ActionEvent event) throws IOException {
        Parent userScr = FXMLLoader.load(getClass().getResource("../UserScreen.fxml"));
        Scene userScene = new Scene(userScr);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(userScene);
        window.show();
    }
}
