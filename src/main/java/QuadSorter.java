class QuadSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Quad Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Phase 1: Sort individual blocks of 4 items using a fixed-gate branchless layout
        for (int i = 0; i < numItems; i += 4) {
            sortQuadBlock(array, i, Math.min(i + 4, numItems));
        }

        // Phase 2: Bottom-up 4-way merge loop
        // The block step size quadruples on every pass (4 -> 16 -> 64 -> 256 -> ...)
        int[] tempBuffer = new int[numItems];
        for (int blockStep = 4; blockStep < numItems; blockStep *= 4) {
            for (int i = 0; i < numItems; i += 4 * blockStep) {
                quadMerge(array, tempBuffer, i, blockStep, numItems);
            }
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    // Hardcoded highly efficient branch-network to sort up to 4 elements instantly
    private void sortQuadBlock(int[] array, int start, int end) {
        int len = end - start;
        if (len <= 1) return;

        if (len == 2) {
            this.numberOfComparisons++;
            if (array[start] > array[start + 1]) {
                swap(array, start, start + 1);
            }
            return;
        }

        if (len == 3) {
            this.numberOfComparisons++;
            if (array[start] > array[start + 1]) swap(array, start, start + 1);
            this.numberOfComparisons++;
            if (array[start + 1] > array[start + 2]) swap(array, start + 1, start + 2);
            this.numberOfComparisons++;
            if (array[start] > array[start + 1]) swap(array, start, start + 1);
            return;
        }

        // Full 4-element sorting logic network path
        this.numberOfComparisons++;
        if (array[start] > array[start + 1]) swap(array, start, start + 1);
        this.numberOfComparisons++;
        if (array[start + 2] > array[start + 3]) swap(array, start + 2, start + 3);

        this.numberOfComparisons++;
        if (array[start + 1] > array[start + 2]) {
            swap(array, start + 1, start + 2);
            this.numberOfComparisons++;
            if (array[start] > array[start + 1]) swap(array, start, start + 1);
            this.numberOfComparisons++;
            if (array[start + 2] > array[start + 3]) swap(array, start + 2, start + 3);
            this.numberOfComparisons++;
            if (array[start + 1] > array[start + 2]) swap(array, start + 1, start + 2);
        }
    }

    // 4-Way Merge engine combining four adjacent sorted blocks into one continuous block segment
    private void quadMerge(int[] array, int[] temp, int start, int step, int totalSize) {
        int end1 = Math.min(start + step, totalSize);
        int end2 = Math.min(end1 + step, totalSize);
        int end3 = Math.min(end2 + step, totalSize);
        int end4 = Math.min(end3 + step, totalSize);

        if (start >= end2) return; // Not enough sub-arrays to execute a 4-way merge operation

        int p1 = start, p2 = end1, p3 = end2, p4 = end3;
        int targetIdx = start;

        // Extract elements using a 4-way parallel pointer sweep check
        while (p1 < end1 || p2 < end2 || p3 < end3 || p4 < end4) {
            int smallestVal = Integer.MAX_VALUE;
            int chosenPointer = 0;

            if (p1 < end1) {
                this.numberOfComparisons++;
                if (array[p1] < smallestVal) { smallestVal = array[p1]; chosenPointer = 1; }
            }
            if (p2 < end2) {
                this.numberOfComparisons++;
                if (array[p2] < smallestVal) { smallestVal = array[p2]; chosenPointer = 2; }
            }
            if (p3 < end3) {
                this.numberOfComparisons++;
                if (array[p3] < smallestVal) { smallestVal = array[p3]; chosenPointer = 3; }
            }
            if (p4 < end4) {
                this.numberOfComparisons++;
                if (array[p4] < smallestVal) { smallestVal = array[p4]; chosenPointer = 4; }
            }

            // Copy the absolute minimum element out into our temporary tracking cache buffer
            if (chosenPointer == 1) temp[targetIdx++] = array[p1++];
            else if (chosenPointer == 2) temp[targetIdx++] = array[p2++];
            else if (chosenPointer == 3) temp[targetIdx++] = array[p3++];
            else if (chosenPointer == 4) temp[targetIdx++] = array[p4++];
            
            this.numberOfExchanges++;
        }

        // Flush elements back down from our temporary cache into the primary list bounds
        for (int i = start; i < end4; i++) {
            array[i] = temp[i];
            this.numberOfExchanges++;
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        this.numberOfExchanges++;
    }
}
