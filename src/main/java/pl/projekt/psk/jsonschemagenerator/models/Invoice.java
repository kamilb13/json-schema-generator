package pl.projekt.psk.jsonschemagenerator.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Invoices")
@Setter
@Getter
public class Invoice {
    @Id
    private String id;
    private String jsonData;
}
