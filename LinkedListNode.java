public class LinkedListNode implements ADTNode {
    private int data;
    private LinkedListNode nextLinkedListNode;

    public LinkedListNode() {
        this(0, null);
    }

    public LinkedListNode(int data) {
        this.data = data;
    }

    public LinkedListNode(int data, LinkedListNode next) {
        this.data = data;
        this.nextLinkedListNode = next;
    }

    public void setData(int newData) {
        data = newData;
    }

    public void setNext(LinkedListNode newNext) {
        this.nextLinkedListNode = newNext;
    }

    public int getData() {
        return data;
    }

    public LinkedListNode getNext() {
        return nextLinkedListNode;
    }

    public void printLinkedListNode() {
        System.out.println(" " + data);
    }
}

