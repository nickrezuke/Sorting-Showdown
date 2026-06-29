public class EliminationSorter implements Sorter {
    public String getName() {
        return Character.toString(0x1F480) + " Elimination Sort";
    }

    public SortResult sort(int[] array) {
        //Sorts the list by eliminating out of place elements
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        int index = 1;
        int highest = array[0];
        QueueArray sorted = new QueueArray();
        sorted.enqueue(array[0]);
        while(index < array.length) {
            int num = array[index];
            if(highest < num) {
                sorted.enqueue(num);
                highest = num;
            } else {
                //We performed an elimination, count these as "exchanges" as we exchange them to the afterlife
                numberOfExchanges++;
            }
            numberOfComparisons++;
            index++;
        }

        //Recreate an array from the remainders
        int[] sortedArray = new int[sorted.size()];
        int size = sorted.size();
        for(int i = 0; i < size; i++) {
            sortedArray[i] = sorted.dequeue();
        }
        
        // Here, the list is sorted, pay no attention to those that have caused us trouble
        return new SortResult(sortedArray, numberOfComparisons, numberOfExchanges);
    }
}