public class BogoSorter implements Sorter {
    public String getName() {
        return "Bogo Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        boolean isSorted = true;

        // Actually check if it is...
        for (int i = 0; i < array.length - 1; i++) {
            numberOfComparisons++; // Comparison on next line
            if (array[i] > array[i + 1]) {
                isSorted = false;
            }
        }

        while (!isSorted) {
            // Fisher Yates Shuffle the array
            for (int i = array.length - 1; i > 0; i--) {
                int j = (int) (Math.random() * (i + 1));
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                numberOfExchanges++;
            }

            // If that shuffle just so happened to sort the list, mark that down
            isSorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                numberOfComparisons++; // Comparison on next line
                if (array[i] > array[i + 1]) {
                    isSorted = false;
                }
            }
        }

        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}