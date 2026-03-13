package pl.projekt.psk.jsonschemagenerator;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Controller
public class SchemaController {

    private final ObjectMapper objectMapper;

    public SchemaController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String jsonInput, Model model) {
        JsonNode jsonNode = objectMapper.readTree(jsonInput);
        String hardcodedSchema = "{\n" +
                "  \"location\": \"object\",\n" +
                "  \"adress\": {\n" +
                "    \"przykladzik\": {\n" +
                "      \"type\": \"string\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        model.addAttribute("schemaOutput", hardcodedSchema);
        model.addAttribute("jsonInput", jsonInput);
        return "index";
    }
}
