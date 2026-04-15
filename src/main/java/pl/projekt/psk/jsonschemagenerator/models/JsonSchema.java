package pl.projekt.psk.jsonschemagenerator.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.projekt.psk.jsonschemagenerator.dto.JsonSchemaRequest;

@Document(collection = "Schemas")
@Setter
@Getter
@NoArgsConstructor
public class JsonSchema {
    @Id
    private String id;
    private String name;
    private String schemaData;

    public JsonSchema(JsonSchemaRequest request) {
        this.name = request.getName();
        this.schemaData = request.getSchemaData();
    }

    public JsonSchema(String schemaName, String schemaData) {
        this.name = schemaName;
        this.schemaData = schemaData;
    }
}
