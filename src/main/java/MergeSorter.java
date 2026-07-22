class MergeSorter implements Sorter {
    // For Merge Sort, I use private global variables to keep track of comparisons
    // and moves, and then increment them as we go along, since we call ourselves
    // recursively
    private static int numberOfComparisons;
    private static int numberOfMoves;
    // We don't really "exchange" here so lets count the number
    // of times we "move" somthing into the destination array

    public String getName() {
        return "Merge Sort";
    }

    // The base call to start the recursive merge sort
    public SortResult sort(int[] array) {
        // Only set these once here in our base call
        numberOfComparisons = 0;
        numberOfMoves = 0;
        int[] destination = new int[array.length]; // Create a new destination array for the sorted list
        return mergesort(array, destination, 0, array.length - 1); // Call recursive merge sort on this whole list
    }

    // The recursive call that we call from inside the itself
    private SortResult mergesort(int[] source, int[] destination, int lower, int upper) {
        if (lower < upper) {
            // int mid = (lower + upper) / 2; // Naive average, makes large int
            int mid = lower + ((upper - lower) / 2); // A safer average to avoid overflow better :)

            // Sort the first half, sort the second half, then merge them
            int[] firstHalf = mergesort(source, destination, lower, mid).sortedArray();
            int[] secondHalf = mergesort(source, destination, mid + 1, upper).sortedArray();
            int[] merged = recursiveMerge(firstHalf, secondHalf, lower, mid, upper);

            // Here, the list is sorted
            return new SortResult(merged, numberOfComparisons, numberOfMoves);
        } else {
            // Base case, the list is one single number, return that number as a sorted list
            if (lower == upper) {
                destination[lower] = source[lower];
            }
            return new SortResult(destination, numberOfComparisons, numberOfMoves);
        }
    }

    // This merges the two lists, defined as
    // source[lower]->source[mid] and source[mid+1]->source[upper]
    // into one combined ordered merged list "destination"
    private int[] recursiveMerge(int[] source, int[] destination, int lower, int mid, int upper) {
        int length = upper - lower + 1;
        int[] tempSource = new int[length];
        for (int i = 0; i < length; i++) {
            tempSource[i] = destination[lower + i]; 
        }
    
        int s1 = lower - lower;       // Starts at 0
        int midOffset = mid - lower;  // End of first half in tempSource
        int s2 = midOffset + 1;       // Start of second half in tempSource
        int upperOffset = upper - lower;
    
        int d = lower; // Pointer for writing back to the real destination array
    
        // While both halves have elements left, append the lowest
        while (s1 <= midOffset && s2 <= upperOffset) {
            numberOfComparisons++;
            if (tempSource[s1] <= tempSource[s2]) {
                destination[d++] = tempSource[s1++];
            } else {
                destination[d++] = tempSource[s2++];
            }
            numberOfMoves++;
        }
    
        // Clean up remaining elements
        while (s1 <= midOffset) {
            destination[d++] = tempSource[s1++];
            numberOfMoves++;
        }
        while (s2 <= upperOffset) {
            destination[d++] = tempSource[s2++];
            numberOfMoves++;
        }
    
        return destination;
    }    
}