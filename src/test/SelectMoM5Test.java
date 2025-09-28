import main.metrics.Metrics;
import main.sorts.SelectMoM5;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        }
    }

    @Test
    void onEdgeCases_isCorrect() {
        int[] arr = {5, 3, 5, 1, 3, 1, 2, 2};
        int[] sorted = {1, 1, 2, 2, 3, 3, 5, 5};

        Metrics metrics = new Metrics();

        // k=0 (smallest)
        assertEquals(sorted[0], SelectMoM5.select(Arrays.copyOf(arr, arr.length), 0, metrics));
        // k=7 (largest)
        assertEquals(sorted[7], SelectMoM5.select(Arrays.copyOf(arr, arr.length), 7, metrics));
        // k=3 (median)
        assertEquals(sorted[3], SelectMoM5.select(Arrays.copyOf(arr, arr.length), 3, metrics));
    }
}