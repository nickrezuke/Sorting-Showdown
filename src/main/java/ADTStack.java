public interface ADTStack {
    public void push(int value) throws StackFullException;
    public int pop() throws StackEmptyException;
    public int onTop() throws StackEmptyException;
    public boolean isFull();
    public boolean isEmpty();
    public int size();
}
