class BinaryInsertionSorter implements Sorter {
    public String getName() {
        return "Binary Insertion Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        for (int index = 1; index < numItems; index++) {
            // Everything to the left of index is sorted
            int key = array[index];

            // Binary Search to find the correct insertion position
            int low = 0;
            int high = index - 1;

            while (low <= high) {
                int mid = low + (high - low) / 2;
                numberOfComparisons++; // Count the binary search comparison

                if (array[mid] > key) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            // 'low' is now the index where 'key' belongs
            // Shift elements to the right to make room for the key
            int j = index - 1;
            while (j >= low) {
                array[j + 1] = array[j];
                numberOfExchanges++; // Tracks single-element array movements/shifts
                j--;
            }

            // Insert the key into its proper location
            array[low] = key;
        }
        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}
