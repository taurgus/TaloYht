package fi.jyu.ohj2.aaekleme.taloyhtio;

public class Asunto {
    private String asunto;
    private int asukasmaara;

    public Asunto() {
    }

    public Asunto(String asunto, int asukasmaara) {
        this.asunto = asunto;
        this.asukasmaara = asukasmaara;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public int getAsukasmaara() {
        return asukasmaara;
    }

    public void setAsukasmaara(int asukasmaara) {
        this.asukasmaara = asukasmaara;
    }
}