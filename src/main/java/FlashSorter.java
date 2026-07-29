class FlashSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Flash Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // 1. Find the minimum and maximum elements to establish our statistical range
        int min = array[0];
        int maxIndex = 0;

        for (int i = 1; i < numItems; i++) {
            this.numberOfComparisons++;
            if (array[i] < min) {
                min = array[i];
            }
            this.numberOfComparisons++;
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }

        int max = array[maxIndex];
        
        // If all elements are identical, the array is already sorted
        if (min == max) {
            return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
        }

        // 2. Determine the optimal number of classes (m). 
        // 0.42 * N is historically proven to minimize conflicts for uniform distributions.
        int m = (int) (0.42 * numItems);
        if (m < 2) m = 2; // Enforce a minimum class ceiling boundary

        int[] L = new int[m]; // Class boundary marker array

        // Scaling factor for mapping integer data points to class brackets safely
        double c1 = (double) (m - 1) / (max - min);

        // Populate the frequency histogram of how many elements drop into each class
        for (int i = 0; i < numItems; i++) {
            int k = (int) (c1 * (array[i] - min));
            L[k]++;
        }

        // Transform frequencies into precise end-of-class pointer boundary markers
        L[0]--;
        for (int i = 1; i < m; i++) {
            L[i] += L[i - 1];
        }

        // 3. In-Place Global Permutation Loop
        // Bring the absolute maximum element to the front to anchor the cycle
        int tempSwap = array[maxIndex];
        array[maxIndex] = array[0];
        array[0] = tempSwap;
        this.numberOfExchanges++;

        int moveCount = 0;
        int j = 0;
        int k = m - 1;

        while (moveCount < numItems - 1) {
            // Advance j until it points to an element that hasn't been properly placed yet
            while (j > L[k]) {
                j++;
                k = (int) (c1 * (array[j] - min));
            }

            int flash = array[j];
            
            // Cycle-shift items into their designated class intervals
            while (j <= L[k]) {
                k = (int) (c1 * (flash - min));
                int hold = array[L[k]];
                array[L[k]] = flash;
                flash = hold;
                
                L[k]--; // Compress the remaining available class boundary index leftward
                this.numberOfExchanges++;
                moveCount++;
            }
        }

        // 4. Local Refinement Phase via highly localized Insertion Sort
        for (int i = 1; i < numItems; i++) {
            int hold = array[i];
            int iIndex = i - 1;
            
            // Since elements are mostly in their correct locations, this loop exits almost instantly
            while (iIndex >= 0) {
                this.numberOfComparisons++;
                if (array[iIndex] > hold) {
                    array[iIndex + 1] = array[iIndex];
                    this.numberOfExchanges++;
                    iIndex--;
                } else {
                    break;
                }
            }
            array[iIndex + 1] = hold;
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }
}
