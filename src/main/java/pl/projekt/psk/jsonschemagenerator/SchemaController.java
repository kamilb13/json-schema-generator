package pl.projekt.psk.jsonschemagenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tools.jackson.core.StreamReadFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;


import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SchemaController {
    private final ObjectMapper objectMapper = JsonMapper.builder()
                                                        .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                                                        .build();
    private final SchemaService schemaService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String jsonInput, Model model, HttpServletRequest request) {
        request.setAttribute("jsonInput", jsonInput);
        if (jsonInput == null || jsonInput.isEmpty()) {
            throw new IllegalArgumentException("Json input cannot be null or empty");
        }
        Map<String, Object> parsedJson = MyObjectMapper.fromJson(jsonInput);
        JsonNode jsonNode = objectMapper.readTree(jsonInput);
        var schema = schemaService.generateSchema(jsonNode);

        model.addAttribute("schemaOutput", schema.toPrettyString());
        model.addAttribute("jsonInput", jsonInput);
        return "index";
    }

    @PostMapping("/checkJson")
    public String isJsonValid(@RequestParam String jsonInput, Model model, HttpServletRequest request) {
        request.setAttribute("jsonInput", jsonInput);
        if (jsonInput == null || jsonInput.isEmpty()) {
            throw new IllegalArgumentException("Json input cannot be null or empty");
        }
        objectMapper.readTree(jsonInput);
        model.addAttribute("isValid", true);
        model.addAttribute("jsonInput", jsonInput);
        return "index";
    }
}
