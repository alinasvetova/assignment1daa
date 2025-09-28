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