package main.metrics;

public class Metrics {
    public long comparisons;
    public long moves;
    public long allocations;
    public int maxDepth;
    public long nanos;

    public void reset() {
        comparisons = moves = allocations = nanos = 0;
        maxDepth = 0;
    }

    public void addCompare() {comparisons++;}
    public void addMove(int k) {moves+= k;}
    public void addAlloc(long bytes) {allocations += bytes; }
    public void observeDepth(int d) { if (d > maxDepth) maxDepth = d; }
}
