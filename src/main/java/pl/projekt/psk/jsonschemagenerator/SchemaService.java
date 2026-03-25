package pl.projekt.psk.jsonschemagenerator;

import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class SchemaService {
    private final ObjectMapper objectMapper = new ObjectMapper();

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
}
