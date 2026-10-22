package com.hcl.day13;

@FunctionalInterface
public interface PriceLabel {
    String format(Product product);
}
