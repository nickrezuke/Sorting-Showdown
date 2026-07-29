class GravitySorter implements Sorter {
    public String getName() {
        return "Gravity Sort (Bead Sort)";
    }

    public SortResult sort(int[] array) {
        int numComparisons = 0;
        int numExchanges = 0;

        int numItems = array.length;
        if (numItems <= 1) {
            return new SortResult(array, numComparisons, numExchanges);
        }

        // Find the maximum value to determine the number of vertical wires
        int max = array[0];
        for (int i = 1; i < numItems; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        // Set up the grid (abacus). count[i] stores the number of beads on wire 'i'
        int[] count = new int[max];

        // Drop the beads down the wires
        // For each number, we place a bead on the first 'array[i]' wires
        for (int i = 0; i < numItems; i++) {
            for (int j = 0; j < array[i]; j++) {
                count[j]++;
            }
        }

        // Reconstruct the sorted array from the bottom up
        // Wires with more beads will naturally stack at the bottom (larger indices)
        for (int i = 0; i < numItems; i++) {
            int k = 0;
            // Check how many wires have at least (numItems - i) beads
            for (int j = 0; j < max; j++) {
                if (count[j] >= numItems - i) {
                    k++;
                }
            }
            array[i] = k;
        }

        // Here, the list is sorted
        return new SortResult(array, 0, 0);
    }
}
