# AI Usage Log — Day 11

## Purpose

This document records how AI coding assistance was used during the Core Java training tasks, along with the review and verification performed before accepting generated code.

## Entry 1 — Student POJO with a vague prompt

- **Prompt:** "Write a Java Student class."
- **Output:** Generated a class with `id`, `name`, and `age`, a constructor, getters, setters, and `toString()`.
- **Review:** The result was a useful starting point but did not validate inputs or implement `equals()` and `hashCode()`.
- **Action:** Saved it as `StudentVague.java` for comparison. The missing features were recorded as omissions relative to the later requirements, not automatically as defects.

## Entry 2 — Student POJO with a specific prompt

- **Prompt:** Requested a `StudentSpecific` class with a positive immutable ID, validated name and age, consistent equality methods, and `toString()`.
- **Output:** Generated a class with constructor and setter validation, a final ID, and `equals()`/`hashCode()` based on ID.
- **Review:** Checked that the validation rules were applied consistently and that equality and hashing used the same field.
- **Action:** Saved as `StudentSpecific.java`. Confirmed that ID-based equality is appropriate if IDs uniquely identify students.

## Entry 3 — StudentService generation and review

- **Prompt:** Requested a simple service using an `ArrayList`, with add, list, find-by-ID, and remove-by-ID methods.
- **Output:** Generated `StudentService.java`.
- **Review:** Examined null handling, duplicate IDs, missing-result behavior, collection exposure, ID validation, and thread-safety assumptions.
- **Action:** Updated the service to reject duplicate IDs, return an unmodifiable snapshot, use `Optional` for lookup, and validate IDs. Documented thread safety as a conditional concern rather than claiming it had been fixed.

## Verification and responsibility

AI-generated code was treated as a draft rather than automatically trusted. I reviewed the code against the stated requirements and documented the changes. Further compilation and tests are still needed to verify the final implementation.

## Key lesson

Specific prompts usually communicate requirements more clearly than vague prompts, but neither guarantees correct code. Human review and testing remain necessary.
