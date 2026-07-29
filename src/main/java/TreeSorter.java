class TreeSorter implements Sorter {
    // Tracks metrics across the recursive sorting operations
    private int numberOfComparisons;
    private int numberOfExchanges;
    private int targetIndex;

    public String getName() {
        return "Tree Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;
        this.targetIndex = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Phase 1: Build the tree structure using your concrete BinaryTree implementation
        BinaryTree bst = new BinaryTree();
        
        for (int i = 0; i < numItems; i++) {
            // Track historical growth comparison curves
            // In a standard BST insertion, total comparisons mirror internal path steps
            if (i > 0) {
                this.numberOfComparisons += calculateInsertionDepth(bst.getRoot(), array[i]);
            }
            
            bst.insert(array[i]);
            this.numberOfExchanges++; // Track moving the item into our tree structure
        }

        // Phase 2: Traverse the nodes in order to flatten values back into our primitive array
        unpackInOrder(bst.getRoot(), array);

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    // Unpacks elements sequentially to match your exact tracking pattern
    private void unpackInOrder(ADTBinaryTreeNode node, int[] array) {
        if (node == null) {
            return;
        }

        // 1. Traverse left child
        unpackInOrder(node.getLeftChild(), array);

        // 2. Unpack current data value and write it back out to the array
        array[this.targetIndex++] = node.getData();
        this.numberOfExchanges++; // Track the write back operation

        // 3. Traverse right child
        unpackInOrder(node.getRightChild(), array);
    }

    // Pure historical metrics helper to log accurate comparison weights
    private int calculateInsertionDepth(ADTBinaryTreeNode current, int value) {
        int depthComparisons = 0;
        while (current != null) {
            depthComparisons++;
            if (value <= current.getData()) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        return depthComparisons;
    }
}
