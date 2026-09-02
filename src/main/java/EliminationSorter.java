public class EliminationSorter implements Sorter {
    public String getName() {
        return "Elimination Sort";
    }

    public SortResult sort(int[] array) {
        // Sorts the list by eliminating any out of place elements
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        if (array == null || array.length <= 1) {
            return new SortResult(array, numberOfComparisons, numberOfExchanges);
        }

        int index = 1;
        int highest = array[0];
        QueueArray sorted = new QueueArray();
        sorted.enqueue(array[0]);
        while (index < array.length) {
            int num = array[index];
            if (highest < num) {
                sorted.enqueue(num);
                highest = num;
            } else {
                // We performed an elimination, count these as "exchanges" 
                // as we have exchanged them to the afterlife
                numberOfExchanges++;
            }
            numberOfComparisons++;
            index++;
        }

        // Recreate an array from the remainders
        int[] sortedArray = new int[sorted.size()];
        int i = 0;
        while (!sorted.isEmpty()) {
            sortedArray[i] = sorted.dequeue();
            i++;
        }

        // Here, the list is sorted, pay no attention to those who may or may not have
        // caused us trouble
        return new SortResult(sortedArray, numberOfComparisons, numberOfExchanges);
    }
}