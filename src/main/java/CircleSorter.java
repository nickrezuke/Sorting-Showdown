class CircleSorter implements Sorter {
    private int numComparisons;
    private int numExchanges;

    public String getName() {
        return "Circle Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numComparisons = 0;
        this.numExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Keep running full circular passes until no more swaps happen
        boolean swapped;
        do {
            swapped = circleSortRecursive(array, 0, numItems - 1);
        } while (swapped);

        return new SortResult(array, this.numComparisons, this.numExchanges);
    }

    private boolean circleSortRecursive(int[] array, int low, int high) {
        // Base case: if pointers meet or cross, this sub-circle is done
        if (low >= high) {
            return false;
        }

        boolean swapped = false;
        int start = low;
        int end = high;

        // Perform the concentric circle swaps from outside-in
        while (start < end) {
            this.numComparisons++;
            if (array[start] > array[end]) {
                // Swap the outer opposites
                int temp = array[start];
                array[start] = array[end];
                array[end] = temp;

                this.numExchanges++;
                swapped = true;
            }
            start++;
            end--;
        }

        // Special case: if the circle has an odd number of elements,
        // the middle element is left over. Compare it with the next element.
        if (start == end) {
            this.numComparisons++;
            if (array[start] > array[start + 1]) {
                int temp = array[start];
                array[start] = array[start + 1];
                array[start + 1] = temp;

                this.numExchanges++;
                swapped = true;
            }
        }

        // Recursively split the segment into two smaller sub-circles
        int mid = low + (high - low) / 2;
        boolean leftSubCircleSwapped = circleSortRecursive(array, low, mid);
        boolean rightSubCircleSwapped = circleSortRecursive(array, mid + 1, high);

        // Return true if ANY swap happened in this circle or its sub-circles
        return swapped || leftSubCircleSwapped || rightSubCircleSwapped;
    }
}
