class PancakeSorter implements Sorter {
    public String getName() {
        return "Pancake Sort";
    }

    public SortResult sort(int[] array) {
        int numComparisons = 0;
        int numExchanges = 0;
        
        // TODO: Implement Pancake Sort

        return new SortResult(array, numComparisons, numExchanges);
    }
}