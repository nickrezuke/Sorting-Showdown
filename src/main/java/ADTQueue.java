public interface ADTQueue {
    public void enqueue(int value) throws QueueFullException;
    public int dequeue() throws QueueEmptyException;
    public int front() throws QueueEmptyException;
    public boolean isFull();
    public boolean isEmpty();
    public int size();
}
