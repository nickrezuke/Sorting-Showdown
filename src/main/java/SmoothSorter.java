class SmoothSorter implements Sorter {
    // Lookup array for Leonardo numbers up to L_45
    private final int[] LP = {
        1, 1, 3, 5, 9, 15, 25, 41, 67, 109, 177, 287, 465, 753, 1219, 1973, 3193, 5167, 8361, 
        13529, 21891, 35421, 57313, 92735, 150049, 242785, 392835, 635621, 1028457, 1664079, 
        2692537, 4356617, 7049155, 11405773, 18454929, 29860703, 48315633, 78176337, 126491971, 
        204668309, 331160281, 535828591, 866988873, 1402817465, 2147483647
    };

    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Smooth Sort";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        int p = 1; 
        int b = 1; 

        // Phase 1: Build the cascading Leonardo heap array from left to right
        for (int i = 1; i < numItems; i++) {
            if ((p & 3) == 3) {
                p >>= 2;
                b += 2;
            } else if (b == 1) {
                p <<= 1;
                while ((p & 1) == 0) {
                    p >>= 1;
                    b++;
                }
            } else {
                p <<= (b - 1);
                b = 1;
            }
            p |= 1;
            trinkle(array, i, p, b);
        }

        // Phase 2: Unwind and extract the roots back down from right to left
        for (int i = numItems - 1; i > 0; i--) {
            if (b <= 1) {
                p--;
                while (p > 0 && (p & 1) == 0) {
                    p >>= 1;
                    b++;
                }
            } else {
                p--;
                p <<= 2;
                p |= 3;
                b -= 2;

                // Stabilize the left child block across neighboring heap structures
                trinkle(array, i - LP[b + 1] - 1, p >> 1, b + 1);
                // Stabilize the right child block
                trinkle(array, i - 1, p, b);
            }
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void trinkle(int[] array, int r, int p, int b) {
        while (p > 1) {
            int step = r - LP[b];
            
            // CRITICAL FIXED GUARD: If the look-behind index crosses out of bounds, stop trickling left
            if (step < 0) {
                break;
            }

            this.numberOfComparisons++;
            if (array[step] <= array[r]) {
                break; 
            }

            if (b > 1) {
                int r2 = r - 1;
                int r3 = r2 - LP[b - 2];
                
                if (r2 >= 0 && r3 >= 0) {
                    this.numberOfComparisons += 2;
                    if (array[step] < array[r2] || array[step] < array[r3]) {
                        break; 
                    }
                }
            }

            swap(array, r, step);
            r = step;

            p--;
            while ((p & 1) == 0) {
                p >>= 1;
                b++;
            }
        }
        semiremedy(array, r, b);
    }

    private void semiremedy(int[] array, int r, int b) {
        while (b > 1) {
            int r2 = r - 1; 
            int r3 = r2 - LP[b - 2]; 
            int largest = r;

            if (r3 >= 0 && r3 < array.length) {
                this.numberOfComparisons++;
                if (array[r3] > array[largest]) {
                    largest = r3;
                }
            }
            if (r2 >= 0 && r2 < array.length) {
                this.numberOfComparisons++;
                if (array[r2] > array[largest]) {
                    largest = r2;
                }
            }

            if (largest == r) {
                break; 
            }

            swap(array, r, largest);
            r = largest;
            
            b = (largest == r3) ? (b - 1) : (b - 2);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        this.numberOfExchanges++;
    }
}
