
# Algorithmic Complexity and Performance Analysis (Assignment 1 DAA)

This project is dedicated to the empirical analysis and comparison of several fundamental algorithms by measuring their performance metrics (runtime, operation count, recursion depth) against varying input sizes ($N$). The core objective is to validate their observed time complexity against their theoretical $O$-notation.

## Algorithms Implemented

The project includes four distinct algorithms:

1.  **MergeSort:** A guaranteed $O(N \log N)$ sorting algorithm.
2.  **QuickSort:** An efficient sorting algorithm, typically $O(N \log N)$ average-case, but $O(N^2)$ worst-case.
3.  **SelectMoM5 (Median of Medians):** A deterministic selection algorithm with a proven linear time complexity of $O(N)$.
4.  **ClosestPair:** A computational geometry algorithm designed to find the closest pair of points in a 2D array, typically implemented with $O(N \log N)$ complexity.

## Project Structure

The project follows a standard Java package structure:

```

assignment1daa/
├── src/
│   ├── main/
│   │   ├── cli/             (Contains Main.java - the execution entry point)
│   │   ├── geometry/        (ClosestPair logic)
│   │   ├── metrics/         (Metrics tracking classes and CSV Writer)
│   │   └── sorts/           (Sorting and Selection algorithms)
│   └── test/                (Contains JUnit tests for all algorithms)
├── out/                     (Build output directory)
│   └── artifacts/           (Contains the compiled assignment1daa.jar)
└── metrics\_results.csv      (Generated file containing all collected benchmark data)

````

## Setup and Testing

### Requirements
* Java Development Kit (JDK) 23 or later.
* IntelliJ IDEA (Recommended IDE).

### Testing (JUnit 5)

Algorithm correctness is verified using dedicated JUnit 5 test classes (e.g., `MergeSortTest.java`, `ClosestPairTest.java`).

To run all tests:
1.  Navigate to the `test` directory in the Project window.
2.  Right-click and select **Run 'All Tests'**.

### Building the Executable JAR

The project is packaged into an executable JAR file for command-line execution:
1.  In IntelliJ IDEA, go to `Build` -> `Build Artifacts` -> `assignment1daa.jar` -> **Build**.
2.  The resulting executable is located at: `out/artifacts/assignment1daa_jar/assignment1daa.jar`.

## Benchmarking and Data Collection

The program is run from the command line to generate performance metrics.

### Execution Syntax

```bash
java -jar <PATH_TO_JAR> <N> <Algorithm> [<Trials>]
````

| Parameter | Description |
| :--- | :--- |
| **\<N\>** | The size of the input data (e.g., 1000, 100000). |
| **\<Algorithm\>** | Algorithm name: `MergeSort`, `QuickSort`, `SelectMoM5`, or `ClosestPair`. |
| **[\<Trials\>]** | (Optional) Number of times to repeat the test (e.g., 10). |

### Example Run

Use the determined path to run benchmarks:

```bash
# Running MergeSort for N=1000, 10 trials
java -jar out/artifacts/assignment1daa_jar/assignment1daa.jar 1000 MergeSort 10

# Running ClosestPair for N=10000, 10 trials
java -jar out/artifacts/assignment1daa_jar/assignment1daa.jar 10000 ClosestPair 10
```

### Output

All collected metrics are appended to the CSV file located in the project's root directory:
**Output File:** `metrics_results.csv`

```
