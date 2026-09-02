class CosmicRaySorter implements Sorter {
    public String getName() {
        return "Cosmic Ray Sort";
    }

    public SortResult sort(int[] array) {
        int numExchanges = 0;
        int numComparisons = 0;

        boolean isSorted = false;

        while (!isSorted) {
            // Check if the list is currently sorted
            isSorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    isSorted = false;
                }
            }

            // Wait for a little bit, to allow some time for a 
            // potential hardware malfunction to have changed something
            try {
                Thread.sleep(1); // Wait for a millisecond before checking again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new SortResult(array, numComparisons, numExchanges);
    }
}