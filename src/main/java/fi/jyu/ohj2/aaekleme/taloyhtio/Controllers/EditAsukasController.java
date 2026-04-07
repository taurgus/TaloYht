package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Asukas;
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

    private void tallenna() {
        String nimi = nimiKentta.getText();
        String sahkoposti = sahkopostiKentta.getText();

        int syntymavuosi;

        try {
            syntymavuosi = Integer.parseInt(syntymavuosiKentta.getText());
        } catch (Exception e) {
            syntymavuosiKentta.setStyle("-fx-border-color: red;");
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