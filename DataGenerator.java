import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class DataGenerator {
    //We can also get these datasets from just calling the class methods
    public static int[] GenerateDataset(String title) {
        //This just helps us generate datasets on the fly without having to read from files
        int[] arr;
        switch(title) {
            case "numbers_1_to_10.txt":
                return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            case "numbers_10_to_1.txt":
                return new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
            case "numbers_even_1_to_10.txt":
                return new int[]{2, 4, 6, 8, 10};
            case "numbers_1_to_2000.txt":
                arr = new int[2000];
                for(int i = 0; i < 2000; i++) {
                    arr[i] = i + 1;
                }
                return arr;
            case "numbers_2000_to_1.txt":
                arr = new int[2000];
                for(int i = 0; i < 2000; i++) {
                    arr[i] = 2000 - i;
                }
                return arr;
            case "random_2000_numbers.txt":
                arr = new int[2000];
                for(int i = 0; i < 2000; i++) {
                    arr[i] = (int)(Math.random() * 10000) + 1;
                }
                return arr;
            case "shuffled_1_to_10_numbers.txt":
                arr = new int[10];
                for (int i = 0; i < 10; i++) {
                    arr[i] = i + 1;
                }
                // Fisher-Yates shuffle to guarentee unique shuffle instead of just random numbers (with duplicate values)
                for (int i = arr.length - 1; i > 0; i--)
                {
                    int j = (int)(Math.random() * (i + 1)); 
                    // Swap arr[i] with arr[j]
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
                return arr;
            case "shuffled_1_to_2000_numbers.txt":
                arr = new int[2000];
                for (int i = 0; i < 2000; i++) {
                    arr[i] = i + 1;
                }
                // Fisher-Yates shuffle to guarentee unique shuffle instead of just random numbers (with duplicate values)
                for (int i = arr.length - 1; i > 0; i--)
                {
                    int j = (int)(Math.random() * (i + 1));
                    // Swap arr[i] with arr[j]
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
                return arr;
            default:
                return new int[0]; // Return an empty array if the title doesn't match any case
        }

    }

    // Use this to shuffle an array instead of sort it
    public static int[] FisherYatesShuffle(int[] array) {
        for (int i = array.length - 1; i > 0; i--)
        {
            int j = (int)(Math.random() * (i + 1));
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
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
    public static void main(String[] args) throws Exception {

        FileWriter out;

        // 1) numbers 1–10
        out = new FileWriter("numbers_1_to_10.txt");
        int h = 1;
        out.write("" + h);
        for (int i = 2; i <= 10; i++) {
            out.write(" " + i);
        }
        out.close();

        // 2) numbers 10–1
        out = new FileWriter("numbers_10_to_1.txt");
        h = 10;
        out.write("" + h);
        for (int i = 9; i >= 1; i--) {
            out.write(" " + i);
        }
        out.close();

        // 3) numbers 1–10 (evens)
        out = new FileWriter("numbers_even_1_to_10.txt");
        h = 2;
        out.write("" + h);
        for (int i = 4; i <= 10; i += 2) {
            out.write(" " + i);
        }
        out.close();

        // 4) numbers 1–2000
        out = new FileWriter("numbers_1_to_2000.txt");
        h = 1;
        out.write("" + h);
        for (int i = 2; i <= 2000; i++) {
            out.write(" " + i);
        }
        out.close();

        // 5) numbers 2000–1
        out = new FileWriter("numbers_2000_to_1.txt");
        h = 2000;
        out.write("" + h);
        for (int i = 1999; i >= 1; i--) {
            out.write(" " + i);
        }
        out.close();

        // 6) 2000 random numbers (range of possible values 1–10,000)
        out = new FileWriter("random_2000_numbers.txt");
        h = (int)(Math.random() * 10000) + 1;
        out.write("" + h);
        for (int i = 1; i < 2000; i++) {
            int n = (int)(Math.random() * 10000) + 1;
            out.write(" " + n);
        }
        out.close();

        //(7) The unique numbers 1 through 10 in a random order (shuffle the numbers 1–10)
        out = new FileWriter("shuffled_1_to_10_numbers.txt");
        int[] arr = new int[10];
        for (int i = 0; i < 10; i++) {
            arr[i] = i + 1;
        }
        arr = FisherYatesShuffle(arr);
        out.write("" + arr[0]);
        for (int i = 1; i < arr.length; i++) {
            out.write(" " + arr[i]);
        }
        out.close();

        //(8) The unique numbers 1 through 2000 in a random order (shuffle the numbers 1–2000)
        out = new FileWriter("shuffled_1_to_2000_numbers.txt");
        arr = new int[2000];
        for (int i = 0; i < 2000; i++) {
            arr[i] = i + 1;
        }
        arr = FisherYatesShuffle(arr);
        out.write("" + arr[0]);
        for (int i = 1; i < arr.length; i++) {
            out.write(" " + arr[i]);
        }
        out.close();
    }
}
