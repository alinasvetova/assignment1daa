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
        double minDistanceSq = closestPair(Px, Py, metrics, 1);
        metrics.nanos = System.nanoTime() - startTime;

        return Math.sqrt(minDistanceSq);
    }

    private static double closestPair(Point[] Px, Point[] Py, Metrics metrics, int depth) {
        int n = Px.length;

        if (n <= 3) return bruteForce(Px, 0, n - 1, metrics);

        metrics.observeDepth(depth);

        int mid = n / 2;
        Point Lx = Px[mid - 1];

        Point[] Qx = Arrays.copyOfRange(Px, 0, mid);
        Point[] Rx = Arrays.copyOfRange(Px, mid, n);

        metrics.addAlloc((Qx.length + Rx.length) * 8);

        List<Point> QyList = new ArrayList<>(mid);
        List<Point> RyList = new ArrayList<>(n - mid);

        for (Point p : Py) {
            if (p.x <= Lx.x) {
                QyList.add(p);
            } else {
                RyList.add(p);
            }
        }

        Point[] Qy = QyList.toArray(new Point[0]);
        Point[] Ry = RyList.toArray(new Point[0]);

        metrics.addAlloc((Qy.length + Ry.length) * 8);

        double deltaLeftSq = closestPair(Qx, Qy, metrics, depth + 1);
        double deltaRightSq = closestPair(Rx, Ry, metrics, depth + 1);

        double deltaSq = Math.min(deltaLeftSq, deltaRightSq);

        double finalDeltaSq = stripCheck(Py, Px[mid].x, deltaSq, metrics);

        return Math.min(deltaSq, finalDeltaSq);
    }

    private static double bruteForce(Point[] Px, int low, int high, Metrics metrics) {
        double minSq = INFINITY;
        for (int i = low; i <= high; i++) {
            for (int j = i + 1; j <= high; j++) {
                double distSq = Px[i].distanceSq(Px[j]);
                minSq = Math.min(minSq, distSq);
                metrics.addCompare();
            }
        }
        return minSq;
    }

    private static double stripCheck(Point[] Py, int Lx, double deltaSq, Metrics metrics) {

        List<Point> SyList = new ArrayList<>();
        double delta = Math.sqrt(deltaSq);

        for (Point p : Py) {
            if (Math.abs(p.x - Lx) < delta) {
                SyList.add(p);
            }
        }

        Point[] Sy = SyList.toArray(new Point[0]);
        metrics.addAlloc(Sy.length * 8);
        double minStripDeltaSq = deltaSq;

        for (int i = 0; i < Sy.length; i++) {
            for (int j = i + 1; j < Sy.length && j < i + 15; j++) {
                metrics.addCompare();
                if ((Sy[j].y - Sy[i].y) >= delta) break;

                double distSq = Sy[i].distanceSq(Sy[j]);
                minStripDeltaSq = Math.min(minStripDeltaSq, distSq);
                metrics.addCompare();
            }
        }
        return minStripDeltaSq;
    }
}