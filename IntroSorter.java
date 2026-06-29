public class IntroSorter implements Sorter {
    public String getName() {
        //return "\u2B4D Intro Sort";
        return Character.toString(0x1F39B) + "  Intro Sort\u3000";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        //TODO: Implement Intro Sort
        
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}