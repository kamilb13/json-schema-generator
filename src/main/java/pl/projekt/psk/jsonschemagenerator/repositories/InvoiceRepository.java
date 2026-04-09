package pl.projekt.psk.jsonschemagenerator.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.projekt.psk.jsonschemagenerator.models.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
}
