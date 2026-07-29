public class PriorityQueueLinkedList implements ADTQueue {
    private int size;
    private LinkedListNode front;

    public PriorityQueueLinkedList() {
        this.size = 0;
        this.front = null;
    }

    // Overloaded enqueue that evaluates priorities using an external lookup array
    public void enqueue(int index, int[] priorities) throws QueueFullException {
        LinkedListNode newNode = new LinkedListNode(index);

        // Case 1: The queue is empty, or the new item has a higher priority (smaller value) than the current front
        if (isEmpty() || priorities[index] < priorities[front.getData()]) {
            newNode.setNext(front);
            front = newNode;
        } else {
            // Case 2: Iterate through the list to find the correct sorted insertion spot
            LinkedListNode current = front;
            while (current.getNext() != null && priorities[current.getNext().getData()] <= priorities[index]) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }
        size++;
    }

    // Overloaded dequeue simply pops the front node (which is always the highest priority item)
    public int dequeue(int[] priorities) throws QueueEmptyException {
        return dequeue(); 
    }

    // --- Standard ADTQueue Interface Implementations ---

    public void enqueue(int index) throws QueueFullException {
        // Default behavior: evaluates sorting priority based on the raw index integer values themselves
        LinkedListNode newNode = new LinkedListNode(index);
        if (isEmpty() || index < front.getData()) {
            newNode.setNext(front);
            front = newNode;
        } else {
            LinkedListNode current = front;
            while (current.getNext() != null && current.getNext().getData() <= index) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }
        size++;
    }

    public int dequeue() throws QueueEmptyException {
        if (isEmpty()) {
            throw new QueueEmptyException("Priority Queue is empty. Cannot dequeue.");
        }
        int val = front.getData();
        front = front.getNext();
        size--;
        return val;
    }

    public int front() throws QueueEmptyException {
        if (isEmpty()) {
            throw new QueueEmptyException("Priority Queue is empty. No front element.");
        }
        return front.getData();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return false; // Linked lists dynamically expand infinitely
    }

    public int size() {
        return size;
    }
}
