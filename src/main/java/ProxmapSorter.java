class ProxmapSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Proxmap Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // 1. Scan the array to establish the numeric range limits
        int min = array[0];
        int max = array[0];
        for (int i = 1; i < numItems; i++) {
            this.numberOfComparisons++;
            if (array[i] < min) min = array[i];
            this.numberOfComparisons++;
            if (array[i] > max) max = array[i];
        }

        // If min equals max, the array is already uniform and fully sorted
        if (min == max) {
            return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
        }

        // 2. Build the hit-count frequency histogram
        int[] hitCount = new int[numItems];
        double mapScale = (double) (numItems - 1) / (max - min);

        for (int i = 0; i < numItems; i++) {
            int mapKey = (int) (mapScale * (array[i] - min));
            hitCount[mapKey]++;
        }

        // 3. Generate the Proximity Map running index boundary list
        int[] proxMap = new int[numItems];
        proxMap[0] = 0; // Explicitly set the first index assignment safely
        for (int i = 1; i < numItems; i++) {
            proxMap[i] = proxMap[i - 1] + hitCount[i - 1];
        }

        // Allocate auxiliary validation buffers to arrange the elements
        int[] sortedBuffer = new int[numItems];
        boolean[] slotFilled = new boolean[numItems];

        // 4. Proximity Placement and Localized Insertion Swaps
        for (int i = 0; i < numItems; i++) {
            int value = array[i];
            int mapKey = (int) (mapScale * (value - min));
            int targetIdx = proxMap[mapKey];

            // If the calculated proximity target slot is empty, insert the item instantly
            if (!slotFilled[targetIdx]) {
                sortedBuffer[targetIdx] = value;
                slotFilled[targetIdx] = true;
                this.numberOfExchanges++;
            } else {
                // If a collision occurs, sweep rightward applying an in-place insertion shift
                int searchIdx = targetIdx;
                
                // Advance forward past filled slots that hold smaller elements
                while (searchIdx < numItems && slotFilled[searchIdx]) {
                    this.numberOfComparisons++;
                    if (sortedBuffer[searchIdx] > value) {
                        break; // Found the location where our new value must sit
                    }
                    searchIdx++;
                }

                // If we hit an empty slot at the end of the cluster, fill it immediately
                if (searchIdx < numItems && !slotFilled[searchIdx]) {
                    sortedBuffer[searchIdx] = value;
                    slotFilled[searchIdx] = true;
                    this.numberOfExchanges++;
                } else {
                    // Slide elements down one spot to the right to open up a clean gap insertion point
                    int slideIdx = numItems - 1;
                    while (slideIdx > searchIdx) {
                        if (slotFilled[slideIdx - 1]) {
                            sortedBuffer[slideIdx] = sortedBuffer[slideIdx - 1];
                            slotFilled[slideIdx] = slotFilled[slideIdx - 1];
                            this.numberOfExchanges++;
                        }
                        slideIdx--;
                    }
                    // Drop our value cleanly into the opened slot position
                    sortedBuffer[searchIdx] = value;
                    slotFilled[searchIdx] = true;
                    this.numberOfExchanges++;
                }
            }
        }

        // 5. Flash copy sorted elements securely back into your primary tracking list
        for (int i = 0; i < numItems; i++) {
            array[i] = sortedBuffer[i];
            this.numberOfExchanges++;
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }
}
