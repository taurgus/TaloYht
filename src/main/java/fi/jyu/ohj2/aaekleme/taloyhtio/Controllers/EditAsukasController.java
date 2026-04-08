package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Models.Asukas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAsukasController implements Initializable {

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

    private Asukas muokattavaAsukas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tallennaPainike.setOnAction(e -> tallenna());
        suljePainike.setOnAction(e -> sulje());
    }

    public void setAsukas(Asukas asukas) {
        this.muokattavaAsukas = asukas;

        nimiKentta.setText(asukas.getNimi());
        syntymavuosiKentta.setText(String.valueOf(asukas.getSyntymavuosi()));
        sahkopostiKentta.setText(asukas.getSahkoposti());
    }
//Tallennusmetodit ja tarkistukset
    private void tallenna() {
        nimiKentta.setStyle("");
        syntymavuosiKentta.setStyle("");
        sahkopostiKentta.setStyle("");

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

            if (syntymavuosiTeksti.length() > 4 || syntymavuosi < 1925 || syntymavuosi > 2026 ) {
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

        muokattavaAsukas.setNimi(nimi);
        muokattavaAsukas.setSyntymavuosi(syntymavuosi);
        muokattavaAsukas.setSahkoposti(sahkoposti);

        sulje();
    }

    private void sulje() {
        Stage ikkuna = (Stage) suljePainike.getScene().getWindow();
        ikkuna.close();
    }
}