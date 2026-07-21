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

        if ((a <= b && b <= c) || (c <= b && b <= a))
            return mid;
        if ((b <= a && a <= c) || (c <= a && a <= b))
            return low;
        return high;
    }

    private int partition(int[] array, int low, int high) {
        int pivotIdx = findPivotIndex(array, low, high);
        int pivotValue = array[pivotIdx];

        while (low < high) {
            while (pivotValue < array[high] && low < high) {
                high--;
                numberOfComparisons++;
            }
            // If the above while loop fails because pivotValue < array[high]
            // it still made a comparison that was false, specifically it 
            // performed a comparison and found that pivotValue >= array[high],
            // meaning that the loop never enters, so we still need to count 
            // that failed comparison by adding one if that was the case.
            if (low < high) {
                numberOfComparisons++;
            }
            if (high != low) {
                swap(array, low, high);
                low++;
            }
            while (array[low] < pivotValue && low < high) {
                low++;
                numberOfComparisons++;
            }
            // If the above while loop fails because array[low] < pivotValue
            // it still made a comparison that was false, specifically it 
            // performed a comparison and found that array[low] >= pivotValue, 
            // meaning that the loop never enters, so we still need to count 
            // that failed comparison by adding one if that was the case.
            if (low < high) {
                numberOfComparisons++;
            }
            if (high != low) {
                swap(array, low, high);
                high--;
            }
        }
        array[high] = pivotValue;

        return high; // high is the pivot after this
    }

    private void swap(int[] array, int i, int j) {
        if (i != j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            numberOfExchanges++;
        }
    }

    // The base call
    public SortResult sort(int[] array) {
        numberOfComparisons = 0;
        numberOfExchanges = 0;

        // Kick off our recursive call on the entire array
        quicksort(array, 0, array.length - 1);

        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }

    // The recursive call able to call itself
    private void quicksort(int[] array, int low, int high) {
        if (low < high) {
            int pivotPoint = partition(array, low, high);
            quicksort(array, low, pivotPoint - 1);
            quicksort(array, pivotPoint + 1, high);
        }
    }
}