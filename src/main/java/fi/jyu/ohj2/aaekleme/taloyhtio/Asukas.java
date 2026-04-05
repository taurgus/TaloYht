package fi.jyu.ohj2.aaekleme.taloyhtio;

import javafx.beans.property.*;

public class Asukas {

    private final StringProperty nimi = new SimpleStringProperty("");
    private final StringProperty sahkoposti = new SimpleStringProperty("");
    private final IntegerProperty syntymavuosi = new SimpleIntegerProperty(0);

    public Asukas() {}

    public Asukas(String nimi, String sahkoposti, int syntymavuosi) {
        setNimi(nimi);
        setSahkoposti(sahkoposti);
        setSyntymavuosi(syntymavuosi);
    }

    public String getNimi() {
        return nimi.get();
    }

    public void setNimi(String nimi) {
        this.nimi.set(nimi);
    }

    public StringProperty nimiProperty() {
        return nimi;
    }

    public String getSahkoposti() {
        return sahkoposti.get();
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti.set(sahkoposti);
    }

    public StringProperty sahkopostiProperty() {
        return sahkoposti;
    }

    public int getSyntymavuosi() {
        return syntymavuosi.get();
    }

    public void setSyntymavuosi(int syntymavuosi) {
        this.syntymavuosi.set(syntymavuosi);
    }

    public IntegerProperty syntymavuosiProperty() {
        return syntymavuosi;
    }

    @Override
    public String toString() {
        return getNimi();
    }
}