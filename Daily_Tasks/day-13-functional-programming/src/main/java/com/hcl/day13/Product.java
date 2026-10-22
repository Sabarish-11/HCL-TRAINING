package com.hcl.day13;

import java.math.BigDecimal;
import java.util.Objects;

public record Product(
        String name,
        String category,
        BigDecimal price,
        boolean inStock) {

    public Product {
        name = requireText(name, "name");
        category = requireText(category, "category");
        Objects.requireNonNull(price, "price");

        if (price.signum() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public Product(ProductSeed seed) {
        this(
                Objects.requireNonNull(seed, "seed").name(),
                seed.category(),
                seed.price(),
                seed.inStock()
        );
    }

    private static String requireText(String value, String field) {
        Objects.requireNonNull(value, field);

        if (value.isBlank()) {
            throw new IllegalArgumentException(field + " cannot be blank");
        }

        return value.trim();
    }

    public static Product of(
            String name, String category, String price, boolean inStock) {
        return new Product(name, category, new BigDecimal(price), inStock);
    }

    public static int compareByPrice(Product first, Product second) {
        return first.price().compareTo(second.price());
    }

    @Override
    public String toString() {
        return "%s | %s | $%s | In stock: %s".formatted(
                name, category, price.toPlainString(), inStock);
    }
}
