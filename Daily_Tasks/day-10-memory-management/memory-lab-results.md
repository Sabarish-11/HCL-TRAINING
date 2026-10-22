Day 10 — Memory Management Lab

1. Stack Overflow Experiment

File: StackOverflowLab.java

Run

Recursive calls before overflow

Default JVM settings

16,186

-Xss256k

2,550

-Xss1m

16,510

What I learned: Each recursive method call uses a stack frame. A smaller thread stack generally allows fewer recursive calls before StackOverflowError occurs. The exact number of calls can vary with the JVM and runtime environment.

2. Heap Exhaustion Experiment

File: HeapExhaustionLab.java

Command: java -Xmx64m HeapExhaustionLab

Observed result:

The program reported allocations up to approximately 24 MiB.

It then threw java.lang.OutOfMemoryError: Java heap space.

The reported requested allocation total was approximately 31 MiB.

What I learned: -Xmx64m sets the maximum Java heap size; it does not mean all 64 MiB are available for my byte arrays. The JVM and other objects also consume heap memory. Because the list retained references to the arrays, those arrays remained reachable and could not simply be reclaimed by garbage collection.

3. String Concatenation Experiment

File: StringPerformanceLab.java

Iterations: 30,000 characters

Method

Measured time

String +=

97.706 ms

StringBuilder

0.883 ms

Observed comparison: StringBuilder was approximately 111 times faster in this timed run.

What I learned: Strings are immutable. Repeated concatenation in a loop can create many intermediate objects and waste time. StringBuilder uses a mutable buffer and is generally more efficient for repeated concatenation. Timing depends on the JVM, hardware, and system load, so this is an illustrative result rather than a universal benchmark.

4. Garbage Collection Logging Experiment

File: GcLoggingLab.java

Command: java -Xms16m -Xmx64m -Xlog:gc GcLoggingLab

Observed results:

The JVM reported that it was using the G1 garbage collector.

The logs showed Young GC pauses and concurrent marking activity.

One example was 24M->0M(42M), indicating that reported heap occupancy dropped from 24 MiB to 0 MiB after that collection.

All 12 allocation batches completed, followed by GC logging experiment complete.

What I learned: Garbage collection can reclaim objects that are no longer reachable. In this experiment, previous batches became eligible for collection as the loop advanced. G1 decides when and how to collect; the application does not control the exact collection timing.

5. Integer Cache Experiment

File: IntegerCacheLab.java

Observed output:

127 == 127: true
128 == 128: false
128 equals(): true
127 values equal: true

What I learned: For Integer objects, == checks whether two references point to the same object, whereas .equals() checks whether their numeric values are equal. With standard default JVM settings, small boxed integer values are normally cached, which explains the result for 127. The two boxed 128 values in this experiment were different objects.

Key Concepts

Stack and Heap

Stack: Holds method call frames and local execution state for each thread. Recursive calls can exhaust the available stack.

Heap: Holds objects and arrays, including objects created with new. Objects remain reachable while live references lead to them.

Reachability

An object is eligible for garbage collection when it is no longer reachable from the program's live references and other GC roots. Eligibility does not guarantee immediate collection.

Young and Old Generations

Many garbage collectors organize objects by age or collection region. Young collections focus on recently allocated objects; older objects may be retained or handled by different collection phases. G1 uses regions and may perform concurrent marking rather than following a simple fixed young/old layout.

Common Memory Pitfalls

Unbounded static collections can retain objects for the lifetime of the class loader.

Unclosed files, streams, and database resources can waste external resources.

Repeated String concatenation inside loops can create unnecessary temporary objects.

Incorrect use of == with wrapper objects can produce unexpected comparisons.

Retaining unnecessary references can prevent otherwise useful objects from being collected.

Conclusion

These experiments helped me understand the difference between stack and heap memory, how object reachability affects garbage collection, why repeated string concatenation can be inefficient, and why wrapper values should usually be compared with .equals() rather than ==.