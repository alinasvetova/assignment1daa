package main.cli;

import main.metrics.Metrics;
import main.metrics.CsWriter;
import main.sorts.MergeSort;
import main.sorts.QuickSort;
import main.sorts.SelectMoM5;
import main.geometry.ClosestPair;
import main.geometry.Point;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final String CSV_FILENAME = "metrics_results.csv";

    public static void main(String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            printUsage();
            return;
        }

        try {
            int N = Integer.parseInt(args[0]);
            String algo = args[1];
            int trials = args.length > 2 ? Integer.parseInt(args[2]) : 5;

            System.out.printf("Running %s for N=%d, trials=%d...\n", algo, N, trials);

            try (CsWriter writer = new CsWriter(new File(CSV_FILENAME))) {
                for (int t = 0; t < trials; t++) {
                    Metrics metrics = new Metrics();
                    metrics.reset();

                    int[] arr = ThreadLocalRandom.current().ints(N, 0, N * 10).toArray();

                    runAlgorithm(algo, N, arr, metrics);

                    writer.writeRow(algo, N, metrics);
                }
            }

            System.out.printf("\nMetrics collected and written to %s\n", CSV_FILENAME);

        } catch (NumberFormatException e) {
            System.err.println("Error: N and Trials must be valid integers.");
            printUsage();
        } catch (IllegalArgumentException | IOException e) {
            System.err.println("Error during execution: " + e.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar <jarname>.jar <N> <Algorithm> [<Trials>]");
        System.out.println("Algorithms: MergeSort, QuickSort, SelectMoM5, ClosestPair");
        System.out.println("Example: java -jar algos.jar 100000 QuickSort 10");
    }

    private static void runAlgorithm(String algo, int N, int[] arr, Metrics metrics) {
        int[] arrCopy = Arrays.copyOf(arr, N);

        switch (algo.toLowerCase()) {
            case "mergesort":
                MergeSort.sort(arrCopy, metrics);
                break;
            case "quicksort":
                QuickSort.sort(arrCopy, metrics);
                break;
            case "selectmom5":
                int k = ThreadLocalRandom.current().nextInt(N);
                SelectMoM5.select(arrCopy, k, metrics);
                break;
            case "closestpair":
                Point[] points = new Point[N];
                for(int i = 0; i < N; i++) {
                    points[i] = new Point(arrCopy[i], arrCopy[i] + i);
                }
                ClosestPair.find(points, metrics);
                break;
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algo);
        }
    }
}