public class CountingSorter implements Sorter {
    public String getName() {
        return "Counting Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        if (array == null || array.length <= 1) {
            int[] result = (array == null) ? new int[0] : array.clone();
            return new SortResult(result, numberOfComparisons, numberOfExchanges);
        }

        // 1. Find the maximum value in the array first
        int maxVal = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxVal) {
                maxVal = array[i];
            }
        }

        int[] numCountArray;
        int numMAXVALUE = 0;

        // 1 and 1/2..... CHEAT INTERCEPT STEP:
        // This algorithm in its standard behavior makes the array of size maxVal + 1
        // In the event that the max value was MAX_VALUE for integers, this is an
        // issue...
        // So if we need to sort MAX_VAL, we "cheat" a little...
        if (maxVal == Integer.MAX_VALUE) {
            // Ok so we're dealing with an array with at least one MAX_VALUE,
            // Lets Count how many times it shows up
            for (int t = 0; t < array.length; t++) {
                if (array[t] == Integer.MAX_VALUE) {
                    numMAXVALUE++;
                }
            }

            // If the whole array is ONLY MaxInts, return immediately (its sorted)
            if (numMAXVALUE == array.length) {
                return new SortResult(array.clone(), numberOfComparisons, numberOfExchanges);
            }

            // Create a clean temporary array holding everything EXCEPT the MaxInts
            int[] filteredArray = new int[array.length - numMAXVALUE];
            int idx = 0;
            for (int t = 0; t < array.length; t++) {
                if (array[t] != Integer.MAX_VALUE) {
                    filteredArray[idx++] = array[t];
                }
            }

            // Point our working array reference to our filtered subset
            array = filteredArray;

            // Recalculate maxVal in our working effective array
            maxVal = array[0];
            for (int i = 1; i < array.length; i++) {
                if (array[i] > maxVal) {
                    maxVal = array[i];
                }
            }

            // Here we wre working with a modified copy of our original array but with all
            // the MAX_VALUEs left out... We'll add them back in later.
        }

        // 2. Standard counting sort code runs here unimpeded
        numCountArray = new int[maxVal + 1];

        for (int i = 0; i < array.length; i++) {
            numCountArray[array[i]]++;
        }

        for (int i = 1; i <= maxVal; i++) {
            numCountArray[i] += numCountArray[i - 1];
        }

        // Allocate the core sorted array based on our current working array length
        int[] sortedArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            int currentNum = array[i];
            sortedArray[numCountArray[currentNum] - 1] = currentNum;
            numCountArray[currentNum]--;
            numberOfExchanges++;
        }

        // 2 and 1/2. Append any MAX_VALUEs back in that we removed in our cheat step
        if (numMAXVALUE > 0) {
            int[] finalMergedArray = new int[sortedArray.length + numMAXVALUE];

            // Fast native memory copy to dump the sorted items into the front half
            System.arraycopy(sortedArray, 0, finalMergedArray, 0, sortedArray.length);

            // Instantly fill out the trailing slots with your saved MaxInt values
            for (int i = sortedArray.length; i < finalMergedArray.length; i++) {
                finalMergedArray[i] = Integer.MAX_VALUE;
                numberOfExchanges++;
            }
            sortedArray = finalMergedArray;
        }

        // Here, the list is sorted
        return new SortResult(sortedArray, numberOfComparisons, numberOfExchanges);
        // Counting sort is typically O(n+k) with no comparisons, so numComparisons is
        // always 0.
        // This is actually correct, it's worth noting this is fundamentally different
        // from comparison-based sorts.
    }
}
