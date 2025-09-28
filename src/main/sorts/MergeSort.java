package main.sorts;

import main.metrics.Metrics;
import main.util.ArrayUtil;
import java.util.Arrays;

public class MergeSort {

    private static final int CUTOFF = 7;

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length < 2) return;
        metrics.addAlloc(a.length * Integer.BYTES);
        int[] aux = new int[a.length];

        long startTime = System.nanoTime();
        sort(a, aux, 0, a.length - 1, metrics, 1);
        metrics.nanos = System.nanoTime() - startTime;
    }

    private static void sort(int[] a, int[] aux, int low, int high, Metrics metrics, int depth) {
        if (high <= low) return;

        metrics.observeDepth(depth);

        if (high - low < CUTOFF) {
            insertionSort(a, low, high, metrics);
            return;
        }

        int mid = low + (high - low) / 2;
        sort(a, aux, low, mid, metrics, depth + 1);
        sort(a, aux, mid + 1, high, metrics, depth + 1);
        merge(a, aux, low, mid, high, metrics);
    }

    private static void merge(int[] a, int[] aux, int low, int mid, int high, Metrics metrics) {
        int length = high - low + 1;

        System.arraycopy(a, low, aux, low, length);
        metrics.addMove(length);

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else if (ArrayUtil.less(aux[j], aux[i], metrics)) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
            metrics.addMove(1);
        }
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
}