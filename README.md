
# Algorithmic Complexity and Performance Analysis (Assignment 1 DAA)

This project implements and empirically evaluates the performance of several classic algorithms against various input sizes ($N$). The goal is to compare the observed runtime complexity with the theoretical big-O notation.

## Algorithms Implemented

The following algorithms are implemented in Java, primarily focused on sorting, selection, and computational geometry:

1.  **MergeSort:** Comparison-based sorting algorithm ($O(N \log N)$ worst-case).
2.  **QuickSort:** Comparison-based sorting algorithm ($O(N \log N)$ average-case, $O(N^2)$ worst-case).
3.  **SelectMoM5 (Median of Medians):** Deterministic selection algorithm for finding the $k$-th smallest element ($O(N)$ worst-case).
4.  **ClosestPair:** Algorithm for finding the minimum distance between two points in a 2D plane ($O(N \log N)$).

## Project Structure

The codebase is organized into logical packages:

```

src/
├── main/
│   ├── cli/             (Main entry point for command-line execution)
│   ├── geometry/        (ClosestPair, Point)
│   ├── metrics/         (Metrics tracking and CSV output: Metrics, CsWriter)
│   └── sorts/           (MergeSort, QuickSort, SelectMoM5)
└── test/
├── ClosestPairTest  (JUnit tests for ClosestPair)
└── ...              (JUnit tests for sorting and selection algorithms)

````

## Requirements

* Java Development Kit (JDK) 23 or later.
* JUnit 5 for running tests.

## Testing Algorithms (JUnit)

All algorithms are validated using JUnit 5 tests located in the `src/test` directory.

To run all tests in IntelliJ IDEA:
1.  Right-click on the `test` folder.
2.  Select **Run 'All Tests'**.

## Building the Project

The project is built as an executable JAR file using the IntelliJ IDEA built-in artifact mechanism.

1.  **Build Artifact:** Navigate to `Build` -> `Build Artifacts` -> `assignment1daa.jar` -> `Build`.
2.  The resulting JAR file is created at: `out/artifacts/assignment1daa_jar/assignment1daa.jar`.

## Data Collection (Running Benchmarks)

The program's entry point (`main.cli.Main`) is designed to run algorithms multiple times for a given $N$ and output the collected performance metrics (nanos, comparisons, recursion depth, etc.) to a CSV file.

### Execution Syntax

```bash
java -jar <PATH_TO_JAR> <N> <Algorithm> [<Trials>]
````

| Parameter | Description |
| :--- | :--- |
| `<N>` | The size of the input data (e.g., 1000, 100000). |
| `<Algorithm>` | Algorithm name: `MergeSort`, `QuickSort`, `SelectMoM5`, or `ClosestPair`. |
| `[<Trials>]` | (Optional) Number of times to run the test (e.g., 10). Default is 5. |

### Example Commands (Using determined path)

Run the following commands in the terminal from the project's root directory:

```bash
# Example 1: Run QuickSort with N=100000, 10 trials
java -jar out/artifacts/assignment1daa_jar/assignment1daa.jar 100000 QuickSort 10

# Example 2: Run ClosestPair with N=10000, 10 trials
java -jar out/artifacts/assignment1daa_jar/assignment1daa.jar 10000 ClosestPair 10
```

### Output File

All metrics are appended to the file **`metrics_results.csv`** in the project's root directory.

```
```