class IterativeBitonicSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Iterative Bitonic Sort";
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

        // 2. Build the padded array using max value placeholders for empty slots
        int[] paddedArray = new int[paddedSize];
        for (int i = 0; i < paddedSize; i++) {
            if (i < numItems) {
                paddedArray[i] = array[i];
            } else {
                paddedArray[i] = Integer.MAX_VALUE; // Sentinel value to float to the end
            }
        }

        // 3. Iterative Sorting Network Stage Logic
        // 'k' is the length of the bitonic stage currently being built (2, 4, 8, ...)
        for (int k = 2; k <= paddedSize; k *= 2) {
            
            // 'j' is the comparison stride/gap distance, decreasing within each stage
            for (int j = k / 2; j > 0; j /= 2) {
                
                // Sweep through the entire padded array applying network gates
                for (int i = 0; i < paddedSize; i++) {
                    
                    // Determine the target paired element across the stride gap
                    int ixj = i ^ j; // Bitwise XOR calculates the paired index
                    
                    // To prevent checking the same pair twice, only evaluate when ixj > i
                    if (ixj > i) {
                        
                        // Bitwise AND determines the desired sorting direction for this stage.
                        // If the bit matches 0, we sort ascending. If it matches, we sort descending.
                        if ((i & k) == 0) {
                            // Ascending Direction
                            this.numberOfComparisons++;
                            if (paddedArray[i] > paddedArray[ixj]) {
                                int temp = paddedArray[i];
                                paddedArray[i] = paddedArray[ixj];
                                paddedArray[ixj] = temp;
                                this.numberOfExchanges++;
                            }
                        } else {
                            // Descending Direction
                            this.numberOfComparisons++;
                            if (paddedArray[i] < paddedArray[ixj]) {
                                int temp = paddedArray[i];
                                paddedArray[i] = paddedArray[ixj];
                                paddedArray[ixj] = temp;
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
