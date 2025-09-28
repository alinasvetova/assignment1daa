package metrics;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CsWriter implements Closeable, Flushable {
    private final PrintWriter out;
    private boolean headerWritten = false;

    public CsWriter(File file) throws IOException {
        this.out = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(file, false), StandardCharsets.UTF_8), true);
    }

    public CsWriter(String path) throws IOException {
        this(new File(path));
    }

    public void writeHeader() {
        if (headerWritten) return;
        out.println("algo,n,nanos,comparisons,moves,allocations,maxDepth");
        headerWritten = true;
    }

    public void writeRow(String algo, int n, Metrics m) {
        writeHeader();
        out.printf("%s,%d,%d,%d,%d,%d,%d%n",
                algo, n, m.nanos, m.comparisons, m.moves, m.allocations, m.maxDepth);
    }

    @Override public void flush() { out.flush(); }
    @Override public void close() { out.close(); }
}
