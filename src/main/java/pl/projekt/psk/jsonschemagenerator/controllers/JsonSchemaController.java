package pl.projekt.psk.jsonschemagenerator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.projekt.psk.jsonschemagenerator.dto.JsonSchemaRequest;
import pl.projekt.psk.jsonschemagenerator.models.Invoice;
import pl.projekt.psk.jsonschemagenerator.models.JsonSchema;
import pl.projekt.psk.jsonschemagenerator.repositories.JsonSchemaRepository;

@Controller
@RequiredArgsConstructor
public class JsonSchemaController {
    private final JsonSchemaRepository jsonSchemaRepository;

    @PostMapping("/schemas")
    public String createJsonSchema(@RequestBody JsonSchemaRequest request) {
        JsonSchema jsonSchema = new JsonSchema();
        jsonSchema.setName(request.getName());
        jsonSchema.setSchemaData(request.getSchemaData());
        jsonSchemaRepository.save(jsonSchema);
        return "index";
    }
}
