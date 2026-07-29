class IdentityCrisisSorter implements Sorter {
    private int numberOfComparisons;
    private int numberOfExchanges;

    public String getName() {
        return "Identity Crisis Sort (10 Identities)";
    }

    public SortResult sort(int[] array) {
        int numItems = array.length;
        this.numberOfComparisons = 0;
        this.numberOfExchanges = 0;

        if (numItems <= 1) {
            return new SortResult(array, 0, 0);
        }

        // Time-seeded LCG formula to get a clean number between 0 and 9
        long seed = System.nanoTime();
        seed = (seed * 1103515245 + 12345) & 0x7fffffff;
        int roll = (int) (seed % 10);

        switch (roll) {
            case 0: runBubble(array, numItems); break;
            case 1: runInsertion(array, numItems); break;
            case 2: runSelection(array, numItems); break;
            case 3: runGnome(array, numItems); break;
            case 4: runCocktail(array, numItems); break;
            case 5: runBrick(array, numItems); break;
            case 6: runComb(array, numItems); break;
            case 7: runShell(array, numItems); break;
            case 8: runQuick(array, 0, numItems - 1); break;
            case 9: runHeap(array, numItems); break;
        }

        return new SortResult(array, this.numberOfComparisons, this.numberOfExchanges);
    }

    private void runBubble(int[] arr, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                this.numberOfComparisons++;
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    private void runInsertion(int[] arr, int n) {
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0) {
                this.numberOfComparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    this.numberOfExchanges++;
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }

    private void runSelection(int[] arr, int n) {
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                this.numberOfComparisons++;
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(arr, i, min);
            }
        }
    }

    private void runGnome(int[] arr, int n) {
        int idx = 0;
        while (idx < n) {
            if (idx == 0) {
                idx++;
            }
            this.numberOfComparisons++;
            if (arr[idx] >= arr[idx - 1]) {
                idx++;
            } else {
                swap(arr, idx, idx - 1);
                idx--;
            }
        }
    }

    private void runCocktail(int[] arr, int n) {
        boolean swapped = true;
        int start = 0;
        int end = n - 1;
        while (swapped) {
            swapped = false;
            for (int i = start; i < end; i++) {
                this.numberOfComparisons++;
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
            swapped = false;
            end--;
            for (int i = end - 1; i >= start; i--) {
                this.numberOfComparisons++;
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    swapped = true;
                }
            }
            start++;
        }
    }

    private void runBrick(int[] arr, int n) {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 1; i < n - 1; i += 2) {
                this.numberOfComparisons++;
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    sorted = false;
                }
            }
            for (int i = 0; i < n - 1; i += 2) {
                this.numberOfComparisons++;
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    sorted = false;
                }
            }
        }
    }

    private void runComb(int[] arr, int n) {
        int gap = n;
        boolean swapped = true;
        while (gap != 1 || swapped) {
            gap = (gap * 10) / 13;
            if (gap < 1) {
                gap = 1;
            }
            swapped = false;
            for (int i = 0; i < n - gap; i++) {
                this.numberOfComparisons++;
                if (arr[i] > arr[i + gap]) {
                    swap(arr, i, i + gap);
                    swapped = true;
                }
            }
        }
    }

    private void runShell(int[] arr, int n) {
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap; j -= gap) {
                    this.numberOfComparisons++;
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        this.numberOfExchanges++;
                    } else {
                        break;
                    }
                }
                arr[j] = temp;
            }
        }
    }

    private void runQuick(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = arr[high];
            int i = low - 1;
            for (int j = low; j < high; j++) {
                this.numberOfComparisons++;
                if (arr[j] < pivot) {
                    i++;
                    swap(arr, i, j);
                }
            }
            swap(arr, i + 1, high);
            int pivotIndex = i + 1;

            runQuick(arr, low, pivotIndex - 1);
            runQuick(arr, pivotIndex + 1, high);
        }
    }

    private void runHeap(int[] arr, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n) {
            this.numberOfComparisons++;
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }
        if (right < n) {
            this.numberOfComparisons++;
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        this.numberOfExchanges++;
    }
}
