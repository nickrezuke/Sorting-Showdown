public interface ADTHeap {
    public void insert(int value);
    public int remove() throws HeapEmptyException;
    public boolean isEmpty();
    public boolean isFull();
}
