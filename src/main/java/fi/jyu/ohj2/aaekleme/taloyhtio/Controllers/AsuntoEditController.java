package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Models.Asukas;
import fi.jyu.ohj2.aaekleme.taloyhtio.Models.Asunto;
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
    private Button muokkaaAsukasPainike;

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

        asukasTaulu.setRowFactory(_ -> {
            TableRow<Asukas> rivi = new TableRow<>();

            // Tuplaklikkaus avaa ikkunan
            rivi.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !rivi.isEmpty()) {
                    avaaAsukkaanMuokkaus();
                }
            });

            return rivi;
        });
        //Laitetaan data tauluun
        asukasTaulu.setItems(asukkaat);

        //Nappuloiden toiminnallisuudet
        lisaaAsukasPainike.setOnAction(_ -> avaaLisaysIkkuna());
        muokkaaAsukasPainike.setOnAction(_ -> avaaAsukkaanMuokkaus());
        poistaAsukasPainike.setOnAction(_ -> poistaValittuAsukas());
        suljePainike.setOnAction(_ -> sulje());
    }
        //Vastaanotetaan asunto
    public void setAsunto(Asunto asunto) {
        this.asunto = asunto;

        //Kertoo mikä asunto kyseessä
        asuntoLabel.setText("Asunto: " + asunto.getAsunto());

        //Ladataan asukkaat
        asukkaat.clear();
        asukkaat.addAll(asunto.getAsukkaat());
    }
        //Metodi asukkaiden lisääntymis ikkunaa varten
    private void avaaLisaysIkkuna() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fi/jyu/ohj2/aaekleme/taloyhtio/add-asukas.fxml")
            );
            Parent root = loader.load();
        //Haetaan näkymän controller
            AddAsukasController ohjain = loader.getController();

        //Luodaan uusi stage/ikkuna
            Stage dialogi = new Stage();
            dialogi.setScene(new Scene(root));

            dialogi.setTitle("Lisää asukas");

        //Käyttäjä ei voi klikata pääikkunaa ennen näkymän sulkemista
            dialogi.initModality(Modality.APPLICATION_MODAL);
            dialogi.showAndWait();
        //Haetaan käyttäjän syöttämä asukas controllerilta
            Asukas uusi = ohjain.getUusiAsukas();
            if (uusi != null) {
                //Estetään saman asukkaan lisääminen, nimi + syntymävuosi, asukkailla voi olla sama sposti
                boolean loytyy = asukkaat.stream()
                        .anyMatch(a ->
                                a.getNimi().equalsIgnoreCase(uusi.getNimi()) &&
                                        a.getSyntymavuosi() == uusi.getSyntymavuosi()
                        );
                //Varoitusikkuna jos asukas löytyy jo
                if (loytyy) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Virhe");
                    alert.setHeaderText("Asukas on jo olemassa");
                    alert.setContentText("Sama nimi ja syntymävuosi löytyy jo.");
                    alert.showAndWait();
                    return;
                }
                asukkaat.add(uusi);

                //Päivitetään asunto-olio
                asunto.getAsukkaat().clear();
                asunto.getAsukkaat().addAll(asukkaat);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void avaaAsukkaanMuokkaus() {
        Asukas valittuAsukas = asukasTaulu.getSelectionModel().getSelectedItem();

        if (valittuAsukas == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fi/jyu/ohj2/aaekleme/taloyhtio/edit-asukas.fxml")
            );
            Parent root = loader.load();

            EditAsukasController ohjain = loader.getController();
            ohjain.setAsukas(valittuAsukas);

            Stage dialogi = new Stage();
            dialogi.setScene(new Scene(root));
            dialogi.setTitle("Muokkaa asukasta " + valittuAsukas.getNimi());
            dialogi.initModality(Modality.APPLICATION_MODAL);
            dialogi.showAndWait();

            asukasTaulu.refresh();

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

         //Päivitetään asunto-olio
            asunto.getAsukkaat().clear();
            asunto.getAsukkaat().addAll(asukkaat);
        }
    }
    //Metodi sulje-painikkeelle
    private void sulje() {
        Stage ikkuna = (Stage) suljePainike.getScene().getWindow();
        ikkuna.close();
    }
}