import java.util.Arrays;

public class MergeSorter {
    private long comparisons;
    private long writes;
    private long allocations;
    private int maxDepthObserved;
    private final int cutoff;

    public MergeSorter(int cutoff) {
        this.cutoff = cutoff;
    }

    private void clearMetrics() {
        comparisons = 0;
        writes = 0;
        allocations = 0;
        maxDepthObserved = 0;
    }

    public Result run(int[] arr) {
        clearMetrics();
        if (arr == null) return new Result(0, 0L, 0L, 0L, 0, 0);
        int n = arr.length;
        if (n <= 1) return new Result(n, 0L, 0L, 0L, 0, 0);

        int[] buffer = new int[n];
        allocations++;

        long t0 = System.nanoTime();
        sortRecursive(arr, buffer, 0, n, 0);
        long elapsed = System.nanoTime() - t0;

        return new Result(n, elapsed, comparisons, writes, allocations, maxDepthObserved);
    }

    private void sortRecursive(int[] a, int[] buf, int lo, int hi, int depth) {
        if (depth > maxDepthObserved) maxDepthObserved = depth;
        int len = hi - lo;
        if (len <= cutoff) {
            insertionSort(a, lo, hi);
            return;
        }
        int mid = lo + (len >>> 1);
        sortRecursive(a, buf, lo, mid, depth + 1);
        sortRecursive(a, buf, mid, hi, depth + 1);

        comparisons++;
        if (a[mid - 1] <= a[mid]) return;

        merge(a, buf, lo, mid, hi);
    }

    private void merge(int[] a, int[] buf, int lo, int mid, int hi) {
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) {
            comparisons++;
            if (a[i] <= a[j]) {
                buf[k++] = a[i++];
                writes++;
            } else {
                buf[k++] = a[j++];
                writes++;
            }
        }
        while (i < mid) {
            buf[k++] = a[i++];
            writes++;
        }
        while (j < hi) {
            buf[k++] = a[j++];
            writes++;
        }
        System.arraycopy(buf, lo, a, lo, hi - lo);
        writes += (hi - lo);
    }

    private void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++) {
            int val = a[i];
            writes++;
            int j = i - 1;
            while (j >= lo) {
                comparisons++;
                if (a[j] > val) {
                    a[j + 1] = a[j];
                    writes++;
                    j--;
                } else break;
            }
            a[j + 1] = val;
            writes++;
        }
    }

    public static class Result {
        public final int n;
        public final long timeNs;
        public final long comparisons, writes, allocations;
        public final int maxDepth;

        public Result(int n, long timeNs, long comparisons, long writes, long allocations, int maxDepth) {
            this.n = n;
            this.timeNs = timeNs;
            this.comparisons = comparisons;
            this.writes = writes;
            this.allocations = allocations;
            this.maxDepth = maxDepth;
        }

        @Override
        public String toString() {
            return String.format("n=%d time_ns=%d comps=%d writes=%d alloc=%d depth=%d",
                    n, timeNs, comparisons, writes, allocations, maxDepth);
        }
    }
}