public class SortingShowdown {
    // These are the algorithms currently being considered by the program:
    private static Sorter[] algorithms = {
            new CycleSorter(),
            new ExchangeSorter(),
            new BubbleSorter(),
            new CocktailShakerSorter(),
            new BrickSorter(),
            new PancakeSorter(),
            new CombSorter(),
            new InsertionSorter(),
            new SelectionSorter(),
            new DoubleSelectionSorter(),
            new GnomeSorter(),
            new ShellSorter(),
            new IterativeQuickSorter(),
            new RecursiveQuickSorter(),
            new BottomUpHeapSorter(),
            new TopDownHeapSorter(),
            new MergeSorter(),
            new IntroSorter(),
            new BucketSorter(),
            new LSDRadixSorter(),
            new MSDRadixSorter(),
            new EliminationSorter(),
            new CountingSorter(),
            //new BogoSorter(), //DO NOT USE
            //new CosmicRaySorter(), //DO NOT USE
            
    };
    
    public static void runRandomSorts(int listSize, int trialCount) {
        int[] sequentialArray = DataGenerator.generateSequentialArray(listSize);
        // This sequential array will be shuffled each trial
        runSortsOnArray(sequentialArray, trialCount, "", true);
    }

    public static void runSortsOnFiles(String[] fileNames, int trialCount) {
        for(int i = 0; i < fileNames.length; i++) {
            runSortsOnFile(fileNames[i], trialCount);
        }
    }
    public static void runSortsOnFiles(String[]fileNames) {
        runSortsOnFiles(fileNames, 1);
    }
    public static void runSortsOnFile(String fileName, int trialCount) {
        int[] theArray = DataGenerator.getArrayFromFile(fileName);
        runSortsOnArray(theArray, trialCount, fileName, false);
    }
    public static void runSortsOnFile(String fileName) {
        runSortsOnFile(fileName, 1);
    }

    public static void runSortsOnArray(int[] passedArray, int trialCount, String fileName, boolean useRandomArray) {
        // Determine the length of the longest sorting algorithm name
        // for the calculation of the table formatting dimensions
        int maxNameLength = 0;
        for (Sorter alg : algorithms) {
            String stylizedName = DataGenerator.getStylizedAlgorithmName(alg);
            if (stylizedName.length() > maxNameLength) {
                maxNameLength = stylizedName.length();
            }
        }

        if(trialCount > 1) {
            System.out.print("\nAveraging over " + trialCount + " trials of ");
        } else {
            System.out.print("\nSorting the list of numbers from ");
        }
        if(!useRandomArray) {
            System.out.println("the array in file: " + fileName);
        } else {
            if(passedArray.length > 6) {
                System.out.println("randomly shuffled values ranging [1, 2, 3, ..., " 
                + (passedArray.length-2) + ", " +(passedArray.length-1) + ", " + passedArray.length + "]");
            } else {
                System.out.println("randomly shuffled values [1-" + passedArray.length + "]");
            }
        }
        
        String formatString = "| %-" + Math.max(maxNameLength, 22) + "s | %13s | %11s | %13s |";
        System.out.println("-".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 11 + 3 + 13 + 2)); 
        // Represents the Horizontal bar for this table
        System.out.format(formatString + "\n", "Sorting Algorithm Name", "# Comparisons", "# Exchanges",
                "Duration (ns)");
        System.out.println("=".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 11 + 3 + 13 + 2));
        for (Sorter algorithm : algorithms) {
            // First, print initial line...
            System.out.format(formatString + " %s", DataGenerator.getStylizedAlgorithmName(algorithm), "-", "-", "-", "[0/"+trialCount+"] (0%)");
            // We'll keep track of the cumulative averages
            double avgComparisons = 0.0;
            double avgExchanges = 0.0;
            double avgTime = 0.0;
            boolean failed = false;
            trialStart:
            for (int trialNum = 0; trialNum < trialCount; trialNum++) {
                // For each trial, lets generate a new deep copy of the list
                int[] newArray = new int[passedArray.length];
                for (int i = 0; i < passedArray.length; i++) {
                    newArray[i] = passedArray[i]; // To get a deep copy
                }

                if(useRandomArray) {
                    // Shuffle the array for this trial
                    newArray = FisherYatesShuffler.shuffleArray(passedArray).shuffledArray();
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
                    System.out.format(formatString + " %s", DataGenerator.getStylizedAlgorithmName(algorithm), (int) avgComparisons,
                            (int) avgExchanges, (int) avgTime, progress);
                    System.out.flush();
                }
            }
            // Equivelant to \r
            System.out.print("\u001b[2K\u001b[G");
            if(failed) {
                System.out.println("| Error: " + algorithm.getName() + " failed to sort the list correctly!" + " ".repeat(Math.max(0, (Math.max(maxNameLength, 22)) - algorithm.getName().length() + 5)) + "|");
            } else {
                System.out.format(formatString + "\n", DataGenerator.getStylizedAlgorithmName(algorithm), (int) avgComparisons, (int) avgExchanges, (int) avgTime);
            }
            
        }
        System.out.println("-".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 11 + 3 + 13 + 2));
    }

    public static void main(String[] args) throws Exception {
        //DataGenerator.generateDatasets();

        int numberOfTrials = 100;
        int listSize = 100;

        runRandomSorts(listSize, numberOfTrials);
        // TODO: Figure out how to skip the initial JIT warmup time...
        // The Java Just-In-Time compiler will take a few runs (1,000?... ~15,000?? what scale???) to optimize the code, so the 
        // first few runs will be slower than the rest. This is a known issue with Java performance testing in general.
        System.out.println();
    }
}