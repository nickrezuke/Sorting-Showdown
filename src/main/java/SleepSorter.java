class SleepSorter implements Sorter {
    public String getName() {
        return "Sleep Sort";
    }

    public SortResult sort(int[] array) {
        int numComparisons = 0;
        int numExchanges = 0;

        int numItems = array.length;
        if (numItems <= 1) {
            return new SortResult(array, numComparisons, numExchanges);
        }

        Thread[] threads = new Thread[numItems];
        
        // A simple shared object tracker to safely increment our destination index pointer
        final int[] writeIndex = new int[1]; // Index 0 holds our current spot in the target array

        for (int i = 0; i < numItems; i++) {
            final int value = array[i];
            
            if (value < 0) {
                throw new IllegalArgumentException("Sleep sort cannot handle negative numbers.");
            }

            threads[i] = new Thread(() -> {
                try {
                    // Multiply by a scalar (e.g., 20 or 50) to beat CPU scheduling lag
                    Thread.sleep(value * 30); 
                    
                    // Thread-safe synchronized block to populate array without CopyOnWriteArrayList
                    synchronized (writeIndex) {
                        array[writeIndex[0]] = value;
                        writeIndex[0]++;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Start all threads sequentially in a dedicated loop 
        // to minimize creation time disparities
        for (int i = 0; i < numItems; i++) {
            threads[i].start();
        }

        // Wait for all threads to finish sleeping and writing
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Here, the list is sorted
        return new SortResult(array, numComparisons, numExchanges);
    }
}
