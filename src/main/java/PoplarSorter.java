class PoplarSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Poplar Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Phase 1: Build the forest of ascending Poplar heaps from left to right
        for (int i = 1; i < numItems; i++) {
            // Re-balance the newly extended right-hand poplar root bounds
            trinklePoplar(array, i);
        }

        // Phase 2: Unwind and extract the roots back down from right to left
        for (int i = numItems - 1; i > 0; i--) {
            // Find the size of the rightmost poplar tree terminating at index i
            int size = getRightmostPoplarSize(i + 1);

            if (size > 1) {
                // Break the perfect binary poplar down into its two smaller children
                int subSize = size / 2;
                int leftChildRoot = i - subSize - 1;
                int rightChildRoot = i - 1;

                // Stabilize both newly exposed sub-poplar tree roots across the forest
                trinklePoplar(array, leftChildRoot);
                trinklePoplar(array, rightChildRoot);
            }
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    // Sifts elements through the roots of adjacent poplars to secure ascending order
    private void trinklePoplar(int[] array, int rootIdx) {
        int current = rootIdx;

        while (current > 0) {
            int leftTreeSize = getRightmostPoplarSize(current);
            int prevRoot = current - leftTreeSize;

            if (prevRoot < 0) {
                break; // No more poplars to the left
            }

            this.numberOfComparisons++;
            if (array[prevRoot] <= array[current]) {
                break; // The chained roots are already in ascending order
            }

            // If the poplar to the left has a larger root, check its internal children first
            int currentSize = getRightmostPoplarSize(current + 1);
            if (currentSize > 1) {
                int subSize = currentSize / 2;
                int leftChild = current - subSize - 1;
                int rightChild = current - 1;

                this.numberOfComparisons += 2;
                if (array[prevRoot] < array[leftChild] || array[prevRoot] < array[rightChild]) {
                    break; // An internal child node blocks root migration; sift down handles it
                }
            }

            // Swap the roots to push the larger element leftward across the forest
            swap(array, current, prevRoot);
            current = prevRoot;
        }

        // Run a local internal sift down to stabilize the target perfect binary poplar tree bounds
        siftDownPoplar(array, current, getRightmostPoplarSize(current + 1));
    }

    // Classic max-heap sift down tailored to perfect binary tree boundaries
    private void siftDownPoplar(int[] array, int rootIdx, int size) {
        while (size > 1) {
            int subSize = size / 2;
            int leftChild = rootIdx - subSize - 1;
            int rightChild = rootIdx - 1;
            int largest = rootIdx;

            this.numberOfComparisons++;
            if (array[leftChild] > array[largest]) largest = leftChild;
            this.numberOfComparisons++;
            if (array[rightChild] > array[largest]) largest = rightChild;

            if (largest == rootIdx) {
                break; // Parent is already the largest element; heap is stable
            }

            swap(array, rootIdx, largest);
            rootIdx = largest;
            size = subSize; // Descend directly down into the lopsided sub-branch tree
        }
    }

    // Bitwise helper that calculates the perfect binary tree size ending at a given length
    private int getRightmostPoplarSize(int length) {
        if (length <= 0) return 0;
        // Find the highest power of 2 that divides or fits the active block length boundaries
        int k = 1;
        while ((k << 1) <= length) {
            k <<= 1;
        }
        // Return the clean perfect binary tree footprint formula: (2^k - 1)
        return k - 1 == 0 ? 1 : k - 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        this.numberOfExchanges++;
    }
}
