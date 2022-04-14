package view.User.RentBikeScreen;

import controller.BikeController;
import controller.ParkingLotController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import object.Bike;
import object.ParkingLot;
import view.AlertBox;
import view.User.ReturnBikeScreen.ScanController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParkingLotScreenController implements Initializable {
    private ParkingLot parkingLot;
    Bike bike = new Bike();
    BikeController bikeController = new BikeController();

    @FXML private BorderPane bp;
    @FXML private GridPane gp;
    @FXML private TableView<Bike> table;
    @FXML private Label nameLabel;

    //Table column
    @FXML private TableColumn<Bike, Integer> idCol;

    @FXML private TableColumn<Bike, String> nameCol;

    @FXML private TableColumn<Bike, String> typeCol;

    @FXML private TableColumn<Bike, String> batteryCol;

    @FXML private TableColumn<Bike, Boolean> statusCol;

    @FXML private TableColumn<Bike, Double> priceCol;


    public ParkingLotScreenController() {
    }

    public void initData(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        batteryCol.setCellValueFactory(new PropertyValueFactory<>("battery"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        table.setItems(getData());
        nameLabel.setText(parkingLot.getName());

    }

    public ObservableList<Bike> getData() {
        ObservableList<Bike> data = FXCollections.observableArrayList();
        ArrayList<Bike> bikeList = bikeController.getBikeListByPlId(this.parkingLot.getId());
        if(bikeList == null) {
            return data;
        }
        for (int i=0;i<bikeList.size();i++) {
            data.add(bikeList.get(i));
        }
        return data;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML private void RentBike(MouseEvent event) {
        bp.setCenter(gp);
    }

    @FXML private void ReturnBike(MouseEvent event) {

        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../ReturnBikeScreen/Scan.fxml"));
            root = loader.load();
            ScanController controller = loader.getController();
            controller.initData(this.parkingLot.getId(), bp);
        } catch(IOException ex) {
            Logger.getLogger(ParkingLotController.class.getName()).log(Level.SEVERE,null,ex);
        }
        bp.setCenter(root);
    }

    @FXML private void RentedBike(MouseEvent event) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../RentedBikeScreen/ScanBikeID.fxml"));
            root = loader.load();
        } catch(IOException ex) {
            Logger.getLogger(ParkingLotController.class.getName()).log(Level.SEVERE,null,ex);
        }
        bp.setCenter(root);
    }

    @FXML private void goToBikeInfo(MouseEvent event) throws IOException{
        bike = table.getSelectionModel().getSelectedItem();
        if(bike == null) {
            AlertBox.display("Bạn chưa chọn xe");
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("BikeInfo.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);

            //access the controller and call a method
            BikeInfoController controller = loader.getController();
            controller.initData(bike);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    @FXML private void backToUserScreen(MouseEvent event) throws IOException {
        Parent userScr = FXMLLoader.load(getClass().getResource("../UserScreen.fxml"));
        Scene userScene = new Scene(userScr);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(userScene);
        window.show();
    }
}