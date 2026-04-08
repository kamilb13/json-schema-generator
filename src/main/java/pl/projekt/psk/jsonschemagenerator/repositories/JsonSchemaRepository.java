package pl.projekt.psk.jsonschemagenerator.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.projekt.psk.jsonschemagenerator.models.JsonSchema;

public interface JsonSchemaRepository extends MongoRepository<JsonSchema, String> {
}
