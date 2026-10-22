package com.hcl.day13;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ProductFilterEngine {
    private final List<Product> products;

    public ProductFilterEngine(List<Product> products) {
        Objects.requireNonNull(products, "products");
        this.products = List.copyOf(products);
    }

    public List<Product> filter(Predicate<Product> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return products.stream().filter(predicate).toList();
    }

    public List<String> transform(
            Predicate<Product> predicate,
            Function<Product, String> formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return filter(predicate).stream().map(formatter).toList();
    }

    public void print(
            Predicate<Product> predicate,
            Consumer<Product> consumer) {
        Objects.requireNonNull(consumer, "consumer");
        filter(predicate).forEach(consumer);
    }

    public static Predicate<Product> priceAtMost(BigDecimal maximum) {
        Objects.requireNonNull(maximum, "maximum");
        return product -> product.price().compareTo(maximum) <= 0;
    }

    public static Predicate<Product> inCategory(String category) {
        Objects.requireNonNull(category, "category");
        return product -> product.category().equalsIgnoreCase(category);
    }

    public static Predicate<Product> inStock() {
        return Product::inStock;
    }

    public static Function<Product, String> nameToUpperCase() {
        Function<Product, String> getName = Product::name;
        return getName.andThen(String::toUpperCase);
    }

    public static Function<String, String> addProductPrefix() {
        return name -> "Product: " + name;
    }

    public static Function<Product, String> productDescription() {
        return product -> product.name() + " (" + product.category() + ")";
    }

    public static Product createDefaultProduct() {
        return Product.of("Notebook", "Stationery", "2.50", true);
    }

    public static Supplier<Product> defaultProductSupplier() {
        return ProductFilterEngine::createDefaultProduct;
    }

    public static List<Product> createProducts(List<ProductSeed> seeds) {
        Objects.requireNonNull(seeds, "seeds");
        Function<ProductSeed, Product> factory = Product::new;
        return seeds.stream().map(factory).toList();
    }

    public static Comparator<Product> byPriceThenName() {
        return Comparator.comparing(Product::price)
                .thenComparing(Product::name);
    }
}
