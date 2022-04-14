package view.User;

import controller.ParkingLotController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import object.ParkingLot;
import view.User.RentBikeScreen.ParkingLotScreenController;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserScreenController implements Initializable {
     ParkingLotController parkingLotController = new ParkingLotController();
     ParkingLot item = new ParkingLot();

     @FXML private TableView<ParkingLot> table = new TableView<>();

     @FXML private TableColumn<ParkingLot, Integer> idCol;

     @FXML private TableColumn<ParkingLot, String> nameCol;

     @FXML private TableColumn<ParkingLot, String> addressCol;

     @FXML private TableColumn<ParkingLot, Double> areaCol;

     @FXML private TableColumn<ParkingLot, Integer> bikenumCol;

     public UserScreenController() {
     }

     public void goToParkingLotScreen(ActionEvent event) throws IOException {
          item = table.getSelectionModel().getSelectedItem();
          if(item == null) {
               Alert a = new Alert(Alert.AlertType.WARNING);
               a.setHeaderText(null);
               a.setTitle("Chọn bãi xe!!");
               a.setContentText("Bạn chưa chọn bãi xe");
               a.show();
          } else {
               // su dung fxml
               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("RentBikeScreen/ParkingLotScreen.fxml"));
               Parent tableViewParent = loader.load();

               Scene tableViewScene = new Scene(tableViewParent);

               //access the controller and call a method
               ParkingLotScreenController controller = loader.getController();
               controller.initData(item);
               //This line gets the Stage information
               Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
               window.setScene(tableViewScene);
               window.show();
          }
     }

     public ObservableList<ParkingLot> getData() {
          ObservableList<ParkingLot> data = FXCollections.observableArrayList();
          for (int i=0;i<parkingLotController.getList().size();i++) {
               data.add(parkingLotController.getList().get(i));
          }
          return data;
     }

     @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
          idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
          nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
          addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
          areaCol.setCellValueFactory(new PropertyValueFactory<>("area"));
          bikenumCol.setCellValueFactory(new PropertyValueFactory<>("bikeNum"));
          table.setItems(getData());
     }

     public void goToMainMenu(ActionEvent event) throws IOException {
          Parent tableViewParent = FXMLLoader.load(getClass().getResource("../Main.fxml"));
          Scene tableViewScene = new Scene(tableViewParent);

          //This line gets the Stage information
          Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

          window.setScene(tableViewScene);
          window.setY(window.getY()-100);
          window.show();
     }
}