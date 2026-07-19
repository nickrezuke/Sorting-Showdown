public class ExchangeSorter implements Sorter {
    public String getName() {
        return "Exchange Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                numberOfComparisons++; // Comparing on next line
                if (array[i] > array[j]) {
                    // Swap array[i] and array[j]
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    numberOfExchanges++; // Counting this swap
                }
            }
        }

        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}