package pl.projekt.psk.jsonschemagenerator.mappers;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MyObjectMapperTest {

    @Test
    void shouldParseSimpleJson() {
        // Given
        String json = "{\"name\": \"John\", \"age\": 30}";

        // When
        Map<String, Object> result = MyObjectMapper.fromJson(json);

        // Then
        assertEquals("John", result.get("name"));
        assertEquals(30, result.get("age"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldParseNestedJson() {
        // Given
        String json = "{\"user\": {\"id\": 1, \"active\": true}}";

        // When
        Map<String, Object> result = MyObjectMapper.fromJson(json);

        // Then
        assertNotNull(result.get("user"));
        Map<String, Object> user = (Map<String, Object>) result.get("user");
        assertEquals(1, user.get("id"));
        assertEquals(true, user.get("active"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldParseJsonWithArray() {
        // Given
        String json = "{\"tags\": [\"java\", \"test\"]}";

        // When
        Map<String, Object> result = MyObjectMapper.fromJson(json);

        // Then
        assertNotNull(result.get("tags"));
        List<Object> tags = (List<Object>) result.get("tags");
        assertEquals(2, tags.size());
        assertEquals("java", tags.get(0));
        assertEquals("test", tags.get(1));
    }
}
