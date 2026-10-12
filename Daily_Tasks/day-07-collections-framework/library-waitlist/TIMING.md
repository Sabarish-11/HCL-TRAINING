# Collection Timing Comparison

## Test Configuration

- List size: 100,000
- Indexed accesses: 10,000
- Measurement: `System.nanoTime()`

## Results

| Operation | ArrayList (ns) | LinkedList (ns) |
|---|---:|---:|
| Insert | 4,954,000 | 2,556,300 |
| `get(i)` | 971,400 | 305,120,800 |

## Observation

In this benchmark, LinkedList completed sequential insertion faster than
ArrayList.

ArrayList was dramatically faster for indexed `get(i)` access.

This demonstrates why ArrayList is generally preferred when frequent
indexed access is required, while LinkedList can be useful for workloads
that perform frequent insertion/removal through linked positions.

> Timing results depend on JVM state, hardware, allocation, and workload,
> so these numbers are an observation from this particular run rather than
> universal performance constants.