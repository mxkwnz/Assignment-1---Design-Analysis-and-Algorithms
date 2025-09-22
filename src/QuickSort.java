import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(int[] nums) {
        quick(nums, 0, nums.length - 1);
    }

    private static void quick(int[] nums, int left, int right) {
        while (left < right) {
            int pivotIndex = left + random.nextInt(right - left + 1);
            int pivotValue = nums[pivotIndex];
            swap(nums, pivotIndex, right);

            int store = left;
            for (int i = left; i < right; i++) {
                if (nums[i] <= pivotValue) {
                    swap(nums, store, i);
                    store++;
                }
            }
            swap(nums, store, right);

            if (store - left < right - store) {
                quick(nums, left, store - 1);
                left = store + 1;
            } else {
                quick(nums, store + 1, right);
                right = store - 1;
            }
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
