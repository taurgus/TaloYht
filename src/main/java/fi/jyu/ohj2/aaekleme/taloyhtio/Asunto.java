package fi.jyu.ohj2.aaekleme.taloyhtio;

import java.util.ArrayList;
import java.util.List;

public class Asunto {

    private String asunto;
    private List<Asukas> asukkaat = new ArrayList<>();

    public Asunto() {
    }

    public Asunto(String asunto) {
        this.asunto = asunto;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public List<Asukas> getAsukkaat() {
        return asukkaat;
    }

    public void setAsukkaat(List<Asukas> asukkaat) {
        this.asukkaat = asukkaat;
    }

    // Asukasmäärä lasketaan listasta
    public int getAsukasmaara() {
        return asukkaat.size();
    }
}