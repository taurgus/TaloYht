package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Models.Asunto;
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
    private Button tallennaPainike;

    @FXML
    private Button suljePainike;

    private Asunto uusiAsunto;

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        asuntoKentta.setPromptText("Asunto max 5 merkkiä");


        suljePainike.setOnAction(event -> sulje());
        tallennaPainike.setOnAction(event -> tallenna());

        // Focus Tallenna nappiin, tässä kysytty AI-apua, requestFocus ei toiminut
        javafx.application.Platform.runLater(() -> tallennaPainike.requestFocus());
    }

    private void tallenna() {
        asuntoKentta.setStyle("");

        String asunto = asuntoKentta.getText();

        if (asunto == null || asunto.isBlank() || asunto.length() < 2 || asunto.length() > 5) {
            asuntoKentta.setStyle("-fx-border-color: red;");
            return;
        }

        uusiAsunto = new Asunto(asunto);
        sulje();
    }

    //Getteri palauttaa asunnon
    public Asunto getUusiAsunto() {
        return uusiAsunto;
    }

    private void sulje() {
        Stage ikkuna = (Stage) suljePainike.getScene().getWindow();
        ikkuna.close();
    }
}