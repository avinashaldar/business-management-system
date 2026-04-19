package com.aa.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aa.main.dto.InvoiceRequest;
import com.aa.main.entity.Invoice;
import com.aa.main.response.ApiResponse;
import com.aa.main.service.InvoicePdfService;
import com.aa.main.service.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoicePdfService invoicePdfService;

    public InvoiceController(InvoiceService invoiceService,
                             InvoicePdfService invoicePdfService) {
        this.invoiceService = invoiceService;
        this.invoicePdfService = invoicePdfService;
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Invoice>> createInvoice(@RequestBody InvoiceRequest request) {

        Invoice invoice = invoiceService.createInvoice(request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Invoice created successfully", invoice)
        );
    }
    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Integer id) {

        Invoice invoice = invoiceService.getInvoiceById(id);

        byte[] pdf = invoicePdfService.generatePdf(invoice);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=invoice.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
}