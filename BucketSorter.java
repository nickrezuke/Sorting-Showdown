public class BucketSorter implements Sorter {
    public String getName() {
        return "???? Bucket Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        //TODO: Implement Bucket Sort
        
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}