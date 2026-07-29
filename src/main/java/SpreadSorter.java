class SpreadSorter implements Sorter {
    // Sizing threshold constant: blocks smaller than this fallback to insertion sort
    private final int INSERTION_THRESHOLD = 16;

    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Spread Sort (b = n/10)";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        int[] buffer = new int[numItems];
        spreadSortRecursive(array, buffer, 0, numItems);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void spreadSortRecursive(int[] array, int[] buffer, int start, int end) {
        int length = end - start;

        // Fallback Step 1: Small sub-arrays are processed instantly via Insertion Sort
        if (length < INSERTION_THRESHOLD) {
            localInsertionSort(array, start, end);
            return;
        }

        // 2. Identify the minimum and maximum boundaries to analyze distribution
        int min = array[start];
        int max = array[start];

        for (int i = start + 1; i < end; i++) {
            this.numberOfComparisons++;
            if (array[i] < min) {
                min = array[i];
            }
            this.numberOfComparisons++;
            if (array[i] > max) {
                max = array[i];
            }
        }

        // If min equals max, all elements are identical and already sorted
        if (min == max) {
            return;
        }

        // 3. Define the number of buckets based on the b = n/10 formula profile
        int numBuckets = length / 10;
        if (numBuckets < 2) numBuckets = 2; // Enforce minimum active bucket ceiling

        // Calculate a safe mathematical divisor multiplier step
        double div = (double) numBuckets / ((double) max - min);

        // Track bucket item frequency distribution sizes
        int[] count = new int[numBuckets];
        for (int i = start; i < end; i++) {
            int bucketIdx = (int) (div * (array[i] - min));
            if (bucketIdx >= numBuckets) bucketIdx = numBuckets - 1; // Guard index bounds
            count[bucketIdx]++;
        }

        // Transform frequencies into precise starting marker offset indexes
        int[] bucketOffsets = new int[numBuckets];
        bucketOffsets[0] = start;
        for (int i = 1; i < numBuckets; i++) {
            bucketOffsets[i] = bucketOffsets[i - 1] + count[i - 1];
        }

        // Duplicate the offsets to maintain a running placement tracker
        int[] currentOffsets = new int[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            currentOffsets[i] = bucketOffsets[i];
        }

        // 4. Redistribute elements from the original array into our sorted bucket buffer
        for (int i = start; i < end; i++) {
            int bucketIdx = (int) (div * (array[i] - min));
            if (bucketIdx >= numBuckets) bucketIdx = numBuckets - 1;
            
            int destIdx = currentOffsets[bucketIdx];
            buffer[destIdx] = array[i];
            currentOffsets[bucketIdx]++;
            this.numberOfExchanges++;
        }

        // Flush elements back down to our primary target array boundaries
        for (int i = start; i < end; i++) {
            array[i] = buffer[i];
            this.numberOfExchanges++;
        }

        // 5. Recursively sort each non-empty bucket branch individually
        for (int i = 0; i < numBuckets; i++) {
            int bStart = bucketOffsets[i];
            int bEnd = (i == numBuckets - 1) ? end : bucketOffsets[i + 1];
            
            if (bEnd - bStart > 1) {
                // If a sub-bucket still clusters heavily, the next step naturally flattens it
                spreadSortRecursive(array, buffer, bStart, bEnd);
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
