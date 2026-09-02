class CycleSorter implements Sorter {
    public String getName() {
        return "Cycle Sort";
    }

    public SortResult sort(int[] array) {
        int numComparisons = 0;
        int numExchanges = 0;

        for (int i = 0; i < array.length - 1; i++) {
            int current = array[i];
            int pos = i;

            // Find the correct position to place this item
            for (int j = i + 1; j < array.length; j++) {
                numComparisons++;
                if (array[j] < current) {
                    pos++;
                }
            }

            // If the item is already in the correct position, skip it
            if (pos == i) {
                continue;
            }

            // Ignore any duplicate elements
            while (true) {
                numComparisons++; // Count the upcoming array boundary equality check
                if (current == array[pos]) {
                    pos++;
                } else {
                    break;
                }
            }

            // Put the item in its correct place
            if (pos != i) {
                int temp = array[pos];
                array[pos] = current;
                current = temp;
                numExchanges++;
            }

            // Rotate the rest of the cycle
            while (pos != i) {
                pos = i; // Find where to put the element

                for (int k = i + 1; k < array.length; k++) {
                    numComparisons++;
                    if (array[k] < current) {
                        pos++;
                    }
                }

                // Ignore any duplicates
                while (true) {
                    numComparisons++; // Count the upcoming array boundary equality check
                    if (array[pos] == current) {
                        pos++;
                    } else {
                        break;
                    }
                }

                // Put the item to its right position
                numComparisons++;
                if (current != array[pos]) {
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