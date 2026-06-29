public class CountingSorter implements Sorter {
    public String getName() {
        //return "\u2246 Counting Sort";
        return Character.toString(0x1F4CA) + " Counting Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        if(array == null || array.length <= 1) {
            return new SortResult(new int[0], numberOfComparisons, numberOfExchanges);
        }

        //Find the maximum value in the array
        int maxVal = array[0];
        for(int i = 1; i < array.length; i++) {
            if(array[i] > maxVal) {
                maxVal = array[i];
            }
        }

        //Create a new array with that size
        int[] numCountArray = new int[maxVal + 1];

        //Count the occurrences of each number
        for(int i = 0; i < array.length; i++) {
            numCountArray[array[i]]++;
        }

        //Calculate the prefix sum
        for(int i = 1; i <= maxVal; i++) {
            numCountArray[i] += numCountArray[i - 1];
        }

        //Recreate an array to hold the sorted elements
        int[] sortedArray = new int[array.length];
        for(int i = array.length - 1; i >= 0; i--) {
            int currentNum = array[i];
            sortedArray[numCountArray[currentNum] - 1] = currentNum;
            numCountArray[currentNum]--;
        }

        //Done
        return new SortResult(sortedArray, numberOfComparisons, numberOfExchanges);
    }
}