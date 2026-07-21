class CycleSorter implements Sorter {
    public String getName() {
        return "Cycle Sort";
    }

    public SortResult sort(int[] array) {
        int numComparisons = 0;
        int numExchanges = 0;

        for(int i = 0; i < array.length - 1; i++) {
            int current = array[i];

            // Find the correct position to place this item
            int pos = i;
            for(int j = i + 1; j < array.length; j++) {
                numComparisons++; // Comparison on next line
                if(array[j] < current) {
                    pos++;
                }
            }

            // If the item is already in the correct position, skip it
            if (pos == i) {
                continue;
            }

            // Ignore any duplicate elements
            numComparisons++; // We compare on the next line
            while(current == array[pos]) {
                pos++;
                numComparisons++; // Count the comparison that occurs on the next itteration
            }

            // Put the item in its correct place
            if(pos != i) {
                int temp = array[pos];
                array[pos] = current;
                current = temp;
                numExchanges++;
            }

            // Rotate the rest of the cycle
            while(pos != i) {
                pos = i;

                // Find where to put the element
                for(int k = i + 1; k < array.length; k++) {
                    numComparisons++; // Comparison on next line
                    if(array[k] < current) {
                        pos++;
                    }
                }

                // Ignore any duplicates
                numComparisons++; // We compare on the next line
                while(array[pos] == current) {
                    pos++;
                    numComparisons++; // Count the comparison that occurs on the next itteration
                }

                // Put the item to its right position
                numComparisons++;
                if(current != array[pos]) {
                    int temp = array[pos];
                    array[pos] = current;
                    current = temp;
                    numExchanges++;
                }
            }
        }

        return new SortResult(array, numComparisons, numExchanges);
    }
}