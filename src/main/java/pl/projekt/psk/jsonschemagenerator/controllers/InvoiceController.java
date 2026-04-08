package pl.projekt.psk.jsonschemagenerator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.projekt.psk.jsonschemagenerator.models.Invoice;
import pl.projekt.psk.jsonschemagenerator.repositories.InvoiceRepository;

@Controller
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceRepository invoiceRepository;

    @PostMapping("/invoices")
    public String createInvoice(@RequestBody String jsonInput) {
        Invoice invoice = new Invoice();
        invoice.setJsonData(jsonInput);
        invoiceRepository.save(invoice);
        return "index";
    }
}
