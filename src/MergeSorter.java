import java.util.Arrays;
import java.util.Random;

public class MergeSorter {

    public static void mergeSort(int[] a) {
        if (a.length <= 1) return;
        int[] buffer = new int[a.length];
        sortRecursive(a, buffer, 0, a.length);
    }

    private static void sortRecursive(int[] a, int[] buf, int lo, int hi) {
        int len = hi - lo;
        if (len <= 1) return;

        int mid = lo + (len / 2);

        sortRecursive(a, buf, lo, mid);
        sortRecursive(a, buf, mid, hi);

        if (a[mid - 1] <= a[mid]) return;

        merge(a, buf, lo, mid, hi);
    }

    private static void merge(int[] a, int[] buf, int lo, int mid, int hi) {
        int i = lo, j = mid, k = lo;

        while (i < mid && j < hi) {
            if (a[i] <= a[j]) buf[k++] = a[i++];
            else buf[k++] = a[j++];
        }
        while (i < mid) buf[k++] = a[i++];
        while (j < hi) buf[k++] = a[j++];

        System.arraycopy(buf, lo, a, lo, hi - lo);
    }

    public static void main(String[] args) {
        int n = 20;
        int[] arr = new Random().ints(n, 0, 100).toArray();

        System.out.println("До сортировки: " + Arrays.toString(arr));
        mergeSort(arr);
        System.out.println("После сортировки: " + Arrays.toString(arr));
    }
}