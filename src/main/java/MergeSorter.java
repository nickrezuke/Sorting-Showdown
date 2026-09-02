class MergeSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfMoves;
    // We don't really "exchange" here so lets count the number
    // of times we "move" somthing into the destination array

    public String getName() { 
        return "Merge Sort"; 
    }

    // The base call to start the recursive merge sort
    public SortResult sort(int[] array) {
        numberOfComparisons = 0;
        numberOfMoves = 0;
        
        // Clone the original array so we don't mutate the user's input directly
        int[] workingArray = array.clone();
        int[] tempArray = new int[array.length]; 
        
        runMergeSort(workingArray, tempArray, 0, array.length - 1);
        
        return new SortResult(workingArray, numberOfComparisons, numberOfMoves);
    }

    // The recursive call that we call from inside the itself
    private void runMergeSort(int[] array, int[] temp, int lower, int upper) {
        if (lower < upper) {
            int mid = lower + ((upper - lower) / 2); // Safe average to avoid overflow

            // Recursively sort both halves in place inside 'array'
            runMergeSort(array, temp, lower, mid);
            runMergeSort(array, temp, mid + 1, upper);

            // Merge the sorted halves together
            merge(array, temp, lower, mid, upper);
        }
    }

    // Merges two sorted sub-arrays back into 'array'
    private void merge(int[] array, int[] temp, int lower, int mid, int upper) {
        // Copy the current segment into the temporary array
        for (int i = lower; i <= upper; i++) {
            temp[i] = array[i];
            numberOfMoves++; // Elements are moved into the helper array
        }

        int s1 = lower;      // Pointer for the first half
        int s2 = mid + 1;    // Pointer for the second half
        int d = lower;       // Pointer for writing back to the real array

        // While both halves have elements left, append the lowest
        while (s1 <= mid && s2 <= upper) {
            numberOfComparisons++;
            if (temp[s1] <= temp[s2]) {
                array[d++] = temp[s1++];
            } else {
                array[d++] = temp[s2++];
            }
            numberOfMoves++;
        }

        // Clean up remaining elements from the left half (if any)
        while (s1 <= mid) {
            array[d++] = temp[s1++];
            numberOfMoves++;
        }
    }
}
