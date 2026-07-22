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
        if ((a <= b && b <= c) || (c <= b && b <= a)) return mid;
        if ((b <= a && a <= c) || (c <= a && a <= b)) return low;
        return high;
    }

    private int partition(int[] array, int low, int high) {
        int pivotIdx = findPivotIndex(array, low, high);
        
        swap(array, pivotIdx, low);
        int pivotValue = array[low];

        while (low < high) {
            // Scan from right to left
            while (pivotValue < array[high] && low < high) {
                high--;
                numberOfComparisons++;
            }
            if (low < high) {
                numberOfComparisons++;
            }
            if (high != low) {
                array[low] = array[high];
                numberOfExchanges++;
                low++;
            }

            // Scan from left to right
            while (array[low] < pivotValue && low < high) {
                low++;
                numberOfComparisons++;
            }
            if (low < high) {
                numberOfComparisons++;
            }
            if (high != low) {
                array[high] = array[low];
                numberOfExchanges++;
                high--;
            }
        }
        
        array[high] = pivotValue;
        numberOfExchanges++; 
        
        return high; 
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
        quicksort(array, 0, array.length - 1);
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
