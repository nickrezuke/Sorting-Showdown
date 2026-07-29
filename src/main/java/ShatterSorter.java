class ShatterSorter implements Sorter {
    private final int SHATTER_RADIX = 128; // 7-bit fragmentation block window
    private final int BITS_PER_PASS = 7;

    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Shatter Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // 1. Establish the maximum bit-depth range of our active dataset
        int maxVal = array[0];
        for (int i = 1; i < numItems; i++) {
            if (array[i] > maxVal) {
                maxVal = array[i];
            }
        }

        int maxBits = 0;
        while (maxVal > 0) {
            maxBits++;
            maxVal >>= 1;
        }

        int startShift = ((maxBits - 1) / BITS_PER_PASS) * BITS_PER_PASS;
        if (startShift < 0) startShift = 0;

        // Allocate local primitive auxiliary buffer tracking matrices
        int[] buffer = new int[numItems];

        // 2. Execute the recursive shatter distribution engine
        shatterSortRecursive(array, buffer, 0, numItems, startShift);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void shatterSortRecursive(int[] array, int[] buffer, int start, int end, int shift) {
        if (start >= end - 1 || shift < 0) {
            return;
        }

        // 1. Generate the fragmentation histogram count
        int[] count = new int[SHATTER_RADIX];
        for (int i = start; i < end; i++) {
            int fragmentIdx = (array[i] >> shift) & 0x7F;
            count[fragmentIdx]++;
        }

        // 2. Transform counts into precise index boundary configurations
        int[] offsets = new int[SHATTER_RADIX];
        offsets[0] = start; // Secure the initial array index tracking slot
        for (int i = 1; i < SHATTER_RADIX; i++) {
            offsets[i] = offsets[i - 1] + count[i - 1];
        }

        // Duplicate the boundary markers to serve as our active insertion pointers
        int[] activePointers = new int[SHATTER_RADIX];
        for (int i = 0; i < SHATTER_RADIX; i++) {
            activePointers[i] = offsets[i];
        }

        // 3. Shatter and distribute elements into our temporary buffer pool
        for (int i = start; i < end; i++) {
            int fragmentIdx = (array[i] >> shift) & 0x7F;
            int destinationIdx = activePointers[fragmentIdx];
            buffer[destinationIdx] = array[i];
            activePointers[fragmentIdx]++;
            this.numberOfExchanges++;
        }

        // 4. Flush the shattered fragments cleanly back into the primary list segment
        for (int i = start; i < end; i++) {
            array[i] = buffer[i];
            this.numberOfExchanges++;
        }

        // 5. Local Stabilization & Recursive Refinement Phase
        // Slide focus down to the next lower bit-segment layer
        int nextShift = shift - BITS_PER_PASS;
        for (int i = 0; i < SHATTER_RADIX; i++) {
            int subStart = offsets[i];
            int subEnd = (i == SHATTER_RADIX - 1) ? end : offsets[i + 1];

            if (subEnd - subStart > 1) {
                // If the fragment slice is small, optimize processing via local insertion
                if (subEnd - subStart < 16) {
                    localInsertionSort(array, subStart, subEnd);
                } else {
                    shatterSortRecursive(array, buffer, subStart, subEnd, nextShift);
                }
            }
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
