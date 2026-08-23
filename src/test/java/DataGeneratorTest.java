import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataGeneratorTest {

    // --- 1. TESTS FOR generateSequentialArray ---

    @Test
    public void testGenerateSequentialArrayNormal() {
        int[] result = DataGenerator.generateSequentialArray(5);
        int[] expected = {1, 2, 3, 4, 5};
        Assertions.assertArrayEquals(expected, result, "Should generate numbers 1 to N sequentially.");
    }

    @Test
    public void testGenerateSequentialArrayEmpty() {
        int[] result = DataGenerator.generateSequentialArray(0);
        Assertions.assertEquals(0, result.length, "An array of size 0 should be empty.");
    }


    // --- 2. TESTS FOR getArrayFromFile & File I/O ---

    @Test
    public void testGetArrayFromFileCorrectlyParses(@TempDir Path tempDir) throws Exception {
        // Create a fake input file in our safe JUnit sandbox
        Path tempFile = tempDir.resolve("test_input.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("10 20 30 40 50");
        }

        int[] result = DataGenerator.getArrayFromFile(tempFile.toString());
        int[] expected = {10, 20, 30, 40, 50};
        Assertions.assertArrayEquals(expected, result, "Should read and correctly parse numbers split by spaces.");
    }

    @Test
    public void testGetArrayFromFileThrowsExceptionWhenMissing() {
        // Attempting to read a file that doesn't exist
        Assertions.assertThrows(RuntimeException.class, () -> {
            DataGenerator.getArrayFromFile("this_file_does_not_exist_xyz.txt");
        }, "Should wrap FileNotFoundException into a RuntimeException.");
    }


    // --- 3. TESTS FOR generateDatasets ---

    @Test
    public void testGenerateDatasetsCreatesAllExpectedFiles() throws Exception {
        // The generateDatasets method targets a specific folder structure: "target/generated-txt-datasets"
        // Let's call it to make sure the expected output documents exist and have data
        DataGenerator.generateDatasets();

        String[] expectedFiles = {
            "target/generated-txt-datasets/Ascending_Numbers_1_to_10.txt",
            "target/generated-txt-datasets/Descending_Numbers_10_to_1.txt",
            "target/generated-txt-datasets/Ascending_Numbers_1_to_2000.txt",
            "target/generated-txt-datasets/Descending_Numbers_2000_to_1.txt",
            "target/generated-txt-datasets/Random_2000_Numbers.txt",
            "target/generated-txt-datasets/Shuffled_Numbers_1_to_10.txt",
            "target/generated-txt-datasets/Shuffled_Numbers_1_to_2000.txt"
        };

        for (String filePath : expectedFiles) {
            File file = new File(filePath);
            Assertions.assertTrue(file.exists(), "Dataset file was not created: " + filePath);
            Assertions.assertTrue(file.length() > 0, "Dataset file is empty: " + filePath);
            
            // Double check it can be successfully read back into an array
            int[] elements = DataGenerator.getArrayFromFile(filePath);
            Assertions.assertTrue(elements.length > 0, "Parsed array should contain numbers.");
        }
    }


    // --- 4. TESTS FOR getStylizedAlgorithmName ---

    @Test
    public void testGetStylizedAlgorithmNameStandard() {
        // Mock a Sorter or use one of your known classes
        Sorter mockBubble = new BubbleSorter(); // Implicitly returns "Bubble Sort"
        String stylized = DataGenerator.getStylizedAlgorithmName(mockBubble);
        
        // Bubble sort emoji is 0x1FAE7 (🫧)
        String expectedEmoji = Character.toString(0x1FAE7);
        Assertions.assertTrue(stylized.contains(expectedEmoji), "Stylized name should include the correct emoji.");
        Assertions.assertTrue(stylized.contains("Bubble Sort"), "Stylized name should include the base algorithm name.");
    }

    @Test
    public void testGetStylizedAlgorithmNameUnknownFallback() {
        // Test what happens if a new or unrecognized sorter is passed
        Sorter unknownSorter = new Sorter() {
            @Override public String getName() { return "Quantum Magic Sort"; }
            @Override public SortResult sort(int[] arr) { return null; }
        };

        String stylized = DataGenerator.getStylizedAlgorithmName(unknownSorter);
        String fallbackEmoji = Character.toString(0x02754) + '\u200D'; // The (?) that appears as default
        
        Assertions.assertTrue(stylized.startsWith(fallbackEmoji), "Unknown algorithms should fall back to the question mark emoji.");
    }
}
