package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Asunto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAsuntoController implements Initializable {

    @FXML
    private TextField asuntoKentta;

    @FXML
    private TextField asukasmaaraKentta;

    @FXML
    private Button tallennaPainike;

    @FXML
    private Button suljePainike;

    private Asunto uusiAsunto;

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        asuntoKentta.setPromptText("Asunto max 5 merkkiä");
        asukasmaaraKentta.setPromptText("< 10");

        suljePainike.setOnAction(event -> sulje());
        tallennaPainike.setOnAction(event -> tallenna());

        // Focus Tallenna nappiin, tässä kysytty AI-apua, requestFocus ei toiminut
        javafx.application.Platform.runLater(() -> tallennaPainike.requestFocus());
    }

    private void tallenna() {
        // nollataan tyylit
        asuntoKentta.setStyle("");
        asukasmaaraKentta.setStyle("");

        boolean ok = true;

        String asunto = asuntoKentta.getText();

        //Estetään virheelliset syötteet
        if (asunto == null || asunto.isBlank() || asunto.length() > 5) {
            asuntoKentta.setStyle("-fx-border-color: red;");
            ok = false;
        }

        int asukasmaara = 0;

        try {
            asukasmaara = Integer.parseInt(asukasmaaraKentta.getText());
            if (asukasmaara >= 10) {
                asukasmaaraKentta.setStyle("-fx-border-color: red;");
                ok = false;
            }
        } catch (Exception e) {
            asukasmaaraKentta.setStyle("-fx-border-color: red;");
            ok = false;
        }

        if (!ok) {
            return;
        }

        uusiAsunto = new Asunto(asunto);
        sulje();
    }

    public Asunto getUusiAsunto() {
        return uusiAsunto;
    }

    private void sulje() {
        Stage ikkuna = (Stage) suljePainike.getScene().getWindow();
        ikkuna.close();
    }
}