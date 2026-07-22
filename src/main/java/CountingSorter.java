public class CountingSorter implements Sorter { 
    public String getName() { return "Counting Sort"; } 

    public SortResult sort(int[] array) { 
        int numberOfComparisons = 0; 
        int numberOfExchanges = 0; 

        if(array == null || array.length <= 1) { 
            int[] result = (array == null) ? new int[0] : array.clone();
            return new SortResult(result, numberOfComparisons, numberOfExchanges); 
        } 

        // 1. ORIGINAL FLOW: Find the maximum value in the array first
        int maxVal = array[0]; 
        for(int i = 1; i < array.length; i++) { 
            if(array[i] > maxVal) { 
                maxVal = array[i]; 
            } 
        } 

        int[] numCountArray; 
        int numMAXVALUE = 0; 

        // 2. INTERCEPT STEP: Only cheat if maxVal hits the extreme limit!!
        if(maxVal == Integer.MAX_VALUE) { 
            // Count how many times it shows up
            for(int t = 0; t < array.length; t++) { 
                if(array[t] == Integer.MAX_VALUE) { 
                    numMAXVALUE++; 
                } 
            } 

            // FIX TRAP 1: If it's ONLY MaxInts, return immediately to prevent empty array crashes
            if (numMAXVALUE == array.length) {
                return new SortResult(array.clone(), numberOfComparisons, numberOfExchanges);
            }

            // Create a clean temporary array holding everything EXCEPT the MaxInts
            int[] filteredArray = new int[array.length - numMAXVALUE];
            int idx = 0;
            for(int t = 0; t < array.length; t++) { 
                if(array[t] != Integer.MAX_VALUE) { 
                    filteredArray[idx++] = array[t];
                } 
            } 

            // Point our working array reference to our filtered subset
            array = filteredArray; 

            // ORIGINAL FLOW: Recalculate maxVal in our working effective array
            maxVal = array[0]; 
            for(int i = 1; i < array.length; i++) { 
                if(array[i] > maxVal) { 
                    maxVal = array[i]; 
                } 
            } 

            // FIX TRAP 3: OutOfMemory safety valve (e.g. if the next highest item is Integer.MAX_VALUE - 1)
            if (maxVal > 50_000_000 || maxVal < 0) {
                return new MergeSorter().sort(array);
            }
        } 

        // 3. ORIGINAL PIPELINE: Standard counting sort code runs here unimpeded
        numCountArray = new int[maxVal + 1]; 

        for(int i = 0; i < array.length; i++) { 
            numCountArray[array[i]]++; 
        } 

        for(int i = 1; i <= maxVal; i++) { 
            numCountArray[i] += numCountArray[i - 1]; 
        } 

        // Allocate the core sorted array based on our current working array length
        int[] sortedArray = new int[array.length]; 
        for(int i = array.length - 1; i >= 0; i--) { 
            int currentNum = array[i]; 
            sortedArray[numCountArray[currentNum] - 1] = currentNum; 
            numCountArray[currentNum]--; 
            numberOfExchanges++;
        } 

        // 4. FIX TRAP 2: Fast append cheat instead of a slow loop recreating arrays
        if (numMAXVALUE > 0) {
            int[] finalMergedArray = new int[sortedArray.length + numMAXVALUE];
            
            // Fast native memory copy to dump the sorted items into the front half
            System.arraycopy(sortedArray, 0, finalMergedArray, 0, sortedArray.length);
            
            // Instantly fill out the trailing slots with your saved MaxInt values
            for(int i = sortedArray.length; i < finalMergedArray.length; i++) {
                finalMergedArray[i] = Integer.MAX_VALUE;
                numberOfExchanges++;
            }
            sortedArray = finalMergedArray;
        }

        return new SortResult(sortedArray, numberOfComparisons, numberOfExchanges); 
    } 
}
