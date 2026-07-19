public class GnomeSorter implements Sorter {
    public String getName() {
        return "Gnome Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        int currentIndex = 1;
        int nextUnsortedIndex = 2;

        while (currentIndex < array.length) {
            int previousIndex = currentIndex - 1;

            numberOfComparisons++;
            if (array[previousIndex] <= array[currentIndex]) {
                // Elements are in order; teleport forward to the next unsorted area
                currentIndex = nextUnsortedIndex;
                nextUnsortedIndex++;
            } else {
                // Elements are out of order; swap them
                int temporaryValue = array[previousIndex];
                array[previousIndex] = array[currentIndex];
                array[currentIndex] = temporaryValue;
                numberOfExchanges++;

                // Step backward to bubble the element down
                currentIndex--;

                // If we reach the starting edge, jump back up to the unsorted front
                if (currentIndex == 0) {
                    currentIndex = nextUnsortedIndex;
                    nextUnsortedIndex++;
                }
            }
        }

        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}