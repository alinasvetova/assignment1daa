package main.metrics;

public class DepthTracker implements AutoCloseable {
    private final Metrics m;
    private final int depth;

    public DepthTracker(Metrics m, int parentDepth) {
        this.m = m;
        this.depth = parentDepth + 1;
        m.observeDepth(this.depth);
    }

    public int depth() {
        return depth;
    }

    @Override
    public void close() {
        // no-op: used only for try-with-resources
    }
}
