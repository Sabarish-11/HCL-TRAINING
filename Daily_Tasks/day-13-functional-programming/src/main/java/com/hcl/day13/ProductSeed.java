package com.hcl.day13;

import java.math.BigDecimal;

public record ProductSeed(
        String name,
        String category,
        BigDecimal price,
        boolean inStock) {
}
