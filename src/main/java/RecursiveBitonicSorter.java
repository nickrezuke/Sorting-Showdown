class RecursiveBitonicSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Bitonic Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // 1. Bitonic Sort strictly requires a length that is a power of 2.
        // We find the next highest power of 2 to pad the array safely.
        int paddedSize = 1;
        while (paddedSize < numItems) {
            paddedSize <<= 1;
        }

        // 2. Build the padded array using max value placeholders for empty slots
        int[] paddedArray = new int[paddedSize];
        for (int i = 0; i < paddedSize; i++) {
            if (i < numItems) {
                paddedArray[i] = array[i];
            } else {
                paddedArray[i] = Integer.MAX_VALUE; // Sentinel value to float to the end
            }
        }

        // 3. Kick off the recursive bitonic sorting network over the full padded space
        // 1 indicates an ascending sort direction
        bitonicSort(paddedArray, 0, paddedSize, 1);

        // 4. Extract the sorted elements back into your original primitive array
        for (int i = 0; i < numItems; i++) {
            array[i] = paddedArray[i];
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    // Recursively produces a bitonic sequence by sorting halves in opposite directions
    private void bitonicSort(int[] arr, int low, int cnt, int dir) {
        if (cnt > 1) {
            int k = cnt / 2;

            // Sort the left half in ascending order (1)
            bitonicSort(arr, low, k, 1);

            // Sort the right half in descending order (0)
            bitonicSort(arr, low + k, k, 0);

            // Merge the resulting halves back together in the target direction
            bitonicMerge(arr, low, cnt, dir);
        }
    }

    // Recursively splits and merges a bitonic sequence based on a fixed gap size
    private void bitonicMerge(int[] arr, int low, int cnt, int dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            
            // Compare and swap elements across the current stride distance gap
            for (int i = low; i < low + k; i++) {
                compareAndSwap(arr, i, i + k, dir);
            }

            // Continue merging the broken sub-segments recursively
            bitonicMerge(arr, low, k, dir);
            bitonicMerge(arr, low + k, k, dir);
        }
    }

    // The fundamental network gate comparison step
    private void compareAndSwap(int[] arr, int i, int j, int dir) {
        this.numberOfComparisons++;
        
        // If dir is 1, we want ascending (swap if left > right)
        // If dir is 0, we want descending (swap if left < right)
        if ((dir == 1 && arr[i] > arr[j]) || (dir == 0 && arr[i] < arr[j])) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            this.numberOfExchanges++;
        }
    }
}
