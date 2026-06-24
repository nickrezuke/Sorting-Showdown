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
            new IntroSorter(),
            new CountingSorter(),
            new IntroSorter(),
            new BucketSorter()
    };

    // This prints out the average results of sorting lists of size "listSize" 
    // over the course of trialCount sorts.  Average results printed. 
    public static void runRandomTests(int listSize, int trialCount) {
        // TODO: Implement this
    }

    // This prints the sort results using a passed in file(s) to sort
    public static void runTestsOnFiles(String[] fileNames, int trialCount) {
        for(int i = 0; i < fileNames.length; i++) {
            runTestsOnFile(fileNames[i], trialCount);
        }
    }
    public static void runTestsOnFiles(String[]fileNames) {
        runTestsOnFiles(fileNames, 1);
    }
    public static void runTestsOnFile(String fileName, int trialCount) {
        // TODO: Implement this

    }
    public static void runTestsOnFile(String fileName) {
        runTestsOnFile(fileName, 1);
    }


    public static void main(String[] args) {
        // Get the data from the files, replace these strings with your own test
        // file(s), or pass one in as a command line argument
        String[] fileNames = {
                // These are my default test files that are created from my DataGenerator.java
                // Feel free to use these or create your own test files with different data and
                // replace these names with your own as needed, or replace these names with your
                // own as needed or pass txt file names in as a command line argument, such as
                // "java SortingAlgorithms myTestFile1.txt myTestFile2.txt myTestFile3.txt"
                "numbers_1_to_10.txt",
                "numbers_10_to_1.txt",
                "numbers_even_1_to_10.txt",
                "numbers_1_to_2000.txt",
                "numbers_2000_to_1.txt",
                "random_2000_numbers.txt"
        };

        if (args.length > 0) {
            // If a file name was passed in, use the passed in filename(s) instead of the
            // default ones above
            fileNames = new String[args.length];
            for (int i = 0; i < args.length; i++) {
                fileNames[i] = args[i];
            }
        } else {
            try {
                DataGenerator.main(new String[] {});
                // At least try to start with my default files if no filename was passed
            } catch (Exception e) {

            }
        }

        // Determine the length of the longest sorting algorithm name
        // for the calculation of the table formatting dimensions
        int maxNameLength = 0;
        for (Sorter alg : algorithms) {
            if (alg.getName().length() > maxNameLength) {
                maxNameLength = alg.getName().length();
            }
        }
        // Create a format for the table printout
        String formatString = "| %-" + Math.max(maxNameLength, 22) + "s | %13s | %13s |%n";

        // This Layout describes one "|" and one space character " " separating each
        // cell in the table, Interlaced by one string of length maxNameLength (holds
        // the algoritms names) and two 12-character strings (the number of
        // comparisons and exchanges respectively)
        // Note: (Assumng we won't go greater than 10 trillion with the 12 digit
        // hard-code)

        // Loop for each file we want to test these sorts on
        // (just in case we entered multiple file names)
        for (String fileName : fileNames) {

            // List the current file
            System.out.println("\nUsing data from file: " + fileName + "...");

            // Print the table header
            // Here, we print out the specific number of "-" characters as needed... 
            System.out.println("-".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 13 + 2)); // Horizontal Bar
            System.out.format(formatString, "Sorting Algorithm", "# Comparisons", "# Exchanges");
            System.out.println("=".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 13 + 2));
            // To explain these numbers, we need to use exactly 3 spaces for the " | ", 2 spaces for 
            // either " |" or "| " at end & beginning, and 12 spaces for both the comparison 
            // and exchange number columns

            // We want to run this test on all of our algorithms
            for (Sorter algorithm : algorithms) {
                // Make a new copy for each sort, in case we mutated data from a previous sort
                int[] theArray = DataGenerator.getArrayFromFile(fileName);

                // Run the Sorting Algorithm and record of the Comparisons and Exchanges
                SortResult result = algorithm.sort(theArray);
                // Double Check the array is sorted correctly
                int[] sortedArr = result.sortedArray();
                for (int i = 0; i < sortedArr.length - 1; i++) {
                    // If the current element is greater than the next element, it is not sorted
                    if (sortedArr[i] > sortedArr[i + 1]) {
                        throw new IllegalStateException(
                                "\n" + algorithm.getName() + " failed to sort the list correctly");
                    }
                }

                // Print out the Name, and the results
                System.out.format(formatString, algorithm.getName(), result.numberOfComparisons(),
                        result.numberOfExchanges());

                // int[] sortedArray = result.sortedArray();
                // If we want to actually do something with the sorted array later, here it is!
                // Defined in this line above, an int[] "sortedArray", for later use / analysis
            }

            // Print the last line of the table
            System.out.println("-".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 13 + 2));
        }

        // To get better imperical averages, we could test over many random trials...
        int RANDOM_TEST_COUNT = 5000; // Average over this many trials (Set to 0 if you dont want to do this)
        int RANDOM_TEST_SIZE = 2000; // Lists up to this size N, such as [1,2,3,4,5,6,7,8,9,10] for N=10

        // Only do this if requested, and we didn't want to run a passed filename
        if (RANDOM_TEST_COUNT > 0 && args.length == 0) {
            // Lets test the algorithms over TEST_COUNT random sorts,
            // with each sort being TEST_SIZE items with values 1-TEST_SIZE
            System.out.println("\nAveraging over " + RANDOM_TEST_COUNT
                    + " trials of randomly shuffled lists of values [1-" + RANDOM_TEST_SIZE + "]...");
            formatString = "| %-" + Math.max(maxNameLength, 22) + "s | %13s | %11s | %13s |";
            System.out.println("-".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 11 + 3 + 13 + 2)); // Horizontal
                                                                                                            // Bar for
                                                                                                            // this
                                                                                                            // table
            System.out.format(formatString + "\n", "Sorting Algorithm Name", "# Comparisons", "# Exchanges",
                    "Duration (ns)");
            System.out.println("=".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 11 + 3 + 13 + 2));
            for (Sorter algorithm : algorithms) {
                // We'll keep track of the cumulative averages
                double avgComparisons = 0.0;
                double avgExchanges = 0.0;
                double avgTime = 0.0;
                for (int trialNum = 0; trialNum < RANDOM_TEST_COUNT; trialNum++) {
                    // For each trial, lets generate a new random list
                    int[] newArray = new int[RANDOM_TEST_SIZE];
                    for (int i = 0; i < RANDOM_TEST_SIZE; i++) {
                        newArray[i] = i + 1; // To get 1,2,3,4,5,.....,1998,1999,2000 for N=2000
                    }
                    newArray = DataGenerator.FisherYatesShuffle(newArray);

                    // Lets sort the array based on the algorithm, and time it in nanoseconds
                    long totalTime = System.nanoTime();
                    SortResult result = algorithm.sort(newArray);
                    totalTime = System.nanoTime() - totalTime;

                    // Double Check the array is sorted correctly
                    int[] sortedArr = result.sortedArray();
                    for (int i = 0; i < sortedArr.length - 1; i++) {
                        // If the current element is greater than the next element, it is not sorted
                        if (sortedArr[i] > sortedArr[i + 1]) {
                            throw new IllegalStateException("\n" + algorithm.getName() + " failed to sort the list!");
                        }
                    }

                    // Update averages using cumulative average
                    avgComparisons += (result.numberOfComparisons() - avgComparisons) / (trialNum
                            + 1.0);
                    avgExchanges += (result.numberOfExchanges() - avgExchanges) / (trialNum +
                            1.0);
                    avgTime += (totalTime - avgTime) / (trialNum + 1.0);

                    // Only print out when the percent meaningfully changes so we're
                    // not printing the same line a million times if its slow
                    if (RANDOM_TEST_COUNT < 1000 || (trialNum % Math.max(1, RANDOM_TEST_COUNT / 1000) == 0)) {
                        // Equivelant to \r
                        System.out.print("\u001b[2K\u001b[G");

                        String progress = String.format("[%d/%d] (%d%%)", trialNum + 1,
                                RANDOM_TEST_COUNT,
                                (int) ((trialNum + 1) * 100.0 / RANDOM_TEST_COUNT));
                        System.out.format(formatString + " %s", algorithm.getName(), (int) avgComparisons,
                                (int) avgExchanges, (int) avgTime, progress);
                        System.out.flush();
                    }
                }
                // Equivelant to \r
                System.out.print("\u001b[2K\u001b[G");
                System.out.format(formatString + "\n", algorithm.getName(), (int) avgComparisons, (int) avgExchanges,
                        (int) avgTime);
            }
            System.out.println("-".repeat(2 + Math.max(maxNameLength, 22) + 3 + 13 + 3 + 11 + 3 + 13 + 2));
        }
        System.out.println("");
    }
}