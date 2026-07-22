class MSDRadixSorter implements Sorter {
    private int numberOfMoves;
    private int numberOfComparisons;

    public String getName() {
        return "MSD Radix Sort";
    }

    // Default Sort (uses base 10 by default unless one is passed in)
    public SortResult sort(int[] array) {
        return sort(array, 10);
    }

    // Also allow passing in of which base we want to use
    public SortResult sort(int[] array, int base) {
        numberOfMoves = 0;
        numberOfComparisons = 0;

        // Find the maximum value to determine the starting divisor
        int maxVal = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxVal) {
                // Dont count this as a comparison, since we are just finding the max value for
                // set-up purposes, not sorting...
                maxVal = array[i];
            }
        }

        // Calculate the highest divisor (ex. if maxVal is 345 in base 10, maxDivisor
        // should be 100)
        long maxDivisor = 1;
        while (maxVal / maxDivisor >= base) {
            maxDivisor *= base;
        }

        // Kick off the recursive MSD sort across the entire array range
        recurseMSDRadix(array, 0, array.length, maxDivisor, base);

        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfMoves);
    }

    // Recursive helper method to sort a specific subarray range [start, end)
    private void recurseMSDRadix(int[] array, int start, int end, long divisor, int base) {
        // Base case: Stop if the subarray has 1 or 0 elements, or we passed the 1s
        // place
        if (end - start <= 1 || divisor < 1) {
            return;
        }

        // Create queues for distribution
        ADTQueue[] queues = new ADTQueue[base]; // We will have an array of queues
        for (int i = 0; i < base; i++) {
            // queues[i] = new QueueArray(end - start); // Use an Array implementation
            queues[i] = new QueueLinkedList(); // Use a Linked List implementation
        }

        // Arrays to track the starting index and size of each bucket in the main array
        int[] bucketStarts = new int[base];
        int[] bucketSizes = new int[base];

        // Distribute elements into queues based on the current high digit
        for (int i = start; i < end; i++) {
            int digitValue = (int) (((long) array[i] / divisor) % (long) base);
            try {
                // Enqueue this number into a queue based on the value of the current digit
                queues[digitValue].enqueue(array[i]);
                // Enqueues and Dequeues are "moves" so we'll count this whole
                // enque-dequeue pair as a move once we dequeue it later
            } catch (QueueFullException e) {
                // Enqueueing failed, say so
                String error = "Error: Queue ";
                error = error + digitValue;
                error = error + " is full.  This should never happen. - ";
                error = error + "Code may have logical errors if this appears.";
                System.out.println(error);
            }
        }

        // Collect elements back into the main array and record bucket positions
        int arrayIndex = start;
        for (int queueNum = 0; queueNum < base; queueNum++) {
            bucketStarts[queueNum] = arrayIndex; // Track where this bucket begins
            int size = 0;

            while (!queues[queueNum].isEmpty()) {
                array[arrayIndex] = queues[queueNum].dequeue();
                arrayIndex++;
                size++;
                numberOfMoves++;
            }
            bucketSizes[queueNum] = size; // Track how many items ended up in this bucket
        }

        // Now we have to recursively sort each bucket for the next lower digit
        for (int queueNum = 0; queueNum < base; queueNum++) {
            int bStart = bucketStarts[queueNum];
            int bEnd = bStart + bucketSizes[queueNum];

            // Only recurse if the sub-bucket actually has elements to sort
            if (bucketSizes[queueNum] > 1) {
                recurseMSDRadix(array, bStart, bEnd, divisor / base, base);
            }
        }
    }
}
