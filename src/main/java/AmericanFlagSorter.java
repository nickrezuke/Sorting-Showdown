class AmericanFlagSorter implements Sorter {
    // A radix base of 128 (7 bits per digit block) provides an exceptional performance balance
    private final int RADIX_BASE = 128; 
    private final int BITS_PER_DIGIT = 7;

    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "American Flag Sort (b = 128)";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // 1. Find the maximum value in the array to determine the maximum bit depth shift needed
        int maxVal = array[0];
        for (int i = 1; i < numItems; i++) {
            if (array[i] > maxVal) {
                maxVal = array[i];
            }
        }

        // Calculate the highest active bit position
        int maxBits = 0;
        while (maxVal > 0) {
            maxBits++;
            maxVal >>= 1;
        }

        // Determine the starting bit shift position for our most significant digit chunk
        int maxShift = ((maxBits - 1) / BITS_PER_DIGIT) * BITS_PER_DIGIT;
        if (maxShift < 0) maxShift = 0;

        // Kick off the in-place recursive flag distribution engine
        americanFlagSortRecursive(array, 0, numItems, maxShift);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void americanFlagSortRecursive(int[] array, int start, int end, int shift) {
        // Base case: segments with 1 or 0 elements are already sorted, or we've processed all digits
        if (start >= end - 1 || shift < 0) {
            return;
        }

        // 1. The Count Histogram phase
        int[] count = new int[RADIX_BASE];
        for (int i = start; i < end; i++) {
            int digit = getDigitValue(array[i], shift);
            count[digit]++;
        }

        // 2. Compute the precise bucket boundary offset positions
        int[] bucketOffsets = new int[RADIX_BASE];
        int[] nextBucketOffsets = new int[RADIX_BASE];
        
        bucketOffsets[0] = start;
        nextBucketOffsets[0] = start;
        for (int i = 1; i < RADIX_BASE; i++) {
            bucketOffsets[i] = bucketOffsets[i - 1] + count[i - 1];
            nextBucketOffsets[i] = bucketOffsets[i];
        }

        // 3. In-Place Cyclic Swapping (The Flag Partition Step)
        for (int bucketIdx = 0; bucketIdx < RADIX_BASE; bucketIdx++) {
            // Process the current bucket until its elements have been completely cycled out
            while (nextBucketOffsets[bucketIdx] < (bucketIdx == RADIX_BASE - 1 ? end : bucketOffsets[bucketIdx + 1])) {
                int currIdx = nextBucketOffsets[bucketIdx];
                int val = array[currIdx];
                int targetBucket = getDigitValue(val, shift);

                if (targetBucket == bucketIdx) {
                    // This element already belongs in this bucket; advance the offset pointer
                    nextBucketOffsets[bucketIdx]++;
                } else {
                    // Displace the item resting at the target position via a cyclic swap step
                    int destIdx = nextBucketOffsets[targetBucket];
                    int temp = array[destIdx];
                    array[destIdx] = val;
                    array[currIdx] = temp;
                    
                    this.numberOfExchanges++;
                    // Advance the pointer of the bucket that just accepted an element
                    nextBucketOffsets[targetBucket]++;
                }
            }
        }

        // 4. Recursive Sub-Sorting: Run the engine down on each isolated bucket partition block
        // Shift focus down to the next lower digit block (less significant bits)
        int nextShift = shift - BITS_PER_DIGIT;
        for (int i = 0; i < RADIX_BASE; i++) {
            int bucketStart = bucketOffsets[i];
            int bucketEnd = (i == RADIX_BASE - 1) ? end : bucketOffsets[i + 1];
            
            if (bucketEnd - bucketStart > 1) {
                americanFlagSortRecursive(array, bucketStart, bucketEnd, nextShift);
            }
        }
    }

    // Helper method to extract the targeted digit chunk using bit manipulation masks
    private int getDigitValue(int value, int shift) {
        // Extract 7 bits from the shifted position and isolate them via a mask of 127 (0x7F)
        return (value >> shift) & 0x7F;
    }
}
