package fi.jyu.ohj2.aaekleme.taloyhtio;

public class Asukas {

    private String nimi;
    private int syntymavuosi;
    private String sahkoposti;

    public Asukas() {
    }

    public Asukas(String nimi, int syntymavuosi, String sahkoposti) {
        this.nimi = nimi;
        this.syntymavuosi = syntymavuosi;
        this.sahkoposti = sahkoposti;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getSyntymavuosi() {
        return syntymavuosi;
    }

    public void setSyntymavuosi(int syntymavuosi) {
        this.syntymavuosi = syntymavuosi;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }
}