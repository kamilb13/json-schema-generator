package pl.projekt.psk.jsonschemagenerator.services;

import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SchemaServiceTest {

    private final SchemaService schemaService = new SchemaService();

    @Test
    @SuppressWarnings("unchecked")
    void shouldGenerateSchemaForSimpleObject() {
        // Given
        Map<String, Object> input = new LinkedHashMap<>();
        input.put("name", "John");
        input.put("age", 30);
        input.put("isStudent", false);

        // When
        Map<String, Object> schema = schemaService.generateSchema(input);

        // Then
        assertEquals("object", schema.get("type"));
        Map<String, Object> properties = (Map<String, Object>) schema.get("properties");
        
        assertNotNull(properties);
        assertEquals("string", ((Map<String, Object>) properties.get("name")).get("type"));
        assertEquals("number", ((Map<String, Object>) properties.get("age")).get("type"));
        assertEquals("boolean", ((Map<String, Object>) properties.get("isStudent")).get("type"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldGenerateSchemaForNestedObjectAndArray() {
        // Given
        Map<String, Object> address = new LinkedHashMap<>();
        address.put("city", "Kielce");
        
        Map<String, Object> input = new LinkedHashMap<>();
        input.put("address", address);
        input.put("przedmioty", List.of("programowanie_systemow_rozporszonych", "technologie_obiektowe"));

        // When
        Map<String, Object> schema = schemaService.generateSchema(input);

        // Then
        assertEquals("object", schema.get("type"));
        Map<String, Object> properties = (Map<String, Object>) schema.get("properties");

        Map<String, Object> addressSchema = (Map<String, Object>) properties.get("address");
        assertEquals("object", addressSchema.get("type"));
        
        Map<String, Object> tagsSchema = (Map<String, Object>) properties.get("przedmioty");
        assertEquals("array", tagsSchema.get("type"));
        assertEquals("string", ((Map<String, Object>) tagsSchema.get("items")).get("type"));
    }
}
