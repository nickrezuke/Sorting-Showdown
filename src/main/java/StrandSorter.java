class StrandSorter implements Sorter {
    public String getName() {
        return "Strand Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, numberOfComparisons, numberOfExchanges);
        }

        // To avoid imports, we track which elements have been pulled out into strands
        boolean[] removed = new boolean[numItems];
        int itemsRemaining = numItems;

        // Allocation arrays for merging
        int[] result = new int[numItems];
        int resultSize = 0;

        int[] strand = new int[numItems];

        while (itemsRemaining > 0) {
            int strandSize = 0;

            // Step 1 & 2: Pull out a sorted strand from the remaining items
            for (int i = 0; i < numItems; i++) {
                if (!removed[i]) {
                    // If strand is empty, automatically take the first available item
                    if (strandSize == 0) {
                        strand[strandSize++] = array[i];
                        removed[i] = true;
                        itemsRemaining--;
                    } else {
                        // Compare current item to the tail end of our growing strand
                        numberOfComparisons++;
                        if (array[i] >= strand[strandSize - 1]) {
                            strand[strandSize++] = array[i];
                            removed[i] = true;
                            itemsRemaining--;
                        }
                    }
                }
            }

            // Step 3: Merge the fresh strand into our running result array
            int[] tempMerge = new int[resultSize + strandSize];
            int i = 0, j = 0, k = 0;

            while (i < resultSize && j < strandSize) {
                numberOfComparisons++;
                if (result[i] <= strand[j]) {
                    tempMerge[k++] = result[i++];
                } else {
                    tempMerge[k++] = strand[j++];
                }
                numberOfExchanges++;
            }

            while (i < resultSize) {
                tempMerge[k++] = result[i++];
                numberOfExchanges++;
            }
            while (j < strandSize) {
                tempMerge[k++] = strand[j++];
                numberOfExchanges++;
            }

            // Copy back the temporary merged list into our primary result container
            for (int m = 0; m < tempMerge.length; m++) {
                result[m] = tempMerge[m];
            }
            resultSize = tempMerge.length;
        }

        // Overwrite original array parameters with sorted results
        for (int i = 0; i < numItems; i++) {
            array[i] = result[i];
        }

        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}
