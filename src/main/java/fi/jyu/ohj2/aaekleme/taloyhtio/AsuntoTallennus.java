package fi.jyu.ohj2.aaekleme.taloyhtio;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

        return mapper.readValue(
                tiedosto.toFile(),
                new TypeReference<List<Asunto>>() {}
        );
    }

    public void tallennaAsunnot(List<Asunto> asunnot) {
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(tiedosto.toFile(), asunnot);
    }
}