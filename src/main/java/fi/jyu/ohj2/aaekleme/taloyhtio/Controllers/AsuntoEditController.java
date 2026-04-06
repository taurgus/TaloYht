package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Asukas;
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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AsuntoEditController implements Initializable {

    @FXML
    private Label asuntoLabel;

    @FXML
    private TableView<Asukas> asukasTaulu;

    @FXML
    private TableColumn<Asukas, String> nimiSarake;

    @FXML
    private TableColumn<Asukas, Number> syntymavuosiSarake;

    @FXML
    private TableColumn<Asukas, String> sahkopostiSarake;

    @FXML
    private Button lisaaAsukasPainike;

    @FXML
    private Button poistaAsukasPainike;

    @FXML
    private Button suljePainike;

    private Asunto asunto;
    private final ObservableList<Asukas> asukkaat = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nimiSarake.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNimi())
        );

        syntymavuosiSarake.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getSyntymavuosi())
        );

        sahkopostiSarake.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getSahkoposti())
        );

        asukasTaulu.setItems(asukkaat);

        lisaaAsukasPainike.setOnAction(e -> avaaLisaysIkkuna());
        poistaAsukasPainike.setOnAction(e -> poistaValittuAsukas());
        suljePainike.setOnAction(e -> sulje());
    }

    public void setAsunto(Asunto asunto) {
        this.asunto = asunto;
        asuntoLabel.setText("Asunto: " + asunto.getAsunto());

        asukkaat.clear();
        asukkaat.addAll(asunto.getAsukkaat());
    }

    private void avaaLisaysIkkuna() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fi/jyu/ohj2/aaekleme/taloyhtio/add-asukas.fxml")
            );
            Parent root = loader.load();

            AddAsukasController ohjain = loader.getController();

            Stage dialogi = new Stage();
            dialogi.setScene(new Scene(root));
            dialogi.setTitle("Lisää asukas");
            dialogi.initModality(Modality.APPLICATION_MODAL);
            dialogi.showAndWait();

            Asukas uusi = ohjain.getUusiAsukas();
            if (uusi != null) {
                asukkaat.add(uusi);

                // päivitetään asunto-olio
                asunto.getAsukkaat().clear();
                asunto.getAsukkaat().addAll(asukkaat);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void poistaValittuAsukas() {
        Asukas valittu = asukasTaulu.getSelectionModel().getSelectedItem();

        if (valittu == null) {
            return;
        }

        Alert varmistus = new Alert(Alert.AlertType.CONFIRMATION);
        varmistus.setTitle("Vahvista poisto");
        varmistus.setHeaderText("Poistetaanko asukas?");
        varmistus.setContentText("Poistetaan asukas " + valittu.getNimi());

        Optional<ButtonType> vastaus = varmistus.showAndWait();

        if (vastaus.isPresent() && vastaus.get() == ButtonType.OK) {
            asukkaat.remove(valittu);

            // päivitetään asunto-olio
            asunto.getAsukkaat().clear();
            asunto.getAsukkaat().addAll(asukkaat);
        }
    }

    private void sulje() {
        Stage ikkuna = (Stage) suljePainike.getScene().getWindow();
        ikkuna.close();
    }
}