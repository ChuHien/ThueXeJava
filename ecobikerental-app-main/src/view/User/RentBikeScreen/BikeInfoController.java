package view.User.RentBikeScreen;

import BankSystem.Bank;
import controller.BikeController;
import controller.ParkingLotController;
import controller.TransactionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import object.Bike;
import object.ParkingLot;
import object.Transaction;

import view.AlertBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BikeInfoController implements Initializable {

    Bike bike = new Bike();
    ObservableList<String> paymentMethod = FXCollections.observableArrayList("Theo giờ","Theo ngày");
    TransactionController transactionController = new TransactionController();
    Bank banksystem = new Bank();

    @FXML private Text idText;
    @FXML private Text nameText;
    @FXML private Text batteryText;
    @FXML private Text typeText;
    @FXML private Text priceText;
    @FXML private Text depositText;

    @FXML private ChoiceBox choiceBox;

    @FXML private TextField bankAcc;

    public BikeInfoController() {
    }

    public void initData(Bike bike) {
        this.bike = bike;
        Integer id = bike.getId();
        Double price = bike.getPrice();
        Double deposit = price*0.4;
        idText.setText(id.toString());
        nameText.setText(bike.getName());
        typeText.setText(bike.getType());
        batteryText.setText(bike.getBattery());
        priceText.setText(price.toString());
        bankAcc.setPromptText("Nhập mã thẻ ngân hàng");
        depositText.setText(deposit.toString());

        choiceBox.setValue("Theo giờ");
        choiceBox.setItems(paymentMethod);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML public void rentBike(MouseEvent event) throws Exception{
        if(bike.isStatus()) {
            AlertBox.display("Xe hiện tại không có sẵn");
        } else {
            saveTransaction(event);
        }
    }

    @FXML public void saveTransaction(MouseEvent event) throws Exception{
        String cardID = bankAcc.getText();
        bankAcc.clear();
        boolean result = banksystem.getCard(cardID);
        if(result==true) {
            if(!transactionController.checkCardID(cardID)) {
                AlertBox.display("Thẻ đang được sử dụng để thuê xe");
            } else {

                Transaction trans = new Transaction(bike.getId(),cardID,(float)bike.getPrice(),choiceBox.getSelectionModel().getSelectedIndex()) ;
                float amount = banksystem.getAmount(cardID);
                //subtract money from card
                System.out.println(bike.getPrice());
                System.out.println(amount);
                if(bike.getPrice()<=amount){
                    // update bike's status
                    BikeController bikeController = new BikeController();
                    bikeController.updateStatus(bike.getId(), bike.getPlID(),true);
                    banksystem.subtractDeposit((float) bike.getPrice(), amount, cardID);
                    // insert transaction
                    transactionController.insert(trans);
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Thuê xe thành công!");
                    a.setHeaderText(null);
                    a.setHeight(100);
                    a.setContentText("Thuê xe thành công. Quay về màn hình chính");
                    a.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                    a.show();
                    try {
                        Thread.sleep(1000);
                        //Back to UserScreen
                        Parent userScr = FXMLLoader.load(getClass().getResource("../UserScreen.fxml"));
                        Scene userScene = new Scene(userScr);

                        //This line gets the Stage information
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                        window.setScene(userScene);
                        window.show();
                    } catch (Exception ex) {
                        System.err.println("Error: " + ex.toString());
                    }
                } else {
                    AlertBox.display("Không đủ tiền để thực hiện giao dịch");
                }
            }
        } else {
//            System.err.println(result.getMessage());
            AlertBox.display("Mã thẻ không hợp lệ");
        }
    }

    @FXML public void backtoParkingLotScreen(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ParkingLotScreen.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        //get ParkingLot by ID
        ParkingLotController parkingLotController = new ParkingLotController();
        ParkingLot parkingLot = parkingLotController.getPlById(this.bike.getPlID());
        //access the controller and call a method
        ParkingLotScreenController controller = loader.getController();
        controller.initData(parkingLot);
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}