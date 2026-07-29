public class BinaryTree implements ADTBinaryTree {
    private BinaryTreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public ADTBinaryTreeNode getRoot() {
        return this.root;
    }

    public ADTBinaryTreeNode getLeftChild() {
        return (this.root != null) ? this.root.getLeftChild() : null;
    }

    public ADTBinaryTreeNode getRightChild() {
        return (this.root != null) ? this.root.getRightChild() : null;
    }

    public void insert(int newVal) {
        this.root = insertRecursive(this.root, newVal);
    }

    private BinaryTreeNode insertRecursive(BinaryTreeNode current, int newVal) {
        if (current == null) {
            return new BinaryTreeNode(newVal);
        }
        if (newVal <= current.getData()) {
            current.setLeftChild(insertRecursive((BinaryTreeNode) current.getLeftChild(), newVal));
        } else {
            current.setRightChild(insertRecursive((BinaryTreeNode) current.getRightChild(), newVal));
        }
        return current;
    }

    public void delete(int val) {
        this.root = deleteRecursive(this.root, val);
    }

    private BinaryTreeNode deleteRecursive(BinaryTreeNode current, int val) {
        if (current == null) return null;

        if (val < current.getData()) {
            current.setLeftChild(deleteRecursive((BinaryTreeNode) current.getLeftChild(), val));
        } else if (val > current.getData()) {
            current.setRightChild(deleteRecursive((BinaryTreeNode) current.getRightChild(), val));
        } else {
            // Node found: handle 3 structural cases
            if (current.getLeftChild() == null) {
                return (BinaryTreeNode) current.getRightChild();
            } else if (current.getRightChild() == null) {
                return (BinaryTreeNode) current.getLeftChild();
            }
            // Two children: find min value in right subtree
            current.setData(findMin((BinaryTreeNode) current.getRightChild()));
            current.setRightChild(deleteRecursive((BinaryTreeNode) current.getRightChild(), current.getData()));
        }
        return current;
    }

    private int findMin(BinaryTreeNode node) {
        int min = node.getData();
        while (node.getLeftChild() != null) {
            node = (BinaryTreeNode) node.getLeftChild();
            min = node.getData();
        }
        return min;
    }

    public void expandExternal(ADTBinaryTreeNode node) {
        // Turns an external leaf node into an internal node by adding 2 dummy children
        if (node != null && node.getLeftChild() == null && node.getRightChild() == null) {
            BinaryTreeNode concreteNode = (BinaryTreeNode) node;
            concreteNode.setLeftChild(new BinaryTreeNode(0));  // 0 acting as dummy sentinel data
            concreteNode.setRightChild(new BinaryTreeNode(0));
        }
    }

    public void removeAboveExternal(ADTBinaryTreeNode node) {
        // Removes an external node along with its parent, replacing the parent with the sibling node
        if (node == null || node == this.root) return;

        BinaryTreeNode targetNode = (BinaryTreeNode) node;
        BinaryTreeNode parentNode = targetNode.getParent();
        if (parentNode == null) return;

        BinaryTreeNode grandParentNode = parentNode.getParent();
        
        // Find the sibling node
        BinaryTreeNode siblingNode = (parentNode.getLeftChild() == targetNode) 
            ? (BinaryTreeNode) parentNode.getRightChild() 
            : (BinaryTreeNode) parentNode.getLeftChild();

        if (grandParentNode == null) {
            this.root = siblingNode;
            if (siblingNode != null) siblingNode.setParent(null);
        } else {
            if (grandParentNode.getLeftChild() == parentNode) {
                grandParentNode.setLeftChild(siblingNode);
            } else {
                grandParentNode.setRightChild(siblingNode);
            }
        }
    }

    public void preOrder() { preOrderRecursive(this.root); System.out.println(); }
    private void preOrderRecursive(BinaryTreeNode node) {
        if (node == null) return;
        System.out.print(node.getData() + " ");
        preOrderRecursive((BinaryTreeNode) node.getLeftChild());
        preOrderRecursive((BinaryTreeNode) node.getRightChild());
    }

    public void inOrder() { inOrderRecursive(this.root); System.out.println(); }
    private void inOrderRecursive(BinaryTreeNode node) {
        if (node == null) return;
        inOrderRecursive((BinaryTreeNode) node.getLeftChild());
        System.out.print(node.getData() + " ");
        inOrderRecursive((BinaryTreeNode) node.getRightChild());
    }

    public void postOrder() { postOrderRecursive(this.root); System.out.println(); }
    private void postOrderRecursive(BinaryTreeNode node) {
        if (node == null) return;
        postOrderRecursive((BinaryTreeNode) node.getLeftChild());
        postOrderRecursive((BinaryTreeNode) node.getRightChild());
        System.out.print(node.getData() + " ");
    }

    public void levelOrder() {
        if (this.root == null) return;

        // Custom array-based queue buffer to maintain zero imports
        BinaryTreeNode[] queue = new BinaryTreeNode[10000]; 
        int head = 0, tail = 0;

        queue[tail++] = this.root;
        while (head < tail) {
            BinaryTreeNode current = queue[head++];
            System.out.print(current.getData() + " ");

            if (current.getLeftChild() != null) {
                queue[tail++] = (BinaryTreeNode) current.getLeftChild();
            }
            if (current.getRightChild() != null) {
                queue[tail++] = (BinaryTreeNode) current.getRightChild();
            }
        }
        System.out.println();
    }
}
