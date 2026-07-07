import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class DataGenerator {

    // This method will generate an array of sequential integers from [1, 2, 3, ..., size]
    public static int[] generateSequentialArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i + 1;
        }
        return array;
    }
    
    // A method for grabbing the int[] described by the files we are passing in
    public static int[] getArrayFromFile(String fileName) {
        // Read in the file, and convert it into an int[] for later sorting
        try {
            // Open the file
            File file = new File(fileName);

            // Read in the long line of numbers from the file
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            scanner.close();

            // Split up that one long String into an array of Strings, each representing
            // the number the int should become after parsing the string
            String[] parts = line.split(" ");

            // Create an array of ints based off the read-in String array
            int[] unsortedList = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                unsortedList[i] = Integer.parseInt(parts[i]);
            }

            return unsortedList;

        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurred: The file " + fileName
                    + " was not found.  Pass in a valid file name as a command line argument.");
        }
    }
    
    // Running this main method will generate the datasets to the same directory as this file is running from
    public static void generateDatasets() throws Exception {

        FileWriter out;

        // If the directory GeneratedDatasets does not exist, create it
        File dir = new File("GeneratedDatasets");
        if (!dir.exists()) {
            dir.mkdir();
        }

        // 1) numbers 1–10
        out = new FileWriter("GeneratedDatasets/Numbers_1_to_10.txt");
        int h = 1;
        out.write("" + h);
        for (int i = 2; i <= 10; i++) {
            out.write(" " + i);
        }
        out.close();

        // 2) numbers 10–1
        out = new FileWriter("GeneratedDatasets/Numbers_10_to_1.txt");
        h = 10;
        out.write("" + h);
        for (int i = 9; i >= 1; i--) {
            out.write(" " + i);
        }
        out.close();

        // 3) numbers 1–10 (evens)
        out = new FileWriter("GeneratedDatasets/Numbers_Even_1_to_10.txt");
        h = 2;
        out.write("" + h);
        for (int i = 4; i <= 10; i += 2) {
            out.write(" " + i);
        }
        out.close();

        // 4) numbers 1–2000
        out = new FileWriter("GeneratedDatasets/Numbers_1_to_2000.txt");
        h = 1;
        out.write("" + h);
        for (int i = 2; i <= 2000; i++) {
            out.write(" " + i);
        }
        out.close();

        // 5) numbers 2000–1
        out = new FileWriter("GeneratedDatasets/Numbers_2000_to_1.txt");
        h = 2000;
        out.write("" + h);
        for (int i = 1999; i >= 1; i--) {
            out.write(" " + i);
        }
        out.close();

        // 6) 2000 random numbers (range of possible values 1–10,000)
        out = new FileWriter("GeneratedDatasets/Random_2000_Numbers.txt");
        h = (int)(Math.random() * 10000) + 1;
        out.write("" + h);
        for (int i = 1; i < 2000; i++) {
            // duplicates are allowed
            int n = (int)(Math.random() * 10000) + 1;
            out.write(" " + n);
        }
        out.close();

        //(7) The unique numbers 1 through 10 in a random order (shuffle the numbers 1–10)
        out = new FileWriter("GeneratedDatasets/Shuffled_1_to_10_Numbers.txt");
        int[] arr = new int[10];
        for (int i = 0; i < 10; i++) {
            arr[i] = i + 1;
        }
        arr = FisherYatesShuffler.shuffleArray(arr).shuffledArray();
        out.write("" + arr[0]);
        for (int i = 1; i < arr.length; i++) {
            out.write(" " + arr[i]);
        }
        out.close();

        //(8) The unique numbers 1 through 2000 in a random order (shuffle the numbers 1–2000)
        out = new FileWriter("GeneratedDatasets/Shuffled_1_to_2000_Numbers.txt");
        arr = new int[2000];
        for (int i = 0; i < 2000; i++) {
            arr[i] = i + 1;
        }
        arr = FisherYatesShuffler.shuffleArray(arr).shuffledArray();
        out.write("" + arr[0]);
        for (int i = 1; i < arr.length; i++) {
            out.write(" " + arr[i]);
        }
        out.close();
    }
}
