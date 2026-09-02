class CantBelieveItCanSorter implements Sorter {
    // This algorithm (discovered by Stanley P. Y. Fung) is famously brief but wildly inefficient. Unlike classic algorithms that constrain the inner loop relative to the outer loop (like j = i + 1), this algorithm loops completely over the entire array inside both nested structures.
    public String getName() {
        return "I Can't Believe It Can Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        // Loop i from [0 - array.length - 1]
        for (int i = 0; i < numItems; i++) {
            // Also Loop j from [0 - array.length - 1]....????
            for (int j = 0; j < numItems; j++) {
                numberOfComparisons++;
                if (array[i] < array[j]) {
                    // If they are in the CORRECT ORDER, we swap them OUT OF ORDER????
                    numberOfExchanges++;
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }

        // All we did was loop two variables over the whole array, 
        // and swap if NOT needed, but yet somehow it works....

        // I can't believe the list is sorted here!
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}
