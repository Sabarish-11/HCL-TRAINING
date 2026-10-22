# A1 Core Java Assessment — Authentication Service

A plain Java 21 console application implementing two authentication-service domain flows in memory:

1. Register a user account (standard or admin), rejecting duplicate usernames.
2. Activate a registered account, rejecting missing or already-active accounts.

No Spring and no database are used. The project is a learning exercise, not a production authentication system.

## Run

From this folder:

```powershell
mvn test
mvn package
java -cp target\classes com.hcl.auth.Main
```

If the `exec:java` command is unavailable because the Maven Exec plugin is not configured, run the packaged classes with your IDE or add the Exec plugin. The simplest guaranteed workflow is to open `Main.java` in VS Code and run it after Maven compilation.

## Rubric mapping

- **OOP:** `UserAccount` is abstract and encapsulates account state; `StandardUser` and `AdminUser` override `getAccountType()`.
- **Interfaces/polymorphism:** `Activatable`, `PasswordHasher`, and generic `Repository<T, ID>` are interfaces. `UserService` depends on `UserRepository` and `PasswordHasher`.
- **Checked exception:** `DuplicateUsernameException`.
- **Unchecked exceptions:** `UserNotFoundException`, `AccountAlreadyActiveException`, and `InvalidUserDataException`.
- **Collections:** `LinkedHashMap` stores entities in insertion order; `HashMap` indexes usernames; `HashSet` enforces normalized username uniqueness; `ArrayDeque` maintains FIFO pending activations; `List` provides snapshots and sorted views.
- **Generics:** `Repository<T extends Identifiable<ID>, ID>` is a bounded generic repository.
- **Sorting:** `UserAccount` implements `Comparable`; `UserService` also uses a `Comparator` for account-type/username sorting.
- **Safe collection handling:** repository methods return immutable snapshots with `List.copyOf`; service sorts a separate copy.
- **Resource handling:** console input uses try-with-resources.
- **Tests:** JUnit 5 tests cover successful registration/activation and edge cases.

## Days 1–11 integration map

The original daily exercises remain in their own folders. This assessment reuses relevant concepts in one coherent authentication workflow rather than copying unrelated exercises into the application.

| Day | Concept carried into this app | Where to demonstrate it |
| --- | --- | --- |
| 1 | Java platform and runtime | `pom.xml`: Java 21 compiler release |
| 2 | Constants, types, and validation | `Pbkdf2PasswordHasher` constants; `UserService.validateRegistration` |
| 3 | Control flow and console interaction | `Main`: menu loop, switch, input parsing, error handling |
| 4 | Encapsulation and service/model separation | `UserAccount`, `UserService` |
| 5 | Inheritance, polymorphism, interfaces | `StandardUser`, `AdminUser`, `UserAccount`, `Activatable`, `PasswordHasher` |
| 6 | Exception handling | `DuplicateUsernameException`, `InvalidUserDataException`, `UserNotFoundException`, `AccountAlreadyActiveException` |
| 7 | Collections and safe iteration | `InMemoryRepository`, `InMemoryUserRepository`: `List`, `Map`, `Set`, `Queue` |
| 8 | Natural ordering and custom sorting | `UserAccount.compareTo`; `UserService.listUsersSorted` |
| 9 | Generics and bounded type parameters | `Repository<T extends Identifiable<ID>, ID>` |
| 10 | Memory and sensitive-data handling | Password-array clearing in `UserService`; immutable collection snapshots |
| 11 | Testing and AI-assisted review | `UserServiceTest`; surrounding Day 11 review notes |

The memory-management labs from Day 10 are not embedded in the authentication workflow because deliberate heap exhaustion and garbage-collection demonstrations do not belong in a normal registration/activation flow. The relevant memory-awareness lessons are documented and applied instead.
## Memory awareness

Local variables and method-call references are associated with stack frames; objects such as accounts, maps, sets, and queues are allocated on the heap. A common memory pitfall is retaining unnecessary objects in long-lived collections. This demo intentionally retains registered accounts for the lifetime of the application and uses immutable list snapshots when returning collections.

## Password caveat

The demo uses PBKDF2 via the Java standard library to avoid storing a raw password. This is educational code only; production authentication also needs a reviewed security design, rate limiting, secret handling, password-reset flows, and a vetted framework/library.

## AI review evidence

See `../../ai-code-review.md` and `../../AI-USAGE.md` in the surrounding Day 11 folder for the prompt comparison and human-review notes. Explain at least one concrete AI-generated-code flaw and the fix during the assessment.
