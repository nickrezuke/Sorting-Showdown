class WeaveSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Weave Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        weaveSortRecursive(array, 0, numItems - 1);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void weaveSortRecursive(int[] array, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid = low + (high - low) / 2;

        // 1. Standard divide and conquer steps
        weaveSortRecursive(array, low, mid);
        weaveSortRecursive(array, mid + 1, high);

        // 2. Weave and clean merge phase
        weaveMerge(array, low, mid, high);
    }

    private void weaveMerge(int[] array, int low, int mid, int high) {
        int leftStart = low;
        int rightStart = mid + 1;

        // Phase 1: Perfect In-Place Weave Interleaving
        // We interleave elements from the left and right halves step-by-step
        while (leftStart < rightStart && rightStart <= high) {
            this.numberOfComparisons++;
            if (array[leftStart] > array[rightStart]) {
                // The right element needs to be woven directly before the left element
                int value = array[rightStart];
                int index = rightStart;

                // Slide elements right to make room for the woven insertion
                while (index > leftStart) {
                    array[index] = array[index - 1];
                    this.numberOfExchanges++;
                    index--;
                }
                array[leftStart] = value;

                // Shift all boundary markers to compensate for the slide
                leftStart += 2; // Jump past the newly inserted item and its partner
                mid++;
                rightStart++;
            } else {
                // Left element is already smaller, advance the stride to look at the next slot
                leftStart++;
            }
        }

        // Phase 2: Localized "Clean Up" Pass
        // Because interleaving can leave items slightly out of place locally, 
        // a swift optimized insertion check forces the newly woven block into perfect order
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= low) {
                this.numberOfComparisons++;
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    this.numberOfExchanges++;
                    j--;
                } else {
                    break;
                }
            }
            array[j + 1] = key;
        }
    }
}
