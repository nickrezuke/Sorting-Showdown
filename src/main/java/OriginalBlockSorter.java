class OriginalBlockSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Original Block Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        originalBlockSortRecursive(array, 0, numItems - 1);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void originalBlockSortRecursive(int[] array, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid = low + (high - low) / 2;

        // 1. Classical divide and conquer partitioning
        originalBlockSortRecursive(array, low, mid);
        originalBlockSortRecursive(array, mid + 1, high);

        // 2. Original Block Merge Step using square-root dynamic layout sizing
        originalBlockMerge(array, low, mid, high);
    }

    private void originalBlockMerge(int[] array, int start, int mid, int end) {
        int leftLen = mid - start + 1;
        int rightLen = end - mid;
        int totalLen = leftLen + rightLen;

        // Math rule: block size is determined strictly by the square root of the active range
        int sqrtBlockSize = (int) Math.sqrt(totalLen);
        if (sqrtBlockSize < 2) {
            // If the chunk is tiny, clean it up immediately via a standard localized insertion sweep
            localInsertionSort(array, start, end + 1);
            return;
        }

        // Phase 1: Rearrange blocks based on their leading element values
        // Move across the segments block-by-block using the calculated square root stride
        for (int i = start; i <= end; i += sqrtBlockSize) {
            int blockEnd = Math.min(i + sqrtBlockSize, end + 1);
            
            // Find the minimum leader block among the subsequent sections
            int minLeaderIdx = i;
            for (int j = i + sqrtBlockSize; j <= end; j += sqrtBlockSize) {
                this.numberOfComparisons++;
                if (array[j] < array[minLeaderIdx]) {
                    minLeaderIdx = j;
                }
            }

            // Swap the block blocks if a smaller leader section is identified further down
            if (minLeaderIdx != i) {
                int targetBlockEnd = Math.min(minLeaderIdx + sqrtBlockSize, end + 1);
                swapBlocks(array, i, minLeaderIdx, Math.min(blockEnd - i, targetBlockEnd - minLeaderIdx));
            }
        }

        // Phase 2: Localized in-place clean up sweep to resolve boundary overlaps safely
        localInsertionSort(array, start, end + 1);
    }

    // Helper method to swap two blocks of elements in-place
    private void swapBlocks(int[] array, int idx1, int idx2, int len) {
        for (int i = 0; i < len; i++) {
            int temp = array[idx1 + i];
            array[idx1 + i] = array[idx2 + i];
            array[idx2 + i] = temp;
            this.numberOfExchanges++;
        }
    }

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
}
