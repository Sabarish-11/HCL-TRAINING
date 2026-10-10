# Day 6 - AI Stack Trace Notes

## Copilot Availability

GitHub Copilot Chat was checked in VS Code.
The available option was "Chat: Upgrade to GitHub Copilot Pro".
The HCL training GitHub account does not provide Copilot access.

No Copilot response was fabricated.

## Stack Trace 1 - Invalid Quantity

### Stack Trace

```text
InvalidQuantityException: Quantity must be greater than zero.
    at OrderProcessor.validateQuantity(OrderProcessor.java:32)
    at OrderProcessor.processOrder(OrderProcessor.java:7)
    at StackTraceDemo.main(StackTraceDemo.java:14)

Verified Explanation
- Exception type: InvalidQuantityException
- Type: Unchecked exception because it extends RuntimeException.
- Root cause: quantity was 0.
- Exception was thrown in validateQuantity() at line 32.
- validateQuantity() was called by processOrder() at line 7.
- processOrder() was called by StackTraceDemo.main() at line 14.
Verification Result
RIGHT - The explanation was verified against the source code.
Stack Trace 2 - Exception Chaining
Stack Trace
OrderProcessingException: Unable to process order for Monitor
    at OrderProcessor.processOrder(OrderProcessor.java:23)
    at StackTraceDemo.main(StackTraceDemo.java:16)
Caused by: InsufficientStockException: Insufficient stock for Monitor: requested 15, available 5
    at OrderProcessor.processOrder(OrderProcessor.java:10)

Verified Explanation
- Outer exception: OrderProcessingException.
- The order failed because Monitor stock was insufficient.
- The original exception is InsufficientStockException.
- Requested quantity was 15.
- Available stock was 5.
- The original cause is preserved using exception chaining.
- The Caused by: section identifies the original exception.
- The outer exception was created at OrderProcessor.java line 23.
- The original InsufficientStockException was created at line 10.
Verification Result
RIGHT - The explanation was verified against the source code.
Day 6 Concepts Verified
- Checked exception: InsufficientStockException
- Unchecked exception: InvalidQuantityException
- throw: explicitly raises an exception.
- throws: declares a checked exception in a method signature.
- Multi-catch: OrderProcessingException | InvalidQuantityException
- Exception chaining: OrderProcessingException preserves the original cause.
- finally: audit message executes after every order attempt.
- Stack traces were read from the exception through the calling methods.