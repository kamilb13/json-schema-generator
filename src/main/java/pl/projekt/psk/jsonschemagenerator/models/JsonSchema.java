package pl.projekt.psk.jsonschemagenerator.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Schemas")
@Setter
@Getter
public class JsonSchema {
    @Id
    private String id;
    private String name;
    private String schemaData;
}
