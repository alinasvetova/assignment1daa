package main.util;

import main.metrics.Metrics;

public class ArrayUtil {

    public static boolean less(int v, int w, Metrics metrics) {
        metrics.addCompare();
        return v < w;
    }

    public static boolean lessOrEqual(int v, int w, Metrics metrics) {
        metrics.addCompare();
        return v <= w;
    }

    public static void swap(int[] a, int i, int j, Metrics metrics) {
        metrics.addMove(3);
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static int partition(int[] a, int low, int high, Metrics metrics) {
        int pivot = a[low];
        int i = low;
        int j = high + 1;

        while (true) {
            while (ArrayUtil.less(a[++i], pivot, metrics)) {
                if (i == high) break;
            }
            while (ArrayUtil.less(pivot, a[--j], metrics)) {
                if (j == low) break;
            }
            if (i >= j) break;
            ArrayUtil.swap(a, i, j, metrics);
        }

        ArrayUtil.swap(a, low, j, metrics);
        return j;
    }
}