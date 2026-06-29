public class SortingShowdown {
    // These are the algorithms currently being considered by the program:
    private static Sorter[] algorithms = {
            new BubbleSorter(),
            new CocktailShakerSorter(),
            new CombSorter(),
            new InsertionSorter(),
            new SelectionSorter(),
            new ShellSorter(),
            new RadixSorter(),
            new MergeSorter(),
            new QuickSorter(),
            new HeapSorter(),
            new CountingSorter(),
            new IntroSorter(),
            new BucketSorter(),
            new EliminationSorter(),
            new BogoSorter(), //DO NOT USE
            
    };
    
    public static void runRandomSorts(int listSize, int trialCount) {
        int[] newArray = new int[listSize];
        for (int i = 0; i < listSize; i++) {
            newArray[i] = i + 1; // To get 1,2,3,4,5,.....,1998,1999,2000 for N=2000
        }
        newArray = DataGenerator.FisherYatesShuffle(newArray);

        runSortsOnArray(newArray, trialCount, "");
    }

    public static void runSortsOnFiles(String[] fileNames, int trialCount) {
        for(int i = 0; i < fileNames.length; i++) {
            runSortsOnFile(fileNames[i], trialCount);
        }
    }
    public static void runTestsOnFiles(String[]fileNames) {
        runSortsOnFiles(fileNames, 1);
    }
    public static void runSortsOnFile(String fileName, int trialCount) {
        int[] theArray = DataGenerator.getArrayFromFile(fileName);

        runSortsOnArray(theArray, trialCount, fileName);
    }
    public static void runSortsOnFile(String fileName) {
        runSortsOnFile(fileName, 1);
    }

    public static void runSortsOnArray(int[] theArray, int trialCount, String fileName) {
        // Determine the length of the longest sorting algorithm name
        // for the calculation of the table formatting dimensions
        int maxNameLength = 0;
        for (Sorter alg : algorithms) {
            if (alg.getName().length() > maxNameLength) {
                maxNameLength = alg.getName().length();
            }
        }

        if(trialCount > 1) {
            System.out.print("\nAveraging over " + trialCount + " trials of ");
        } else {
            System.out.print("\nRunning one sorting trial of ");
        }
        if(fileName.length() > 0) {
            System.out.println("the file: " + fileName);
        } else {
            if(theArray.length > 6) {
                System.out.println("randomly shuffled lists of values ranging [1, 2, 3, ..., " 
                + (theArray.length-2) + ", " +(theArray.length-1) + ", " + theArray.length + "]");
            } else {
                System.out.println("randomly shuffled lists of values [1-" + theArray.length + "]");
            }
        }
        
        String formatString = "| %-" + Math.max(maxNameLength, 22) + "s | %13s | %11s | %13s |";
        System.out.println("-".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 11 + 3 + 13 + 2)); 
        // Represents the Horizontal bar for this table
        System.out.format(formatString + "\n", "Sorting Algorithm Name", "# Comparisons", "# Exchanges",
                "Duration (ns)");
        System.out.println("=".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 11 + 3 + 13 + 2));
        for (Sorter algorithm : algorithms) {
            // We'll keep track of the cumulative averages
            double avgComparisons = 0.0;
            double avgExchanges = 0.0;
            double avgTime = 0.0;
            boolean failed = false;
            trialStart:
            for (int trialNum = 0; trialNum < trialCount; trialNum++) {
                // For each trial, lets generate a new deep copy of the list
                int[] newArray = new int[theArray.length];
                for (int i = 0; i < theArray.length; i++) {
                    newArray[i] = theArray[i]; // To get a deep copy
                }

                // Lets sort the array and time it in nanoseconds
                long totalTime = System.nanoTime();
                SortResult result = algorithm.sort(newArray);
                totalTime = System.nanoTime() - totalTime;

                // Double Check the array is sorted correctly
                int[] sortedArr = result.sortedArray();
                for (int i = 0; i < sortedArr.length - 1; i++) {
                    // If the current element is greater than the next element, it is not sorted
                    if (sortedArr[i] > sortedArr[i + 1]) {
                        //throw new IllegalStateException("\n" + algorithm.getName() + " failed to sort the list!");
                        failed = true;
                        break trialStart;
                    }
                }

                // Update averages using cumulative average
                avgComparisons += (result.numberOfComparisons() - avgComparisons) / (trialNum
                        + 1.0);
                avgExchanges += (result.numberOfExchanges() - avgExchanges) / (trialNum +
                        1.0);
                avgTime += (totalTime - avgTime) / (trialNum + 1.0);
                //USE CUMULARIVE AVERAGE IN CASE WE USE SUPER HUGE NUMBERS TO AVOID OVERFLOW

                // Only print out when the percent meaningfully changes so we're
                // not printing the same line over and over if its slow
                if (trialCount < 1000 || (trialNum % Math.max(1, trialCount / 1000) == 0)) {
                    // Equivelant to \r
                    System.out.print("\u001b[2K\u001b[G");

                    String progress = String.format("[%d/%d] (%d%%)", trialNum + 1,
                            trialCount,
                            (int) ((trialNum + 1) * 100.0 / trialCount));
                    System.out.format(formatString + " %s", algorithm.getName(), (int) avgComparisons,
                            (int) avgExchanges, (int) avgTime, progress);
                    System.out.flush();
                }
            }
            // Equivelant to \r
            System.out.print("\u001b[2K\u001b[G");
            if(failed) {
                String parsedString = algorithm.getName().replaceFirst("\\p{IsEmoji}","").strip();
                System.out.println("| Error: " + parsedString + " failed to sort the list of numbers correctly!" + " ".repeat(17 - parsedString.length()) + "|");
            } else {
                System.out.format(formatString + "\n", algorithm.getName(), (int) avgComparisons, (int) avgExchanges, (int) avgTime);
            }
            
        }
        System.out.println("-".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 11 + 3 + 13 + 2));
    }

    public static void main(String[] args) {
        int numTrials = 5000;
        int listSize = 2000;

        System.out.println("Running " + numTrials + " trials of lists ranged 1-" + listSize);
        runRandomSorts(listSize, numTrials);
        System.out.println();
    }
}