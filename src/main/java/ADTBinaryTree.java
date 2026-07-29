public interface ADTBinaryTree {
    public boolean isEmpty();
    public ADTBinaryTreeNode getRoot();
    public ADTBinaryTreeNode getLeftChild();
    public ADTBinaryTreeNode getRightChild();
    public void insert(int newVal);
    public void delete(int val);
    public void expandExternal(ADTBinaryTreeNode node);
    public void removeAboveExternal(ADTBinaryTreeNode node);
    public void preOrder();
    public void inOrder();
    public void postOrder();
    public void levelOrder();
}
