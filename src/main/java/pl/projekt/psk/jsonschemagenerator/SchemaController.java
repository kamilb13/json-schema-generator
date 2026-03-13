package pl.projekt.psk.jsonschemagenerator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SchemaController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
