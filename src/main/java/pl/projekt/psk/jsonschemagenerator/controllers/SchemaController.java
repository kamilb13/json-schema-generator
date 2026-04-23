package pl.projekt.psk.jsonschemagenerator.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.projekt.psk.jsonschemagenerator.mappers.MyObjectMapper;
import pl.projekt.psk.jsonschemagenerator.services.SchemaService;
import pl.projekt.psk.jsonschemagenerator.repositories.JsonSchemaRepository;
import pl.projekt.psk.jsonschemagenerator.models.JsonSchema;
import tools.jackson.core.StreamReadFeature;
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
    private final JsonSchemaRepository jsonSchemaRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("schemas", jsonSchemaRepository.findAll());
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String jsonInput, Model model, HttpServletRequest request) {
        model.addAttribute("schemas", jsonSchemaRepository.findAll());

        request.setAttribute("jsonInput", jsonInput);
        if (jsonInput == null || jsonInput.isEmpty()) {
            throw new IllegalArgumentException("Json input cannot be null or empty");
        }
        Map<String, Object> parsedJson = MyObjectMapper.fromJson(jsonInput);
        Map<String, Object> schema = schemaService.generateSchema(parsedJson);

        model.addAttribute("schemaOutput", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
        model.addAttribute("jsonInput", jsonInput);
        return "index";
    }

    @PostMapping("/checkJson")
    public String isJsonValid(@RequestParam String jsonInput, @RequestParam String schemaOutput, Model model, HttpServletRequest request) {
        model.addAttribute("schemas", jsonSchemaRepository.findAll());

        request.setAttribute("jsonInput", jsonInput);
        if (jsonInput == null || jsonInput.isEmpty()) {
            throw new IllegalArgumentException("Json input cannot be null or empty");
        }

        try {
            Map<String, Object> parsedJson = MyObjectMapper.fromJson(jsonInput);
            Map<String, Object> schema = schemaService.generateSchema(parsedJson);

            String prettySchema = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);

            Object schemaOutputObj = objectMapper.readValue(schemaOutput, Object.class);
            String prettySchemaOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaOutputObj);

            boolean isValid = prettySchema.equals(prettySchemaOutput);
            model.addAttribute("isValid", isValid);
            model.addAttribute("schemaOutput", schemaOutput);
        } catch (Exception e) {
            model.addAttribute("isValid", false);
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("jsonInput", jsonInput);
        return "index";
    }

    @PostMapping("/saveSchema")
    public String saveSchema(@RequestParam String schemaName,
                             @RequestParam String schemaData,
                             @RequestParam(required = false) String jsonInput,
                             Model model) {
        JsonSchema jsonSchema = new JsonSchema(schemaName, schemaData);
        jsonSchemaRepository.save(jsonSchema);
        model.addAttribute("schemas", jsonSchemaRepository.findAll());
        model.addAttribute("message", "Schemat '" + schemaName + "' został pomyślnie zapisany w bazie danych");
        if (jsonInput != null) {
            model.addAttribute("jsonInput", jsonInput);
        }
        return "index";
    }
}
