public class HeapArray implements ADTHeap {
    // This is supposed to be a heap but I'd like to strictly use Bottom-Up Construction since its better
    // How can I do this in this class, instead of calling 
    private final int MAXSIZE = 1000000; // Default maximum size of the queue
    private int size;
    private int[] heapArray;
    private int numComparisons;
    private int numExchanges;

    public HeapArray() {
        size = 0;
        heapArray = new int[MAXSIZE];
        numComparisons = 0;
        numExchanges = 0;
    }

    public HeapArray(int maxSize) {
        size = 0;
        heapArray = new int[maxSize];
        numComparisons = 0;
        numExchanges = 0;
    }

    public void insert(int newval) throws HeapFullException {
        if (size < heapArray.length) {
            heapArray[size] = newval; // Insert the new value at the end of the heap
            numExchanges++; // We inserted the new value, so that's an exchange technically
            upheap(size); // Restore the heap property by bubbling up the new value
            size++; // Increment the size of the heap
        } else {
            throw new HeapFullException("Heap is full. Cannot insert " + newval);
        }
    }

    public int remove() throws HeapEmptyException {
        if(isEmpty()) {
            throw new HeapEmptyException("Heap is empty. Cannot remove.");
        } else {
            int root = heapArray[0]; // The value to return
            heapArray[0] = heapArray[size - 1]; // Replace root with last element in the heap
            numExchanges++; // We moved the last element to the root, so that's an exchange technically
            size--;
            downheap(0); // Restore heap condition
            return root;
        }
    }

    public void buildHeapTopDown(int[] array) {
        // Insert each element into the heap one by one and restore the heap property each time
        for(int i = 0; i < array.length; i++) {
            insert(array[i]); // Inserts this number at the end of the heap. Insert heapifies up to restore the heap property.
        }
    }

    public void buildHeapBottomUp(int[] array) {
        // Start with the whole array as an invalid heap
        System.arraycopy(array, 0, heapArray, 0, array.length);

        // Define size
        size = array.length;

        // Then downheap all non-leafs (1/2 the nodes)
        for (int i = (size / 2) - 1; i >= 0; i--) {
            downheap(i);
        } 
    }

    private void upheap(int index) {
        int temp = heapArray[index];
        while (index > 0) {
            int parent = (index - 1) / 2;
            numComparisons++; // We're about to compare with parent on next line
            if (temp > heapArray[parent]) {
                // If the new value is greater than its parent, we need to swap it with its parent and continue bubbling up
                numExchanges++; // "Exchange" or shift value
                heapArray[index] = heapArray[parent];
                index = parent;
            } else {
                // If the new value is not greater than its parent, we can stop bubbling up
                break;
            }
        }
        heapArray[index] = temp;
        // Count this last one too
        numExchanges++; 
    }
    
    private void downheap(int index) {
        int root = heapArray[index];
        while (index < size / 2) {
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;
            int largerChild = leftChild;

            if (rightChild < size) {
                numComparisons++; // We compare two children next line
                if (heapArray[rightChild] > heapArray[leftChild]) {
                    largerChild = rightChild;
                }
            }

            numComparisons++; // Compare larger child with root next line
            if (root >= heapArray[largerChild]) break;

            numExchanges++; // "Exchange" or shift value
            heapArray[index] = heapArray[largerChild];
            index = largerChild;
        }
        heapArray[index] = root;
        // Count this last one too
        numExchanges++; 
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == heapArray.length;
    }

    public int getNumComparisons() {
        return numComparisons;
    }

    public int getNumExchanges() {
        return numExchanges;
    }
    
}