package com.aa.main.service;

import java.util.List;

import com.aa.main.dto.InvoiceRequest;
import com.aa.main.entity.Invoice;

public interface InvoiceService {
    Invoice createInvoice(InvoiceRequest request);
    List<Invoice> getAllInvoices();
    List<Invoice> getInvoicesByCompany(Integer companyId);
    Invoice getInvoiceById(Integer id);
    Invoice updateInvoice(Integer id, InvoiceRequest request);
    void deleteInvoice(Integer id);
	Invoice getInvoiceByNumber(String invoiceNumber);
}
