class TernaryHeapSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Bottom-Up Ternary Heap Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Phase 1: Build the Max Ternary Heap from the bottom up
        for (int i = (numItems - 2) / 3; i >= 0; i--) {
            siftDownTopDown(array, numItems, i);
        }

        // Phase 2: Bottom Up Extraction Loop
        for (int i = numItems - 1; i > 0; i--) {
            // Extracted max element goes to its final sorted home at the end
            int targetValue = array[i];
            array[i] = array[0];
            this.numberOfExchanges++;

            // Run the optimized Bottom-Up path logic to find the insertion index slot
            int leafIndex = findMaxLeafPath(array, i, 0);
            
            // Bubble back up the chosen path to find where targetValue safely rests
            int insertionIndex = bubbleUpPath(array, leafIndex, targetValue);
            
            // Shift elements along the path to drop targetValue into its precise slot
            shiftPathDown(array, 0, insertionIndex, targetValue);
        }

        // Here, the list is sorted
        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    // 1. Travels straight down to the leaf level by selecting only the largest available child
    private int findMaxLeafPath(int[] array, int heapSize, int rootIdx) {
        while (true) {
            int left  = 3 * rootIdx + 1;
            int mid   = 3 * rootIdx + 2;
            int right = 3 * rootIdx + 3;

            // If there are no children, this index is a leaf node
            if (left >= heapSize) {
                return rootIdx;
            }

            int largestChildIdx = left;

            // Check if middle child exists and is larger
            if (mid < heapSize) {
                this.numberOfComparisons++;
                if (array[mid] > array[largestChildIdx]) {
                    largestChildIdx = mid;
                }
            }

            // Check if right child exists and is larger
            if (right < heapSize) {
                this.numberOfComparisons++;
                if (array[right] > array[largestChildIdx]) {
                    largestChildIdx = right;
                }
            }

            rootIdx = largestChildIdx;
        }
    }

    // 2. Navigates backward up the parent slots using a single comparison per level
    private int bubbleUpPath(int[] array, int leafIdx, int targetValue) {
        int current = leafIdx;
        while (current > 0) {
            int parent = (current - 1) / 3;
            this.numberOfComparisons++;
            if (array[parent] >= targetValue) {
                break; // Target value fits here or below this point
            }
            current = parent; // Keep climbing up the branch path
        }
        return current;
    }

    // 3. Shifts elements down the cleared path and drops the target item into place
    private void shiftPathDown(int[] array, int rootIdx, int insertionIdx, int targetValue) {
        if (rootIdx == insertionIdx) {
            array[rootIdx] = targetValue;
            this.numberOfExchanges++;
            return;
        }
        
        // Recursively or iteratively crawl down the path to shift elements
        // For simplicity, a standard backward index-climb shifts elements cleanly
        int current = insertionIdx;
        while (current > 0) {
            int parent = (current - 1) / 3;
            array[current] = array[parent];
            this.numberOfExchanges++;
            current = parent;
        }
        array[rootIdx] = targetValue;
        this.numberOfExchanges++;
    }

    // Standard top-down sift used ONLY for Phase 1 construction
    private void siftDownTopDown(int[] array, int heapSize, int rootIdx) {
        int largest = rootIdx;
        while (true) {
            int left  = 3 * rootIdx + 1;
            int mid   = 3 * rootIdx + 2;
            int right = 3 * rootIdx + 3;

            if (left < heapSize) {
                this.numberOfComparisons++;
                if (array[left] > array[largest]) largest = left;
            }
            if (mid < heapSize) {
                this.numberOfComparisons++;
                if (array[mid] > array[largest]) largest = mid;
            }
            if (right < heapSize) {
                this.numberOfComparisons++;
                if (array[right] > array[largest]) largest = right;
            }

            if (largest == rootIdx) break;

            int temp = array[rootIdx];
            array[rootIdx] = array[largest];
            array[largest] = temp;
            this.numberOfExchanges++;
            rootIdx = largest;
        }
    }
}
