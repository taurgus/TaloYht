package fi.jyu.ohj2.aaekleme.taloyhtio.Controllers;

import fi.jyu.ohj2.aaekleme.taloyhtio.Asukas;
import fi.jyu.ohj2.aaekleme.taloyhtio.Asunto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        suljePainike.setOnAction(event -> sulje());
    }

    public void setAsunto(Asunto asunto) {
        this.asunto = asunto;
        asuntoLabel.setText("Asunto: " + asunto.getAsunto());
    }

    private void sulje() {
        Stage ikkuna = (Stage) suljePainike.getScene().getWindow();
        ikkuna.close();
    }
}