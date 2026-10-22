Day 10 — Project Memory Review

1. Reviewed Class

File: Project/kanban-project/auth-service/src/main/java/auth_service/service/AppUserService.java

Method reviewed: registerUser(AppUser user)

This method checks whether a username already exists, marks a new user as inactive, and saves the user through the repository.

2. Stack and Heap Diagram

CURRENT THREAD — STACK
┌────────────────────────────────────┐
│ registerUser(AppUser user) frame   │
│                                    │
│ user ──────────────────────────────┼────┐
│ temporary lookup result            │    │
│ method execution state             │    │
└────────────────────────────────────┘    │
                                          ▼
HEAP
┌────────────────────────────────────┐
│ AppUser object                     │
│ username, active status, etc.      │
└────────────────────────────────────┘

┌────────────────────────────────────┐
│ AppUserService instance            │
│ userRepository reference ──────────┼───► Repository/proxy
└────────────────────────────────────┘

Temporary lookup objects, such as Optional,
may also be created during the repository call.

Explanation: The method's local parameter user is a reference held in the current stack frame. The AppUser object it refers to normally resides on the heap. The service holds a reference to its repository dependency. Temporary objects may become eligible for garbage collection when they are no longer reachable.

3. Memory Pitfall Review

Potential pitfall

Observation

Unbounded static collections

None found in the reviewed class.

Repeated string concatenation

No concatenation loop. A short error message is created only when a duplicate username is detected.

Unclosed resources

The method does not directly open files, streams, or sockets.

Unnecessary object retention

No obvious long-lived collection or growing cache is visible in this class.

Exception allocation

Exceptions are created on error paths. This is normal and is not a memory issue by itself.

4. Findings

The reviewed AppUserService class does not show an obvious memory-management problem. Its repository dependency is a normal service dependency, and the method does not visibly retain an unbounded amount of data.

This is a preliminary review of one class only. The repository implementation, entity mappings, database queries, and other service classes still need to be checked before drawing conclusions about the whole project.

5. Recommendations

Use pagination when queries may return large numbers of database records.

Avoid retaining large collections or entity objects longer than necessary.

Close manually managed resources using try-with-resources where applicable.

Inspect repository queries and entity relationships for unnecessary loading or caching.

Use profiling tools and garbage-collection logs if the application shows unusually high memory usage.

6. Day 10 Conclusion

I learned how stack frames and heap objects differ, how garbage collection reclaims unreachable objects, and how memory problems can occur through excessive allocation or unnecessary object retention. The standalone labs demonstrated stack overflow, heap exhaustion, string-building performance, GC logging, and the difference between == and .equals() for Integer objects.

## 7. Additional Review — CollectionService

**File:** `Project/kanban-project/auth-service/src/main/java/auth_service/service/CollectionService.java`

### Findings

- `users` is an `ArrayList` that retains references to all added users.
- `usernames` is a `HashSet` that retains unique username strings.
- `activationQueue` is an `ArrayDeque` that stores users waiting for activation.
- `processNextActivation()` removes the next user from the queue, allowing the queue to release its reference to that user. However, the `users` list still retains the user.
- `getUsers()` and `getUsernames()` return unmodifiable copies. Creating these snapshots can temporarily increase memory usage, especially for large collections.
- If this Spring-managed service continuously receives new users, its collections may grow without a defined limit.

### Recommendations

1. Confirm whether keeping every user in memory is a functional requirement.
2. If only pending activations are needed, remove processed entries from the queue and avoid maintaining unnecessary duplicate references.
3. If the service must retain all users, consider a bounded retention policy or database-backed storage where appropriate.
4. For large collections, consider the memory cost of returning complete snapshots.

**Conclusion:** The main potential memory concern is unbounded collection growth and duplicate references to user objects. Whether this is a real defect depends on the intended application behavior.

## 8. Additional Review — MapService

**File:** `Project/kanban-project/auth-service/src/main/java/auth_service/service/MapService.java`

### Findings

- `usersByUsername` is an instance-level `HashMap` that retains username keys and references to `AppUser` objects.
- Adding a new username increases the map's size. Adding an existing username replaces its mapped value.
- `getSortedUserReport()` creates a new `TreeMap` and status strings each time it runs.
- `getStatusCounts()` creates a small map containing active and inactive user counts.
- `getUsersSortedByUsernameThenEmail()` creates a temporary `ArrayList`, sorts its entries, and returns an unmodifiable snapshot.
- Large maps and repeated full-list snapshots may increase memory consumption.
- No obvious resource leak or repeated string-concatenation loop was found in this class.

### Recommendations

1. Confirm whether all users need to remain in the in-memory map indefinitely.
2. Consider database-backed lookups or pagination if the number of users becomes large.
3. Avoid repeatedly generating complete reports when a smaller or cached result would meet the requirements.
4. Consider the memory cost of returned snapshots when callers retain them.

**Conclusion:** The main potential concern is unbounded map growth. Report generation and sorting also allocate temporary objects, which may be acceptable for small collections but should be evaluated for larger datasets.

## 9. Overall Project Memory Review

The three reviewed service classes show different memory behaviors:

- `AppUserService`: no obvious memory issue in the reviewed methods.
- `CollectionService`: retains user objects in multiple collections, which may grow over time.
- `MapService`: retains users in a map and allocates temporary report and sorting collections.

These findings are based on code inspection, not a memory profile. The collection sizes, application requirements, and actual runtime behavior should be considered before making changes.
