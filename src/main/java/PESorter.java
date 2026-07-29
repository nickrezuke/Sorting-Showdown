class PESorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Proportion Extend Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        peSortRecursive(array, 0, numItems - 1);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void peSortRecursive(int[] array, int low, int high) {
        int length = high - low + 1;

        // Fallback boundary optimization for small sublist chunks
        if (length < 32) {
            localInsertionSort(array, low, high);
            return;
        }

        // 1. Calculate the 1/16th sample prefix size threshold
        int sampleSize = length / 16;
        if (sampleSize < 2) sampleSize = 2; // Enforce minimum sample footprint

        int sampleEnd = low + sampleSize - 1;

        // 2. Pre-sort the prefix slice recursively to secure our sampling base
        peSortRecursive(array, low, sampleEnd);

        // 3. Extract the median item of our sorted sample to act as our pivot anchor
        int sampleMedianIdx = low + (sampleSize / 2);
        int pivotValue = array[sampleMedianIdx];

        // Move the pivot out of the way to the far left to initialize standard partitioning
        swap(array, low, sampleMedianIdx);

        // 4. In-place partitioning of the extended unsorted range window
        int pivotFinalIdx = partition(array, low, high, pivotValue);

        // 5. Recursively clear remaining left and right balance paths
        peSortRecursive(array, low, pivotFinalIdx - 1);
        peSortRecursive(array, pivotFinalIdx + 1, high);
    }

    private int partition(int[] array, int low, int high, int pivotValue) {
        int left = low + 1;
        int right = high;

        while (true) {
            while (left <= right) {
                this.numberOfComparisons++;
                if (array[left] < pivotValue) {
                    left++;
                } else {
                    break;
                }
            }

            while (right >= left) {
                this.numberOfComparisons++;
                if (array[right] > pivotValue) {
                    right--;
                } else {
                    break;
                }
            }

            if (left >= right) {
                break;
            }

            swap(array, left, right);
            left++;
            right--;
        }

        // Place the pivot back securely into its final sorted boundary divide
        swap(array, low, right);
        return right;
    }

    private void localInsertionSort(int[] array, int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= start) {
                this.numberOfComparisons++;
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    this.numberOfExchanges++;
                    j--;
                } else {
                    break;
                }
            }
            array[j + 1] = key;
        }
    }

    private void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        this.numberOfExchanges++;
    }
}
