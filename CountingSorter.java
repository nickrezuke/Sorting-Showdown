public class CountingSorter implements Sorter {
    public String getName() {
        return "???? Counting Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        //TODO: Implement Counting Sort
        
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}