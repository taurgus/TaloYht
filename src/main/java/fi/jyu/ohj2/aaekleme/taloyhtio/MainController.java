package fi.jyu.ohj2.aaekleme.taloyhtio;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Asunto> asuntoTaulu;

    @FXML
    private TableColumn<Asunto, String> asuntoSarake;

    @FXML
    private TableColumn<Asunto, Number> asukasmaaraSarake;

    @FXML
    private Button lisaaAsuntoPainike;

    @FXML
    private Button muokkaaPainike;

    @FXML
    private Button poistaPainike;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Sarakkeet
        asuntoSarake.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getAsunto())
        );

        asukasmaaraSarake.setCellValueFactory(data ->
                new SimpleIntegerProperty(0)
        );

        // Testidata liikkumista varten
        asuntoTaulu.getItems().addAll(
                new Asunto("A1"),
                new Asunto("A2"),
                new Asunto("B1")
        );

        // Muokkaa nappi
        muokkaaPainike.setOnAction(event -> avaaAsunnonMuokkaus());

        // Tuplaklikkaus siirtymiseen
        asuntoTaulu.setRowFactory(tv -> {
            TableRow<Asunto> rivi = new TableRow<>();

            rivi.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !rivi.isEmpty()) {
                    avaaAsunnonMuokkaus();
                }
            });

            return rivi;
        });
    }

    private void avaaAsunnonMuokkaus() {
        Asunto valittuAsunto = asuntoTaulu.getSelectionModel().getSelectedItem();

        if (valittuAsunto == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-asunto.fxml"));
            Parent root = loader.load();

            AsuntoEditController ohjain = loader.getController();
            ohjain.setAsunto(valittuAsunto);

            Stage dialogi = new Stage();
            dialogi.setScene(new Scene(root));
            dialogi.setTitle("Muokkaa asuntoa");
            dialogi.initModality(Modality.APPLICATION_MODAL);
            dialogi.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}