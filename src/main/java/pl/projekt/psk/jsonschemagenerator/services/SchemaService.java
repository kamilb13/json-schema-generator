package pl.projekt.psk.jsonschemagenerator.services;

import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchemaService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> generateSchema(Object object) {
        Map<String, Object> schema = new LinkedHashMap<>();

        if (object instanceof Map) {
            schema.put("type", "object");
            Map<?, ?> objectMap = (Map<?, ?>) object;
            Map<String, Object> properties = new LinkedHashMap<>();
            for (Map.Entry<?, ?> property : objectMap.entrySet()) {
                properties.put(property.getKey().toString(), generateSchema(property.getValue()));
            }
            schema.put("properties", properties);
        }

        if (object instanceof List) {
            schema.put("type", "array");
            List<?> array = (List<?>) object;
            if (!array.isEmpty()) {
                schema.put("items", generateSchema(array.getFirst()));
            }
        }

        if (object instanceof String) {
            schema.put("type", "string");
        }

        if (object instanceof Integer || object instanceof Double) {
            schema.put("type", "number");
        }

        if (object instanceof Boolean) {
            schema.put("type", "boolean");
        }

        return schema;
    }

    private String toPrettyString(Object object) {
        if (object == null) {
            return "null";
        }

        if (object instanceof String) {
            return "\"" + object + "\"";
        }

        if (object instanceof Number || object instanceof Boolean) {
            return object.toString();
        }

        if (object instanceof List) {
            StringBuilder sb = new StringBuilder("[");
            List<?> list = (List<?>) object;
            for (int i = 0; i < list.size(); i++) {
                sb.append(toPrettyString(list.get(i)));
                if (i < list.size() - 1) sb.append(", ");
            }
            return sb.append("]").toString();
        }

        if (object instanceof Map) {
            StringBuilder sb = new StringBuilder("{");
            Map<?, ?> map = (Map<?, ?>) object;
            int i = 0;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                sb.append("\"").append(entry.getKey()).append("\": ").append(toPrettyString(entry.getValue()));
                if (++i < map.size()) sb.append(", ");
            }
            return sb.append("}").toString();
        }

        return "\"" + object + "\"";
    }
}
