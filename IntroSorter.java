public class IntroSorter implements Sorter {
    public String getName() {
        return "Intro Sort";
    }

    // The threshold for switching to insertion sort
    private static final int INSERTION_THRESHOLD = 16;

    private static int numberOfComparisons;
    private static int numberOfExchanges;
    

    public SortResult sort(int[] array) {
        if (array == null || array.length <= 1) {
            return new SortResult(array, 0, 0);
        }
        numberOfComparisons = 0;
        numberOfExchanges = 0;

        int depthLimit = (int) (2 * Math.floor(Math.log(array.length) / Math.log(2)));
        dointrosort(array, 0, array.length - 1, depthLimit);
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }

    private static void dointrosort(int[] arr, int low, int high, int depthLimit) {
        while (high - low > INSERTION_THRESHOLD) {
            if (depthLimit == 0) {
                heapsort(arr, low, high);
                return;
            }
            depthLimit--;
            
            // Partition and eliminate tail recursion to preserve stack space
            int pivotIdx = partition(arr, low, high);
            dointrosort(arr, pivotIdx + 1, high, depthLimit);
            high = pivotIdx - 1; 
        }
        insertionSort(arr, low, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        
        numberOfComparisons++;
        if (arr[mid] < arr[low]) {
            swap(arr, mid, low);
        }

        numberOfComparisons++;
        if (arr[high] < arr[low]) {
            swap(arr, high, low);  
        }

        numberOfComparisons++;
        if (arr[high] < arr[mid]) {
            swap(arr, high, mid);
        }
        
        // Mid is now the median, place it as pivot at high - 1
        swap(arr, mid, high - 1);
        int pivot = arr[high - 1];
        
        int i = low;
        int j = high - 1;
        
        while (true) {
            while (true) { // Do until we find an element >= pivot
                numberOfComparisons++;
                if (arr[++i] >= pivot) {
                    break;
                }
            }
            while (true) { // Do until we find an element <= pivot
                numberOfComparisons++;
                if (pivot >= arr[--j]) {
                    break;
                }
            }
            if (i >= j) { // If the pointers have crossed, we are done partitioning
                break;
            }

            swap(arr, i, j);
        }
        swap(arr, i, high - 1);
        return i;
    }

    // A local implementation of insertion sort that sorts a subarray when needed
    private static void insertionSort(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= low) {
                numberOfComparisons++; // Count the comparison we are about to make
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    numberOfExchanges++; // Value actually shifted
                    j--;
                } else {
                    break; // Comparison failed, stop the loop safely
                }
            }
            
            // Only count an exchange if the key actually moved to a new position
            if (j + 1 != i) {
                arr[j + 1] = key;
                numberOfExchanges++;
            }
        }
    }
    

    // A local implementation of heapsort that sorts a subarray when needed
    private static void heapsort(int[] arr, int low, int high) {
        int n = high - low + 1; // Number of elements in this specific subarray
        
        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, low);
        }
        
        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            swap(arr, low, low + i); // Move current root to the end of the heap range
            heapify(arr, i, 0, low); // Maintain heap property 
        }
    }
    
    private static void heapify(int[] arr, int n, int i, int low) {
        int largest = i; 
        int left = 2 * i + 1;
        int right = 2 * i + 2; 
    
        // Check if left child exists and is larger than root
        if (left < n) {
            numberOfComparisons++; // We are performing the array comparison on the next line
            if (arr[low + left] > arr[low + largest]) {
                largest = left;
            }
        }
    
        // Check if right child exists and is larger than current largest
        if (right < n) {
            numberOfComparisons++; // We are performing the array comparison on the next line
            if (arr[low + right] > arr[low + largest]) {
                largest = right;
            }
        }
    
        // If largest is not the root, swap them and continue down
        if (largest != i) {
            swap(arr, low + i, low + largest);
            heapify(arr, n, largest, low);
        }
    }
    

    private static void swap(int[] arr, int i, int j) {
        if (i != j) { // Avoid counting unnecessary swaps
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            numberOfExchanges++;
        }
    }
}
