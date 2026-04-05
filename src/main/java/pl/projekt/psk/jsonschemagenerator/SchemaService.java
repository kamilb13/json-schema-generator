package pl.projekt.psk.jsonschemagenerator;

import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchemaService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateSchema(Object object) {
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

        return toPrettyString(schema);
    }

    public JsonNode generateSchema(JsonNode jsonNode) {
        var schema = objectMapper.createObjectNode();

        if (jsonNode.isObject()) {
            schema.put("type", "object");
            var properties = objectMapper.createObjectNode();
            var fields = jsonNode.properties().iterator();
            while (fields.hasNext()) {
                var field = fields.next();
                properties.set(field.getKey(), generateSchema(field.getValue()));
            }
            schema.set("properties", properties);

        } else if (jsonNode.isArray()) {
            schema.put("type", "array");
            if (!jsonNode.isEmpty()) {
                schema.set("items", generateSchema(jsonNode.get(0)));
            } else {
                schema.set("items", objectMapper.createObjectNode());
            }

        } else if (jsonNode.isString()) {
            schema.put("type", "string");
        } else if (jsonNode.isIntegralNumber()) {
            schema.put("type", "integer");
        } else if (jsonNode.isFloatingPointNumber()) {
            schema.put("type", "number");
        } else if (jsonNode.isBoolean()) {
            schema.put("type", "boolean");
        } else if (jsonNode.isNull()) {
            schema.put("type", "null");
        }

        return schema;
    }

    private String toPrettyString(Map<String, Object> schema) {
        StringBuilder prettyJson = new StringBuilder("{");
        for (Map.Entry<String, Object> properties : schema.entrySet()) {
            prettyJson.append("\r\n\"").append(properties.getKey()).append("\":")
                    .append("\"").append(properties.getValue()).append("\",");
        }
        return prettyJson.toString();
    }
}
