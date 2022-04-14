package view.Admin;

import controller.ParkingLotController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminController implements Initializable {

    @FXML private BorderPane bp;

    @FXML public void goToChangePrice(MouseEvent event) throws IOException {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ChangePriceScreen/ChangePriceScreen.fxml"));
            root = loader.load();
        } catch(IOException ex) {
            Logger.getLogger(ParkingLotController.class.getName()).log(Level.SEVERE,null,ex);
        }
        bp.setCenter(root);
    }

    @FXML public void goToNewType(MouseEvent event) throws IOException {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NewTypeScreen/NewTypeScreen.fxml"));
            root = loader.load();

        } catch(IOException ex) {
            Logger.getLogger(ParkingLotController.class.getName()).log(Level.SEVERE,null,ex);
        }
        bp.setCenter(root);
    }

    @FXML public void backToMainScreen(MouseEvent event) throws IOException {
        Parent mainScr = FXMLLoader.load(getClass().getResource("../Main.fxml"));
        Scene mainScene = new Scene(mainScr);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.setY(window.getY()-100);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ChangePriceScreen/ChangePriceScreen.fxml"));
            root = loader.load();
        } catch(IOException ex) {
            Logger.getLogger(ParkingLotController.class.getName()).log(Level.SEVERE,null,ex);
        }
        bp.setCenter(root);
        
    }
}
