package sorts;

import metrics.Metrics;
import util.ArrayUtil;
import java.util.Arrays;

public class SelectMoM5 {

    private static final int GROUP_SIZE = 5;

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid input array or index k.");
        }
        int[] arrCopy = Arrays.copyOf(a, a.length);
        long startTime = System.nanoTime();
        int result = selectRecursive(arrCopy, 0, arrCopy.length - 1, k, metrics, 1);
        metrics.nanos = System.nanoTime() - startTime;
        return result;
    }

    private static int selectRecursive(int[] a, int low, int high, int k, Metrics metrics, int depth) {
        metrics.observeDepth(depth);

        if (low == high) {
            return a[low];
        }

        if (k < low || k > high) {
            throw new IllegalArgumentException("Index k is out of bounds for current subarray.");
        }

        int pivotValue = medianOfMedians(a, low, high, metrics);
        int pivotIndex = partitionAroundValue(a, low, high, pivotValue, metrics);

        if (k == pivotIndex) {
            return a[k];
        } else if (k < pivotIndex) {
            return selectRecursive(a, low, pivotIndex - 1, k, metrics, depth + 1);
        } else {
            return selectRecursive(a, pivotIndex + 1, high, k, metrics, depth + 1);
        }
    }

    private static int medianOfMedians(int[] a, int low, int high, Metrics metrics) {
        int n = high - low + 1;
        if (n <= GROUP_SIZE) {
            insertionSort(a, low, high, metrics);
            return a[low + n / 2];
        }

        int numGroups = (n + GROUP_SIZE - 1) / GROUP_SIZE;
        int[] medians = new int[numGroups];
        metrics.addAlloc(numGroups * Integer.BYTES);

        for (int i = 0; i < numGroups; i++) {
            int groupLow = low + i * GROUP_SIZE;
            int groupHigh = Math.min(groupLow + GROUP_SIZE - 1, high);

            insertionSort(a, groupLow, groupHigh, metrics);

            medians[i] = a[groupLow + (groupHigh - groupLow) / 2];
        }

        Arrays.sort(medians);
        return medians[(medians.length - 1) / 2];
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