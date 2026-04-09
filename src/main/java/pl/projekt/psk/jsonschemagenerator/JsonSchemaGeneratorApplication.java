package pl.projekt.psk.jsonschemagenerator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.projekt.psk.jsonschemagenerator.models.JsonSchema;
import pl.projekt.psk.jsonschemagenerator.repositories.JsonSchemaRepository;

@SpringBootApplication
public class JsonSchemaGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonSchemaGeneratorApplication.class, args);
    }

    // todo na razie dla testów
    @Bean
    CommandLineRunner initDatabase(JsonSchemaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                JsonSchema s1 = new JsonSchema();
                s1.setName("Faktura VAT");
                s1.setSchemaData("{\"type\": \"object\", \"properties\": {\"nr\": {\"type\": \"string\"}}}");
                repository.save(s1);

                JsonSchema s2 = new JsonSchema();
                s2.setName("Test 2");
                s2.setSchemaData("{\"type\": \"object\", \"required\": [\"kwota\"]}");
                repository.save(s2);
            }
        };
    }
}
