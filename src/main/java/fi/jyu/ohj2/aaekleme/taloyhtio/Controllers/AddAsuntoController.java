package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        suljePainike.setOnAction(event -> sulje());
        tallennaPainike.setOnAction(event -> sulje());
    }

    private void sulje() {
        Stage ikkuna = (Stage) suljePainike.getScene().getWindow();
        ikkuna.close();
    }
}