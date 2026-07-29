class TimSorter implements Sorter {
    // Structural tuning constant: ideal run size should be a power of 2 between 32 and 64
    private final int MIN_MERGE = 32;

    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Tim Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // 1. Calculate the ideal minimum run length for balanced merging
        int minRun = calculateMinRun(numItems);

        // 2. Scan and build runs, padding short ones using Binary Insertion Sort
        int i = 0;
        // Stack to store run boundaries: runBase[idx] and runLen[idx]
        int[] runBase = new int[numItems];
        int[] runLen = new int[numItems];
        int stackSize = 0;

        while (i < numItems) {
            int runLength = countRunAndMakeAscending(array, i, numItems);

            // If the natural run is too short, force-extend it using Binary Insertion Sort
            if (runLength < minRun) {
                int forceLen = Math.min(minRun, numItems - i);
                binaryInsertionSortRange(array, i, i + forceLen, i + runLength);
                runLength = forceLen;
            }

            // Push this run onto our structural tracker stack
            runBase[stackSize] = i;
            runLen[stackSize] = runLength;
            stackSize++;

            // Step 3: Collapse and merge stack items to preserve strict balance rules
            stackSize = collapseStack(array, runBase, runLen, stackSize);

            i += runLength;
        }

        // Force-merge any remaining runs left over on the stack
        while (stackSize > 1) {
            mergeAt(array, runBase, runLen, stackSize - 2);
            stackSize--;
            // Shift the top item down over the merged zone
            runBase[stackSize - 1] = runBase[stackSize];
            runLen[stackSize - 1] = runLen[stackSize];
        }

        // Here, the list is sorted
        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    // Finds the natural run length. If it is descending, it reverses it in place.
    private int countRunAndMakeAscending(int[] array, int lo, int hi) {
        if (lo == hi - 1) return 1;
        int runHi = lo + 1;

        this.numberOfComparisons++;
        if (array[runHi] < array[lo]) { // Descending run detected
            while (runHi < hi && array[runHi] < array[runHi - 1]) {
                this.numberOfComparisons++;
                runHi++;
            }
            if (runHi < hi) this.numberOfComparisons++; // Count the failing loop comparison
            reverseRange(array, lo, runHi);
        } else { // Ascending run detected
            while (runHi < hi && array[runHi] >= array[runHi - 1]) {
                this.numberOfComparisons++;
                runHi++;
            }
            if (runHi < hi) this.numberOfComparisons++;
        }
        return runHi - lo;
    }

    // Reverses a subset range in place to convert a descending run to ascending
    private void reverseRange(int[] array, int lo, int hi) {
        hi--;
        while (lo < hi) {
            int temp = array[lo];
            array[lo] = array[hi];
            array[hi] = temp;
            this.numberOfExchanges++;
            lo++;
            hi--;
        }
    }

    // Custom Binary Insertion Sort optimized to fill out a partially sorted range
    private void binaryInsertionSortRange(int[] array, int lo, int hi, int start) {
        if (start == lo) start++;
        for (; start < hi; start++) {
            int key = array[start];
            int left = lo;
            int right = start - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                this.numberOfComparisons++;
                if (array[mid] > key) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            int j = start - 1;
            while (j >= left) {
                array[j + 1] = array[j];
                this.numberOfExchanges++;
                j--;
            }
            array[left] = key;
        }
    }

    // Checks the stack rules (A > B + C and B > C) and merges if they are violated
    private int collapseStack(int[] array, int[] runBase, int[] runLen, int stackSize) {
        while (stackSize > 1) {
            int n = stackSize - 2;
            // Check Rule 1: A <= B + C (where C is top, B is mid, A is low)
            if (n > 0 && runLen[n - 1] <= runLen[n] + runLen[n + 1]) {
                if (runLen[n - 1] < runLen[n + 1]) n--;
                mergeAt(array, runBase, runLen, n);
                stackSize--;
            } 
            // Check Rule 2: B <= C
            else if (runLen[n] <= runLen[n + 1]) {
                mergeAt(array, runBase, runLen, n);
                stackSize--;
            } else {
                break; // Both rules are satisfied; stack is completely stable
            }
        }
        return stackSize;
    }

    // Merges adjacent runs at stack position 'i' and 'i+1'
    private void mergeAt(int[] array, int[] runBase, int[] runLen, int i) {
        int base1 = runBase[i];
        int len1 = runLen[i];
        int base2 = runBase[i + 1];
        int len2 = runLen[i + 1];

        // Update the stack coordinates to reflect the combined run size
        runLen[i] = len1 + len2;
        if (i == runLen.length - 2) {
            runBase[i + 1] = runBase[i + 2];
            runLen[i + 1] = runLen[i + 2];
        }

        // Standard two-pointer merge implementation using a local temporary buffer array
        int[] temp = new int[len1];
        for (int k = 0; k < len1; k++) {
            temp[k] = array[base1 + k];
            this.numberOfExchanges++;
        }

        int cursor1 = 0;
        int cursor2 = base2;
        int dest = base1;

        while (cursor1 < len1 && cursor2 < base2 + len2) {
            this.numberOfComparisons++;
            if (temp[cursor1] <= array[cursor2]) {
                array[dest++] = temp[cursor1++];
            } else {
                array[dest++] = array[cursor2++];
            }
            this.numberOfExchanges++;
        }

        // Copy any remaining elements left over in our temporary buffer
        while (cursor1 < len1) {
            array[dest++] = temp[cursor1++];
            this.numberOfExchanges++;
        }
    }

    // Determines the ideal minRun size based on bitwise operations
    private int calculateMinRun(int n) {
        int r = 0;
        while (n >= MIN_MERGE) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }
}
