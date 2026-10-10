# Day 6 - Exception Handling + AI-Assisted Debugging

## Topics Covered

- Checked vs unchecked exceptions
- `throw` vs `throws`
- Custom exceptions
- Multi-catch
- Exception chaining
- `finally` semantics
- Reading Java stack traces

## Standalone Exercise

The `order-processor` exercise contains:

- `InsufficientStockException` - checked exception
- `InvalidQuantityException` - unchecked exception
- `OrderProcessingException` - exception-chaining wrapper
- `OrderProcessor` - business validation and exception handling
- `OrderProcessorApp` - menu recovery and `finally` audit
- `StackTraceDemo` - stack-trace generation

### Business Rules

1. Quantity must be greater than zero.
2. Requested quantity must not exceed available stock.

The menu continues running after an exception and prints an audit message from `finally`.

## Stack Trace Verification

Two stack traces were generated and manually verified against the source code.

### Stack Trace 1

`InvalidQuantityException` was traced through:

`StackTraceDemo.main()` -> `OrderProcessor.processOrder()` -> `validateQuantity()`

The exception is unchecked because it extends `RuntimeException`.

### Stack Trace 2

`OrderProcessingException` preserves the original `InsufficientStockException` using exception chaining.

The `Caused by:` section identifies the original exception.

## AI-Assisted Debugging

GitHub Copilot Chat was checked in VS Code, but the HCL training GitHub account does not provide Copilot access.

No Copilot response was fabricated.

The stack-trace explanations were manually verified against the Java source code and recorded in `AI-STACKTRACE-NOTES.md`.

## Project Changes

The `auth-service` project now contains:

- `DuplicateUserException`
- `InvalidUserStateException`
- `AppUserService`

Business rules:

1. Duplicate usernames are rejected.
2. Activating an already-active user is rejected.
3. New users are initially inactive.

## Verification

Project compilation:

```text
BUILD SUCCESS

Evidence
- screenshots/01-order-processor-output.png
- screenshots/02-project-tests-pass.png
Training Requirement
Tag:
day-6-exception-handling
Note: Git tag names cannot contain :, so the valid equivalent day-6-exception-handling is used.
Key Outcome
Custom exceptions are used for business-rule violations, exception causes are preserved, errors are handled without empty catch blocks, and the existing project test suite continues to pass.