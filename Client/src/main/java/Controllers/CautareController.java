package Controllers;

import Entities.Client;
import Entities.DTOForBuyTable;
import Networking.ServicesRPCProxy;
import Services.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class CautareController implements Serializable, Observer {

    public TableView table;

    public TableColumn artistCol;
    public TableColumn hourCol;
    public TableColumn scenaCol;
    public TableColumn llibereCol;

    public Button findBtn;
    public Button sellBtn;

    public TextField ziText;
    public TextField lunaText;
    public TextField anText;

    public Integer id_artist, id_scena;
    public String nume;
    public TextField numeText;
    public TextField numarText;

    private ServicesRPCProxy client;

    @FXML
    public void initialize(){

        client = MyClient.getInstance().getClient();

        sellBtn.setDisable(true);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                DTOForBuyTable dto = (DTOForBuyTable) table.getSelectionModel().getSelectedItem();
                id_artist = dto.getId_artist();
                id_scena = dto.getId_scena();
                System.out.println(id_artist);
                System.out.println(id_scena);
                sellBtn.setDisable(false);
            }
        });
    }

    public void chestiidiferite(){

        LocalDate date = LocalDate.of(Integer.parseInt(anText.getText()), Integer.parseInt(lunaText.getText()), Integer.parseInt(ziText.getText()));

        artistCol.setCellValueFactory(new PropertyValueFactory("nume"));
        hourCol.setCellValueFactory(new PropertyValueFactory("hour"));
        scenaCol.setCellValueFactory(new PropertyValueFactory("denumire"));
        llibereCol.setCellValueFactory(new PropertyValueFactory("locuri_libere"));


        ObservableList<DTOForBuyTable> data = null;
        try {
            data = FXCollections.observableList(client.getAllDate(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.setItems(data);
        table.setRowFactory(tr -> new TableRow<DTOForBuyTable>() {
            @Override
            protected void updateItem(DTOForBuyTable item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) setStyle("");
                else if (item.getLocuri_libere().equals("0"))
                    setStyle("-fx-background-color: #ff0000;");
            }
        });

    }

    @Override
    public void update(ArrayList<DTOForBuyTable> cl){
        LocalDate date = LocalDate.of(Integer.parseInt(anText.getText()), Integer.parseInt(lunaText.getText()), Integer.parseInt(ziText.getText()));

        artistCol.setCellValueFactory(new PropertyValueFactory("nume"));
        hourCol.setCellValueFactory(new PropertyValueFactory("hour"));
        scenaCol.setCellValueFactory(new PropertyValueFactory("denumire"));
        llibereCol.setCellValueFactory(new PropertyValueFactory("locuri_libere"));

        System.out.println(cl.size());

        ObservableList<DTOForBuyTable> data = FXCollections.observableList(cl);
        table.setItems(data);
        table.setRowFactory(tr -> new TableRow<DTOForBuyTable>() {
            @Override
            protected void updateItem(DTOForBuyTable item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) setStyle("");
                else if (item.getLocuri_libere().equals("0"))
                    setStyle("-fx-background-color: #ff0000;");
            }
        });
    }

    @FXML
    public void handleFindButton(ActionEvent actionEvent) {

        try {

            chestiidiferite();

        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    public void handleSellButton(ActionEvent actionEvent) {

        try {

            String nume = numeText.getText();
            String nrLocuri = numarText.getText();

            Random rand = new Random();

            Client klient = new Client(rand.nextInt(100000000), Integer.parseInt(nrLocuri), nume, id_scena, id_artist);
            client.ticketing(klient, this);

            sellBtn.setDisable(true);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
