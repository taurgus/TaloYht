package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Models.Asukas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAsukasController implements Initializable {

    @FXML
    private TextField nimiKentta;

    @FXML
    private TextField syntymavuosiKentta;

    @FXML
    private TextField sahkopostiKentta;

    @FXML
    private Button tallennaPainike;

    @FXML
    private Button suljePainike;

    private Asukas uusiAsukas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tallennaPainike.setOnAction(_ -> tallenna());
        suljePainike.setOnAction(_ -> sulje());

        // Asetetaan fokus tallenna-nappiin
        javafx.application.Platform.runLater(() -> tallennaPainike.requestFocus());
    }

    private void tallenna() {
        //Normaali ulkoasu
        nimiKentta.setStyle("");
        syntymavuosiKentta.setStyle("");
        sahkopostiKentta.setStyle("");

        //Muuttuja tarkistuksiin
        boolean ok = true;

        String nimi = nimiKentta.getText();
        String syntymavuosiTeksti = syntymavuosiKentta.getText();
        String sahkoposti = sahkopostiKentta.getText();

        if (nimi == null || nimi.isBlank() || !nimi.matches("[a-zA-ZåäöÅÄÖ ]+")) {
            nimiKentta.setStyle("-fx-border-color: red;");
            ok = false;
        }

        int syntymavuosi = 0;

        try {
            syntymavuosi = Integer.parseInt(syntymavuosiTeksti);

            if (syntymavuosiTeksti.length() > 4 || syntymavuosi < 1925 || syntymavuosi > 2026) {
                syntymavuosiKentta.setStyle("-fx-border-color: red;");
                ok = false;
            }

        } catch (Exception e) {
            syntymavuosiKentta.setStyle("-fx-border-color: red;");
            ok = false;
        }

        if (sahkoposti == null
                || sahkoposti.isBlank()
                || !sahkoposti.contains("@")
                || sahkoposti.length() < 10
                || sahkoposti.length() > 20) {

            sahkopostiKentta.setStyle("-fx-border-color: red;");
            ok = false;
        }

        if (!ok) {
            return;
        }

        uusiAsukas = new Asukas(nimi, syntymavuosi, sahkoposti);
        sulje();
    }

    public Asukas getUusiAsukas() {
        return uusiAsukas;
    }

    private void sulje() {
        Stage ikkuna = (Stage) suljePainike.getScene().getWindow();
        ikkuna.close();
    }
}