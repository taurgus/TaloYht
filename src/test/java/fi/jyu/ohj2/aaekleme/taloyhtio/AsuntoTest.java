package fi.jyu.ohj2.aaekleme.taloyhtio;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsuntoTest {

    @Test
    void asunnonLuontiToimii() {
        Asunto asunto = new Asunto("A1");

        assertEquals("A1", asunto.getAsunto());
        assertEquals(0, asunto.getAsukasmaara());
    }

    @Test
    void asukkaanLisaaminenKasvattaaAsukasmaaraa() {
        Asunto asunto = new Asunto("A1");
        Asukas asukas = new Asukas("Matti", 1990, "matti@testi.fi");

        asunto.getAsukkaat().add(asukas);

        assertEquals(1, asunto.getAsukasmaara());
    }

    @Test
    void asukkaanPoistaminenPienentaaAsukasmaaraa() {
        Asunto asunto = new Asunto("A1");
        Asukas asukas1 = new Asukas("Matti", 1990, "matti@testi.fi");
        Asukas asukas2 = new Asukas("Maija", 1992, "maija@testi.fi");

        asunto.getAsukkaat().add(asukas1);
        asunto.getAsukkaat().add(asukas2);
        asunto.getAsukkaat().remove(asukas1);

        assertEquals(1, asunto.getAsukasmaara());
    }

    @Test
    void tallennusJaLatausToimii() {
        Path polku = Path.of("testi.json");

        AsuntoTallennus tallennus = new AsuntoTallennus(polku);

        List<Asunto> lista = new ArrayList<>();
        Asunto asunto = new Asunto("A1");
        asunto.getAsukkaat().add(new Asukas("Matti", 1990, "matti@testi.fi"));
        lista.add(asunto);

        tallennus.tallennaAsunnot(lista);

        List<Asunto> ladattu = tallennus.lataaAsunnot();

        assertEquals(1, ladattu.size());
        assertEquals("A1", ladattu.getFirst().getAsunto());
    }
}