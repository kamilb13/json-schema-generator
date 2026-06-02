package pl.projekt.psk.jsonschemagenerator.services;

import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchemaService {
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
}
