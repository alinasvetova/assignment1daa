package util;

public class ArrayGuards {
    public static void checkRange(int[] a, int low, int high) {
        if (a == null) throw new IllegalArgumentException("Array cannot be null.");
        if (low < 0 || high >= a.length || low > high) {
            throw new IndexOutOfBoundsException(String.format("Invalid range [%d, %d] for array of length %d", low, high, a.length));
        }
    }
}