package pl.projekt.psk.jsonschemagenerator.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.projekt.psk.jsonschemagenerator.models.Invoice;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice, String> {
}
