package com.aa.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aa.main.entity.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Integer> {
    List<InvoiceItem> findByInvoiceId(Integer invoiceId);
}
