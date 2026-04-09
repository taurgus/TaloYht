package fi.jyu.ohj2.aaekleme.taloyhtio;

import fi.jyu.ohj2.aaekleme.taloyhtio.Models.Asunto;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


//Tiedon tallennus ja lataus
public class AsuntoTallennus {

    private final Path tiedosto;
    private final ObjectMapper mapper = new ObjectMapper();

    public AsuntoTallennus(Path tiedosto) {
        this.tiedosto = tiedosto;
    }


    public List<Asunto> lataaAsunnot() {
        if (!Files.exists(tiedosto)) {
            return new ArrayList<>();
        }
    //Muunnetaan JSON olioksi
        return mapper.readValue(
                tiedosto.toFile(),
                new TypeReference<List<Asunto>>() {}
        );
    }

    //Tallentaa asunnot
    public void tallennaAsunnot(List<Asunto> asunnot) {
        //JSON siistissä muodossa, kysytty AI apua
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(tiedosto.toFile(), asunnot);
    }
}