package view.Admin.ChangePriceScreen;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.PriceModel;
import object.Price;
import view.AlertBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePriceController implements Initializable {

    PriceModel priceModel = new PriceModel();

    @FXML
    private TableView<Price> table;
    @FXML
    private TableColumn<Price, Integer> id;
    @FXML
    private TableColumn<Price, String> name;
    @FXML
    private TableColumn<Price, Double> price;
    @FXML
    TextField input_id, input_price;

    ObservableList<Price> data = FXCollections.observableArrayList();

    public void changePrice(ActionEvent event) throws SQLException, IOException {
        String id = input_id.getText();
        String price = input_price.getText();
        input_id.clear();
        input_price.clear();
        if(id.equals("") || price.equals("")) {
            AlertBox.display("Bạn chưa nhập id hoặc giá mới");
        } else if(!isNumeric(id) || !isNumeric(price)) {
            AlertBox.display("ID hoặc giá mới không hợp lệ");
        } else if(Float.parseFloat(price) <= 0) {
            AlertBox.display("Giá tiền phải có giá trị dương");
        } else {
            int success = priceModel.updatePrice(Integer.parseInt(id),Float.parseFloat(price));
            if (success==0) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Thất bại!");
                a.setHeaderText(null);
                a.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                a.show();
            } else {
                data.clear();
                getData(priceModel);
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Thành công!");
                a.setHeaderText(null);
                a.setContentText("Sửa giá thành công");
                a.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                a.show();
            }
        }
    }

    public void getData(PriceModel priceModel) {
        try {
            priceModel.getPricesToTable(data);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getData(priceModel);
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
