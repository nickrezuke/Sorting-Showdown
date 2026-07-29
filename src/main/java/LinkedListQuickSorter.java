class LinkedListQuickSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Quick Sort (Linked List)";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // 1. Build an in-house linked list from the array primitives
        LinkedListNode head = new LinkedListNode(array[0]);
        LinkedListNode current = head;
        for (int i = 1; i < numItems; i++) {
            LinkedListNode newNode = new LinkedListNode(array[i]);
            current.setNext(newNode);
            current = newNode;
            this.numberOfExchanges++; // Track node population moves
        }

        // 2. Execute the Quick LL Pointer Partition Sort engine
        head = quickLLRecursive(head);

        // 3. Unroll the newly re-linked nodes back into your original primitive array
        current = head;
        int targetIdx = 0;
        while (current != null) {
            array[targetIdx++] = current.getData();
            this.numberOfExchanges++; // Track writing back out to array elements
            current = current.getNext();
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private LinkedListNode quickLLRecursive(LinkedListNode head) {
        // Base case: an empty list or single node list is already sorted
        if (head == null || head.getNext() == null) {
            return head;
        }

        // Select the first node as the Pivot
        LinkedListNode pivot = head;
        LinkedListNode current = head.getNext();
        pivot.setNext(null); // Isolate the pivot node

        // Allocate separate local chain pointers for partitioning
        LinkedListNode lessHead = null, lessTail = null;
        LinkedListNode greaterHead = null, greaterTail = null;
        LinkedListNode equalHead = pivot, equalTail = pivot;

        // Partition phase: cycle linearly down the node chain
        while (current != null) {
            LinkedListNode nextNode = current.getNext();
            current.setNext(null); // Disconnect the node temporarily

            this.numberOfComparisons++;
            if (current.getData() < pivot.getData()) {
                // Link into the 'less' sub-list chain
                if (lessHead == null) {
                    lessHead = current;
                    lessTail = current;
                } else {
                    lessTail.setNext(current);
                    lessTail = current;
                }
                this.numberOfExchanges++;
            } else if (current.getData() > pivot.getData()) {
                // Link into the 'greater' sub-list chain
                if (greaterHead == null) {
                    greaterHead = current;
                    greaterTail = current;
                } else {
                    greaterTail.setNext(current);
                    greaterTail = current;
                }
                this.numberOfExchanges++;
            } else {
                // Handle duplicate values smoothly by appending to the pivot group
                equalTail.setNext(current);
                equalTail = current;
                this.numberOfExchanges++;
            }
            current = nextNode;
        }

        // Recursively sort the sub-lists
        lessHead = quickLLRecursive(lessHead);
        greaterHead = quickLLRecursive(greaterHead);

        // Concatenate phase: stitch the sorted pieces back together
        LinkedListNode finalHead = null;

        if (lessHead != null) {
            finalHead = lessHead;
            // Find the tail end of the sorted 'less' list
            LinkedListNode lessTailScan = lessHead;
            while (lessTailScan.getNext() != null) {
                lessTailScan = lessTailScan.getNext();
            }
            lessTailScan.setNext(equalHead);
        } else {
            finalHead = equalHead;
        }

        equalTail.setNext(greaterHead);

        return finalHead;
    }
}
