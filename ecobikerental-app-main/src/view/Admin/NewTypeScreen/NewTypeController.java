package view.Admin.NewTypeScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.BikeTypeModel;
import object.BikeType;
import view.AlertBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewTypeController implements Initializable {

    BikeTypeModel bikeTypeModel = new BikeTypeModel();

    @FXML
    private TableView<BikeType> table;
    @FXML
    private TableColumn<BikeType, Integer> id;
    @FXML
    private TableColumn<BikeType, String> name;
    @FXML
    TextField input_id, input_name;

    ObservableList<BikeType> data = FXCollections.observableArrayList();

    public void newType(ActionEvent event) throws SQLException, IOException {
        String id = input_id.getText();
        String name = input_name.getText();
        input_id.clear();
        input_name.clear();
        if(id.equals("") || name.equals("")) {
            AlertBox.display("Bạn chưa nhập id hoặc tên mới");
        } else if(!isNumeric(id) || !onlyLettersSpaces(name)) {
            AlertBox.display("ID hoặc tên không hợp lệ");
        } else {
            int success = bikeTypeModel.insertBikeType(Integer.parseInt(id),name);
            if (success==0) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Thất bại!");
                a.setHeaderText(null);
                a.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                a.show();
            } else {
                data.clear();
                getData(bikeTypeModel);
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Thành công!");
                a.setHeaderText(null);
                a.setContentText("Thêm loại xe mới thành công");
                a.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                a.show();
            }
        }
    }

    public void getData(BikeTypeModel bikeTypeModel) {
        try {
            bikeTypeModel.getTypesToTable(data);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        table.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getData(bikeTypeModel);
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

    public static boolean onlyLettersSpaces(String s){
        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);
            if (Character.isLetter(ch) || ch == ' ') {
                continue;
            }
            return false;
        }
        return true;
    }
}
