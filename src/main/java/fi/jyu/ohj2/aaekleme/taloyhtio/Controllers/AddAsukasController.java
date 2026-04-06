package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Asukas;
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
        tallennaPainike.setOnAction(e -> tallenna());
        suljePainike.setOnAction(e -> sulje());
    }

    private void tallenna() {
        String nimi = nimiKentta.getText();
        int syntymavuosi = Integer.parseInt(syntymavuosiKentta.getText());
        String sahkoposti = sahkopostiKentta.getText();

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