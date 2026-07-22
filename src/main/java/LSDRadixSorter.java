class LSDRadixSorter implements Sorter {
    public String getName() {
        return "LSD Radix Sort";
    }

    // Default Sort (uses base 10 by default unless one is passed in)
    public SortResult sort(int[] array) {
        return sort(array, 10); // Default base of 10 if one wasn't passed
    }

    // Also allow passing in of which base we want to use
    public SortResult sort(int[] array, int base) {
        if (array == null || array.length < 2) {
            return new SortResult(array, 0, 0);
        }

        // We will use 'array' as our master list, but now we need more arrays,
        // (specifically one for each possible digit) for queues to sort the digits

        int numberOfMoves = 0; // Equivelant to numExchanges in Radix Sort since we move instead of swap
        int numberOfComparisons = 0; // Radix Sort is a non-comparitive sort, we never make any comparisons

        int digits = 1; // This variable will keep track of the "place" we are sorting by, starting with
        // 1 = "1"s place, 2 = "10"s place, 3 = "100"s place, etc. in base "base"

        long divisor = 1; // This will come to represent the "base" to the "digit"s power. (used later on)
        // Used a long because this might get large for datasets with huge numbers

        int maxDigits = 0; // This variable will keep track of the maximum number of digits in
        // any number in the original array, so we know when to stop sorting by more
        // digits

        // Lets find the maximum value for set-up purposes
        int maxVal = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxVal) {
                maxVal = array[i];
            }
        }

        // Get the number of digits that maxVal has in base "base"
        int temp = maxVal; // Make a new int to "divide down" by, until its 0
        if (temp == 0) { // If the number is already 0, there is technically still 1 digit... "0"
            maxDigits = 1;
        } else {
            while (temp > 0) { // The number of times we can divide maxVal (temp) into base is
                maxDigits++; // the number of digits the value has in base "base"
                temp /= base;
            }
        }
        // So now, maxDigits is the number of digits in the largest number in the
        // original array, so, we know we need to sort by at most "maxDigits" digits to
        // ensure the whole list is sorted
        while (digits <= maxDigits) {
            // Set up Queues
            ADTQueue[] queues = new ADTQueue[base]; // We will have an array of queues
            for (int i = 0; i < base; i++) {
                queues[i] = new QueueArray(array.length); // Use an Array implementation
                // queues[i] = new QueueLinkedList(); // Use a Linked List implementation
            }
            // First we need to distribute the numbers in the original array into the
            // appropriate queues based on the current digit we are sorting by
            for (int num = 0; num < array.length; num++) {
                // Calculate the value of the current digit we are sorting by for this number
                // (I used longs here because the numbers could get pretty large)
                int digitValue = (int) (((long) array[num] / divisor) % (long) base);
                // Now, digitValue is the specific digit we are looking at, put this value into
                // that numbered queue
                try {
                    // Enqueue this number into a queue based on the value of the current digit
                    queues[digitValue].enqueue(array[num]);
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

            for (int queueNum = 0, arrayIndex = 0; queueNum < base; queueNum++) {
                // Now we need to collect the numbers from the queues back into the original
                // array in order, starting with queue 0 and going up to queue (base - 1), so
                // that they are sorted by the current digit
                while (!queues[queueNum].isEmpty()) {
                    // Dequeue the next number from this queue and put it
                    // back into the original array at the current index
                    array[arrayIndex] = queues[queueNum].dequeue();
                    arrayIndex++; // Move to the next index in the original array for the next number we put back
                    numberOfMoves++; // Enqueue-Dequeues are "moves" so count this one here as a move

                }
            }

            digits++; // Move to the next digits place value
            divisor *= base; // Divisor is increased one more power of "base" larger
        }

        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfMoves);
    }
}