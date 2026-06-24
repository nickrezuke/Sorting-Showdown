public class CombSorter implements Sorter {
    public String getName() {
        return "???? Comb Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        //TODO: Implement Comb Sort
        
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}