package com.aa.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aa.main.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByCompanyId(Integer companyId);
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    @Query("SELECT i FROM Invoice i LEFT JOIN FETCH i.invoiceItems items LEFT JOIN FETCH items.product WHERE i.id = :id")
    Optional<Invoice> findByIdWithItems(@Param("id") Integer id);
}
