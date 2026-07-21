class PancakeSorter implements Sorter {
    public String getName() {
        return "Pancake Sort";
    }

    public SortResult sort(int[] array) {
        // Pancake Sort is a variation of the sorting problem where the only allowed operation 
        // is a "flip" (reversing a subarray from index 0 to k), instead of the traditional 
        // "exchange" or "write" operation where we simply change the data value of one element. 
        // The name comes from a fun mathematical analogy: imagine a stack of pancakes of different 
        // sizes, and you want to sort them from smallest (top) to largest (bottom). The only thing 
        // you can do is slide a spatula underneath any specific pancake, lift the whole top stack 
        // above your spatule up into the air, and flip it upside down.

        int numComparisons = 0;

        // Due to the nature of this algorithm, we will count flips as "exchanges"
        int numFlips = 0;

        // Start from the full array and reduce
        for (int currSize = array.length; currSize > 1; currSize--) {
            
            // Find the index of the maximum element in array[0...currSize-1]
            int maxElemIndex = 0;
            for (int i = 1; i < currSize; i++) {
                numComparisons++; // Comparison on next line
                if (array[i] > array[maxElemIndex]) {
                    maxElemIndex = i;
                }
            }

            // Move that maximum element to the end of the current sub-array
            if (maxElemIndex != currSize - 1) {
                // Step 1: Flip to move the max element to the front (index 0)
                if (maxElemIndex != 0) {
                    flip(array, maxElemIndex);
                    numFlips++;
                }
                
                // Step 2: Flip the entire active sub-array to move the max to the back
                flip(array, currSize - 1);
                numFlips++;
            }
        }

        // Here, the list is sorted
        return new SortResult(array, numComparisons, numFlips);
    }

    // Helper method to flip/reverse the sub-array from 0 to k
    private void flip(int[] array, int k) {
        int start = 0;
        while (start < k) {
            // Swap elements at 'start' and 'k'
            int temp = array[start];
            array[start] = array[k];
            array[k] = temp;

            start++;
            k--;
        }
    }
}
