public class DeterministicSelect {
    private static int comparisons = 0;
    private static int maxDepth = 0;
    private static int currentDepth = 0;

    public static int select(int[] arr, int k) {
        comparisons = 0;
        maxDepth = 0;
        currentDepth = 0;

        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k must be between 1 and array length");
        }

        return quickSelect(arr, 0, arr.length - 1, k - 1);
    }

    private static int quickSelect(int[] arr, int left, int right, int k) {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }

        try {
            if (left == right) {
                return arr[left];
            }

            int pivotIndex = medianOfMedians(arr, left, right);
            pivotIndex = partition(arr, left, right, pivotIndex);

            if (k == pivotIndex) {
                return arr[k];
            } else if (k < pivotIndex) {
                return quickSelect(arr, left, pivotIndex - 1, k);
            } else {
                return quickSelect(arr, pivotIndex + 1, right, k);
            }
        } finally {
            currentDepth--;
        }
    }

    private static int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;

        if (n < 5) {
            return findMedian(arr, left, right);
        }

        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int groupLeft = left + i * 5;
            int groupRight = Math.min(groupLeft + 4, right);
            medians[i] = findMedian(arr, groupLeft, groupRight);
        }

        return select(medians, (numGroups + 1) / 2);
    }

    private static int findMedian(int[] arr, int left, int right) {
        insertionSort(arr, left, right);
        return arr[left + (right - left) / 2];
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= left && arr[j] > key) {
                comparisons++;
                arr[j + 1] = arr[j];
                j--;
            }
            if (j >= left) comparisons++;
            arr[j + 1] = key;
        }
    }

    private static int partition(int[] arr, int left, int right, int pivotValue) {
        int pivotIndex = -1;
        for (int i = left; i <= right; i++) {
            if (arr[i] == pivotValue) {
                pivotIndex = i;
                break;
            }
        }

        swap(arr, pivotIndex, right);

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            comparisons++;
            if (arr[i] < arr[right]) {
                swap(arr, i, storeIndex);
                storeIndex++;
            }
        }

        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int getComparisons() {
        return comparisons;
    }

    public static int getMaxDepth() {
        return maxDepth;
    }
}