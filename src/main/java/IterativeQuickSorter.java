class IterativeQuickSorter implements Sorter {
    public String getName() {
        return "Iterative Quick Sort";
    }

    public SortResult sort(int[] array) {
        int numComparisons = 0;
        int numExchanges = 0;

        StackArray stack = new StackArray();  // Use Array implementation of a Stack
        //StackLinkedList stack = new StackLinkedList();  // Use Linked List implementation of a Stack

        if (array == null || array.length < 2) {
            return new SortResult(array, numComparisons, numExchanges);
        }

        try {
            // Push initial low (0) and high (length - 1) boundaries
            stack.push(0);
            stack.push(array.length - 1);

            while (!stack.isEmpty()) {
                int high = stack.pop();
                int low = stack.pop();

                int pivotIndex = low - 1;
                int pivotValue = array[high];

                for (int j = low; j < high; j++) {
                    numComparisons++; // Track element comparison against pivot on next line
                    if (array[j] <= pivotValue) {
                        pivotIndex++;
                        
                        // Swap array[pivotIndex] and array[j]
                        int temp = array[pivotIndex];
                        array[pivotIndex] = array[j];
                        array[j] = temp;
                        numExchanges++;
                    }
                }

                // Final swap to place pivot in its correct position
                int temp = array[pivotIndex + 1];
                array[pivotIndex + 1] = array[high];
                array[high] = temp;
                numExchanges++;

                int actualPivotPosition = pivotIndex + 1;

                // Push boundaries for unsorted sub-arrays, then repeat it all until stack is empty
                if (actualPivotPosition - 1 > low) {
                    stack.push(low);
                    stack.push(actualPivotPosition - 1);
                }
                if (actualPivotPosition + 1 < high) {
                    stack.push(actualPivotPosition + 1);
                    stack.push(high);
                }
            }
        } catch (StackFullException | StackEmptyException e) {
            System.err.println("Stack operations failed: " + e.getMessage());
        }

        return new SortResult(array, numComparisons, numExchanges);
    }
}
