class InsertionSorter implements Sorter {
    public String getName() {
        return "Insertion Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        for (int index = 1; index < numItems; index++) {
            // Everything to the left of index is sorted
            int key = array[index];
            int j = index - 1;

            while (j >= 0) {
                // Count the comparison about to happen between array[j] and key
                numberOfComparisons++;

                if (array[j] > key) {
                    array[j + 1] = array[j];
                    numberOfExchanges++;
                    j--;
                } else {
                    // The element is in the correct position relative to the sorted subarray
                    break;
                }
            }

            // Insert the key into its proper location
            array[j + 1] = key;
        }
        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}