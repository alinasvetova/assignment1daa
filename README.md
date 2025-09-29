Algorithmic Complexity and Performance Analysis (Assignment 1 DAA)

This project focuses on the empirical analysis and comparison of several fundamental Divide & Conquer (D&C) algorithms. The goal is to measure their performance metrics (runtime, operation count, recursion depth) against varying input sizes N, and validate their observed time complexity against theoretical O-notation.

Algorithms Implemented

The project includes four distinct algorithms, all designed with D&C principles:

MergeSort
	•	Time Complexity: O(N \log N)
	•	Implementation Note: Uses a small-n cutoff to switch to Insertion Sort for improved efficiency on small subarrays.

QuickSort (Robust Implementation)
	•	Time Complexity:
	•	Expected average-case: O(N \log N)
	•	Worst-case: O(N^2)
	•	Implementation Note: Uses random pivot selection and handles the smaller recursive call first to maintain a stable stack depth of approximately O(\log N).

SelectMoM5 (Deterministic Select)
	•	Time Complexity: O(N) (guaranteed worst-case linear time)
	•	Implementation Note: Implements the Median-of-Medians strategy (groups of 5) to ensure the pivot selection maintains the linear time bound.

ClosestPair (2D Points)
	•	Time Complexity: O(N \log N)
	•	Implementation Note: Follows the classic D&C approach involving sorting by x, recursive split, and a linear-time strip check with guaranteed few neighbor checks.

Metrics and Data Collection

For each run, the program collects the following performance metrics and appends them to a CSV file for further plotting and analysis:

Collected Metrics
	•	Execution Time (ns): Total runtime in nanoseconds.
	•	Max Recursion Depth: Maximum depth reached during execution.
	•	Number of Key Comparisons: Count of logical comparison operations.
	•	Logical Allocations: Count of significant buffer/array allocations (e.g., merge buffer).

The results are appended to the output file using the following header structure:
	•	Algorithm, N, Trial, TimeNs, Comparisons, Allocations, MaxDepth

Note: The final results of all algorithm tests are recorded in the file final_results.csv.

Project Architecture Notes
	•	Maven Structure: The project follows standard Maven conventions (src/main/java, src/test/java) for portability and build standards.
	•	Recursion Depth Control: QuickSort guarantees a bounded stack depth of O(\log N) by always recurring on the smaller partition and iterating on the larger one. The DepthTracker utility explicitly monitors and caps this metric.
	•	Allocation Control: MergeSort uses a reusable auxiliary buffer to avoid repeated memory allocations inside recursive calls, reducing the “Logical Allocations” count and minimizing GC overhead.

Analysis (Theoretical Complexity)

Algorithm	Recurrence Relation (Intuition)	Theoretical Complexity	Master Theorem Case
MergeSort	T(n) = 2T(n/2) + \Theta(n)	\Theta(n \log n)	Case 2
QuickSort	(Not standard Master)	Expected \Theta(n \log n)	Akra–Bazzi / Substitution
Select (MoM5)	T(n) \leq T(n/5) + T(7n/10) + \Theta(n)	\Theta(n)	None (Substitution)
Closest Pair	T(n) = 2T(n/2) + \Theta(n)	\Theta(n \log n)	Case 2

Setup, Build, and Run Instructions

Requirements
	•	Java Development Kit (JDK): Version 23 or later.
	•	Maven: The project is configured to be built via the Maven pom.xml file.
	•	JUnit 5: For testing algorithm correctness (JUnit tests are located in src/test/java).

Running the Tests

To verify algorithm correctness, use the following command:

mvn test

Building the Executable JAR

The project can be built using Maven to generate the final executable JAR:
	1.	Clean and Package the Project:

mvn clean package


	2.	The executable JAR will be located in the target directory as assignment1daa-1.0-SNAPSHOT.jar.

Command Line Interface (CLI)

The main class main.cli.Main is run via the generated Maven JAR file using positional arguments:

java -jar target/assignment1daa-1.0-SNAPSHOT.jar <N> <Algorithm> [<Trials>]

Parameters:
	•	<N>: The size of the input data (or points).
	•	<Algorithm>: Algorithm name: MergeSort, QuickSort, SelectMoM5, or ClosestPair.
	•	[<Trials>]: The number of times to repeat the test (default is 1).

Example Runs:
	•	Running QuickSort for N = 100000, 10 trials:

java -jar target/assignment1daa-1.0-SNAPSHOT.jar 100000 QuickSort 10


	•	Running Closest Pair for N = 10000, 10 trials:

java -jar target/assignment1daa-1.0-SNAPSHOT.jar 10000 ClosestPair 10



Output

All collected metrics are appended to the CSV file located in the project’s root directory:
	•	Output File: final_results.csv


<img width="401" height="218" alt="image" src="https://github.com/user-attachments/assets/51bcc29a-5f4c-414a-a7a1-4e7965353538" />
