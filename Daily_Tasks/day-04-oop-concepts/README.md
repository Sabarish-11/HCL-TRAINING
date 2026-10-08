# Day 4 - OOP Concepts + IDE & Debugging

## Objective

Practice Java OOP concepts and debugger-based bug fixing.

## Topics Covered

- Classes and objects
- Fields and methods
- Constructors
- Constructor overloading and chaining
- `this` and `super`
- Access modifiers
- Packages
- Encapsulation
- Static vs instance members
- `equals()` and `hashCode()`
- Debugging with conditional breakpoints
- Variable inspection
- Hot Code Replace

## BankAccount Implementation

The `BankAccount` class contains:

- Private fields for account data
- Static account counter
- Three chained constructors
- Deposit validation
- Withdrawal validation
- `equals()` and `hashCode()`
- `toString()`

Package structure:

```text
src/main/java
├── app
│   └── BankAccountApp.java
├── model
│   └── BankAccount.java
└── service
    └── BankAccountService.java
Debugging Exercise

An intentional bug was introduced in withdraw():

balance += amount;

This caused a withdrawal of INR 1500 from a balance of INR 12000 to produce INR 13500.

A conditional breakpoint was used with:

amount > 0

The debugger was used to inspect:

balance = 12000.0
amount = 1500.0

The bug was identified and corrected using Hot Code Replace:

balance -= amount;

The corrected result was:

Balance = 10500.0
Evidence

Screenshots are available in the screenshots folder:

01-debugger-found-bug.png
02-debugger-hot-replace-fixed.png
Verification

The application was compiled and executed successfully.

equals() returned true for accounts with the same account number, and both equal objects produced the same hash code.