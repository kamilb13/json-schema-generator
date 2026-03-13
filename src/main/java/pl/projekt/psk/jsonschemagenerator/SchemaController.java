package pl.projekt.psk.jsonschemagenerator;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SchemaController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String jsonInput, Model model) {
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
