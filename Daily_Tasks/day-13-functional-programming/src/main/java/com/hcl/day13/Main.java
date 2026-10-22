package com.hcl.day13;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        List<Product> products = List.of(
                Product.of("Laptop", "Electronics", "799.99", true),
                Product.of("Mouse", "Electronics", "19.99", true),
                Product.of("Keyboard", "Electronics", "49.50", false),
                Product.of("Notebook", "Stationery", "2.50", true),
                Product.of("Pen Set", "Stationery", "5.75", true),
                Product.of("Desk Chair", "Furniture", "129.00", true),
                Product.of("Monitor", "Electronics", "179.00", true)
        );

        ProductFilterEngine engine = new ProductFilterEngine(products);

        System.out.println("=== DAY 13: PRODUCT FILTER ENGINE ===");

        // Effectively-final capture: maximumPrice is assigned only once.
        BigDecimal maximumPrice = new BigDecimal("200.00");
        String selectedCategory = "Electronics";

        Predicate<Product> affordable =
                ProductFilterEngine.priceAtMost(maximumPrice);
        Predicate<Product> correctCategory =
                ProductFilterEngine.inCategory(selectedCategory);
        Predicate<Product> available = ProductFilterEngine.inStock();

        // Predicate.and()
        Predicate<Product> electronicsDeal =
                affordable.and(correctCategory).and(available);

        System.out.println("\n1. Affordable, in-stock electronics:");
        engine.print(electronicsDeal, Main::printProduct);

        // Predicate.or()
        Predicate<Product> stationeryOrFurniture =
                ProductFilterEngine.inCategory("Stationery")
                        .or(ProductFilterEngine.inCategory("Furniture"));

        System.out.println("\n2. Stationery OR furniture:");
        engine.print(stationeryOrFurniture, Main::printProduct);

        // Predicate.negate()
        System.out.println("\n3. Out-of-stock products:");
        engine.print(available.negate(), Main::printProduct);

        // Function.andThen()
        Function<Product, String> uppercaseName =
                ProductFilterEngine.nameToUpperCase();
        Function<String, String> prefix =
                ProductFilterEngine.addProductPrefix();
        Function<Product, String> formatted =
                uppercaseName.andThen(prefix);

        System.out.println("\n4. Function.andThen():");
        engine.transform(available, formatted).forEach(System.out::println);

        // Function.compose(): trim the input first, then add brackets.
        Function<String, String> trim = String::trim;
        Function<String, String> addBrackets =
                value -> "[" + value + "]";
        Function<String, String> trimThenBracket =
                addBrackets.compose(trim);

        System.out.println("\n5. Function.compose():");
        System.out.println(trimThenBracket.apply("  demo  "));

        // Consumer<Product>
        System.out.println("\n6. Consumer<Product>:");
        Consumer<Product> printer = Main::printProduct;
        engine.print(affordable, printer);

        // Custom functional interface.
        System.out.println("\n7. Custom @FunctionalInterface:");
        PriceLabel priceLabel =
                product -> product.name() + " costs $" + product.price();
        engine.print(affordable,
                product -> System.out.println(priceLabel.format(product)));

        // Supplier<Product>
        System.out.println("\n8. Supplier<Product>:");
        Supplier<Product> supplier =
                ProductFilterEngine.defaultProductSupplier();
        System.out.println(supplier.get());

        // Static method reference.
        System.out.println("\n9. Static method reference:");
        Consumer<Product> staticReference = Main::printProduct;
        staticReference.accept(supplier.get());

        // Bound instance method reference.
        System.out.println("\n10. Bound instance method reference:");
        Consumer<String> boundReference = System.out::println;
        boundReference.accept("Printed through System.out::println");

        // Unbound instance method reference.
        System.out.println("\n11. Unbound instance method reference:");
        Function<String, String> unboundReference = String::toUpperCase;
        System.out.println(unboundReference.apply("functional programming"));

        // Constructor method reference.
        System.out.println("\n12. Constructor method reference:");
        Function<ProductSeed, Product> constructorReference = Product::new;
        Product constructed = constructorReference.apply(
                new ProductSeed(
                        "Pen", "Stationery", new BigDecimal("1.25"), true));
        System.out.println(constructed);

        // Static comparator method reference.
        System.out.println("\n13. Products sorted by price:");
        products.stream()
                .sorted(Product::compareByPrice)
                .forEach(Main::printProduct);

        System.out.println("\n14. Products sorted by price, then name:");
        products.stream()
                .sorted(ProductFilterEngine.byPriceThenName())
                .forEach(Main::printProduct);

        // Captured minimumNameLength is effectively final.
        int minimumNameLength = 5;
        Predicate<Product> longName =
                product -> product.name().length() >= minimumNameLength;

        System.out.println("\n15. Effectively-final variable capture:");
        engine.print(longName, Main::printProduct);

        System.out.println("\nDemo complete.");
    }

    private static void printProduct(Product product) {
        System.out.println(product);
    }
}
