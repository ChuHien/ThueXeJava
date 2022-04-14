package view.User.RentedBikeScreen;

import controller.BikeController;
import controller.TransactionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import object.Bike;
import object.Transaction;
import view.AlertBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScanController implements Initializable {

    TransactionController transactionController = new TransactionController();
    BikeController bikeController = new BikeController();

    @FXML
    TextField bikeID;

    public ScanController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void goToRentedBike(MouseEvent event) throws IOException {
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
