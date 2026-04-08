package fi.jyu.ohj2.aaekleme.taloyhtio.Models;

import java.util.ArrayList;
import java.util.List;

public class Asunto {

    //Asunnon tunnus
    private String asunto;
    //Lista asukkaista
    private List<Asukas> asukkaat = new ArrayList<>();

    //Konstrukotorit
    public Asunto() {
    }

    //Asettaa asunnolle osoitteen/tunnuksen
    public Asunto(String asunto) {
        this.asunto = asunto;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    //Palauttaa asukaslistan
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