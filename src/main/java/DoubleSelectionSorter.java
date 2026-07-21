class DoubleSelectionSorter implements Sorter {
    public String getName() {
        return "Double Selection Sort";
    }

    public SortResult sort(int[] array) {
        int numComparisons = 0;
        int numExchanges = 0;

        // The edges
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int minIndex = left;
            int maxIndex = left;

            // Find both minimum and maximum in the remaining unsorted subarray
            for (int i = left + 1; i <= right; i++) {
                numComparisons++; // Compairing for minimum
                if (array[i] < array[minIndex]) {
                    minIndex = i;
                }
                
                numComparisons++; // Compairing for maximum
                if (array[i] > array[maxIndex]) {
                    maxIndex = i;
                }
            }

            // Swap the minimum element to the left boundary
            if (minIndex != left) {
                int temp = array[left];
                array[left] = array[minIndex];
                array[minIndex] = temp;
                numExchanges++;

                // If the max element was originally at 'left', it just got swapped 
                // to 'minIndex', so now we need to update maxIndex to reflect this
                if (maxIndex == left) {
                    maxIndex = minIndex;
                }
            }

            // Swap the maximum element to the right boundary
            if (maxIndex != right) {
                int temp = array[right];
                array[right] = array[maxIndex];
                array[maxIndex] = temp;
                numExchanges++; 
            }

            // Shrink the unsorted window from both sides & continue the loop
            left++;
            right--;
        }

        // Here, the list is sorted
        return new SortResult(array, numComparisons, numExchanges);
    }
}
