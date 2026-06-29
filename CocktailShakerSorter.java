class CocktailShakerSorter implements Sorter {
    // This is essentially just Bubble sort but every other itteration it 
    // alternates bubbling down the smallest element and bubbling up the 
    // largest, as a simple means to speed up the "turtles"
    public String getName() {
        //return "\u21C5 Cocktail Shaker Sort";
        return Character.toString(0x1F378) + " Cocktail Shaker Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        // Did we make any swaps? (End Early Logic)
        boolean swapped = true;

        // The border of the left sorted list
        int start = 0;
        // The border of the right sorted list
        int end = array.length - 1;

        // While we are at the left about to go right...
        while (swapped) {
            // Reset swapped flag for the forward pass
            swapped = false;

            // 1. FORWARD PASS: Bubbles the largest element to the end
            for (int i = start; i < end; i++) {
                numberOfComparisons++;
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    numberOfExchanges++;
                    swapped = true;
                }
            }

            // If no swaps occurred, the array is already sorted
            if (!swapped)
                break;

            // Mark the last element as sorted and shrink the range
            swapped = false;
            end--;

            // 2. BACKWARD PASS: Bubbles the smallest element (the "turtles") to the start
            for (int i = end - 1; i >= start; i--) {
                numberOfComparisons++;
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    numberOfExchanges++;
                    swapped = true;
                }
            }

            // Mark the first element as sorted and shrink the range
            start++;
        }

        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}