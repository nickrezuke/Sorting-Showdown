public class QueueArray implements ADTQueue {
    private final int MAXSIZE = 1000000; // Default maximum size of the queue
    private int size;
    private int[] queueArray;
    private int front;
    private int rear;

    public QueueArray() {
        this.size = MAXSIZE;
        this.queueArray = new int[size];
        this.front = 0; // Points to the front of the queue
        this.rear = -1; // Points to the rear of the queue
    }

    public QueueArray(int size) { //Overloaded so we can pass in a new maximum size if we want
        this.size = size;
        this.queueArray = new int[size];
        this.front = 0; 
        this.rear = -1; 
    }

    public void enqueue(int value) throws QueueFullException {
        if (size() < size) {
            rear = (rear + 1) % size; // Circular increment
            queueArray[rear] = value;
        } else {
            throw new QueueFullException("Queue is full. Cannot enqueue " + value);
        }
    }

    public int dequeue() throws QueueEmptyException {
        if (!isEmpty()) {
            int value = queueArray[front];
            front = (front + 1) % size; // Circular increment
            return value;
        } else {
            throw new QueueEmptyException("Queue is empty. Cannot dequeue.");
        }
    }

    public int front() throws QueueEmptyException {
        if (!isEmpty()) {
            return queueArray[front];
        } else {
            throw new QueueEmptyException("Queue is empty. No front element.");
        }
    }

    public boolean isEmpty() {
        return (front == (rear + 1) % size);
    }

    public boolean isFull() {
        return (front == (rear + 2) % size);
    }

    public int size() {
        if (rear >= front) {
            // If rear is ahead of front, the size is simply the difference plus one
            return rear - front + 1;
        } else {
            // If rear has wrapped around b/c circular increment, the size is the total size minus the gap between rear and front
            return (size - front + rear + 1) % size; // Modulo to handle the case when rear is just behind front (empty queue)
        }
    }
}
