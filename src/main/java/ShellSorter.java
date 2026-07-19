class ShellSorter implements Sorter {
    // For Shell Sort, we use private global variables to keep track of comparisons
    // and exchanges since we call a helper function to do segmented insertion sort
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Shell Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        numberOfComparisons = 0;
        numberOfExchanges = 0;

        // Start with an h
        int h = numItems / 2;

        while (h > 0) {
            SegmentedInsertionSort(array, numItems, h);
            if (h == 2) {
                h = 1;
            } else {
                h = (int) (h / 2.25);
            }
        }

        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }

    // A helper method to perform an insertion sort on a segment of the array
    // defined by the gap h, which is used in shell sort
    private void SegmentedInsertionSort(int[] array, int numElements, int h) {
        for (int i = h; i < numElements; i++) {
            int current = array[i];
            int j = i;
            while (j > h - 1) {
                numberOfComparisons++; // Count this as a comparison
                if(array[j - h] > current) {
                    array[j] = array[j - h];
                    j = j - h;
                    numberOfExchanges++; // Count this as an exchange
                } else {
                    break; // If the current element is not less than the compared element, we can stop
                }
            }
            array[j] = current;
            // Count this last move as an exchange if we actually moved the current element
            if (j != i) {
                numberOfExchanges++;    
            }
        }
    }
}