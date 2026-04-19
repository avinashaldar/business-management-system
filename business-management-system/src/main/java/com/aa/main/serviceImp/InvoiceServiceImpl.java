package com.aa.main.serviceImp;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aa.main.dto.InvoiceItemRequest;
import com.aa.main.dto.InvoiceRequest;
import com.aa.main.entity.*;
import com.aa.main.exception.ResourceNotFoundException;
import com.aa.main.repository.*;
import com.aa.main.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              ProductRepository productRepository,
                              CompanyRepository companyRepository) {
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Invoice createInvoice(InvoiceRequest request) {

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
        invoice.setCompany(company);

        BigDecimal total = BigDecimal.ZERO;

        for (InvoiceItemRequest itemReq : request.getItems()) {

            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            InvoiceItem item = new InvoiceItem();
            item.setProduct(product);
            item.setProductName(product.getName());
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice());

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            item.setTotalPrice(itemTotal);

            // Correct way: Use helper method (sets both sides of relationship)
            invoice.addInvoiceItem(item);

            total = total.add(itemTotal);
        }

        invoice.setTotalAmount(total);

        BigDecimal tax = request.getTaxAmount() != null ? request.getTaxAmount() : BigDecimal.ZERO;
        BigDecimal discount = request.getDiscountAmount() != null ? request.getDiscountAmount() : BigDecimal.ZERO;

        invoice.setTaxAmount(tax);
        invoice.setDiscountAmount(discount);

        BigDecimal net = total.add(tax).subtract(discount);
        invoice.setNetAmount(net);

        invoice.setPaymentMode(request.getPaymentMode());

        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> getInvoicesByCompany(Integer companyId) {
        return invoiceRepository.findByCompanyId(companyId);
    }

    @Override
    public Invoice getInvoiceById(Integer id) {
        return invoiceRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
    }

    @Override
    public Invoice updateInvoice(Integer id, InvoiceRequest request) {
        throw new UnsupportedOperationException("Update not implemented");
    }

    @Override
    public void deleteInvoice(Integer id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public Invoice getInvoiceByNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
    }
}