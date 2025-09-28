package main.util;

import java.util.Random;

public class Rand {
    private static final Random RAND = new Random();

    public static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = RAND.nextInt(i + 1);
            ArrayUtil.swap(a, i, j);
        }
    }

    public static int randomPivotIndex(int low, int high) {
        return low + RAND.nextInt(high - low + 1);
    }
}