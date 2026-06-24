class MergeSorter implements Sorter {
    // For Merge Sort, I use private global variables to keep track of comparisons
    // and moves, and then increment them as we go along, since we call ourselves
    // recursively
    private static int numberOfComparisons;
    private static int numberOfMoves;
    // We don't really "exchange" here so lets count the number
    // of times we "move" somthing into the destination array

    public String getName() {
        return "\u2444 Merge Sort";
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
        int s1 = lower;
        int s2 = mid + 1;
        int d = lower;

        // While both still have something left, append the lowest
        while (s1 <= mid && s2 <= upper) {
            numberOfComparisons++; // We compared s[s1] and s[s2]
            if (source[s1] <= source[s2]) {
                destination[d++] = source[s1++]; // Insert and advance
            } else {
                destination[d++] = source[s2++]; // Insert and advance
            }
            numberOfMoves++; // Count this insert and advance as a "move"
        }

        // Once one of them is empty, just fill in the rest with the other one
        while (s1 <= mid) {
            destination[d++] = source[s1++];
            numberOfMoves++;
        }
        while (s2 <= upper) {
            destination[d++] = source[s2++];
            numberOfMoves++;
        }
        return destination;
    }

}