package main.geometry;

import main.metrics.Metrics;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ClosestPair {

    private static final double INFINITY = Double.MAX_VALUE;

    public static double find(Point[] points, Metrics metrics) {
        if (points == null || points.length < 2) return INFINITY;

        Point[] Px = Arrays.copyOf(points, points.length);
        Arrays.sort(Px, Comparator.comparingInt(p -> p.x));

        Point[] Py = Arrays.copyOf(points, points.length);
        Arrays.sort(Py, Comparator.comparingInt(p -> p.y));

        metrics.addAlloc(Px.length * 2 * 8);

        long startTime = System.nanoTime();
        double minDistance = closestPair(Px, Py, metrics, 1);
        metrics.nanos = System.nanoTime() - startTime;

        return minDistance;
    }

    private static double closestPair(Point[] Px, Point[] Py, Metrics metrics, int depth) {
        int n = Px.length;

        if (n <= 3) return bruteForce(Px, 0, n - 1, metrics);

        metrics.observeDepth(depth);

        int mid = n / 2;
        Point[] Qx = Arrays.copyOfRange(Px, 0, mid);
        Point[] Rx = Arrays.copyOfRange(Px, mid, n);

        List<Point> QyList = new ArrayList<>(mid);
        List<Point> RyList = new ArrayList<>(n - mid);

        for (Point p : Py) {
            if (p.x < Px[mid].x) {
                QyList.add(p);
            } else {
                RyList.add(p);
            }
        }
        Point[] Qy = QyList.toArray(new Point[0]);
        Point[] Ry = RyList.toArray(new Point[0]);

        double deltaLeft = closestPair(Qx, Qy, metrics, depth + 1);
        double deltaRight = closestPair(Rx, Ry, metrics, depth + 1);
        double delta = Math.min(deltaLeft, deltaRight);

        double finalDelta = stripCheck(Py, Px[mid].x, delta, metrics);

        return finalDelta;
    }

    private static double bruteForce(Point[] Px, int low, int high, Metrics metrics) {
        double min = INFINITY;
        for (int i = low; i <= high; i++) {
            for (int j = i + 1; j <= high; j++) {
                // Every distance calculation implies a constant number of arithmetic operations
                // and should be counted as 'comparisons' or 'moves' if we track ops.
                // For simplicity, we only track the final Math.min comparison.
                min = Math.min(min, Px[i].distance(Px[j]));
                metrics.addCompare();
            }
        }
        return min;
    }

    private static double stripCheck(Point[] Py, int Lx, double delta, Metrics metrics) {

        List<Point> SyList = new ArrayList<>();
        for (Point p : Py) {
            if (Math.abs(p.x - Lx) < delta) {
                SyList.add(p);
            }
        }

        Point[] Sy = SyList.toArray(new Point[0]);
        metrics.addAlloc(Sy.length * 8); // Allocation for strip array
        double minStripDelta = delta;

        for (int i = 0; i < Sy.length; i++) {
            for (int j = i + 1; j < Sy.length; j++) {
                metrics.addCompare(); // Compare y-coords
                if ((Sy[j].y - Sy[i].y) >= minStripDelta) break;

                double dist = Sy[i].distance(Sy[j]);
                minStripDelta = Math.min(minStripDelta, dist);
                metrics.addCompare(); // Compare final distance
            }
        }
        return minStripDelta;
    }
}