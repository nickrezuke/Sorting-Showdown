class BlockSorter implements Sorter {
    // Standard block sizing denominator: 16 to 32 items optimizes local insertion sort limits
    private final int BLOCK_SIZE = 16;

    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Block Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Phase 1: Locally sort individual blocks of size 16 using Insertion Sort
        for (int i = 0; i < numItems; i += BLOCK_SIZE) {
            int end = Math.min(i + BLOCK_SIZE, numItems);
            localInsertionSort(array, i, end);
        }

        // Phase 2: Bottom-Up structural block merging phase
        for (int size = BLOCK_SIZE; size < numItems; size *= 2) {
            for (int left = 0; left < numItems; left += 2 * size) {
                int mid = Math.min(left + size, numItems);
                int right = Math.min(left + 2 * size, numItems);
                
                if (mid < right) {
                    blockMergeInPlace(array, left, mid, right);
                }
            }
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    // High performance insertion sort for handling fixed local blocks
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

    // In-place stable merge logic that operates entirely within O(1) space constraints
    private void blockMergeInPlace(int[] array, int start, int mid, int end) {
        int leftCursor = start;
        int rightCursor = mid;

        while (leftCursor < rightCursor && rightCursor < end) {
            this.numberOfComparisons++;
            
            // If the element in the left block is already smaller or equal, it's in the correct position
            if (array[leftCursor] <= array[rightCursor]) {
                leftCursor++;
            } else {
                // The element in the right block is smaller. We need to shift it into place.
                int value = array[rightCursor];
                int index = rightCursor;

                // Shift all elements between leftCursor and rightCursor to the right by one
                while (index > leftCursor) {
                    array[index] = array[index - 1];
                    this.numberOfExchanges++;
                    index--;
                }
                array[leftCursor] = value;

                // Move all pointers forward to maintain tracking alignment
                leftCursor++;
                rightCursor++;
            }
        }
    }
}
