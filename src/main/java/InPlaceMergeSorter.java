class InPlaceMergeSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "In-Place Merge Sort (Basic)";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        inPlaceMergeSortRecursive(array, 0, numItems - 1);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void inPlaceMergeSortRecursive(int[] array, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;

            // Divide the list into left and right halves recursively
            inPlaceMergeSortRecursive(array, low, mid);
            inPlaceMergeSortRecursive(array, mid + 1, high);

            // Merge the sorted halves completely in place
            inPlaceMerge(array, low, mid, high);
        }
    }

    private void inPlaceMerge(int[] array, int start, int mid, int end) {
        int leftCursor = start;
        int rightCursor = mid + 1;

        // Loop runs until one of the two sub-arrays is exhausted
        while (leftCursor <= mid && rightCursor <= end) {
            this.numberOfComparisons++;
            
            // Case 1: Left item is smaller or equal. It's already in position.
            if (array[leftCursor] <= array[rightCursor]) {
                leftCursor++;
            } else {
                // Case 2: Right item is smaller. We must slide it all the way down to leftCursor.
                int smallerValue = array[rightCursor];
                int index = rightCursor;

                // Shift all elements between leftCursor and rightCursor to the right by 1
                while (index > leftCursor) {
                    array[index] = array[index - 1];
                    this.numberOfExchanges++;
                    index--;
                }
                
                // Drop the smaller element into its new sorted index position
                array[leftCursor] = smallerValue;

                // Advance all tracking pointers to adjust for the shifted layout
                leftCursor++;
                mid++;
                rightCursor++;
            }
        }
    }
}
