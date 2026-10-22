# Day 13 — Functional Programming

## Objective

Build a Java Product Filter Engine using lambdas, built-in functional interfaces, predicate composition, function composition, method references, and a custom functional interface.

## Concepts demonstrated

- `Predicate<Product>`: price, category, and in-stock filtering.
- Predicate composition: `and()`, `or()`, and `negate()`.
- `Function<Product, String>`: product-name transformation.
- Function composition: `andThen()` and `compose()`.
- `Consumer<Product>`: printing filtered products.
- `Supplier<Product>`: supplying a default product.
- Custom `@FunctionalInterface`: `PriceLabel`.
- Effectively-final variable capture in a lambda.
- Four method-reference types:
  1. Static method: `Main::printProduct`
  2. Bound instance method: `System.out::println`
  3. Unbound instance method: `String::toUpperCase`
  4. Constructor reference: `Product::new`
- Comparator method references.
- Immutable product records and defensive copying of the product list.
- Unit tests using JUnit 5.

## Requirements

- Java 21
- Maven

Check your versions:

```powershell
java -version
mvn -version
```

## Run tests and package

From this project directory:

```powershell
mvn test
mvn package
```

## Run the demonstration

After packaging:

```powershell
java -cp target\classes com.hcl.day13.Main
```

## Project structure

```text
day-13-functional-programming/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/hcl/day13/
    │   ├── Product.java
    │   ├── ProductSeed.java
    │   ├── PriceLabel.java
    │   ├── ProductFilterEngine.java
    │   └── Main.java
    └── test/java/com/hcl/day13/
        └── ProductFilterEngineTest.java
```

## Review checklist

- [x] Filter products by price, category, and stock.
- [x] Compose predicates using `and`, `or`, and `negate`.
- [x] Transform product names using functions.
- [x] Demonstrate `andThen` and `compose`.
- [x] Print products with a consumer.
- [x] Create a default product with a supplier.
- [x] Capture effectively-final local variables.
- [x] Demonstrate all four method-reference types.
- [x] Define a custom functional interface.
- [x] Test important behavior and validation.

## Integration note

The Day 12 authentication project already uses the `PasswordHasher` strategy abstraction. A lambda is suitable for simple test doubles, but the PBKDF2 implementation should remain a named class because it contains non-trivial hashing and resource-management logic. Do not replace meaningful control flow with lambdas just to remove `if` or `switch` statements.
