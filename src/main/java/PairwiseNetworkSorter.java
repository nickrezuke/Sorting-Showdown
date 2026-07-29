class PairwiseNetworkSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Pairwise Sorting Network";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // 1. Find the next highest power of 2 to pad the array safely
        int paddedSize = 1;
        while (paddedSize < numItems) {
            paddedSize <<= 1;
        }

        // 2. Build the padded array using max value placeholders
        int[] paddedArray = new int[paddedSize];
        for (int i = 0; i < paddedSize; i++) {
            if (i < numItems) {
                paddedArray[i] = array[i];
            } else {
                paddedArray[i] = Integer.MAX_VALUE; // Sentinel value
            }
        }

        // 3. Pairwise Sorting Network Implementation
        // Phase 1: Establish initial localized sorted pairs
        for (int i = 1; i < paddedSize; i += 2) {
            compareAndSwap(paddedArray, i - 1, i);
        }

        // Phase 2: Layered network stride weaving loops
        for (int step = 2; step < paddedSize; step <<= 1) {
            for (int i = step; i < paddedSize; i += 2 * step) {
                for (int j = 0; j < step; j += 2) {
                    // Compare cross elements between neighboring blocks
                    compareAndSwap(paddedArray, i + j - 1, i + j);
                }
            }

            int Stride = step >> 1;
            while (Stride > 0) {
                for (int i = Stride; i < paddedSize; i += 2 * Stride) {
                    for (int j = 0; j < Stride; j++) {
                        // Weave discrepancies across the remaining network distance gaps
                        if ((i & step) == 0) {
                            compareAndSwap(paddedArray, i + j - Stride, i + j);
                        }
                    }
                }
                Stride >>= 1;
            }
        }

        // 4. Extract the sorted elements back into your original primitive array
        for (int i = 0; i < numItems; i++) {
            array[i] = paddedArray[i];
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void compareAndSwap(int[] arr, int i, int j) {
        this.numberOfComparisons++;
        if (arr[i] > arr[j]) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            this.numberOfExchanges++;
        }
    }
}
