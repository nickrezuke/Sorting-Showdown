public class BrickSorter implements Sorter {
    public String getName() {
        return "Brick Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        boolean isSorted = false; // Start unsorted

        // Edge case: 0 or 1 elements are already sorted
        if (array == null || array.length <= 1) {
            return new SortResult(array, numberOfComparisons, numberOfExchanges);
        }

        while (!isSorted) { // While we aren't sorted, keep going
            isSorted = true; // Naievely set to true, will be reset later on if needed

            // Odd Phase
            for (int i = 1; i < array.length - 1; i += 2) {
                numberOfComparisons++; // About to make a comparison
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    numberOfExchanges++;
                    isSorted = false; // We just had to swap, so set this
                }
            }

            // Even Phase
            for (int i = 0; i < array.length - 1; i += 2) {
                numberOfComparisons++; // About to make a comparison
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    numberOfExchanges++;
                    isSorted = false; // We just had to swap, so set this
                }
            }
        }
        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}