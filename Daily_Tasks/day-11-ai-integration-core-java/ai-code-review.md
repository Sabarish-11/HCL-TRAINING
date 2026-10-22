# Day 11 — AI-Generated Code Review

## 1. Prompt Comparison

| Aspect | Vague prompt | Specific prompt |
|---|---|---|
| Prompt | "Write a Java Student class." | Specified fields, validation, immutable ID, equality, and error handling. |
| Validation | No input validation generated. | Validates ID, name, and age. |
| Encapsulation | Private fields, but all fields are mutable through setters. | Private fields; ID is immutable. |
| Equality | `equals()` and `hashCode()` omitted. | Both use ID consistently. |
| Result | A basic starting point requiring further requirements and review. | Better aligned with the requested requirements, but still needs human review. |

## 2. StudentService Review

**Original file:** `StudentService.java`

**Reviewed version:** `StudentService.java`, after fixes

| # | Finding | Classification | Fix or decision |
|---:|---|---|---|
| 1 | `getAllStudents()` returns a mutable copy. | Design improvement, not an internal-list leak. | Changed to `List.copyOf()` to provide an unmodifiable snapshot. |
| 2 | `findStudentById()` returns `null` when no match exists. | Potential null-handling defect for callers. | Changed the return type to `Optional<StudentSpecific>`. |
| 3 | `addStudent()` allows duplicate IDs. | Genuine requirement-dependent defect. | Added a duplicate-ID check. |
| 4 | Search and removal accept non-positive IDs. | Input-validation gap. | Added positive-ID validation. |
| 5 | The service is not thread-safe. | Conditional concurrency concern. | Documented as a limitation; synchronization was not added because concurrent access is not established as a requirement. |
| 6 | Returned student objects were mutable through setters, allowing callers to alter stored service data. | Encapsulation defect. | Made StudentSpecific immutable by declaring fields final and removing setters; recompiled and reran tests successfully. |

**Review note:** These findings are based on the generated code and the stated design requirements. The mutable copy in finding 1 does not expose the service's internal list, and lack of thread safety is only a defect if concurrent access is required. Avoid describing either as an unconditional bug.

## 3. Additional Review Checklist

- [x] Encapsulation — fields are private.
- [x] Returned internal collections — the original returned a copy, not the internal list; the revised version makes the snapshot unmodifiable.
- [x] `equals()` and `hashCode()` — checked in `StudentSpecific`; both use `id`.
- [x] Null handling — student input is checked and missing lookups use `Optional`.
- [x] Collection choice — `ArrayList` is suitable for a simple ordered in-memory service; lookup by ID is linear.
- [x] Exceptions — invalid input is rejected rather than silently ignored.
- [ ] Thread safety — not implemented; consider it if concurrent use becomes a requirement.

## 4. Conclusion

AI-generated code can provide a useful starting point, but a prompt and generated output do not establish correctness. Reviewing requirements, edge cases, API behavior, collection semantics, and concurrency assumptions is necessary before accepting the code.


## Test Evidence

Compiled StudentVague.java, StudentSpecific.java, StudentService.java,
and StudentServiceTest.java successfully using javac.

Executed StudentServiceTest successfully.

Results:
- PASS: Duplicate ID rejected
- PASS: Returned list is unmodifiable
- PASS: Invalid ID rejected
- PASS: All checks passed

Remaining concern:
List.copyOf protects the returned list structure, but StudentSpecific
objects remain mutable through their setters. The service does not fully
protect its internal student state from object mutation.
