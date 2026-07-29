class SlowSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Slow Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        slowSortRecursive(array, 0, numItems - 1);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void slowSortRecursive(int[] array, int low, int high) {
        // Base case: if pointers cross or point to the same slot, stop recursing
        if (low >= high) {
            return;
        }

        int mid = low + (high - low) / 2;

        // Step 1: Recursively sort both structural halves in isolation
        slowSortRecursive(array, low, mid);
        slowSortRecursive(array, mid + 1, high);

        // Step 2: Compare the far ends of the sorted halves and swap the larger element to the right
        this.numberOfComparisons++;
        if (array[mid] > array[high]) {
            int temp = array[mid];
            array[mid] = array[high];
            array[high] = temp;
            this.numberOfExchanges++;
        }

        // Step 3: Recurse over the remaining scrambled sub-array window
        slowSortRecursive(array, low, high - 1);
    }
}
