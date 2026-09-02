public class BucketSorter implements Sorter {
    private int numberOfExchanges;
    private int numberOfComparisons;

    public String getName() {
        return "Bucket Sort";
    }

    public SortResult sort(int[] array) {
        numberOfComparisons = 0;
        numberOfExchanges = 0;

        if (array == null || array.length <= 1) {
            return new SortResult(array, numberOfComparisons, numberOfExchanges);
        }

        // Find the range of input data to consider
        int min = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            // Don't track these comparisons, as they are just for 
            // finding the min and max values, not for sorting
            if (array[i] < min) {
                min = array[i];
            } else if (array[i] > max) {
                max = array[i];
            }
        }

        // Astromically rare, but somehow if all elements are identical, no need to sort
        if (min == max) {
            return new SortResult(array, numberOfComparisons, numberOfExchanges);
        }

        // Define the number of buckets
        int numBuckets = array.length;
        long range = (long) max - min + 1;

        // Count frequencies (Scatter phase A)
        int[] bucketCounts = new int[numBuckets];
        for (int i = 0; i < array.length; i++) {
            int bucketIndex = getBucketIndex(array[i], min, range, numBuckets);
            bucketCounts[bucketIndex]++;
        }

        // Calculate starting memory positions for each bucket
        int[] bucketOffsets = new int[numBuckets];
        bucketOffsets[0] = 0;
        for (int i = 1; i < numBuckets; i++) {
            bucketOffsets[i] = bucketOffsets[i - 1] + bucketCounts[i - 1];
        }

        // Make flat temp array (Scatter phase B)
        int[] tempArray = new int[array.length];
        int[] currentOffsets = new int[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            currentOffsets[i] = bucketOffsets[i];
        }

        for (int i = 0; i < array.length; i++) {
            int bucketIndex = getBucketIndex(array[i], min, range, numBuckets);
            tempArray[currentOffsets[bucketIndex]++] = array[i];
            numberOfExchanges++; // Finally we track this
        }

        // Sort individual sections
        for (int i = 0; i < numBuckets; i++) {
            int start = bucketOffsets[i];
            int size = bucketCounts[i];
            if (size > 1) {
                // Do Insertion Sort and record the comparisons and exchanges
                int[] updatedValues = insertionSort(tempArray, start, start + size - 1);
                numberOfComparisons += updatedValues[0];
                numberOfExchanges += updatedValues[1];
            }
        }

        // Here, the list is sorted
        return new SortResult(tempArray, numberOfComparisons, numberOfExchanges);
    }

    private static int getBucketIndex(int value, int min, long range, int numBuckets) {
        return (int) (((long) value - min) * (numBuckets) / range);
    }

    private int[] insertionSort(int[] array, int low, int high) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= low) {
                numberOfComparisons++; // Upcoming comparison
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    numberOfExchanges++;
                    j--;
                } else {
                    break;
                }
            }
            if (j + 1 != i) {
                // Count it if the key actually moved positions
                array[j + 1] = key;
                numberOfExchanges++;
            }
        }
        // Return these values this specific sort did to contribute to the overall count
        int[] updatedValues = { numberOfComparisons, numberOfExchanges };
        return updatedValues;
    }
}