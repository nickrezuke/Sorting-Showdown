public class BogoSorter implements Sorter {
    public String getName() {
        return Character.toString(0x1F3B2) + " Bogo Sort";
    }

    private boolean isSorted(int[] array) {
        for(int i = 0; i < array.length - 1; i++) {
            if(array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private int[] FisherYatesShuffle(int[] array) {
        for (int i = array.length - 1; i > 0; i--)
        {
            int j = (int)(Math.random() * (i + 1));
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        while(!isSorted(array)) {
            FisherYatesShuffle(array);
        }
        
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}