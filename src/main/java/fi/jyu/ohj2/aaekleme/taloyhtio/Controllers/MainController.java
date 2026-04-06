package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Asunto;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private final ObservableList<Asunto> asunnot = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        asuntoSarake.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getAsunto())
        );

        asukasmaaraSarake.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getAsukasmaara())
        );


        asuntoTaulu.setItems(asunnot);

        lisaaAsuntoPainike.setOnAction(event -> avaaLisaysIkkuna());
        muokkaaPainike.setOnAction(event -> avaaAsunnonMuokkaus());

        asuntoTaulu.setRowFactory(tv -> {
            TableRow<Asunto> rivi = new TableRow<>();
    // Estetään tyhjät ja virheelliset syötteet

            rivi.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !rivi.isEmpty()) {
                    avaaAsunnonMuokkaus();
                }
            });

            return rivi;
        });
    }

    private void avaaLisaysIkkuna() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fi/jyu/ohj2/aaekleme/taloyhtio/add-asunto.fxml"));
            Parent root = loader.load();

            AddAsuntoController ohjain = loader.getController();

            Stage dialogi = new Stage();
            dialogi.setScene(new Scene(root));
            dialogi.setTitle("Lisää asunto");
            dialogi.initModality(Modality.APPLICATION_MODAL);
            dialogi.showAndWait();

            Asunto uusiAsunto = ohjain.getUusiAsunto();
            if (uusiAsunto != null) {
                asunnot.add(uusiAsunto);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void avaaAsunnonMuokkaus() {
        Asunto valittuAsunto = asuntoTaulu.getSelectionModel().getSelectedItem();

        if (valittuAsunto == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fi/jyu/ohj2/aaekleme/taloyhtio/edit-asunto.fxml"));
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