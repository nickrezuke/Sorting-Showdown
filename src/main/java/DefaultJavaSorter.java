public class DefaultJavaSorter implements Sorter {
    public String getName() {
        return "Default Java .sort() Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        // Just use the standard Java .sort()
        java.util.Arrays.sort(array);

        // TODO: Figure out how to possibly count comparision & exchanges with the default sort??
        numberOfComparisons = -1;
        numberOfExchanges = -1;
        
        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}