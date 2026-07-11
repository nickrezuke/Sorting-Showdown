public class BrickSorter implements Sorter {
    public String getName() {
        return "Brick Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        
        // TODO: Implement Brick Sort aka Odd Even Sort

        // Here, the list is sorted
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}