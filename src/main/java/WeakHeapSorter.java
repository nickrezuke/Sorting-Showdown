class WeakHeapSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Weak Heap Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // 1. Allocate the bit flag array to track structural reversals
        boolean[] reverseBits = new boolean[numItems];

        // 2. Phase 1: Build the Weak Heap from the bottom up
        for (int i = numItems - 1; i > 0; i--) {
            join(array, reverseBits, getParentIndex(i, reverseBits), i);
        }

        // 3. Phase 2: Extract elements one by one from the root
        for (int i = numItems - 1; i > 0; i--) {
            // Swap the max element (at root index 0) to its final sorted spot at the end
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            this.numberOfExchanges++;

            // Re-heapify the remaining elements down to index 'i'
            int currentRoot = 0;
            int child = getLeftChildIndex(currentRoot, reverseBits);
            
            // Navigate straight down to the leaf level
            while (child < i) {
                currentRoot = child;
                child = getLeftChildIndex(currentRoot, reverseBits);
            }

            // Re-merge back up along the path to restore the weak heap constraints
            while (currentRoot > 0) {
                join(array, reverseBits, getParentIndex(currentRoot, reverseBits), currentRoot);
                currentRoot = getParentIndex(currentRoot, reverseBits);
            }
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    // The fundamental building block of Weak Heap: merges two sub-weak-heaps seamlessly
    private void join(int[] array, boolean[] reverseBits, int parent, int child) {
        this.numberOfComparisons++;
        if (array[parent] < array[child]) {
            // Swap parent and child values
            int temp = array[parent];
            array[parent] = array[child];
            array[child] = temp;
            this.numberOfExchanges++;

            // Flip the bit flag of the child node to invert its child branches instantly
            reverseBits[child] = !reverseBits[child];
        }
    }

    // Dynamic bit-aware parent index pointer mapping
    private int getParentIndex(int index, boolean[] reverseBits) {
        while ((index & 1) == 0 && reverseBits[index >> 1]) {
            index >>= 1;
        }
        return index >> 1;
    }

    // Dynamic bit-aware left child pointer mapping
    private int getLeftChildIndex(int index, boolean[] reverseBits) {
        return reverseBits[index] ? (2 * index) : (2 * index + 1);
    }
}
