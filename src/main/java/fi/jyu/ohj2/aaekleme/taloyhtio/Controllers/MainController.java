package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Models.Asunto;
import fi.jyu.ohj2.aaekleme.taloyhtio.AsuntoTallennus;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
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

    @FXML
    private Button suljePainike;

    private final ObservableList<Asunto> asunnot = FXCollections.observableArrayList();
    //JSON
    private final AsuntoTallennus tallennus = new AsuntoTallennus(Path.of("asunnot.json"));

    //Initialize, mitä tehdään heti näkymän aluksi
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        asuntoSarake.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getAsunto())
        );

        asukasmaaraSarake.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getAsukasmaara())
        );

        //Ladataan ja lisätään listaan asunnot
        asunnot.addAll(tallennus.lataaAsunnot());
        asuntoTaulu.setItems(asunnot);

        //Toiminnallisuudet
        lisaaAsuntoPainike.setOnAction(_ -> avaaLisaysIkkuna());
        muokkaaPainike.setOnAction(_ -> avaaAsunnonMuokkaus());
        poistaPainike.setOnAction(_ -> poistaValittuAsunto());
        suljePainike.setOnAction(_ -> sulje());

        //Tuplaklikkauksella asunto-ikkunan avaaminen
        asuntoTaulu.setRowFactory(_ -> {
            TableRow<Asunto> rivi = new TableRow<>();

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
            //Ladataan lisäys-näkymä
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fi/jyu/ohj2/aaekleme/taloyhtio/add-asunto.fxml"));
            Parent root = loader.load();

            //Haetaan asunnon lisäys controller
            AddAsuntoController ohjain = loader.getController();

            //Tehdään uusi ikkuna
            Stage dialogi = new Stage();
            dialogi.setScene(new Scene(root));
            dialogi.setTitle("Lisää asunto");
            dialogi.initModality(Modality.APPLICATION_MODAL);
            dialogi.showAndWait();

            //Haetaan lisätty asunto
            Asunto uusiAsunto = ohjain.getUusiAsunto();
            if (uusiAsunto != null) {
            //Estetään tuplat
                boolean loytyy = asunnot.stream()
                        .anyMatch(a -> a.getAsunto().equalsIgnoreCase(uusiAsunto.getAsunto()));

                if (loytyy) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Virhe");
                    alert.setHeaderText("Asunto on jo olemassa");
                    alert.setContentText("Asunto " + uusiAsunto.getAsunto() + " on jo lisätty.");
                    alert.showAndWait();
                    return;
                }
            //Lisätään asunto listaan ja tallenettaan
                asunnot.add(uusiAsunto);
                tallennus.tallennaAsunnot(asunnot);

            //Valitaan lisätty asunto taulusta
                asuntoTaulu.getSelectionModel().select(uusiAsunto);
                asuntoTaulu.requestFocus();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
            //Asunnon muokkaus-metodi
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
            dialogi.setTitle("Muokkaa asuntoa " +  valittuAsunto.getAsunto());
            dialogi.initModality(Modality.APPLICATION_MODAL);
            dialogi.showAndWait();

            //Tallennetaan muutokset
            asuntoTaulu.refresh();
            tallennus.tallennaAsunnot(asunnot);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void poistaValittuAsunto() {

        //Haetaan poistettava asunto
        Asunto valittuAsunto = asuntoTaulu.getSelectionModel().getSelectedItem();

        if (valittuAsunto == null) {
            return;
        }

        //Alert-systeemi, varmistetaan että käyttäjä haluaa poistaa asunnon
        Alert varmistus = new Alert(Alert.AlertType.CONFIRMATION);
        varmistus.setTitle("Vahvista poisto");
        varmistus.setHeaderText("Poistetaanko asunto?");
        varmistus.setContentText("Poistetaan asunto " + valittuAsunto.getAsunto() + ".");

        Optional<ButtonType> vastaus = varmistus.showAndWait();
        //Poistetaan asunto, jos käyttäjä hyväksyy sen
        if (vastaus.isPresent() && vastaus.get() == ButtonType.OK) {
            //Poisto
            asunnot.remove(valittuAsunto);
            //Tallennus
            tallennus.tallennaAsunnot(asunnot);
        }
    }
    private void sulje() {
        Stage stage = (Stage) suljePainike.getScene().getWindow();
        stage.close();
    }
}