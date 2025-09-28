package main.sorts;

import main.metrics.Metrics;
import main.util.ArrayUtil;
import main.util.Rand;

public class QuickSort {

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length < 2) return;
        long startTime = System.nanoTime();
        Rand.shuffle(a, metrics);
        sort(a, 0, a.length - 1, metrics, 1);
        metrics.nanos = System.nanoTime() - startTime;
    }

    private static void sort(int[] a, int low, int high, Metrics metrics, int depth) {
        while (high > low) {
            metrics.observeDepth(depth);

            int randomIndex = Rand.randomPivotIndex(low, high);
            ArrayUtil.swap(a, low, randomIndex, metrics);

            int pivotIndex = ArrayUtil.partition(a, low, high, metrics);

            if (pivotIndex - low < high - pivotIndex) {
                sort(a, low, pivotIndex - 1, metrics, depth + 1);
                low = pivotIndex + 1;
            } else {
                sort(a, pivotIndex + 1, high, metrics, depth + 1);
                high = pivotIndex - 1;
            }
        }
    }
}