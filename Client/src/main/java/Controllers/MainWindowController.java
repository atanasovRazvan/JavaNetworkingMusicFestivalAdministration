package Controllers;

import Entities.Angajat;
import Entities.DTOForTable;
import Networking.ServicesRPCProxy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainWindowController {

    public Button cautareBtn;
    public Button logoutBtn;
    public TableView table;
    public TableColumn artistCol;
    public TableColumn dataCol;
    public TableColumn scenaCol;
    public TableColumn llibereCol;
    public TableColumn locupateCol;
    private ServicesRPCProxy client;

    private Angajat angajat;

    @FXML
    public void initialize(){
        try {
            artistCol.setCellValueFactory(new PropertyValueFactory("nume"));
            dataCol.setCellValueFactory(new PropertyValueFactory("date"));
            scenaCol.setCellValueFactory(new PropertyValueFactory("denumire"));
            llibereCol.setCellValueFactory(new PropertyValueFactory("locuri_libere"));
            locupateCol.setCellValueFactory(new PropertyValueFactory("locuri_ocupate"));

            client = MyClient.getInstance().getClient();
            List<DTOForTable> data = client.getAll();

            ObservableList<DTOForTable> Data = FXCollections.observableArrayList(data);
            table.setItems(Data);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void handleCautareBtn(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/CautareView.fxml"));
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            CautareController controller = loader.getController();
            client.setClient(controller);
            Stage stage2 = new Stage();

            stage2.setTitle("Select & sell");
            stage2.setScene(scene);
            stage2.show();

        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    public void handleLogoutBtn(ActionEvent actionEvent) {

        try {

            String logout = "Logout";
            client.logout(MyClient.getInstance().getUser());
            client.closeConnection();

            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/LoginView.fxml"));
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            LoginController controller = loader.getController();
            Stage stage2 = new Stage();

            stage2.setTitle("Login");
            stage2.setScene(scene);
            stage2.show();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
