package test;

import main.geometry.ClosestPair;
import main.geometry.Point;
import main.metrics.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClosestPairTest {

    private static final double EPSILON = 1e-9;

    @Test
    void onKnownPoints_isCorrect() {
        Point[] points = {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)
        };

        Metrics metrics = new Metrics();
        double closestDist = ClosestPair.find(points, metrics);

        double expectedDist = Math.sqrt(2);

        assertEquals(expectedDist, closestDist, EPSILON);
        assertTrue(metrics.nanos > 0);
    }

    @Test
    void onTwoPoints_isCorrect() {
        Point[] points = {new Point(0, 0), new Point(3, 4)};
        Metrics metrics = new Metrics();
        double closestDist = ClosestPair.find(points, metrics);

        assertEquals(5.0, closestDist, EPSILON);
    }

    @Test
    void onCollinearPoints_isCorrect() {
        Point[] points = {
                new Point(0, 0), new Point(1, 0), new Point(10, 0), new Point(11, 0)
        };
        Metrics metrics = new Metrics();
        double closestDist = ClosestPair.find(points, metrics);

        assertEquals(1.0, closestDist, EPSILON);
    }
}