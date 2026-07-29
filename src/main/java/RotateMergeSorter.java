class RotateMergeSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Rotate Merge Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        rotateMergeSortRecursive(array, 0, numItems - 1);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void rotateMergeSortRecursive(int[] array, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;

            // 1. Classical divide and conquer steps
            rotateMergeSortRecursive(array, low, mid);
            rotateMergeSortRecursive(array, mid + 1, high);

            // 2. Perform the optimized block rotation merge
            rotateMerge(array, low, mid, high);
        }
    }

    private void rotateMerge(int[] array, int start, int mid, int end) {
        // Base checks: if boundaries overlap or are empty, terminate
        if (mid < start || mid >= end) {
            return;
        }

        // Calculate division positions for our blocks
        int leftMid = start + (mid - start) / 2;
        
        // Use binary search to locate the split mirror point in the right half array
        int rightMid = binarySearchLowerBound(array, mid + 1, end, array[leftMid]);

        // Calculate the physical lengths of the inner blocks we are about to rotate
        int leftBlockLen = mid - leftMid + 1;
        int rightBlockLen = rightMid - (mid + 1);

        // Perform the O(1) space block rotation swap on segment [leftMid ... rightMid - 1]
        rotateRangeInPlace(array, leftMid, mid, rightMid - 1);

        // Calculate where the old leftMid element ended up landing after rotation
        int newPivotIdx = leftMid + rightBlockLen;

        // 3. Recursively clean up the remaining unaligned left and right blocks
        rotateMerge(array, start, leftMid - 1, newPivotIdx - 1);
        rotateMerge(array, newPivotIdx + 1, newPivotIdx + leftBlockLen - 1, end);
    }

    // Binary search helper to accurately find block division splitting boundaries
    private int binarySearchLowerBound(int[] array, int low, int high, int target) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            this.numberOfComparisons++;
            if (array[mid] >= target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    // Rotates a range by splitting it into sub-blocks and reversing them
    private void rotateRangeInPlace(int[] array, int first, int mid, int last) {
        reverse(array, first, mid);
        reverse(array, mid + 1, last);
        reverse(array, first, last);
    }

    // Standard in-place array reflection reversal
    private void reverse(int[] array, int lo, int hi) {
        while (lo < hi) {
            int temp = array[lo];
            array[lo] = array[hi];
            array[hi] = temp;
            this.numberOfExchanges++;
            lo++;
            hi--;
        }
    }
}
