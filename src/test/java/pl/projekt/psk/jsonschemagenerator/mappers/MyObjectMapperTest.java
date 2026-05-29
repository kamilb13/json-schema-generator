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

    @Test
    public void testFromJson_CommaInString() {
        // Given
        String json = "{\n" +
                "  \"name\": \"George Washington\",\n" +
                "  \"birthday\": \"February 22, 1732\",\n" +
                "  \"address\": \"Mount Vernon, Virginia, United States\"\n" +
                "}\n" +
                "\n";

        // When
        Map<String, Object> result = MyObjectMapper.fromJson(json);

        // Then
        assertEquals("George Washington", result.get("name"));
        assertEquals("February 22, 1732", result.get("birthday"));
        assertEquals("Mount Vernon, Virginia, United States", result.get("address"));
    }

    @Test
    public void testFromJson_MapsToInteger() {
        // Given
        String json = "{\"user\": {\"id\": 1, \"active\": true}}";

        // When
        Map<String, Object> result = MyObjectMapper.fromJson(json);
        Map<?, ?> user = assertInstanceOf(Map.class, result.get("user"));

        // Then
        assertInstanceOf(Integer.class, user.get("id"));
        assertEquals(1,  user.get("id"));
    }

    @Test
    public void testFromJson_MapsToDouble() {
        // Given
        String json = "{\"car\": {\"id\": 1, \"price\": 2.0}}";

        // When
        Map<String, Object> result = MyObjectMapper.fromJson(json);
        Map<?, ?> car = assertInstanceOf(Map.class, result.get("car"));

        // Then
        assertInstanceOf(Double.class, car.get("price"));
        assertEquals(2.0, car.get("price"));
    }

    @Test
    public void testFromJson_MapsToBoolean() {
        // Given
        String json = "{\"user\": {\"id\": 1, \"active\": true}}";

        // When
        Map<String, Object> result = MyObjectMapper.fromJson(json);
        Map<?, ?> user = assertInstanceOf(Map.class, result.get("user"));

        // Then
        assertInstanceOf(Boolean.class, user.get("active"));
        assertEquals(true,  user.get("active"));
    }

    @Test
    public void testFromJson_MapsToString() {
        // Given
        String json = "{\"name\": \"John\", \"age_in_quotes\": \"30\"}";

        // When
        Map<String, Object> result = MyObjectMapper.fromJson(json);

        // Then
        assertInstanceOf(String.class,  result.get("age_in_quotes"));
        assertEquals("30", result.get("age_in_quotes"));
    }
}
