public class StackArray implements ADTStack {
    private final int MAXSIZE = 100; // Default maximum size of the stack
    private int size;
    private int[] stackArray;
    private int top;

    public StackArray() {
        this.size = MAXSIZE;
        this.stackArray = new int[MAXSIZE];
        this.top = -1; // Indicates an empty stack
    }

    public StackArray(int size) { //Overloaded so we can pass in a new maximum size if we want
        this.size = size;
        this.stackArray = new int[size];
        this.top = -1; 
    }

    public void push(int value) throws StackFullException {
        if (!isFull()) {
            stackArray[++top] = value;
        } else {
            throw new StackFullException("Stack is full. Cannot push " + value);
        }
    }

    public int pop() throws StackEmptyException {
        if (!isEmpty()) {
            int value = stackArray[top--];
            return value;
        } else {
            throw new StackEmptyException("Stack is empty. Cannot pop.");
        }
    }

    public int onTop() throws StackEmptyException {
        int i = pop(); //The pop method will also throw an exception if the stack is empty
        push(i);
        return i;
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    public boolean isFull() {
        return (top == size - 1);
    }

    public int size() {
        return top + 1;
    }
    
}
