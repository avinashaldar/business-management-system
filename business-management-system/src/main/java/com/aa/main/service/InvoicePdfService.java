package com.aa.main.service;

import com.aa.main.entity.Invoice;

public interface InvoicePdfService {

    byte[] generatePdf(Invoice invoice);
}