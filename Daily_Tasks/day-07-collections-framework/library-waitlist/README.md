:
# Day 7 — Collections Framework Part 1

## Topics Covered

- List
- Set
- Queue
- ArrayList
- LinkedList
- HashSet
- LinkedHashSet
- TreeSet
- ArrayDeque
- PriorityQueue
- for-each iteration
- Iterator.remove()
- ListIterator
- ConcurrentModificationException

## Library Waitlist Exercise

The `library-waitlist` exercise demonstrates appropriate collection choices.

### Collection Choices

| Collection | Purpose | Reason |
|---|---|---|
| ArrayList | Library catalogue | Allows duplicates, preserves insertion order, efficient indexed access |
| LinkedHashSet | Unique book requests | Removes duplicates while preserving insertion order |
| ArrayDeque | Normal waitlist | Efficient FIFO queue operations |
| PriorityQueue | Priority requests | Processes faculty requests before student requests |
| HashSet | Unique technology names | Prevents duplicates without guaranteeing order |
| TreeSet | Sorted unique technology names | Prevents duplicates and maintains sorted order |

### Iteration

- Enhanced `for-each` is used for simple traversal.
- `Iterator.remove()` safely removes overdue books during iteration.
- `ListIterator` supports forward and backward traversal.
- Direct structural modification during enhanced `for-each` can cause `ConcurrentModificationException`.

## ConcurrentModificationException

The exercise intentionally demonstrates unsafe modification of an `ArrayList`
during enhanced `for-each` iteration.

The corrected demonstration produces:

```text
===== CONCURRENT MODIFICATION DEMO =====
Caught: ConcurrentModificationException

ArrayList vs LinkedList Timing
Test configuration:
- List size: 100,000
- Indexed accesses: 10,000
- Measurement: System.nanoTime()
Results from the captured run:
Operation	ArrayList (ns)	LinkedList (ns)
Insert	4,954,000	2,556,300
get(i)	971,400	305,120,800


Observation
LinkedList was faster for sequential insertion in this particular run.
ArrayList was dramatically faster for indexed get(i) access.
Timing results depend on JVM state, hardware, allocation, and workload, so
these values are observations from this run rather than universal constants.
Files
- library-waitlist/LibraryWaitlist.java
- library-waitlist/CollectionTiming.java
- library-waitlist/CollectionVariants.java
- library-waitlist/TIMING.md
- screenshots/01-library-waitlist-output.png
- screenshots/02-collection-timing.png
Verification
Standalone programs were compiled and executed successfully with Java 21.
Verified:
- Library catalogue
- Duplicate removal
- FIFO waitlist
- Faculty-first priority processing
- Iterator-based overdue removal
- Concurrent modification handling
- HashSet
- TreeSet
- ListIterator forward/backward traversal
- ArrayList vs LinkedList timing

## JFS-28 Project — FR1–FR3

The project does not contain a pre-existing FR1–FR3 specification, so the
Day 7 collection requirements were defined around the existing `AppUser`
domain.

### FR1 — User List

Users are stored in:

```java
List<AppUser>

ArrayList is used because the collection maintains an ordered in-memory
user list and supports efficient indexed access.
FR2 — Unique Usernames
Usernames are maintained in:
Set<String>

HashSet is used to guarantee uniqueness.
FR3 — Activation Queue
Users waiting for activation are maintained in:
Deque<AppUser>

ArrayDeque is used as a FIFO queue.
The next activation request is processed with:
pollFirst()

Project Verification
CollectionServiceTest verifies:
- Users are stored in the List.
- Usernames are stored uniquely in the Set.
- Activation requests are processed in FIFO order.
Final project test result:
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS

Project Files
Project/kanban-project/auth-service/
├── src/main/java/auth_service/service/CollectionService.java
└── src/test/java/auth_service/service/CollectionServiceTest.java

Evidence:
screenshots/03-project-tests-pass.png