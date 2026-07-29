class GnomeMergeSorter implements Sorter {
    // Structural tuning threshold: local blocks under 16 items are sorted via Gnome Sort
    private final int GNOME_THRESHOLD = 16;

    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Gnome-Merge Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Allocate a flat, secondary primitive array workspace for the merge phase
        int[] buffer = new int[numItems];

        // Kick off the hybrid divide-and-conquer processing engine
        gnomeMergeSortRecursive(array, buffer, 0, numItems - 1);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void gnomeMergeSortRecursive(int[] array, int[] buffer, int low, int high) {
        int segmentLength = high - low + 1;

        // Step 1: Base Case Optimization - Small windows fall back to Gnome Sort
        if (segmentLength <= GNOME_THRESHOLD) {
            localGnomeSort(array, low, high);
            return;
        }

        // Step 2: Divide and conquer partition split
        int mid = low + (high - low) / 2;

        gnomeMergeSortRecursive(array, buffer, low, mid);
        gnomeMergeSortRecursive(array, buffer, mid + 1, high);

        // Step 3: Combine adjacent sorted blocks stably
        mergeAdjacentBlocks(array, buffer, low, mid, high);
    }

    // Classic, single-pointer Gnome Sort customized to an internal subarray range
    private void localGnomeSort(int[] array, int start, int end) {
        int index = start;

        while (index <= end) {
            if (index == start) {
                index++;
            }
            
            // Boundary safety comparison pass check
            if (index <= end) {
                this.numberOfComparisons++;
                if (array[index] >= array[index - 1]) {
                    index++;
                } else {
                    // Exchange elements back down the line
                    int temp = array[index];
                    array[index] = array[index - 1];
                    array[index - 1] = temp;
                    this.numberOfExchanges++;
                    
                    index--;
                }
            }
        }
    }

    // Stably blends two sorted array slices using a temporary merge buffer workspace
    private void mergeAdjacentBlocks(int[] array, int[] buffer, int start, int mid, int end) {
        int leftPtr = start;
        int rightPtr = mid + 1;
        int targetIdx = start;

        // Trace headers using a standard two-pointer sweep loop
        while (leftPtr <= mid && rightPtr <= end) {
            this.numberOfComparisons++;
            if (array[leftPtr] <= array[rightPtr]) {
                buffer[targetIdx] = array[leftPtr];
                leftPtr++;
            } else {
                buffer[targetIdx] = array[rightPtr];
                rightPtr++;
            }
            this.numberOfExchanges++;
            targetIdx++;
        }

        // Flush any remaining elements left over in the left block
        while (leftPtr <= mid) {
            buffer[targetIdx] = array[leftPtr];
            leftPtr++;
            targetIdx++;
            this.numberOfExchanges++;
        }

        // Flush any remaining elements left over in the right block
        while (rightPtr <= end) {
            buffer[targetIdx] = array[rightPtr];
            rightPtr++;
            targetIdx++;
            this.numberOfExchanges++;
        }

        // Overwrite original array parameters with sorted elements from the buffer
        for (int i = start; i <= end; i++) {
            array[i] = buffer[i];
            this.numberOfExchanges++;
        }
    }
}
