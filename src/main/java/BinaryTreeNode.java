public class BinaryTreeNode implements ADTBinaryTreeNode {
    private int data;
    private BinaryTreeNode leftChild;
    private BinaryTreeNode rightChild;
    private BinaryTreeNode parent; // Helpful tracker for structural operations

    public BinaryTreeNode(int data) {
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    public ADTBinaryTreeNode getLeftChild() {
        return this.leftChild;
    }

    public ADTBinaryTreeNode getRightChild() {
        return this.rightChild;
    }

    public int getData() {
        return this.data;
    }

    // Concrete setter / getter additions for tree management
    public void setLeftChild(BinaryTreeNode left) {
        this.leftChild = left;
        if (left != null) {
            left.setParent(this);
        }
    }

    public void setRightChild(BinaryTreeNode right) {
        this.rightChild = right;
        if (right != null) {
            right.setParent(this);
        }
    }

    public BinaryTreeNode getParent() {
        return this.parent;
    }

    public void setParent(BinaryTreeNode parent) {
        this.parent = parent;
    }

    public void setData(int data) {
        this.data = data;
    }
}
