class DualPivotQuickSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Dual-Pivot Quick Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        dualPivotQuickSortRecursive(array, 0, numItems - 1);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void dualPivotQuickSortRecursive(int[] array, int low, int high) {
        if (low >= high) {
            return;
        }

        // 1. Pivot Setup: Choose leftmost and rightmost elements
        this.numberOfComparisons++;
        if (array[low] > array[high]) {
            swap(array, low, high);
        }

        int p1 = array[low];
        int p2 = array[high];

        // Pointers for three-way partitioning layout
        int lessThanPtr = low + 1;
        int greaterThanPtr = high - 1;
        int currentScanIdx = low + 1;

        // 2. Three-Way Partition Phase Sweep
        while (currentScanIdx <= greaterThanPtr) {
            
            // Region A check: Is it smaller than the primary left pivot?
            this.numberOfComparisons++;
            if (array[currentScanIdx] < p1) {
                swap(array, currentScanIdx, lessThanPtr);
                lessThanPtr++;
            } 
            // Region C check: Is it larger than the secondary right pivot?
            else if (array[currentScanIdx] > p2) {
                this.numberOfComparisons++; // Increment for the second condition test
                
                // Slide greaterThanPtr leftward over elements that are already strictly larger than p2
                while (currentScanIdx < greaterThanPtr && array[greaterThanPtr] > p2) {
                    this.numberOfComparisons++;
                    greaterThanPtr--;
                }
                if (currentScanIdx < greaterThanPtr) this.numberOfComparisons++; // Count loop failure
                
                swap(array, currentScanIdx, greaterThanPtr);
                greaterThanPtr--;
                
                // Re-evaluate the swapped element against pivot 1
                this.numberOfComparisons++;
                if (array[currentScanIdx] < p1) {
                    swap(array, currentScanIdx, lessThanPtr);
                    lessThanPtr++;
                }
            } else {
                // If it hits here, it falls directly into Region B: P1 <= element <= P2
                this.numberOfComparisons++; // Count the failed upper check comparison step
            }
            currentScanIdx++;
        }

        // 3. Move pivots back into their absolute final separation slots
        lessThanPtr--;
        greaterThanPtr++;
        swap(array, low, lessThanPtr);
        swap(array, high, greaterThanPtr);

        // 4. Recursively sort all three isolated sub-zones
        dualPivotQuickSortRecursive(array, low, lessThanPtr - 1);       // Left Zone
        dualPivotQuickSortRecursive(array, lessThanPtr + 1, greaterThanPtr - 1); // Middle Zone
        dualPivotQuickSortRecursive(array, greaterThanPtr + 1, high);      // Right Zone
    }

    private void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        this.numberOfExchanges++;
    }
}
