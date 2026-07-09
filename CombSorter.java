public class CombSorter implements Sorter {
    public String getName() {
        return "Comb Sort";
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