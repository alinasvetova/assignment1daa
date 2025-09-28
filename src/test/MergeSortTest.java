import main.metrics.Metrics;
import main.sorts.MergeSort;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortTest {

    private void runSortTest(int[] arr, int[] expected) {
        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);
        assertArrayEquals(expected, arr, "MergeSort failed.");
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
    void onEmptyAndSingleElementArray_isCorrect() {
        runSortTest(new int[]{}, new int[]{});
        runSortTest(new int[]{5}, new int[]{5});
    }

    @Test
    void onReverseSortedArray_isCorrect() {
        int N = 100;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = N - i;
        int[] expected = new int[N];
        for (int i = 0; i < N; i++) expected[i] = i + 1;
        runSortTest(arr, expected);
    }
}