package com.aa.main.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRequest {

    private String invoiceNumber;
    private String productName;

    @NotNull(message = "Company id is required")
    private Integer companyId;

    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private String paymentMode;

    @Valid
    @NotEmpty(message = "At least one invoice item is required")
    private List<InvoiceItemRequest> items;
}
