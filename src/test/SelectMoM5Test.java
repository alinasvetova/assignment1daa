package test;

import main.metrics.Metrics;
import main.sorts.SelectMoM5;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectMoM5Test {

    @Test
    void onRandomTrials_isCorrect() {
        int N = 1000;
        int trials = 100;
        Random rand = new Random();

        for (int t = 0; t < trials; t++) {
            int[] original = ThreadLocalRandom.current().ints(N, 0, N * 10).toArray();
            int[] arr = Arrays.copyOf(original, N);

            int k = rand.nextInt(N);

            Arrays.sort(original);
            int expected = original[k];

            Metrics metrics = new Metrics();
            int result = SelectMoM5.select(arr, k, metrics);

            assertEquals(expected, result, "SelectMoM5 failed to find the " + k + "-th element.");
            assertTrue(metrics.nanos > 0);
        }
    }

    @Test
    void onEdgeCases_isCorrect() {
        int[] arr = {5, 3, 5, 1, 3, 1, 2, 2};
        int[] sorted = {1, 1, 2, 2, 3, 3, 5, 5};

        Metrics metrics = new Metrics();

        assertEquals(sorted[0], SelectMoM5.select(Arrays.copyOf(arr, arr.length), 0, metrics));
        assertEquals(sorted[7], SelectMoM5.select(Arrays.copyOf(arr, arr.length), 7, metrics));
        assertEquals(sorted[3], SelectMoM5.select(Arrays.copyOf(arr, arr.length), 3, metrics));
    }

    @Test
    void onSmallArray_isCorrect() {
        int[] arr = {9, 1, 4, 6, 2};
        int[] sorted = {1, 2, 4, 6, 9};
        Metrics metrics = new Metrics();

        assertEquals(sorted[2], SelectMoM5.select(Arrays.copyOf(arr, arr.length), 2, metrics));
    }
}