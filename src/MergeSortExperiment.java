import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class MergeSortExperiment {
    public static void main(String[] args) {
        final int CUTOFF = 16;
        final int[] sizes = {1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000};
        final long[] seeds = {42L, 43L, 44L, 45L, 46L};
        final String csvName = "metrics.csv";

        MergeSorter sorter = new MergeSorter(CUTOFF);

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvName))) {
            writer.println("algorithm,n,time_ns,comparisons,writes,allocations,max_depth,seed,case");

            for (int n : sizes) {
                for (long seed : seeds) {
                    int[] a = new Random(seed).ints(n, 0, n).toArray();
                    int[] copy = Arrays.copyOf(a, a.length);

                    MergeSorter.Result res = sorter.run(a);

                    Arrays.sort(copy);
                    if (!Arrays.equals(a, copy)) {
                        throw new AssertionError("Sort failed for n=" + n + " seed=" + seed);
                    }

                    writer.printf("mergesort,%d,%d,%d,%d,%d,%d,%d,random%n",
                            res.n, res.timeNs, res.comparisons, res.writes, res.allocations, res.maxDepth, seed);

                    System.out.println("n=" + n + " seed=" + seed + " " + res.toString());
                }
            }
            System.out.println("CSV saved to " + csvName);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}