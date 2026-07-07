public class CombSorter implements Sorter {
    public String getName() {
        //return "\u0428 Comb Sort";

        // Comb is a new Emoji, so it isnt supported like the others are...
        if (!"vscode".equalsIgnoreCase(System.getenv("TERM_PROGRAM"))) {
            return Character.toString(0x1FAAE) + " Comb Sort";
        } else {
            return Character.toString(0x1FAAE) + "  Comb Sort\u3000";
        }
        // VS Code's Terminal handles the new emoji width on its own, but Terminal (and others) need the full-width space padding helper
        // TODO: Check if other environments need this, or if there's clearly a better solution to this...
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        int gap = array.length;
        boolean swapped = true;

        // The shrink factor for the gap
        // (Experiment with different values to see how it affects performance)
        double shrinkFactor = 1.3; 

        while (gap > 1 || swapped) {
            // Update the gap for the next comb
            gap = (int) (gap / shrinkFactor);
            
            // "Rule of 11" optimization: If the gap is 9 or 10, set it to 11
            if (gap == 9 || gap == 10) {
                gap = 11;  
            } else if (gap < 1) {
                gap = 1;
            }

            // Reset swapped to false before the next pass
            swapped = false;

            for (int i = 0; i + gap  < array.length; i++) {
                numberOfComparisons++;
                if (array[i] > array[i + gap]) {
                    // Swap elements
                    int temp = array[i];
                    array[i] = array[i + gap];
                    array[i + gap] = temp;
                    numberOfExchanges++;
                    swapped = true;
                }
            }
        }

        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}