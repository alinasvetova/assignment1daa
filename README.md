# Algorithmic Complexity and Performance Analysis (Assignment 1 DAA)

This project is dedicated to the empirical analysis and comparison of several fundamental Divide & Conquer (D&C) algorithms by measuring their performance metrics (runtime, operation count, recursion depth) against varying input sizes ($N$). The core objective is to validate their observed time complexity against their theoretical $O$-notation.

## Algorithms Implemented

The project includes four distinct algorithms, all implemented with a focus on D&C principles:

1.  **MergeSort:**
    * Guaranteed $O(N \log N)$ complexity.
    * **Implementation Note:** Uses a small-n cutoff to switch to Insertion Sort for improved efficiency on small subarrays.

2.  **QuickSort (Robust Implementation):**
    * Expected average-case $O(N \log N)$, worst-case $O(N^2)$.
    * **Implementation Note:** Employs strategies such as random pivot selection and handling the smaller recursive call first to maintain a stable stack depth $\approx O(\log N)$.

3.  **SelectMoM5 (Deterministic Select):**
    * Guaranteed worst-case $O(N)$ linear time complexity.
    * **Implementation Note:** Implements the Median-of-Medians strategy (groups of 5) to ensure the pivot selection maintains the linear time bound.

4.  **ClosestPair (2D Points):**
    * $O(N \log N)$ complexity.
    * **Implementation Note:** Follows the classic D&C approach involving sorting by $x$, recursive split, and a linear-time strip check (guaranteed few neighbor checks).

## Metrics and Data Collection

For each run, the program automatically collects metrics and appends them to a CSV file for plotting and analysis.

### Collected Metrics
* **Execution Time (ns)**: Total runtime in nanoseconds.
* **Max Recursion Depth**: Maximum depth reached during execution.
* **Number of Key Comparisons**: Count of logical comparison operations.
* **Logical Allocations**: Count of significant buffer/array allocations (e.g., merge buffer).

### CSV Header
All results are appended to the output file using the following header structure:
`Algorithm, N, Trial, TimeNs, Comparisons, Allocations, MaxDepth`

## Project Architecture Notes (Criteria Requirement)

The project is structured according to **standard Maven conventions** (`src/main/java`, `src/test/java`). This design was chosen for its portability and compliance with professional build standards.

* **Recursion Depth Control:** The **QuickSort** implementation guarantees a bounded stack depth of $O(\log N)$ (typical case) by always recurring on the smaller partition and iterating on the larger one. The **DepthTracker** utility explicitly monitors and caps this metric.
* **Allocation Control:** The **MergeSort** implementation uses a **reusable auxiliary buffer** to avoid repeated memory allocation inside the recursive calls, significantly reducing the "Logical Allocations" count and mitigating GC overhead.

## Analysis (Theoretical Complexity)

| Algorithm | Recurrence Relation (Intuition) | Theoretical Complexity | Master Theorem Case |
| :--- | :--- | :--- | :--- |
| **MergeSort** | $T(n)=2T(n/2)+\Theta(n)$ | $\Theta(n\log n)$ | Case 2 |
| **QuickSort** | (Not standard Master) | Expected $\Theta(n\log n)$ | Akra–Bazzi / Substitution |
| **Select (MoM5)** | $T(n) \le T(n/5)+T(7n/10)+\Theta(n)$ | $\Theta(n)$ | None (Substitution) |
| **Closest Pair** | $T(n)=2T(n/2)+\Theta(n)$ | $\Theta(n\log n)$ | Case 2 |

---

## Setup, Build, and Run Instructions

### Requirements
* **Java Development Kit (JDK) 23** or later.
* **Maven** (The project is configured to be built via the Maven `pom.xml` file).

### Testing (JUnit 5)

Algorithm correctness is verified using dedicated JUnit 5 test classes located in the `src/test/java` directory.

To run all tests using the Maven build tool (as required by the assignment criteria):
```bash
mvn test
Building the Executable JAR

The project must be built using Maven to create the final executable JAR:

Clean and Package: Execute the Maven lifecycle goals from the project root directory:

Bash
mvn clean package
Location: The executable JAR is created in the standard Maven target directory: target/assignment1daa-1.0-SNAPSHOT.jar.

Command Line Interface (CLI)

The main class main.cli.Main is run via the generated Maven JAR file using positional arguments:

Bash
java -jar target/assignment1daa-1.0-SNAPSHOT.jar <N> <Algorithm> [<Trials>]
Parameter	Description
<N>	The size of the input data (or points).
<Algorithm>	Algorithm name: MergeSort, QuickSort, SelectMoM5, or ClosestPair.
[<Trials>]	Number of times to repeat the test (e.g., 10).
Example Runs

Run benchmarks from the project's root directory after successful compilation:

Bash
# Running QuickSort for N=100000, 10 trials
java -jar target/assignment1daa-1.0-SNAPSHOT.jar 100000 QuickSort 10

# Running Closest Pair for N=10000, 10 trials
java -jar target/assignment1daa-1.0-SNAPSHOT.jar 10000 ClosestPair 10
Output

All collected metrics are appended to the CSV file located in the project's root directory:
Output File: metrics_results.csv