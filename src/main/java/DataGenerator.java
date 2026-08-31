import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataGenerator {

    // This method will generate an array of sequential integers from [1, 2, 3, ...,
    // size]
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

    // Running this main method will generate the datasets to the same directory as
    // this file is running from
    public static void generateDatasets() throws Exception {

        Path dirPath = Paths.get("target", "generated-txt-datasets");

        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // 1) numbers 1–10
        try (BufferedWriter out = Files.newBufferedWriter(dirPath.resolve("Ascending_Numbers_1_to_10.txt"))) {
            out.write("1");
            for (int i = 2; i <= 10; i++) {
                out.write(" " + i);
            }
        }

        // 2) numbers 10–1
        try (BufferedWriter out = Files.newBufferedWriter(dirPath.resolve("Descending_Numbers_10_to_1.txt"))) {
            out.write("10");
            for (int i = 9; i > 0; i--) {
                out.write(" " + i);
            }
        }

        // 3) numbers 1–2000
        try (BufferedWriter out = Files.newBufferedWriter(dirPath.resolve("Ascending_Numbers_1_to_2000.txt"))) {
            out.write("1");
            for (int i = 2; i <= 2000; i++) {
                out.write(" " + i);
            }
        }

        // 4) numbers 2000–1
        try (BufferedWriter out = Files.newBufferedWriter(dirPath.resolve("Descending_Numbers_2000_to_1.txt"))) {
            out.write("2000");
            for (int i = 1999; i > 0; i--) {
                out.write(" " + i);
            }
        }

        // 5) 2000 random numbers (duplicates allowed, range of possible values
        // 1–10,000)
        try (BufferedWriter out = Files.newBufferedWriter(dirPath.resolve("Random_2000_Numbers.txt"))) {
            out.write("" + (int) ((Math.random() * 10000) + 1));
            for (int i = 1999; i > 0; i--) {
                out.write(" " + (int) ((Math.random() * 10000) + 1));
            }
        }

        // 6) The unique numbers 1 through 10 in a random order (shuffle the numbers
        // 1–10)
        try (BufferedWriter out = Files.newBufferedWriter(dirPath.resolve("Shuffled_Numbers_1_to_10.txt"))) {
            int[] array = new int[10];
            for (int i = 1; i <= 10; i++) {
                array[i - 1] = i;
            }
            array = FisherYatesShuffler.shuffleArray(array).shuffledArray();
            out.write("" + array[0]);
            for (int i = 1; i < 10; i++) {
                out.write(" " + array[i]);
            }
        }

        // 7) The unique numbers 1 through 2000 in a random order (shuffle the numbers
        // 1–2000)
        try (BufferedWriter out = Files.newBufferedWriter(dirPath.resolve("Shuffled_Numbers_1_to_2000.txt"))) {
            int[] array = new int[2000];
            for (int i = 1; i <= 2000; i++) {
                array[i - 1] = i;
            }
            array = FisherYatesShuffler.shuffleArray(array).shuffledArray();
            out.write("" + array[0]);
            for (int i = 1; i < 2000; i++) {
                out.write(" " + array[i]);
            }
        }
    }

    public static String getStylizedAlgorithmName(Sorter algorithm) {
        String baseName = algorithm.getName(); // Will be just the text name, like "Bubble Sort"

        String emoji; // the unicode symbol that represents this algorithm

        String leadingSpace; // the extra formatting
        String trailingSpace; // space for emojis

        // Define the mapping of algorithm names to their corresponding emoji
        // (https://unicode.org/emoji/charts/full-emoji-list.html)
        switch (baseName) {
            case "Bubble Sort":
                emoji = String.valueOf(Character.toChars(0x1FAE7));
                leadingSpace = " ";
                trailingSpace = "\u3000";
                break;
            case "Cycle Sort":
                emoji = String.valueOf(Character.toChars(0x1F501)); // or 0x1F504?
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Comb Sort":
                emoji = String.valueOf(Character.toChars(0x1FAAE));
                leadingSpace = " ";
                trailingSpace = "\u3000";
                break;
            case "Cocktail Shaker Sort":
                emoji = String.valueOf(Character.toChars(0x1F378));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Gnome Sort":
                emoji = String.valueOf(Character.toChars(0x1F9CC));
                leadingSpace = " ";
                trailingSpace = "\u3000";
                break;
            case "Exchange Sort":
                emoji = String.valueOf(Character.toChars(0x1F4B1));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Selection Sort":
                emoji = String.valueOf(Character.toChars(0x1F3AF));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Double Selection Sort":
                emoji = String.valueOf(Character.toChars(0x1F3F9));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Brick Sort":
                emoji = String.valueOf(Character.toChars(0x1F9F1));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Shell Sort":
                emoji = String.valueOf(Character.toChars(0x1F41A));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Merge Sort":
                emoji = String.valueOf(Character.toChars(0x1F91D));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Insertion Sort":
                emoji = String.valueOf(Character.toChars(0x1F4E5));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Binary Insertion Sort":
                emoji = String.valueOf(Character.toChars(0x1F4E5));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "MSD Radix Sort":
                emoji = String.valueOf(Character.toChars(0x1F524));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "LSD Radix Sort":
                emoji = String.valueOf(Character.toChars(0x1F522));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Top Down Heap Sort":
                emoji = String.valueOf(Character.toChars(0x1F4D0));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Bottom Up Heap Sort":
                emoji = String.valueOf(Character.toChars(0x1F4CF));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Recursive Quick Sort":
                emoji = String.valueOf(Character.toChars(0x1FA86));
                leadingSpace = " ";
                trailingSpace = "\u3000";
                break;
            case "Iterative Quick Sort":
                emoji = String.valueOf(Character.toChars(0x1F680));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Intro Sort":
                emoji = String.valueOf(Character.toChars(0x1F39B));
                leadingSpace = " ";
                trailingSpace = "\u3000";
                break;
            case "Counting Sort":
                emoji = String.valueOf(Character.toChars(0x1F4CA));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Bucket Sort":
                emoji = String.valueOf(Character.toChars(0x1FAA3));
                leadingSpace = " ";
                trailingSpace = "\u3000";
                break;
            case "Elimination Sort":
                emoji = String.valueOf(Character.toChars(0x1F480));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Bogo Sort":
                emoji = String.valueOf(Character.toChars(0x1F3B2));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Pancake Sort":
                emoji = String.valueOf(Character.toChars(0x1F95E));
                leadingSpace = "";
                trailingSpace = "";
                break;
            case "Cosmic Ray Sort":
                emoji = String.valueOf(Character.toChars(0x1F320));
                leadingSpace = "";
                trailingSpace = "";
                break;
            default:
                emoji = String.valueOf(Character.toChars(0x02754)) + '\u200D';
                leadingSpace = "";
                trailingSpace = "";
                break;
        }

        // Better approach than checking specific environment: compute the "display"
        // width of the emoji using a Unicode-width heuristic (treat CJK and emoji
        // ranges as wide (2), combining marks as zero-width, everything else as 1).
        // This won't perfectly match every terminal/font (some render emoji as 1 or 2
        // columns differently), but it's a deterministic, cross-platform heuristic
        // that avoids ad-hoc environment checks like TERM_PROGRAM=="vscode".

        int emojiWidth = strWidth(emoji);
        final int targetEmojiCellWidth = 2; // reserve 2 columns for emoji-ish symbols

        // We want at least one space between the emoji and the algorithm name. If the
        // emoji is only 1 cell wide, add an extra padding space so names align.
        int extraPadding = Math.max(1, targetEmojiCellWidth - emojiWidth + 1);
        String padding = repeat(' ', extraPadding);

        return emoji + leadingSpace + padding + baseName + trailingSpace;
    }

    // --- Unicode width helpers (simple heuristic) ---

    private static int strWidth(String s) {
        if (s == null || s.isEmpty()) return 0;
        int width = 0;
        for (int i = 0; i < s.length(); ) {
            int cp = s.codePointAt(i);
            width += charWidth(cp);
            i += Character.charCount(cp);
        }
        return width;
    }

    private static int charWidth(int cp) {
        // 0 for control characters
        if (cp == 0) return 0;
        if (cp < 32 || (cp >= 0x7f && cp < 0xa0)) return 0;

        int type = Character.getType(cp);
        if (type == Character.NON_SPACING_MARK || type == Character.ENCLOSING_MARK) {
            return 0; // combining marks
        }

        if (isWide(cp)) return 2;
        return 1;
    }

    private static boolean isWide(int cp) {
        // A compact set of ranges that are typically rendered wide in monospace
        // terminals: CJK, Hangul, Hiragana, Katakana, and many emoji/pictograph ranges.
        // This is a heuristic and won't be perfect for every environment.
        if (cp >= 0x1100 && cp <= 0x115F) return true;
        if (cp >= 0x2329 && cp <= 0x232A) return true;
        if (cp >= 0x2E80 && cp <= 0xA4CF) return true; // includes CJK
        if (cp >= 0xAC00 && cp <= 0xD7A3) return true; // Hangul
        if (cp >= 0xF900 && cp <= 0xFAFF) return true;
        if (cp >= 0xFE10 && cp <= 0xFE19) return true;
        if (cp >= 0xFE30 && cp <= 0xFE6F) return true;
        if (cp >= 0xFF00 && cp <= 0xFF60) return true;
        if (cp >= 0xFFE0 && cp <= 0xFFE6) return true;

        // Emoji/pictograph ranges
        if (cp >= 0x1F300 && cp <= 0x1F64F) return true;
        if (cp >= 0x1F900 && cp <= 0x1F9FF) return true;
        if (cp >= 0x1F680 && cp <= 0x1F6FF) return true;
        if (cp >= 0x1F1E6 && cp <= 0x1F1FF) return true; // regional indicators
        if (cp >= 0x2600 && cp <= 0x26FF) return true;
        if (cp >= 0x2700 && cp <= 0x27BF) return true;

        return false;
    }

    private static String repeat(char c, int times) {
        if (times <= 0) return "";
        StringBuilder sb = new StringBuilder(times);
        for (int i = 0; i < times; i++) sb.append(c);
        return sb.toString();
    }

}
