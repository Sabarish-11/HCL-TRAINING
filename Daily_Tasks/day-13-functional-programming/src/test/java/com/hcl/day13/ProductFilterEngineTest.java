package com.hcl.day13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class ProductFilterEngineTest {
    private ProductFilterEngine engine;

    @BeforeEach
    void setUp() {
        engine = new ProductFilterEngine(List.of(
                Product.of("Mouse", "Electronics", "19.99", true),
                Product.of("Keyboard", "Electronics", "49.50", false),
                Product.of("Notebook", "Stationery", "2.50", true),
                Product.of("Chair", "Furniture", "129.00", true)
        ));
    }

    @Test
    void composedPredicatesFilterByPriceCategoryAndStock() {
        Predicate<Product> predicate =
                ProductFilterEngine.priceAtMost(new BigDecimal("30"))
                        .and(ProductFilterEngine.inCategory("Electronics"))
                        .and(ProductFilterEngine.inStock());

        assertEquals(
                List.of("Mouse"),
                engine.filter(predicate).stream().map(Product::name).toList()
        );
    }

    @Test
    void predicateOrAndNegateWork() {
        Predicate<Product> predicate =
                ProductFilterEngine.inCategory("Stationery")
                        .or(ProductFilterEngine.inCategory("Furniture"));

        assertEquals(2, engine.filter(predicate).size());
        assertEquals(
                1,
                engine.filter(ProductFilterEngine.inStock().negate()).size()
        );
    }

    @Test
    void functionTransformsFilteredProducts() {
        List<String> descriptions = engine.transform(
                ProductFilterEngine.inStock(),
                ProductFilterEngine.productDescription()
        );

        assertEquals(3, descriptions.size());
        assertTrue(descriptions.contains("Mouse (Electronics)"));
    }

    @Test
    void functionAndThenUppercasesAndAddsPrefix() {
        Function<Product, String> function =
                ProductFilterEngine.nameToUpperCase()
                        .andThen(ProductFilterEngine.addProductPrefix());

        Product mouse =
                engine.filter(p -> p.name().equals("Mouse")).get(0);

        assertEquals("Product: MOUSE", function.apply(mouse));
    }

    @Test
    void functionComposeAppliesTrimBeforeBracketing() {
        Function<String, String> trim = String::trim;
        Function<String, String> brackets = value -> "[" + value + "]";

        assertEquals("[hello]", brackets.compose(trim).apply("  hello  "));
    }

    @Test
    void consumerReceivesEveryMatchingProduct() {
        AtomicInteger count = new AtomicInteger();

        engine.print(
                ProductFilterEngine.inStock(),
                product -> count.incrementAndGet()
        );

        assertEquals(3, count.get());
    }

    @Test
    void supplierCreatesDefaultProduct() {
        Product product =
                ProductFilterEngine.defaultProductSupplier().get();

        assertEquals("Notebook", product.name());
        assertTrue(product.inStock());
        assertEquals(0, product.price().compareTo(new BigDecimal("2.50")));
    }

    @Test
    void customFunctionalInterfaceFormatsProduct() {
        PriceLabel label =
                product -> product.name() + ":" + product.price();

        Product mouse =
                engine.filter(p -> p.name().equals("Mouse")).get(0);

        assertEquals("Mouse:19.99", label.format(mouse));
    }

    @Test
    void constructorMethodReferenceCreatesProducts() {
        Product product = ProductFilterEngine.createProducts(List.of(
                new ProductSeed(
                        "Pen", "Stationery", new BigDecimal("1.25"), true)
        )).get(0);

        assertEquals("Pen", product.name());
        assertEquals("Stationery", product.category());
    }

    @Test
    void constructorCopiesInputList() {
        ArrayList<Product> source = new ArrayList<>();
        source.add(Product.of("Mouse", "Electronics", "19.99", true));

        ProductFilterEngine copied = new ProductFilterEngine(source);
        source.clear();

        assertEquals(1, copied.filter(product -> true).size());
    }

    @Test
    void rejectsNegativePrices() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Product.of("Bad product", "Test", "-1.00", true)
        );
    }

    @Test
    void rejectsNullProductList() {
        assertThrows(
                NullPointerException.class,
                () -> new ProductFilterEngine(null)
        );
    }
}
