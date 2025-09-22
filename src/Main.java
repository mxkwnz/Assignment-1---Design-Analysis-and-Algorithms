import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        System.out.println("Array: " + Arrays.toString(arr));

        int k = 3;
        int result = DeterministicSelect.select(arr.clone(), k);

        System.out.println(k + "-th smallest element: " + result);
        System.out.println("Comparisons: " + DeterministicSelect.getComparisons());
        System.out.println("Max depth: " + DeterministicSelect.getMaxDepth());
    }
}