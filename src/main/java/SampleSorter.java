class SampleSorter implements Sorter {
    private final int INSERTION_THRESHOLD = 16;

    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Sample Sort (b = n/10, s = 2)";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        int[] buffer = new int[numItems];
        sampleSortRecursive(array, buffer, 0, numItems);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void sampleSortRecursive(int[] array, int[] buffer, int start, int end) {
        int length = end - start;

        // Fallback Step 1: Small sub-arrays default to a localized Insertion Sort
        if (length < INSERTION_THRESHOLD) {
            localInsertionSort(array, start, end);
            return;
        }

        // 2. Define our bucket count and sample sizing boundaries (b = n/10, s = 2)
        int numBuckets = length / 10;
        if (numBuckets < 2) numBuckets = 2; // Enforce a minimum bucket ceiling
        
        int sampleMultiplier = 2; 
        int sampleSize = numBuckets * sampleMultiplier;
        if (sampleSize >= length) {
            localInsertionSort(array, start, end);
            return;
        }

        // 3. The Sampling Phase: Extract elements to form our sample array
        int[] sample = new int[sampleSize];
        // For simplicity and structural stability, we pull evenly spaced elements
        int stride = length / sampleSize;
        for (int i = 0; i < sampleSize; i++) {
            sample[i] = array[start + i * stride];
        }

        // Sort our tiny sample in isolation
        localInsertionSort(sample, 0, sampleSize);

        // Extract our evenly spaced splitters (pivots) from the sorted sample
        int numSplitters = numBuckets - 1;
        int[] splitters = new int[numSplitters];
        for (int i = 0; i < numSplitters; i++) {
            splitters[i] = sample[(i + 1) * sampleMultiplier - 1];
        }

        // 4. Bucket Frequency Mapping Phase
        int[] count = new int[numBuckets];
        for (int i = start; i < end; i++) {
            int bucketIdx = determineBucket(array[i], splitters);
            count[bucketIdx]++;
        }

        // Transform frequencies into precise starting marker index offsets
        int[] bucketOffsets = new int[numBuckets];
        bucketOffsets[0] = start; // FIXED LINE HERE
        for (int i = 1; i < numBuckets; i++) {
            bucketOffsets[i] = bucketOffsets[i - 1] + count[i - 1];
        }

        // Duplicate the offsets to maintain an active item placement tracker
        int[] currentOffsets = new int[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            currentOffsets[i] = bucketOffsets[i];
        }

        // 5. Distribute elements into our safe secondary buffer array
        for (int i = start; i < end; i++) {
            int bucketIdx = determineBucket(array[i], splitters);
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

        // 6. Recursively clean up each resulting bucket partition block independently
        for (int i = 0; i < numBuckets; i++) {
            int bStart = bucketOffsets[i];
            int bEnd = (i == numBuckets - 1) ? end : bucketOffsets[i + 1];
            
            if (bEnd - bStart > 1) {
                sampleSortRecursive(array, buffer, bStart, bEnd);
            }
        }
    }

    // Binary search helper to rapidly route an element to its target splitter bucket
    private int determineBucket(int value, int[] splitters) {
        int low = 0;
        int high = splitters.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            this.numberOfComparisons++;
            if (splitters[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low; // Points directly to the chosen bucket index range layout
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
