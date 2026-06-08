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
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SchemaController {
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
        String prettySchema = MyObjectMapper.toPrettyJson(schema, 0);

        model.addAttribute("schemaOutput", prettySchema);
        model.addAttribute("jsonInput", jsonInput);
        model.addAttribute("isGenerated", true);
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

            Map<String, Object> providedSchema = MyObjectMapper.fromJson(schemaOutput);
            boolean isValid = schema.equals(providedSchema);

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
