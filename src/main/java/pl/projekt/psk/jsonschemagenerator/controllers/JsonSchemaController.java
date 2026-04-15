package pl.projekt.psk.jsonschemagenerator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.projekt.psk.jsonschemagenerator.dto.JsonSchemaRequest;
import pl.projekt.psk.jsonschemagenerator.models.JsonSchema;
import pl.projekt.psk.jsonschemagenerator.repositories.JsonSchemaRepository;

@RestController
@RequestMapping("/api/schemas")
@RequiredArgsConstructor
public class JsonSchemaController {
    private final JsonSchemaRepository jsonSchemaRepository;

    @PostMapping
    public String createJsonSchema(@RequestBody JsonSchemaRequest request) {
        JsonSchema jsonSchema = new JsonSchema(request);
        jsonSchemaRepository.save(jsonSchema);
        return "index";
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getSchemaData(@PathVariable String id) {
        return jsonSchemaRepository.findById(id)
                .map(schema -> ResponseEntity.ok().body(schema.getSchemaData()))
                .orElse(ResponseEntity.notFound().build());
    }
}
