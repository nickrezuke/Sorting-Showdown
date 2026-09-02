public class QueueLinkedList implements ADTQueue {
    private int size;
    private LinkedListNode front;
    private LinkedListNode rear;

    public QueueLinkedList() {
        size = 0;
        front = null;
        rear = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(int newNumber) throws QueueFullException { // Even though this will never be full...
        LinkedListNode newNode = new LinkedListNode(newNumber);
        if (this.isEmpty()) {
            front = newNode;
        } else {
            rear.setNext(newNode);
        }
        rear = newNode;
        size++;
    }

    public int dequeue() {
        if (front == null) {
            throw new QueueEmptyException("Queue is empty. Cannot dequeue.");
        }
        int val = front.getData();
        front = front.getNext();
        size--;
        return val;
    }

    public boolean isFull() {
        return false; // Linked lists don't typically fill up...
    }

    public int front() {
        return front.getData();
    }
}
