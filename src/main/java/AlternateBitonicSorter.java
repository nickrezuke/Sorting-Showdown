class AlternateBitonicSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Alternate Bitonic Sort";
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
                paddedArray[i] = Integer.MAX_VALUE; // Sentinel placeholder
            }
        }

        // 3. Alternate (Weave) Network Logic
        // The loops alternate the stride layouts to interleave the comparison groups
        for (int i = 2; i <= paddedSize; i <<= 1) {
            for (int j = i >> 1; j > 0; j >>= 1) {
                for (int m = 0; m < paddedSize; m++) {
                    
                    // Bitwise logic calculates the paired interleaved index wire
                    int partnerIdx = m ^ j;
                    
                    if (partnerIdx > m) {
                        // The weave mapping alters the masking key rule 
                        // to flip comparison gates based on interleaved positions
                        if ((m & i) == 0) {
                            // Ascending Gate Check
                            this.numberOfComparisons++;
                            if (paddedArray[m] > paddedArray[partnerIdx]) {
                                int temp = paddedArray[m];
                                paddedArray[m] = paddedArray[partnerIdx];
                                paddedArray[partnerIdx] = temp;
                                this.numberOfExchanges++;
                            }
                        } else {
                            // Descending Gate Check
                            this.numberOfComparisons++;
                            if (paddedArray[m] < paddedArray[partnerIdx]) {
                                int temp = paddedArray[m];
                                paddedArray[m] = paddedArray[partnerIdx];
                                paddedArray[partnerIdx] = temp;
                                this.numberOfExchanges++;
                            }
                        }
                    }
                }
            }
        }

        // 4. Extract the sorted elements back into your original primitive array
        for (int i = 0; i < numItems; i++) {
            array[i] = paddedArray[i];
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }
}
