package main.sorts;

import main.metrics.Metrics;
import main.util.ArrayUtil;
import java.util.Arrays;

public class SelectMoM5 {

    private static final int GROUP_SIZE = 5;

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid input array or index k.");
        }
        long startTime = System.nanoTime();
        int result = select(a, 0, a.length - 1, k, metrics, 1);
        metrics.nanos = System.nanoTime() - startTime;
        return result;
    }

    private static int select(int[] a, int low, int high, int k, Metrics metrics, int depth) {
        while (low <= high) {
            metrics.observeDepth(depth);

            if (high - low < GROUP_SIZE) {
                insertionSort(a, low, high, metrics);
                return a[k];
            }

            int pivotValue = medianOfMedians(a, low, high, metrics, depth + 1);
            int pivotIndex = partitionAroundValue(a, low, high, pivotValue, metrics);

            if (k == pivotIndex) {
                return a[k];
            } else if (k < pivotIndex) {
                high = pivotIndex - 1;
            } else {
                low = pivotIndex + 1;
            }
        }
        return -1;
    }

    private static int medianOfMedians(int[] a, int low, int high, Metrics metrics, int nextDepth) {
        int n = high - low + 1;
        int numGroups = (n + GROUP_SIZE - 1) / GROUP_SIZE;
        int[] medians = new int[numGroups];
        metrics.addAlloc(numGroups * Integer.BYTES);

        for (int i = 0; i < numGroups; i++) {
            int groupLow = low + i * GROUP_SIZE;
            int groupHigh = Math.min(groupLow + GROUP_SIZE - 1, high);

            insertionSort(a, groupLow, groupHigh, metrics);

            medians[i] = a[groupLow + (groupHigh - groupLow) / 2];
            metrics.addMove(1);
        }

        return select(medians, 0, medians.length - 1, (medians.length - 1) / 2, metrics, nextDepth);
    }

    private static void insertionSort(int[] a, int low, int high, Metrics metrics) {
        for (int i = low + 1; i <= high; i++) {
            for (int j = i; j > low; j--) {
                if (ArrayUtil.less(a[j], a[j - 1], metrics)) {
                    ArrayUtil.swap(a, j, j - 1, metrics);
                } else {
                    break;
                }
            }
        }
    }

    private static int partitionAroundValue(int[] a, int low, int high, int pivotValue, Metrics metrics) {
        int pivotIndex = -1;
        for (int i = low; i <= high; i++) {
            if (a[i] == pivotValue) {
                pivotIndex = i;
                break;
            }
        }
        ArrayUtil.swap(a, low, pivotIndex, metrics);

        return ArrayUtil.partition(a, low, high, metrics);
    }
}