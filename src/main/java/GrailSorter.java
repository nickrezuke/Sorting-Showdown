class GrailSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Grail Sort";
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
                    grailBlockMerge(array, left, mid, right, blockSize);
                }
            }
        }

        // Phase 3: Final localized cleanup sweep over internal boundary intersections
        localInsertionSort(array, 0, numItems);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    // Grail Sort rolling block merge implementation
    private void grailBlockMerge(int[] array, int start, int mid, int end, int blockSize) {
        int leftCursor = start;
        int rightCursor = mid;

        while (leftCursor < mid && rightCursor < end) {
            this.numberOfComparisons++;
            if (array[leftCursor] <= array[rightCursor]) {
                leftCursor++;
            } else {
                // Determine the size of the smaller right block chunk
                int endOfRightBlock = rightCursor;
                while (endOfRightBlock < end && endOfRightBlock < rightCursor + blockSize) {
                    this.numberOfComparisons++;
                    if (array[endOfRightBlock] < array[leftCursor]) {
                        endOfRightBlock++;
                    } else {
                        break;
                    }
                }

                int rightBlockLen = endOfRightBlock - rightCursor;
                // Rotate the smaller right chunk cleanly into place ahead of leftCursor using pointer reversals
                rotateRangeInPlace(array, leftCursor, rightCursor - 1, endOfRightBlock - 1);

                // Shift tracking markers forward to maintain geometric alignment
                leftCursor += rightBlockLen;
                mid += rightBlockLen;
                rightCursor += rightBlockLen;
            }
        }
    }

    // In-place rotation via three sequential array pointer reflections
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
