public class StackLinkedList implements ADTStack {
    private LinkedListNode top;
    private int size;

    public StackLinkedList() {
        top = null;
        size = 0;
    }

    public void push(int value) {
        LinkedListNode newNode = new LinkedListNode();
        newNode.setData(value);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    public int pop() throws StackEmptyException {
        if (!isEmpty()) {
            int value = top.getData();
            top = top.getNext();
            size--;
            return value;
        } else {
            throw new StackEmptyException("Stack is empty. Cannot pop.");
        }
    }

    public int onTop() throws StackEmptyException {
        int value = pop();
        push(value);
        return value;
    }

    public boolean isEmpty() {
        return (top == null);
    }

    public boolean isFull() {
        return false;
        // Linked Lists typically don't get full
    }

    public int size() {
        return size;
    }
    
}
