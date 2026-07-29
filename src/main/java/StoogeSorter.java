class StoogeSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Stooge Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        stoogeSortRecursive(array, 0, numItems - 1);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void stoogeSortRecursive(int[] array, int low, int high) {
        this.numberOfComparisons++;
        // Step 1: Compare and swap the outer boundaries if they are out of order
        if (array[low] > array[high]) {
            int temp = array[low];
            array[low] = array[high];
            array[high] = temp;
            this.numberOfExchanges++;
        }

        // Base case: if there are fewer than 3 elements total, this sub-segment is sorted
        if (high - low + 1 < 3) {
            return;
        }

        // Step 2: Calculate the 1/3rd offset fraction parameter step size
        int oneThirdOffset = (high - low + 1) / 3;

        // Step 3: Run the mandatory triple-recursive overlapping passes
        stoogeSortRecursive(array, low, high - oneThirdOffset);       // First two-thirds
        stoogeSortRecursive(array, low + oneThirdOffset, high);       // Last two-thirds
        stoogeSortRecursive(array, low, high - oneThirdOffset);       // First two-thirds again
    }
}
