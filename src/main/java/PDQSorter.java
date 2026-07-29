class PDQSorter implements Sorter {
    private final int INSERTION_THRESHOLD = 24;
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Pattern-Defeating Quick Sort (PDQ)";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Calculate a safe recursive depth ceiling based on log2(N) * 2
        int badAllowed = 0;
        int temp = numItems;
        while (temp > 0) {
            badAllowed++;
            temp >>= 1;
        }
        badAllowed *= 2;

        pdqSortRecursive(array, 0, numItems - 1, badAllowed);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void pdqSortRecursive(int[] array, int low, int high, int badAllowed) {
        int length = high - low + 1;

        // Step 1: Small sub-array optimization via local Insertion Sort
        if (length < INSERTION_THRESHOLD) {
            localInsertionSort(array, low, high + 1);
            return;
        }

        // Step 2: Worst-case degradation prevention check via Heap Sort fallback
        if (badAllowed <= 0) {
            localHeapSort(array, low, high);
            return;
        }

        // Step 3: Median-of-Three pivot calculation logic
        int mid = low + length / 2;
        sortThreeElements(array, low, mid, high);
        // Use the median element at 'mid' as our anchor pivot point
        swap(array, low, mid); 
        int pivot = array[low];

        // Step 4: Perform standard two-pointer quick sort partitioning
        int left = low;
        int right = high + 1;

        while (true) {
            do {
                this.numberOfComparisons++;
                left++;
            } while (left <= high && array[left] < pivot);

            do {
                this.numberOfComparisons++;
                right--;
            } while (array[right] > pivot);

            if (left >= right) break;
            swap(array, left, right);
        }
        swap(array, low, right); // Place pivot into its final location

        // Evaluate if this partition step balanced efficiently
        int leftLen = right - low;
        int rightLen = high - right;
        boolean isBadPartition = (leftLen < length / 8 || rightLen < length / 8);

        if (isBadPartition) {
            badAllowed--;
            
            // Step 5: Defeat potential patterned structures by shuffling key element slots
            if (leftLen > INSERTION_THRESHOLD) {
                swap(array, low, low + leftLen / 4);
                swap(array, right - 1, right - leftLen / 4);
            }
            if (rightLen > INSERTION_THRESHOLD) {
                swap(array, right + 1, right + 1 + rightLen / 4);
                swap(array, high, high - rightLen / 4);
            }
        }

        // Step 6: Recurse over remaining left and right segments
        pdqSortRecursive(array, low, right - 1, badAllowed);
        pdqSortRecursive(array, right + 1, high, badAllowed);
    }

    // --- In-House Hybrid Fallback Engines ---

    private void localInsertionSort(int[] array, int start, int end) {
        for (int i = start + 1; i < end; i++) {
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

    private void localHeapSort(int[] array, int low, int high) {
        int n = high - low + 1;
        // Build max-heap internally over the slice range
        for (int i = n / 2 - 1; i >= 0; i--) {
            localSiftDown(array, n, i, low);
        }
        // Extract elements sequentially from the heap bounds
        for (int i = n - 1; i > 0; i--) {
            swap(array, low, low + i);
            localSiftDown(array, i, 0, low);
        }
    }

    private void localSiftDown(int[] array, int n, int rootIdx, int offset) {
        int largest = rootIdx;
        while (true) {
            int left = 2 * rootIdx + 1;
            int right = 2 * rootIdx + 2;

            if (left < n) {
                this.numberOfComparisons++;
                if (array[offset + left] > array[offset + largest]) {
                    largest = left;
                }
            }
            if (right < n) {
                this.numberOfComparisons++;
                if (array[offset + right] > array[offset + largest]) {
                    largest = right;
                }
            }

            if (largest == rootIdx) break;

            swap(array, offset + rootIdx, offset + largest);
            rootIdx = largest;
        }
    }

    private void sortThreeElements(int[] array, int a, int b, int c) {
        this.numberOfComparisons++;
        if (array[a] > array[b]) swap(array, a, b);
        this.numberOfComparisons++;
        if (array[b] > array[c]) swap(array, b, c);
        this.numberOfComparisons++;
        if (array[a] > array[b]) swap(array, a, b);
    }

    private void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        this.numberOfExchanges++;
    }
}
