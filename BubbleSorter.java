public class BubbleSorter implements Sorter {
    public String getName() {
        //return "\u21EE Bubble Sort";

        //Bubbles is a new Emoji, so it isnt supported like the others are...
        if (!"vscode".equalsIgnoreCase(System.getenv("TERM_PROGRAM"))) {
            return Character.toString(0x1FAE7) + " Bubble Sort";
        } else {
            return Character.toString(0x1FAE7) + "  Bubble Sort\u3000";
        }
        // VS Code's Terminal handles the new emoji width on its own, but Terminal (and others) need the full-width space padding helper
        // TODO: Check if other environments need this, or if there's clearly a better solution to this...
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        int temp;
        int numItems = array.length;
        boolean continueGoing = true;
        // if continueGoing is set, we must have gone through a whole pass without
        // comparisons, meaning the remaining list must be sorted, so we should stop

        for (int pass = 0; pass < numItems; pass++) {
            // If we did a pass without any swaps, we end early
            if (continueGoing) {
                continueGoing = false;

                for (int index = 0; index < numItems - pass - 1; index++) {
                    // Perform a Comparison (Inside both for loops, so O(n^2) comparisons in average
                    // case)
                    numberOfComparisons++;
                    if (array[index] > array[index + 1]) {
                        // Perform an Exchange (Inside both for loops, so O(n^2) exchanges in average
                        // case)
                        numberOfExchanges++;
                        temp = array[index];
                        array[index] = array[index + 1];
                        array[index + 1] = temp;

                        // Since we swapped, we should continue going
                        continueGoing = true;
                    }
                }
            } else {
                break;
            }
        }
        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}