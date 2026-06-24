// This is the object I return after a sort. Its a basically a report containing
// the number of comparsisons and exchanges, along with the sorted list for
// verification
public class SortResult {
    private int[] sortedArray;
    private int numberOfComparisons;
    private int numberOfExchanges;

    public SortResult(int[] arr, int Comp, int Exch) {
        sortedArray = arr;
        numberOfComparisons = Comp;
        numberOfExchanges = Exch;
    }

    public int[] sortedArray() {
        return sortedArray;
    }

    public int numberOfExchanges() {
        return numberOfExchanges;
    }

    public int numberOfComparisons() {
        return numberOfComparisons;
    }
}