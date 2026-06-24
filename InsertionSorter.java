class InsertionSorter implements Sorter {
    public String getName() {
        return "\u21DF Insertion Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        for (int index = 1; index < numItems; index++) {
            // everything to the left of index is sorted

            // Lets consider the item at index, and insert it into the sorted portion
            int key = array[index];

            // j represents the index of the last item in the sorted portion
            int j = index - 1;

            while (j >= 0 && array[j] > key) {
                // Perform Exchanges as the while loop condition, which is already inside a for
                // loop (Meaning O(n^2) comparisons and exchanges in average case)
                numberOfComparisons++;

                // Shift the item at j to the right (Happens inside both loops so O(n^2)
                // exchanges in average case)
                numberOfExchanges++;
                array[j + 1] = array[j];
                j = j - 1;
            }
            // If the above while loop fails because of the array[j] > key condition, it
            // still made a comparison that was false,
            // specifically it performed a comparison and found that array[j] <= key,
            // meaning that the loop never enters,
            // so we still need to count that failed comparison by adding one if that was
            // the case.
            if (j >= 0) {
                numberOfComparisons++;
            }

            // Insert the key into its proper location
            array[j + 1] = key;
        }
        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}