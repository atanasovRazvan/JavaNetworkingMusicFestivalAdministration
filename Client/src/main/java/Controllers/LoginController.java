package Controllers;

import Entities.Angajat;
import Networking.ServicesRPCProxy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    public TextField passText;
    public TextField userText;
    public Button submitBtn;
    private ServicesRPCProxy client;

    @FXML
    public void handleSubmitButton() {

        Integer user = Integer.parseInt(userText.getText());
        String pass = passText.getText();

        Angajat angajat = new Angajat(user, pass);
        MyClient.getInstance().setUser(user.toString());
        ServicesRPCProxy client = MyClient.getInstance().getClient();

        try {
            client.login(angajat, null);
            Stage stage = (Stage) submitBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/MainWindowView.fxml"));
            loader.getController();
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage2 = new Stage();
            stage2.setTitle("Ticket selling & Management");
            stage2.setScene(scene);
            stage2.show();


        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, ex.getMessage());
            alert.show();
        }
    }

}
