import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        System.out.println("Array: " + Arrays.toString(arr));

        int k = 3;
        int result = DeterministicSelect.select(arr.clone(), k);

        QuickSort.sort(arr);

        System.out.println("~Deterministic Select~");
        System.out.println(k + " самый маленький элемент: " + result);
        System.out.println("Сравнения: " + DeterministicSelect.getComparisons());
        System.out.println("Максимальная глубина: " + DeterministicSelect.getMaxDepth());

        System.out.println();
        System.out.println("~QuickSort~");
        System.out.println(Arrays.toString(arr));

        System.out.println();
        System.out.println("~MergeSort~");
        MergeSorter.main(new String[]{});

        System.out.println();
        System.out.println("~ClosestPair~");
        ClosestPair.Point[] pts = {
                new ClosestPair.Point(2, 3), new ClosestPair.Point(12, 30),
                new ClosestPair.Point(40, 50), new ClosestPair.Point(5, 1),
                new ClosestPair.Point(12, 10), new ClosestPair.Point(3, 4)
        };

        System.out.println("Минимальное расстояние = " + ClosestPair.closest(pts));
    }
}