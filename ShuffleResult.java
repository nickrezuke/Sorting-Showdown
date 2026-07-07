// This is the object I return after a shuffle. Its a basically a report containing
// the number of comparisons and exchanges, along with the shuffled list for
// verification
public class ShuffleResult {
    private int[] shuffledArray;
    private int numberOfExchanges;

    public ShuffleResult(int[] arr, int Exch) {
        shuffledArray = arr;
        numberOfExchanges = Exch;
    }

    public int[] shuffledArray() {
        return shuffledArray;
    }

    public int numberOfExchanges() {
        return numberOfExchanges;
    }
}