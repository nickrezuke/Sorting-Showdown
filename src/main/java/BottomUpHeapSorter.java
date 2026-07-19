class BottomUpHeapSorter implements Sorter {
    public String getName() {
        return "Bottom Up Heap Sort";
    }

    public SortResult sort(int[] array) {
        // This one uses my own HeapArray class, which extends abstract data type ADTHeap
        HeapArray heap = new HeapArray();

        // Build the heap (bottom-up for this sorter)
        heap.buildHeapBottomUp(array);

        // Now remove the elements from the heap one by one and put them back into the original array in order
        for(int j = array.length - 1; j >= 0; j--) {
            array[j] = heap.remove(); // Put them in descending order from right to left, i.e, increasing order from left to right
        }

        return new SortResult(array, heap.getNumComparisons(), heap.getNumExchanges());
    }
}