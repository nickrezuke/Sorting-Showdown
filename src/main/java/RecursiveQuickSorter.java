class RecursiveQuickSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Recursive Quick Sort";
    }

    private int findPivotIndex(int[] array, int low, int high) {
        int mid = low + (high - low) / 2;
        int a = array[low];
        int b = array[mid];
        int c = array[high];

        if ((a <= b && b <= c) || (c <= b && b <= a)) {
            return mid;
        }
        if ((b <= a && a <= c) || (c <= a && a <= b)) {
            return low;
        }
        return high;
    }

    private int partition(int[] array, int low, int high) {
        int pivotIdx = findPivotIndex(array, low, high);

        // Hide the pivot value at the end of the partition range
        swap(array, pivotIdx, high);
        int pivotValue = array[high];

        int i = low - 1; // Index of smaller element

        for (int j = low; j < high; j++) {
            numberOfComparisons++;
            if (array[j] < pivotValue) {
                i++;
                swap(array, i, j);
            }
        }

        // Place the pivot into its final correct sorted position
        swap(array, i + 1, high);

        return i + 1;
    }

    private void swap(int[] array, int i, int j) {
        if (i != j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            numberOfExchanges++;
        }
    }

    public SortResult sort(int[] array) {
        numberOfComparisons = 0;
        numberOfExchanges = 0;

        // Handle edge cases for empty or single-element arrays
        if (array != null && array.length > 1) {
            quicksort(array, 0, array.length - 1);
        }

        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }

    private void quicksort(int[] array, int low, int high) {
        if (low < high) {
            int pivotPoint = partition(array, low, high);
            quicksort(array, low, pivotPoint - 1);
            quicksort(array, pivotPoint + 1, high);
        }
    }
}
