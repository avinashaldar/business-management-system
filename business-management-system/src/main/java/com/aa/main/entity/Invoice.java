package com.aa.main.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String invoiceNumber;

    // Kept for compatibility with your original structure.
    private String productName;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 12, scale = 2)
    private BigDecimal taxAmount;

    @Column(precision = 12, scale = 2)
    private BigDecimal discountAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal netAmount;

    @Column(name = "payment_mode")
    private String paymentMode;
    private LocalDateTime invoiceDate;

    @ManyToOne
    @JoinColumn(name = "invoice_company")
    @JsonIgnoreProperties({"employees", "products", "invoices"})
    private Company company;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"invoice"})
    private List<InvoiceItem> invoiceItems = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (invoiceDate == null) {
            invoiceDate = LocalDateTime.now();
        }
    }
    public void addInvoiceItem(InvoiceItem item) {
        this.invoiceItems.add(item);
        item.setInvoice(this);   // Ensures both sides are synced
    }
}
