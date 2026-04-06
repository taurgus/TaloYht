package fi.jyu.ohj2.aaekleme.taloyhtio;

public class Asunto {
    private String asunto;

    public Asunto() {
    }

    public Asunto(String asunto) {
        this.asunto = asunto;
    }

    //GETTERI
    public String getAsunto() {
        return asunto;
    }
    //SETTERI
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
}