class WikiSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "WikiSort (Stable Block Merge)";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Phase 1: Determine the square-root block stride size
        int blockSize = (int) Math.sqrt(numItems);
        if (blockSize < 2) {
            localInsertionSort(array, 0, numItems);
            return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
        }

        // Phase 2: Bottom-Up structural block merging passes
        for (int size = blockSize; size < numItems; size *= 2) {
            for (int left = 0; left < numItems; left += 2 * size) {
                int mid = Math.min(left + size, numItems);
                int right = Math.min(left + 2 * size, numItems);
                
                if (mid < right) {
                    wikiBlockMerge(array, left, mid, right, blockSize);
                }
            }
        }

        // Phase 3: Final localized cleanup sweep over internal boundary intersections
        localInsertionSort(array, 0, numItems);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    // WikiSort custom look-ahead rolling block merge architecture
    private void wikiBlockMerge(int[] array, int start, int mid, int end, int blockSize) {
        int leftCursor = start;
        int rightCursor = mid;

        // Roll blocks sequentially through the bounds to establish sorting regions
        while (leftCursor < mid && rightCursor < end) {
            this.numberOfComparisons++;
            if (array[leftCursor] <= array[rightCursor]) {
                leftCursor++;
            } else {
                // Determine how many items in the right section are smaller than array[leftCursor]
                int endOfRightBlock = rightCursor;
                while (endOfRightBlock < end && endOfRightBlock < rightCursor + blockSize) {
                    this.numberOfComparisons++;
                    if (array[endOfRightBlock] < array[leftCursor]) {
                        endOfRightBlock++;
                    } else {
                        break;
                    }
                }

                // Rotate the smaller right chunk cleanly into its place ahead of leftCursor
                int rightBlockLen = endOfRightBlock - rightCursor;
                rotateRangeInPlace(array, leftCursor, rightCursor - 1, endOfRightBlock - 1);

                // Re-align tracking markers forward to accommodate the shifted geometry
                leftCursor += rightBlockLen;
                mid += rightBlockLen;
                rightCursor += rightBlockLen;
            }
        }
    }

    // In-place block rotation via localized array pointer reversals
    private void rotateRangeInPlace(int[] array, int first, int mid, int last) {
        reverse(array, first, mid);
        reverse(array, mid + 1, last);
        reverse(array, first, last);
    }

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
