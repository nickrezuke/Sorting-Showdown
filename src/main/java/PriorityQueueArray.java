public class PriorityQueueArray implements ADTQueue {
    private final int MAXSIZE = 1000000;
    private int capacity;
    private int currentSize;
    private int[] heapArray; // Stores indices/identifiers instead of raw values

    public PriorityQueueArray() {
        this.capacity = MAXSIZE;
        this.heapArray = new int[capacity + 1];
        this.currentSize = 0;
    }

    public PriorityQueueArray(int size) {
        this.capacity = size;
        this.heapArray = new int[capacity + 1];
        this.currentSize = 0;
    }

    // Overloaded helper methods so enqueue and dequeue can accept an evaluation array
    public void enqueue(int index, int[] priorities) throws QueueFullException {
        if (isFull()) {
            throw new QueueFullException("Priority Queue is full. Cannot insert index " + index);
        }
        currentSize++;
        heapArray[currentSize] = index;
        swim(currentSize, priorities);
    }

    // Overloaded helper methods so enqueue and dequeue can accept an evaluation array
    public int dequeue(int[] priorities) throws QueueEmptyException {
        if (isEmpty()) {
            throw new QueueEmptyException("Priority Queue is empty. Cannot dequeue.");
        }
        int rootIndex = heapArray[1];
        heapArray[1] = heapArray[currentSize];
        currentSize--;

        if (currentSize > 0) {
            sink(1, priorities);
        }
        return rootIndex; 
    }

    // Original signature method
    public void enqueue(int index) throws QueueFullException {
        if (isFull()) {
            throw new QueueFullException("Priority Queue is full. Cannot insert index " + index);
        }
        currentSize++;
        heapArray[currentSize] = index;
        swim(currentSize, heapArray); // Defaults to comparing the array values themselves
    }

    // Original signature methods
    public int dequeue() throws QueueEmptyException {
        if (isEmpty()) {
            throw new QueueEmptyException("Priority Queue is empty. Cannot dequeue.");
        }
        int rootIndex = heapArray[1];
        heapArray[1] = heapArray[currentSize];
        currentSize--;

        if (currentSize > 0) {
            sink(1, heapArray);
        }
        return rootIndex;
    }

    public int front() throws QueueEmptyException {
        if (isEmpty()) {
            throw new QueueEmptyException("Priority Queue is empty. No front element.");
        }
        return heapArray[1];
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean isFull() {
        return currentSize == capacity;
    }

    public int size() {
        return currentSize;
    }

    // --- Heap Management Helper Methods ---

    private void swim(int k, int[] priorities) {
        while (k > 1) {
            int parent = k / 2;
            
            // Extract the raw integer elements from the heap positions
            int currentVal = heapArray[k];
            int parentVal = heapArray[parent];
            
            // If priorities is heapArray, we are sorting raw values directly. 
            // Otherwise, we do an indirect array lookup.
            boolean shouldSwap = (priorities == heapArray) 
                ? (currentVal < parentVal) 
                : (priorities[currentVal] < priorities[parentVal]);
    
            if (shouldSwap) {
                swap(k, parent);
                k = parent;
            } else {
                break;
            }
        }
    }
    
    private void sink(int k, int[] priorities) {
        while (2 * k <= currentSize) {
            int childIndex = 2 * k;
    
            // 1. Determine if the right child has higher priority than the left child
            if (childIndex + 1 <= currentSize) {
                int leftVal = heapArray[childIndex];
                int rightVal = heapArray[childIndex + 1];
                
                boolean rightIsSmaller = (priorities == heapArray)
                    ? (rightVal < leftVal)
                    : (priorities[rightVal] < priorities[leftVal]);
                    
                if (rightIsSmaller) {
                    childIndex++;
                }
            }
    
            // 2. Determine if the child has higher priority than the current parent element
            int parentVal = heapArray[k];
            int targetChildVal = heapArray[childIndex];
            
            boolean parentIsSmallerOrEqual = (priorities == heapArray)
                ? (parentVal <= targetChildVal)
                : (priorities[parentVal] <= priorities[targetChildVal]);
    
            if (parentIsSmallerOrEqual) {
                break;
            }
    
            swap(k, childIndex);
            k = childIndex;
        }
    }    
    

    private void swap(int i, int j) {
        int temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }
}
