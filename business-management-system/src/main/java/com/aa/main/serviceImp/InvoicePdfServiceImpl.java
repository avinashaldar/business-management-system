package com.aa.main.serviceImp;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.aa.main.entity.Invoice;
import com.aa.main.entity.InvoiceItem;
import com.aa.main.service.InvoicePdfService;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.Paragraph;

@Service
public class InvoicePdfServiceImpl implements InvoicePdfService {

    @Override
    public byte[] generatePdf(Invoice invoice) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            doc.add(new Paragraph("===== INVOICE ====="));
            doc.add(new Paragraph("Invoice No: " + invoice.getInvoiceNumber()));
            doc.add(new Paragraph("Company: " + invoice.getCompany().getName()));
            doc.add(new Paragraph("Date: " + invoice.getInvoiceDate()));

            doc.add(new Paragraph("--------------------------"));

            for (InvoiceItem item : invoice.getInvoiceItems()) {

                String productName = item.getProductName() != null
                        ? item.getProductName()
                        : "N/A";

                doc.add(new Paragraph(
                        productName +
                        " | Qty: " + item.getQuantity() +
                        " | Price: " + item.getPrice() +
                        " | Total: " + item.getTotalPrice()
                ));
            }

            doc.add(new Paragraph("--------------------------"));
            doc.add(new Paragraph("Total: " + invoice.getTotalAmount()));
            doc.add(new Paragraph("Tax: " + invoice.getTaxAmount()));
            doc.add(new Paragraph("Discount: " + invoice.getDiscountAmount()));
            doc.add(new Paragraph("Net Amount: " + invoice.getNetAmount()));

            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}