import main.metrics.Metrics;
import main.sorts.QuickSort;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuickSortTest {

    private void runSortTest(int[] arr, int[] expected) {
        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);
        assertArrayEquals(expected, arr, "QuickSort failed.");
    }

    @Test
    void onRandomArray_isCorrect() {
        int N = 1000;
        int[] original = ThreadLocalRandom.current().ints(N, 0, N * 10).toArray();
        int[] arr = Arrays.copyOf(original, N);
        int[] expected = Arrays.copyOf(original, N);
        Arrays.sort(expected);
        runSortTest(arr, expected);
    }

    @Test
    void onArrayWithDuplicates_isCorrect() {
        int[] arr = {5, 1, 5, 2, 2, 1, 8};
        int[] expected = {1, 1, 2, 2, 5, 5, 8};
        runSortTest(arr, expected);
    }

    @Test
    void checksBoundedDepth() {
        int N = 100000;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = i;

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        // Ожидаемая глубина для QuickSort с оптимизацией хвостовой рекурсии и рандомизацией
        int maxExpectedDepth = (int) (2 * Math.floor(Math.log(N) / Math.log(2))) + 10;

        assertTrue(metrics.maxDepth < maxExpectedDepth,
                "QuickSort depth (" + metrics.maxDepth + ") exceeded O(log N) bound (Expected < " + maxExpectedDepth + ").");
    }
}