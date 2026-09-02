public class HeapArray implements ADTHeap {
    // This is supposed to be a heap but I'd like to strictly use Bottom-Up Construction since its better
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
            size++;                   // Increment the size of the heap
            upheap(size - 1);         // Restore the heap property by bubbling up
        } else {
            throw new HeapFullException("Heap is full. Cannot insert " + newval);
        }
    }

    public int remove() throws HeapEmptyException {
        if (isEmpty()) {
            throw new HeapEmptyException("Heap is empty. Cannot remove.");
        } else {
            int root = heapArray[0]; // The value to return
            heapArray[0] = heapArray[size - 1]; // Overwrite root with the last element
            size--;
            downheap(0); // Restore heap condition
            return root;
        }
    }

    public void buildHeapTopDown(int[] array) {
        for (int i = 0; i < array.length; i++) {
            try {
                insert(array[i]);
            } catch (HeapFullException e) {
                break; // Stop if the heap capacity is reached
            }
        }
    }

    public void buildHeapBottomUp(int[] array) {
        System.arraycopy(array, 0, heapArray, 0, Math.min(array.length, heapArray.length));
        size = Math.min(array.length, heapArray.length);
        for (int i = (size / 2) - 1; i >= 0; i--) {
            downheap(i);
        }
    }

    private void upheap(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            numComparisons++; 
            if (heapArray[index] > heapArray[parent]) {
                swap(index, parent); // Safely performs a true exchange
                index = parent;
            } else {
                break;
            }
        }
    }

    private void downheap(int index) {
        while (index < size / 2) {
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;
            int largerChild = leftChild;

            if (rightChild < size) {
                numComparisons++; 
                if (heapArray[rightChild] > heapArray[leftChild]) {
                    largerChild = rightChild;
                }
            }

            numComparisons++; 
            if (heapArray[index] >= heapArray[largerChild]) {
                break;
            }

            swap(index, largerChild); // Safely performs a true exchange
            index = largerChild;
        }
    }

    private void swap(int i, int j) {
        int temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
        numExchanges++; // Increments strictly when a pair of items exchange spots
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
