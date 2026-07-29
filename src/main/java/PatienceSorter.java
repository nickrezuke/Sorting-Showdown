class PatienceSorter implements Sorter {
    public String getName() {
        return "Patience Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Phase 1: Create a raw 2D array to act as our card piles
        int[][] piles = new int[numItems][numItems];
        int[] pileSizes = new int[numItems]; 
        int pileCount = 0;

        // Distribute elements into piles using Binary Search
        for (int i = 0; i < numItems; i++) {
            int key = array[i];
            
            int low = 0;
            int high = pileCount - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                numberOfComparisons++;
                
                int topCardOfMidPile = piles[mid][pileSizes[mid] - 1];
                if (topCardOfMidPile >= key) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            piles[low][pileSizes[low]] = key;
            pileSizes[low]++;
            numberOfExchanges++; 

            if (low == pileCount) {
                pileCount++;
            }
        }

        // Phase 2: Merge piles
        // It strictly takes ints, so we feed it raw card values. No tricks.
        ADTQueue minHeap = new PriorityQueueArray(pileCount); // Array Implementation
        //ADTQueue minHeap = new PriorityQueueLinkedList(); // Linked List Implementation


        // Load the heap with the initial top card of every pile
        for (int i = 0; i < pileCount; i++) {
            int topCard = piles[i][pileSizes[i] - 1];
            try {
                minHeap.enqueue(topCard); // Standard enqueue of a raw int
            } catch (QueueFullException e) {
                // Guaranteed not to fill past capacity
            }
        }

        int targetIndex = 0;
        while (!minHeap.isEmpty()) {
            int smallestValue = 0;
            try {
                smallestValue = minHeap.dequeue(); // Standard dequeue of a raw int
            } catch (QueueEmptyException e) {
                break;
            }

            // Write the value back to your sorted array
            array[targetIndex++] = smallestValue;
            numberOfExchanges++;

            // Simple Search: Look across our active piles to find which one
            // matches the card value we just processed.
            int sourcePileIdx = -1;
            for (int i = 0; i < pileCount; i++) {
                // If this pile still has cards, check its top card
                if (pileSizes[i] > 0 && piles[i][pileSizes[i] - 1] == smallestValue) {
                    sourcePileIdx = i;
                    break; // Found the matching pile!
                }
            }

            // Pop the used card out of that pile
            pileSizes[sourcePileIdx]--; 

            // If that pile still has remaining cards left, add its new top card to the heap
            if (pileSizes[sourcePileIdx] > 0) {
                int newTopCard = piles[sourcePileIdx][pileSizes[sourcePileIdx] - 1];
                try {
                    minHeap.enqueue(newTopCard);
                } catch (QueueFullException e) {
                    // Safe because we just dequeued an element
                }
            }
            numberOfComparisons++;
        }

        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}
