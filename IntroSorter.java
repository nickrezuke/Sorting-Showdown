public class IntroSorter implements Sorter {
    public String getName() {
        return "???? Intro Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        //TODO: Implement Intro Sort
        
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}