class SelectionSorter implements Sorter {
    public String getName() {
        return "\u2316 Selection Sort";
    }

    public SortResult sort(int[] array) {
        int temp;
        int numItems = array.length;
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        for (int pass = 0; pass < numItems - 1; pass++) {
            int min = pass;
            for (int j = pass + 1; j < numItems; j++) {
                // Perform a Comparison (Inside both for loops, so O(n^2) comparisons in avg
                // case)
                numberOfComparisons++;
                if (array[j] < array[min]) {
                    min = j;
                }
            }

            // Perform an Exchange (Inside the first for loop, so O(n) exchanges in avg
            // case)
            numberOfExchanges++;
            temp = array[pass];
            array[pass] = array[min];
            array[min] = temp;
        }
        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}