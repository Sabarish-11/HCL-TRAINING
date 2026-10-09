# Day 5 — Inheritance & Polymorphism

## Topics Covered

- Inheritance
- `extends`
- `super`
- Method overriding
- `@Override`
- Method overloading
- Abstract classes
- Interfaces
- Runtime polymorphism
- Git branching
- Pull Requests
- Merging

## Payment Hierarchy

The payment exercise contains:

- `Payment` — abstract base class
- `CardPayment`
- `UpiPayment`
- `CashPayment`
- `Refundable` — interface
- `PaymentApp` — demonstration program

## Concepts Demonstrated

### Inheritance

`CardPayment`, `UpiPayment`, and `CashPayment` extend `Payment`.

### Method Overriding

Each payment type overrides the `pay()` method.

### Method Overloading

`Payment` provides multiple `pay()` methods with different parameters.

### Runtime Polymorphism

A `Payment` reference can refer to different payment implementations.

### Interface

`CardPayment` implements the `Refundable` interface.

## Verification

The program was compiled using:

```text
javac *.java

and executed using:

java PaymentApp

The program completed successfully and demonstrated runtime polymorphism, method overloading, and the Refundable interface.

Project Task

The Spring Boot project was extended with:

BaseEntity
Role hierarchy
Strategy interface
Two Strategy implementations

The changes were developed on:

feature/day-5-inheritance-strategy

and merged into main through a GitHub Pull Request.

Evidence
screenshots/01-payment-hierarchy-output.png
UML.md
Add-Content "Daily_Tasks\day-05-inheritance-polymorphism\README.md" "`nRebase demonstration prepared during Day 5 Git practice."