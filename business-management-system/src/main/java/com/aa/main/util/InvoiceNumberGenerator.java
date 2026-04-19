package com.aa.main.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public final class InvoiceNumberGenerator {

    private InvoiceNumberGenerator() {
    }

    public static String generate() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int randomPart = ThreadLocalRandom.current().nextInt(100, 999);
        return "INV-" + datePart + "-" + randomPart;
    }
}
